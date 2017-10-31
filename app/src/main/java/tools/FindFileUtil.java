package tools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJunLong on 2017/10/13.
 */

public class FindFileUtil {
    private FindFileUtil(){
        if(getScanState()==false) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    scan("/mnt/udisk");
//                    scan("/storage/emulated/0/");
                    isScan = false;
                }
            }).start();
        }
    }

    public static class FindFileUtilHolder{
        private static final FindFileUtil mFindFileUtil=new FindFileUtil();
    }

    public static FindFileUtil getNewInstance(){
        return FindFileUtilHolder.mFindFileUtil;
    }


    boolean isScan=false;
    //图片后缀
    public static final String[] IMAGE_EXTENSION = {".png", ".jpg", ".bmp",
            ".jpeg"};
    //音乐后缀
    public static final String[] AUDIO_EXTENSION = {".mp3"};
    //视频后缀
    public static final String[] VIDEO_EXTENSION = {".mp4"};

    //图片过滤
    static FileFilter imageFilter = new FileFilter() {

        @Override
        public boolean accept(File file) {
            if (file.isFile()) {
                for (int i = 0; i < IMAGE_EXTENSION.length; i++) {
                    if (file.getAbsolutePath().endsWith(IMAGE_EXTENSION[i].toLowerCase())) {
                        return true;
                    }
                }
            }
            return false;
        }
    };

    //声音过滤
    static FileFilter audioFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            if (file.isFile()) {
                for (int i = 0; i < AUDIO_EXTENSION.length; i++) {
                    if (file.getAbsolutePath().endsWith(AUDIO_EXTENSION[i].toLowerCase())) {
                        return true;
                    }
                }
            }
            return false;
        }
    };

    //视频过滤
    static FileFilter videoFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            if (file.isFile()) {
                for (int i = 0; i < VIDEO_EXTENSION.length; i++) {
                    if (file.getAbsolutePath().endsWith(VIDEO_EXTENSION[i].toLowerCase())) {
                        return true;
                    }
                }
            }
            return false;
        }
    };
    //文件夹过滤
    static FileFilter directoryFilter = new FileFilter() {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            return false;
        }

    };
    //文件过滤
    static FileFilter fileFilter = new FileFilter() {

        @Override
        public boolean accept(File file) {
            if (file.isFile()) {
                return true;
            }
            return false;
        }

    };

    /**
     * 扫描某个指定路径下的文件
     *
     * @param path
     */
    private void scan(String path) {
        File temp = new File(path);
        this.scan(temp);
    }

    //这4个集合变量,在文件过多时,极易溢出
    List<File> allFiles = new ArrayList();//所有文件
    List<File> allImages = new ArrayList();//所有图片
    List<File> allAudios = new ArrayList();//所有音乐
    List<File> allVideos = new ArrayList();//所有视频
    //扫描并筛选出对应文件
    private void scan(File path) {
        if (isScan = false) {
            isScan = true;
        }
        if (path.isDirectory()) {
            File[] childfiles2=path.listFiles();
            if (childfiles2 != null && childfiles2.length > 0) {
                for (File childFolder : childfiles2) {
                    scan(childFolder);
                }
            }
            childfiles2=null;
        } else {
            //判断单个文件是否是符合过滤规则的文件
            if (imageFilter.accept(path)) {
                allImages.add(path);
            } else if (audioFilter.accept(path)) {
                allAudios.add(path);
            } else if (videoFilter.accept(path)) {
                allVideos.add(path);
            }
            allFiles.add(path);
        }
    }


    public List getScanResult() {
        return allFiles;
    }

    public static final String SCAN_RESULT = "scan_result";

    /**
     * 要求返回所有文件信息的扫描结果
     * @param handler
     */
    public void getScanResult(Handler handler,long delaytime) {//通过钩子handler把最新扫描的数据传回去让界面处理
        Message message = handler.obtainMessage(1);
        List handlerList = (List) message.getData().getSerializable(SCAN_RESULT);
        if (handlerList == null) {
            handlerList = new ArrayList<>();
        }
        if (handlerList.size() < allFiles.size()) {//allFiles有更新
            Bundle newbBundle = new Bundle();
            newbBundle.putSerializable(SCAN_RESULT, (Serializable) allFiles);
            message.setData(newbBundle);

            handler.sendMessageDelayed(message,delaytime);
        } else if (handlerList.size() == allFiles.size()) {//没有更新

        }
    }

    /**
     * 要求返回所有图片文件信息的扫描结果
     * @param handler
     */
    public void getImageScanResult(Handler handler,long delaytime) {//通过钩子handler把最新扫描的数据传回去让界面处理
        Message message = handler.obtainMessage(2);
        List handlerList = (List) message.getData().getSerializable(SCAN_RESULT);
        if (handlerList == null) {
            handlerList = new ArrayList<>();
        }
        if (handlerList.size() < allImages.size()) {//allFiles有更新
            Bundle newbBundle = new Bundle();
            newbBundle.putSerializable(SCAN_RESULT, (Serializable) allImages);
            message.setData(newbBundle);

            handler.sendMessageDelayed(message,delaytime);

        } else if (handlerList.size() == allImages.size()) {//没有更新

        }
    }

    /**
     * 要求返回所有声音文件信息的扫描结果
     * @param handler
     */
    public void getAudioScanResult(Handler handler,long delaytime) {//通过钩子handler把最新扫描的数据传回去让界面处理
        Message message = handler.obtainMessage(3);
        List handlerList = (List) message.getData().getSerializable(SCAN_RESULT);
        if (handlerList == null) {
            handlerList = new ArrayList<>();
        }
        if (handlerList.size() < allAudios.size()) {//allFiles有更新
            Bundle newbBundle = new Bundle();
            newbBundle.putSerializable(SCAN_RESULT, (Serializable) allAudios);
            message.setData(newbBundle);

            handler.sendMessage(message);

        } else if (handlerList.size() == allAudios.size()) {//没有更新

        }
    }

    /**
     * 要求返回所有视频文件信息的扫描结果
     * @param handler
     */
    public void getVideoScanResult(Handler handler,long delaytime) {//通过钩子handler把最新扫描的数据传回去让界面处理
        Message message = handler.obtainMessage(4);
        List handlerList = (List) message.getData().getSerializable(SCAN_RESULT);
        if (handlerList == null) {
            handlerList = new ArrayList<>();
        }
        if (handlerList.size() < allVideos.size()) {//allFiles有更新
            Bundle newbBundle = new Bundle();
            newbBundle.putSerializable(SCAN_RESULT, (Serializable) allVideos);
            message.setData(newbBundle);

            handler.sendMessageDelayed(message,delaytime);

        } else if (handlerList.size() == allVideos.size()) {//没有更新

        }
    }


    /**
     * 返回当前扫描状态 true还在扫描 false停止扫描
     * @return
     */
    public boolean getScanState(){
        return isScan;
    }

}
