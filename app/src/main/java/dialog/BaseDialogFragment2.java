package dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tools.MyLog;

/**
 * 这是对前一个版本,在设置数据的方式上修改.使用了handler来通知当前处于哪个生命周期
 * <p>
 * * 一个带关闭按钮的通用动态dialogfragmnet模板.
 * (注意,生命周期是从show开始的.getdialog在除了生命周期以外的地方使用,可能会引起空指针问题.)
 * 使用方式:
 * 1/子类实现该类,实现各个方法
 * 2/子类的initIncludeView是返回要填充内容的View
 * 3/new 并show
 * <p>
 * <p>
 * 生命周期:
 * <p>
 * initIncludeView(在onCreateView中)--->initListener(在onCreateView中)--->initData(在onResume中)
 *
 * @param <T> 数据的类型
 */
public abstract class BaseDialogFragment2<T> extends DialogFragment implements MyLog.ThisClassCloseLog {
    boolean isExternalClick = false;//是否允许接收外部的点击而消失.依需要修改

    /**
     * 实现待添加的VIew
     */
    abstract View initIncludeView(Context mContext);

    /**
     * 初始化需要赋值给各个控件的数据
     */
    abstract void initData();

    /**
     * 正式的把数据分配给各个控件
     */
    abstract void setViewData();

    abstract void initListener();

    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    private ViewGroup rootview;
    private View includeview;
    private ImageView close;

    private RelativeLayout.LayoutParams rootviewparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private RelativeLayout.LayoutParams clickviewparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private RelativeLayout.LayoutParams includeviewparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//    private T data;//数据
//    private Collection<T> dataArray;//数据集
    /**
     * dialog的总体大小比例:0~1
     */
    double DIALOG_SIZE_SCALE = 1.0;

    //用来获得当前生命周期
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initData();
                    break;
            }
        }
    };


    public BaseDialogFragment2() {

    }


    public View getIncludeview() {
        return includeview;
    }

    public ImageView getCloseView() {
        return close;
    }

//    /**
//     * 给dialog设置数据
//     *
//     * @param data
//     */
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public void setData(T data, int position) {
//        this.data = data;
//    }


    /**
     * 设置dialog大小系数
     *
     * @param scale
     */
    public void setDialogSize(double scale) {
        if (scale > 0 && scale <= 1) {
            DIALOG_SIZE_SCALE = scale;
        }
    }


    //*************继承的fragment的生命周期**********

    /**
     * 实现用户可见的界面
     * 后接onActivityCreated
     * 重写fragment的方法.
     *
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(127, 127, 127, 127)));//背景透明
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //去除标题栏
//        getDialog().getWindow().setLayout(300,200); //宽高.实测无用,定制dialog的大小,还是依据includeview的大小来吧

//        getDialog().setCanceledOnTouchOutside(isExternalClick);//设置点击dialog外部是否取消弹框

        //点击返回键不消失，需要监听OnKeyListener:
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        //**注意,getdialog在除了生命周期以外的地方使用,可能会引起空指针问题.生命周期是从show开始的

        //通过DisplayMetrics获得当前屏幕大小,并对dialog显示大小做出限制
        Window window = this.getDialog().getWindow();
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (dm2.heightPixels * DIALOG_SIZE_SCALE); // 改变的是dialog框在屏幕中的位置而不是大小
        p.width = (int) (dm2.widthPixels * DIALOG_SIZE_SCALE); // 宽度设置为屏幕的0.65
        window.setAttributes(p);

        //根布局
        rootview = new RelativeLayout(mContext);
        rootview.setLayoutParams(rootviewparams);

        //代添加布局.依赖ListViewItem

        includeview = initIncludeView(mContext);
        //当引入的布局不是相对布局,而是其他布局时,获得宽高
        if (includeview.getLayoutParams() != null) {
            ViewGroup.LayoutParams tempparams = includeview.getLayoutParams();
            includeviewparams.width = tempparams.width;
            includeviewparams.height = tempparams.height;
        }
        includeviewparams.addRule(RelativeLayout.CENTER_IN_PARENT);
        includeview.setLayoutParams(includeviewparams);

        includeview.setId(View.generateViewId());
        rootview.addView(includeview);
        //关闭按钮
        close = new ImageView(mContext);
        close.setBackgroundResource(android.R.drawable.btn_dialog);
        close.setId(View.generateViewId());
        clickviewparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        clickviewparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        close.setLayoutParams(clickviewparams);
        rootview.addView(close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootview;
    }

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
        mContext = activity.getBaseContext();
        super.onAttach(activity);
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    /**
     * 当所有保存的状态被恢复到片段的视图层次结构时调用.可以根据保存的状态进行初始化，让视图层次跟踪自己，比如当前检查复选框小部件。
     * 后接onStart
     *
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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

    /**
     * 暂停界面
     */
    @Override
    public void onPause() {
        MyLog.showLog(getActivity(), "---" + "onPause:start" + "---");
        super.onPause();

    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        MyLog.showLog(getActivity(), "---" + "onDestroy:start" + "---");
        super.onDestroy();
    }

    /**
     * 非生命周期
     * 当fragment执行加载一个动画时调用。返回Animation动画类
     *
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        MyLog.showLog(getActivity(), "---" + "onCreateAnimation:start" + "---");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    /**
     * 非生命周期,当配置（例如屏幕方向、键盘可用性及语言）发生改变时调用
     * onConfigurationChanged这个方法是用在设备配置在应用运行期间发生变化。
     * 一般由于设置变更,要让新设置生效,需要重启app.而onConfigurationChanged就是为了解决在不重启的情况下解决方案.
     * <p>
     * 由于fragment是依托于activity,要想让fragment的onConfigurationChanged生效执行,依托的activity也必须重写它的onConfigurationChanged方法
     * <p>
     * 使用方式:
     * 1/在AndroidManifest中<activity>里,添加android:configChanges="orientation|keyboardHidden"
     * 2/activity重写onConfigurationChanged.
     * 详细:https://developer.android.google.cn/guide/topics/resources/runtime-changes.html
     * <p>
     * super方法不能省去
     * 需先重写activity里同名方法
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        MyLog.showLog(getActivity(), "---" + "onConfigurationChanged:start" + "---");
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 非生命周期
     * 当整个系统在内存中处于低运行状态时，就会调用它.
     * 这个方法主要做一些清理工作,释放缓存等.清理程序员自己写的缓存
     * super方法不能省去
     */
    @Override
    public void onLowMemory() {
        MyLog.showLog(getActivity(), "---" + "onLowMemory:start" + "---");
        super.onLowMemory();
    }

    /**
     * 非生命周期
     * 当app将要因为内存不足被kill的时候，就会调用它.
     * 在onPause方法之后执行在onStop方法之前执行
     * 当用户退出,或finish退出,不会调用它.
     * <p>
     * super方法不能省去,且先执行.它会去执行每个有id的view的onSaveInstanceState方法
     * 详细:https://developer.android.google.cn/guide/components/activities.html#Lifecycle
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        MyLog.showLog(getActivity(), "---" + "onSaveInstanceState:start" + "---");
        super.onSaveInstanceState(outState);
    }

    /**
     * 非生命周期
     * 创建Context菜单时调用
     * 使用方式:
     * 1/注册长按那个view控件弹出菜单registerForContextMenu(View);
     * 当然也可以使用 view.showContextMenu();//单击直接显示Context菜单
     * 2/在onCreateContextMenu中完成菜单界面的创建
     * 3/通过onContextItemSelected来判断哪一项被点击了
     * <p>
     * 需先重写activity里同名方法
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MyLog.showLog(getActivity(), "---" + "onCreateContextMenu:start" + "---");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * 非生命周期
     * Context菜单子条目选择事件
     *
     * @param item 菜单选择item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyLog.showLog(getActivity(), "---" + "onContextItemSelected:start" + "---");
        return super.onContextItemSelected(item);
    }

    /**
     * 非生命周期
     * 创建Option菜单时调用,和onCreateContextMenu不同的是,onCreateContextMenu可以多次调用,而onCreateOptionsMenu只执行一次.
     * 主要用于权限赋予提示框
     * <p>
     * 需先重写activity里同名方法
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MyLog.showLog(getActivity(), "---" + "onCreateOptionsMenu:start" + "---");
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 当Option菜单被关闭时调用
     *
     * @param menu
     */
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        MyLog.showLog(getActivity(), "---" + "onOptionsMenuClosed:start" + "---");
        super.onOptionsMenuClosed(menu);
    }

    /**
     * 在准备显示Option菜单前调用
     * <p>
     * 需先重写activity里同名方法
     *
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MyLog.showLog(getActivity(), "---" + "onPrepareOptionsMenu:start" + "---");
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * 销毁Option菜单时调用
     */
    @Override
    public void onDestroyOptionsMenu() {
        MyLog.showLog(getActivity(), "---" + "onDestroyOptionsMenu:start" + "---");
        super.onDestroyOptionsMenu();
    }

    /**
     * 非生命周期
     * option菜单子条目选择事件
     *
     * @param item 菜单选择item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MyLog.showLog(getActivity(), "---" + "onOptionsItemSelected:start" + "---");
        return super.onOptionsItemSelected(item);
    }

    /**
     * 这是API 26的新方法,包括接下的onPictureInPictureModeChanged
     * 多窗口模式变化时进行通知(进入或退出多窗口)
     * 需先重写activity里同名方法
     * <p>
     * 详细:https://segmentfault.com/a/1190000007249755
     *
     * @param isInMultiWindowMode
     */
    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        MyLog.showLog(getActivity(), "---" + "onMultiWindowModeChanged:start" + "---");
        super.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    /**
     * 这是API 26的新方法,包括接下的onMultiWindowModeChanged
     * 画中画模式变化时进行通知（进入或退出画中画模式）
     * 需先重写activity里同名方法
     *
     * @param isInPictureInPictureMode
     */
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        MyLog.showLog(getActivity(), "---" + "onPictureInPictureModeChanged:start" + "---");
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    /**
     * 非生命周期
     * 当fragment在show和hide间转换时调用
     *
     * @param hidden true表示隐藏,false表示其他
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        MyLog.showLog(getActivity(), "---" + "onHiddenChanged:start" + "---");
        super.onHiddenChanged(hidden);
    }

    /**
     * API 23(安卓6.0)
     * 由于6.0后权限系统全面修改了,对于一些危险权限,不仅要在清单文件中注册,在app运行时,还需要用户动态确认授权
     * 回调请求权限的结果
     * 使用方式:
     * 1/AndroidManifest注册权限
     * 2/使用requestPermissions(String[], int)动态请求权限,checkSelfPermission()来检查权限是否成功
     * <p>
     * 详细:https://developer.android.google.cn/guide/topics/security/permissions.html
     *
     * @param requestCode  请求结果
     * @param permissions  所需权限数组
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MyLog.showLog(getActivity(), "---" + "onRequestPermissionsResult:start" + "---");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * API 26
     * <p>
     * 必须调用超类实现
     *
     * @param context
     * @param attrs
     * @param savedInstanceState
     */
    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onInflate:start" + "---");
        super.onInflate(context, attrs, savedInstanceState);
    }


    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onInflate:start" + "---");
        super.onInflate(activity, attrs, savedInstanceState);
    }

    /**
     * 在一个界面(主Activity)通过意图跳转至多个不同子Activity上去，当子模块的代码执行完毕后再次返回主页面，将子activity中得到的数据显示在主界面/完成的数据交给主Activity处理时触发。
     * 使用方式:
     * 1/主activity调用startActivityForResult来把intent传入子activity
     * 2/子activity在oncreate中通过getIntent()获得穿过来的intent
     * 3/子activity通过setResult()把带数据的intent传回给主activity
     * 4/主activity在onActivityResult()中接收子activity传回的数据
     * 必须调用超类实现
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MyLog.showLog(getActivity(), "---" + "onActivityResult:start" + "---");
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 当一个fragment作为另一个fragmnet的子控件连接时调用.在连接fragment的onAttach之后,onCreate之前调用
     *
     * @param childFragment
     */
    @Override
    public void onAttachFragment(Fragment childFragment) {
        MyLog.showLog(getActivity(), "---" + "onAttachFragment:start" + "---");
        super.onAttachFragment(childFragment);
    }

    //*************dialogfragment自己的生命周期 或 重写fragment的方法**********

    /**
     * 内部包含调用activity的onCreate。
     * 这里可以另开一个线程进行数据的初始化操作
     * 后接onCreateView
     * 重写fragment的方法.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onCreate:start" + "---");
        super.onCreate(savedInstanceState);
        //设置dialog全屏显示
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    /**
     * 此时界面已经完成,用于检索界面或恢复状态,一般数据添加给控件可在此进行
     * 后接onViewStateRestored
     * 重写fragment的方法.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onActivityCreated:start" + "---");
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 当用户可以看到片段时调用。这通常与活动有关。内部包含调用activity的onStart。
     * 后接onResume
     * 重写fragment的方法.
     */
    @Override
    public void onStart() {
        MyLog.showLog(getActivity(), "---" + "onStart:start" + "---");
        super.onStart();
    }

    @Override
    public void onStop() {
        MyLog.showLog(getActivity(), "---" + "onStop:start" + "---");
        super.onStop();
    }

    /**
     * 当Fragment的视图被删除时调用
     */
    @Override
    public void onDestroyView() {
        MyLog.showLog(getActivity(), "---" + "onDestroyView:start" + "---");
        super.onDestroyView();
    }

    /**
     * 生命周期的起点
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        MyLog.showLog(getActivity(), "---" + "onAttach:start" + "---");
        super.onAttach(context);
    }

    /**
     * 生命周期的终点
     */
    @Override
    public void onDetach() {
        MyLog.showLog(getActivity(), "---" + "onDetach:start" + "---");
        super.onDetach();
    }

    /**
     * 生命周期
     * 在oncreate之前调用???
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MyLog.showLog(getActivity(), "---" + "onCreateDialog:start" + "---");
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        MyLog.showLog(getActivity(), "---" + "onCancel:start" + "---");
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        MyLog.showLog(getActivity(), "---" + "onDismiss:start" + "---");
        super.onDismiss(dialog);
    }


}
