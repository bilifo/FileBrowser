package fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.setproject.bilifo.mypictrue.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapter.ListAdapter;
import bean.ImageInfo;
import data.ImageInfoData;
import tools.FindFileUtil;
import tools.RefreshLayout;

//显示所有文件
public class ListImages extends BaseFragment {
    ListView list;
    ListAdapter mAdapter;

    Button bt;
    TextView tv;
    ImageInfoData infodata;
    List<ImageInfo> alldata =new ArrayList<>();

    RefreshLayout refreshlayout;

    FindFileUtil findFileUtil2;


    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                List<File> files= (List<File>) msg.getData().getSerializable(FindFileUtil.SCAN_RESULT);
                alldata =ImageInfoData.FilesToImageInfos(files);
                mAdapter.updataAllDatas(alldata);
            }
            if(msg.what==2){
                List<File> files= (List<File>) msg.getData().getSerializable(FindFileUtil.SCAN_RESULT);
                alldata =ImageInfoData.FilesToImageInfos(files);
                mAdapter.updataAllDatas(alldata);
            }
            if(msg.what==3){
                List<File> files= (List<File>) msg.getData().getSerializable(FindFileUtil.SCAN_RESULT);
                alldata =ImageInfoData.FilesToImageInfos(files);
                mAdapter.updataAllDatas(alldata);
            }
            if(msg.what==4){
                List<File> files= (List<File>) msg.getData().getSerializable(FindFileUtil.SCAN_RESULT);
                alldata =ImageInfoData.FilesToImageInfos(files);
                mAdapter.updataAllDatas(alldata);
            }
        }
    };

    @Override
    void initData() {
//        alldata = new SDScanUtils(getContext()).getSDPictureUris();
        findFileUtil2=FindFileUtil.getNewInstance();
        mAdapter = new ListAdapter(getContext(), alldata);
        list.setAdapter(mAdapter);
        tv.setText("" + "text");
    }


    @Override
    void initView(View view) {
        list = (ListView) view.findViewById(R.id.fragment_list_list);
        bt = (Button) view.findViewById(R.id.fragment_list_bt);
        tv = (TextView) view.findViewById(R.id.fragment_list_tv);

        refreshlayout = (RefreshLayout) view.findViewById(R.id.fragment_list_sellout);
    }

    @Override
    void initListener() {
        list.setOnItemClickListener(mOnItemClickListener);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findFileUtil2.getScanResult(mHandler,0);
            }
        });

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                refreshlayout.setRefreshing(true);

                Log.d("usb", "********************");

                // 结束
                refreshlayout.setRefreshing(false);
            }
        });


        // 加载监听器
        refreshlayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {

                Toast.makeText(getContext(), "load", Toast.LENGTH_SHORT).show();


            }
        });

}

    OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {


        }
    };

    public void setTextViewContent(String str) {
        tv.setText(str);
    }

    @Override
    int getRootViewId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_list;
    }


}
