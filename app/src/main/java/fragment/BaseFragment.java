package fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tools.MyLog;

public abstract class BaseFragment<T> extends Fragment implements MyLog.ThisClassCloseLog {

    abstract void initView(View view);

    abstract void initData();

    abstract void initListener();

    abstract int getRootViewId();// 返回rootview的id，必须实现

    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    private View rootview;

    public View getRootView() {
        return rootview;
    }


    @Override
    public void onAttach(Activity activity) {
        MyLog.showLog(getActivity(), "---" + "onAttach:start" + "---");
        mContext = activity.getBaseContext();
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onCreate:start" + "---");
        super.onCreate(savedInstanceState);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onActivityCreated:start" + "---");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        MyLog.showLog(getActivity(), "---" + "onStart:start" + "---");
        super.onStart();
    }

    @Override
    public void onResume() {
        MyLog.showLog(getActivity(), "---" + "onResume:start" + "---");
        super.onResume();
        initData();
    }

    @Override
    public void onPause() {
        MyLog.showLog(getActivity(), "---" + "onPause:start" + "---");
        super.onPause();

    }

    @Override
    public void onStop() {
        MyLog.showLog(getActivity(), "---" + "onStop:start" + "---");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        MyLog.showLog(getActivity(), "---" + "onDestroyView:start" + "---");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        MyLog.showLog(getActivity(), "---" + "onDestroy:start" + "---");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        MyLog.showLog(getActivity(), "---" + "onDetach:start" + "---");
        super.onDetach();
    }

}