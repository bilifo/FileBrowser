//package fragment;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//import com.setproject.bilifo.mypictrue.R;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import adapter.ComPagerAdapter;
//import bean.ImageInfo;
//import data.ImageInfoData;
//import tools.FindFileUtil;
//
//public class ShowImages3 extends BaseFragment3 {
//    ViewPager viewpager;
//
//    Button bt1, bt2, bt3;
//
//    private FragmentManager manager;
//
//    //    private ShowAdapter mAdapter;
//    private ComPagerAdapter mAdapter2;
//
//    List<ImageInfo> alldatas = new ArrayList<>();
//    List<ImageInfo> imagedatas = new ArrayList<>();
//    List<ImageInfo> audiodatas = new ArrayList<>();
//    List<ImageInfo> vediodatas = new ArrayList<>();
//
//    FindFileUtil findFileUtil2;
//
//
//    BaseFragment3<List<ImageInfo>>
//            fragmentAll ,
//            fragmentImage ,
//            fragmentAudio ,
//            fragmentVedio ;
//
//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            List<File> files = (List<File>) msg.getData().getSerializable(FindFileUtil.SCAN_RESULT);
//
//            if (msg.what == 1) {
//                alldatas = ImageInfoData.FilesToImageInfos(files);
//                fragmentAll.setData(alldatas);
//                findFileUtil2.getScanResult(mHandler, 10000);
//            }
//            if (msg.what == 2) {
//                imagedatas = ImageInfoData.FilesToImageInfos(files);
//                fragmentImage.setData(imagedatas);
//                findFileUtil2.getImageScanResult(mHandler, 10000);
//            }
//            if (msg.what == 3) {
//                audiodatas = ImageInfoData.FilesToImageInfos(files);
//                fragmentAudio.setData(audiodatas);
//                findFileUtil2.getAudioScanResult(mHandler, 10000);
//            }
//            if (msg.what == 4) {
//                vediodatas = ImageInfoData.FilesToImageInfos(files);
//                fragmentVedio.setData(vediodatas);
//                findFileUtil2.getVideoScanResult(mHandler,10000);
//            }
//
//        }
//
//    };
//
//    public ShowImages3(Context context) {
//        super(context);
//    }
//
//
//    void initListener() {
//        bt1.setOnClickListener(onClickListener);// �б�
//        bt2.setOnClickListener(onClickListener);// ����
//        bt3.setOnClickListener(onClickListener);// play
//
//    }
//
//    void initView(View rootview2) {
//
//        fragmentAll = new ListImagesCopy3(getContext());
//        fragmentImage = new ListImagesCopy3(getContext());
//        fragmentAudio = new ListImagesCopy3(getContext());
//        fragmentVedio = new ListImagesCopy3(getContext());
//        findFileUtil2 = FindFileUtil.getNewInstance();
//        findFileUtil2.getScanResult(mHandler, 1000);
//        findFileUtil2.getImageScanResult(mHandler, 1000);
//        findFileUtil2.getAudioScanResult(mHandler, 1000);
//        findFileUtil2.getVideoScanResult(mHandler, 1000);
//
//        viewpager = (ViewPager) rootview2
//                .findViewById(R.id.fragment_show_viewpager);
//        manager = getChildFragmentManager();
//        bt1 = (Button) rootview2.findViewById(R.id.fragment_show_button1);
//        bt2 = (Button) rootview2.findViewById(R.id.fragment_show_button2);
//        bt3 = (Button) rootview2.findViewById(R.id.fragment_show_button3);
//
//    }
//
//    @Override
//    void initData(Object data) {
//
//        mAdapter2 = new ComPagerAdapter(manager);
//        mAdapter2.addFragment(fragmentAll, "fileslist")
//                .addFragment(fragmentImage, "imagelist")
//                .addFragment(fragmentAudio, "audiolist")
//                .addFragment(fragmentVedio, "videolist");
//
//        viewpager.setAdapter(mAdapter2);
//    }
//
//    OnClickListener onClickListener = new OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == bt1.getId()) {
//                manager.beginTransaction()
//                        .replace(R.id.main_fragment_holder, new ListImagesCopy3(getContext()))
//                        .commit();
//            }
//            if (v.getId() == bt2.getId()) {
//
//            }
//            if (v.getId() == bt3.getId()) {
//
//            }
//        }
//    };
//
//    int getRootViewId() {
//        // TODO Auto-generated method stub
//        return R.layout.fragment_show;
//    }
//
//}
