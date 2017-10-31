package fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tools.MyLog;

/**
 * 这个是和ListImagesCopy3  ShowImages3一起的.当时的目的是探究BaseFragment赋初始数据分离的可能initData(T data)
 * 但由于Context==null而陷入困境
 *
 * @param <T>
 */
public abstract class BaseFragment3<T> extends Fragment {

    abstract void initView(View view);

    abstract void initData(T data);

    abstract void initListener();

    abstract int getRootViewId();// 返回rootview的id，必须实现

    //*****构造方法****
//    public BaseFragment3(Context context) {
//        this.mContext = context;
//    }

    //*****内部属性和get/set方法
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    private View rootview;

    public View getRootView() {
        return rootview;
    }

    private T data;

    public void setData(T data) {
        this.data = data;
        mHandler.removeMessages(0);
        mHandler.sendMessage(mHandler.obtainMessage(0));
    }

    public T getData() {
        return data;
    }

    boolean isExecOnresume2=false;
    //用来获得当前生命周期
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (data != null && (isExecOnresume2==true)) {
                        mHandler.removeMessages(0);
                        initData(data);
                    } else {
                        mHandler.removeMessages(0);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(0),1000);
                    }
                    break;
            }
        }
    };

    //******生命周期********

    /**
     * 生命周期的开始
     * 当fragment第一次附加到context执行.
     * 后接onCreate
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        MyLog.showLog(getActivity(), "---" + "onAttach:start" + "---");
        mContext = activity.getBaseContext();//并不可靠,有时会返回null

        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onCreateView:start" + "---");
        if (getRootViewId() == 0) {
            try {
                throw new Throwable("getRootViewId() return 0.");
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            rootview = inflater.inflate(getRootViewId(), container, false);
            initView(rootview);
            initListener();
        }
        return rootview;
    }


    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        isExecOnresume2=true;
    }



    /**
     * 当用户可以看到片段时调用。这通常与活动有关。内部包含调用activity的onStart。
     * 如果用户切换到home,接onPause()
     */
    @Override
    public void onResume() {
        MyLog.showLog(getActivity(), "---" + "onResume:start" + "---");
        super.onResume();

        mHandler.removeMessages(0);
        mHandler.sendMessage(mHandler.obtainMessage(0));
    }




}
