//package observer;
//
//import android.content.Context;
//import android.database.ContentObserver;
//import android.os.Handler;
//
//import tools.SDScanUtils;
//
///**
// * Created by PanJunLong on 2017/9/29.
// */
//
//public class ProviderObserver  extends ContentObserver {
//    Context mContext;
//    Handler mHandler;
//
//    public ProviderObserver(Handler handler) {
//        super(handler);
//        mHandler=handler;
//    }
//
//    public ProviderObserver(Context context,Handler handler) {
//        this(handler);
//        mContext=context;
//    }
//
//    @Override
//    public void onChange(boolean selfChange) {
//        new SDScanUtils(mContext,mHandler).getSDPictureUris(mHandler);
//    }
//}
