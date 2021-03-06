package tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 静态:
 * 1/复制该类到工程中
 * 2/在AndroidManifest.xml中写
 * <receiver android:name="tools.UsbStateReceiver">
 * <intent-filter>
 * <action android:name="android.intent.action.MEDIA_EJECT"/>
 * .......
 * <action android:name="android.intent.action.MEDIA_MOUNTED"/>
 * <data android:scheme="file"/><!-- 这句必须要有-->
 * </intent-filter>
 * </receiver>
 * <p>
 * <p>
 * <p>
 * 动态:
 * 1/复制该类到工程中
 * 2/在activity中注册
 * IntentFilter filter = new IntentFilter();
 * filter.addAction(Intent.ACTION_MEDIA_SHARED);// 如果SDCard未安装,并通过USB大容量存储共享返回
 * filter.addAction(Intent.ACTION_MEDIA_MOUNTED);// 表明sd对象是存在并具有读/写权限
 * filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);// SDCard已卸掉,如果SDCard是存在但没有被安装
 * filter.addAction(Intent.ACTION_MEDIA_CHECKING); // 表明对象正在磁盘检查
 * filter.addAction(Intent.ACTION_MEDIA_EJECT); // 物理的拔出 SDCARD
 * filter.addAction(Intent.ACTION_MEDIA_REMOVED); // 完全拔出
 * filter.addDataScheme("file"); // 必须要有此行，否则无法收到广播.静态时，也要<data android:scheme="file" />
 * context.registerReceiver(mReceiver, filter);
 */
public class UsbStateReceiver extends BroadcastReceiver {
    public UsbStateReceiver() {
    }

    public UsbStateReceiver(Context context) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // intent.getAction());获取存储设备当前状态

        Log.i("usb", "BroadcastReceiver:" + intent.getAction());

        // intent.getData().getPath());获取存储设备路径
        Log.i("usb", "path:" + intent.getData().getPath());

        if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
            Log.i("usb", "当前是:" + intent.getAction());
//            scanconn = new MediaScannerConnection(BaseApplication.mcontext, callback);
//            scanconn.connect();

//

        } else if (intent.getAction().equals("android.intent.action.MEDIA_UNMOUNTEDD")) {
            Log.i("usb", "当前是:" + intent.getAction());
        }

    }

}


/*而getAction会获取当前状态，如下描述:

U盘插入：
intent.getAction() == android.intent.action.MEDIA_UNMOUNTED
intent.getAction() == android.intent.action.MEDIA_CHECKING
intent.getAction() == android.intent.action.MEDIA_MOUNTED
U盘拔出：
intent.getAction() == android.intent.action.MEDIA_EJECT
intent.getAction() == android.intent.action.MEDIA_UNMOUNTED
intent.getAction() == android.intent.action.MEDIA_UNMOUNTED
intent.getAction() == android.intent.action.MEDIA_REMOVED
intent.getAction() == android.intent.action.MEDIA_UNMOUNTED

SD卡插入:
intent.getAction() == android.intent.action.MEDIA_UNMOUNTED
intent.getAction() == android.intent.action.MEDIA_CHECKING
intent.getAction() == android.intent.action.MEDIA_MOUNTED
SD卡拔出:
intent.getAction() == android.intent.action.MEDIA_EJECT
intent.getAction() == android.intent.action.MEDIA_UNMOUNTED
intent.getAction() == android.intent.action.MEDIA_UNMOUNTED
intent.getAction() == android.intent.action.MEDIA_REMOVED
*/
