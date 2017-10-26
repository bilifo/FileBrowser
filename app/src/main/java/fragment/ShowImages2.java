package fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setproject.bilifo.mypictrue.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ComPagerAdapter;
import adapter.ShowAdapter;
import bean.ImageInfo;

public class ShowImages2 extends BaseFragment {
    ViewPager viewpager;

    Button bt1, bt2, bt3;

    private FragmentManager manager;

    private ShowAdapter mAdapter;
    private ComPagerAdapter mAdapter2;

    List<ImageInfo> alldatas =new ArrayList<>();
    List<ImageInfo> imagedatas =new ArrayList<>();
    List<ImageInfo> audiodatas =new ArrayList<>();
    List<ImageInfo> vediodatas =new ArrayList<>();

    List<Fragment> listlist;

    BaseFragment<List<ImageInfo>>  fragmentAll=new ListImages(),fragmentImage=new ListImages1(),fragmentAudio=new ListImages2(),fragmentVedio=new ListImages3();


//    void initData() {
//
//    }

    void initListener() {
        bt1.setOnClickListener(onClickListener);// �б�
        bt2.setOnClickListener(onClickListener);// ����
        bt3.setOnClickListener(onClickListener);// play

    }

    void initView(View rootview2) {
        viewpager = (ViewPager) rootview2
                .findViewById(R.id.fragment_show_viewpager);
        manager = getChildFragmentManager();
        bt1 = (Button) rootview2.findViewById(R.id.fragment_show_button1);
        bt2 = (Button) rootview2.findViewById(R.id.fragment_show_button2);
        bt3 = (Button) rootview2.findViewById(R.id.fragment_show_button3);

    }

    @Override
    void initData() {
        mAdapter2 = new ComPagerAdapter(manager);
        mAdapter2.addFragment(fragmentAll, "fileslist")
                .addFragment(fragmentImage, "imagelist")
                .addFragment(fragmentAudio, "audiolist")
                .addFragment(fragmentVedio, "videolist");

        viewpager.setAdapter(mAdapter2);
    }


    OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == bt1.getId()) {
                manager.beginTransaction()
                        .replace(R.id.main_fragment_holder, new ListImages())
                        .commit();
            }
            if (v.getId() == bt2.getId()) {

            }
            if (v.getId() == bt3.getId()) {

            }
        }
    };

    int getRootViewId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_show;
    }

}
