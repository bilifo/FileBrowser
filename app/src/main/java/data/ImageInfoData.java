package data;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bean.ImageInfo;

public class ImageInfoData {
    static List<ImageInfo> testpath;
    static List<Fragment> testfragment;

    static List<ImageInfo> SDImages;

    //测试用Listview数据
    public static List<ImageInfo> getTestImagePath(Context context) {
        testpath=new ArrayList();
        for(int i=0;i<100;i++){
            testpath.add(new ImageInfo());
        }
       return testpath;
    }

//    //测试用showimages的数据
//    public static List<Fragment> getTestFragments() {
//        testfragment = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            testfragment.add(new ListImages());
//        }
//        return testfragment;
//    }

    public static List<ImageInfo> FilesToImageInfos(List<File> paths){
        SDImages=new ArrayList<>();
        for(int i=0;i<paths.size();i++){
            SDImages.add(new ImageInfo(paths.get(i).getName(),paths.get(i).getAbsolutePath()));
        }
        return SDImages;
    }

    public static List<ImageInfo> FileArrayToImageInfos(List<File[]> paths){
        SDImages=new ArrayList<>();
        for(int i=0;i<paths.size();i++){
            File[] temp=paths.get(i);
            for(int j=0;j<temp.length;j++){
                SDImages.add(new ImageInfo(temp[j].getName(),temp[j].getAbsolutePath()));
            }
        }
        return SDImages;
    }

}
