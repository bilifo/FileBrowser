package dialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.setproject.bilifo.mypictrue.R;

import java.lang.reflect.Field;
import java.util.List;

import bean.ImageInfo;

/**
 * 包含了viewpager的弹出框来显示图片.
 * <p>
 * 后续改进:
 * 定位到当前图片,而不是重新从第一张开始
 * Created by PanJunLong on 2017/10/18.
 */

public class ImageDialog extends BaseDialogFragment<List<ImageInfo>> {

    //    ImageView image;
    ViewPager viewpager;
    PagerAdapter adapter;
    List<ImageInfo> data;

    int itemposition = -1;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                case 1:
                    if (adapter != null && itemposition != -1) {
//                        goItem(itemposition);
                        viewpager.setCurrentItem(itemposition);
                        itemposition=-1;
                    } else if (adapter != null && itemposition == -1) {
//                        goItem(0);
                        viewpager.setCurrentItem(0);
                        itemposition=-1;
                    } else if (viewpager == null && itemposition != -1) {
                        mHandler.removeMessages(1);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(1), 1000);
                    } else {
                        mHandler.removeMessages(1);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(1), 1000);
                    }
                    break;
            }
        }
    };


    public ImageDialog(final Context mContext) {
        viewpager = new ViewPager(mContext);
    }

    @Override
    View initIncludeView(Context mContext) {
        return viewpager;
    }

    @Override
    void initData(final List<ImageInfo> data) {
        this.data = data;
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView image = new ImageView(getContext());
                Glide.with(getContext())
                        .load(data.get(position).getPath()).error(R.mipmap.ic_launcher).skipMemoryCache(true).into(image);
                container.addView(image);
                return image;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeViewAt(position);
            }
        };

        viewpager.setAdapter(adapter);
        mHandler.removeMessages(1);
        mHandler.sendMessage(mHandler.obtainMessage(1));
    }


    @Override
    void initListener() {

    }


    public void goItemPositin(int position) {
        itemposition = position;
        mHandler.removeMessages(1);
        mHandler.sendMessage(mHandler.obtainMessage(1));
    }

    private void goItem(int itemposition) {
        //先强制设定跳转到指定页面
        try {
            Field field = viewpager.getClass().getField("mCurItem");//参数mCurItem是系统自带的
            field.setAccessible(true);
            field.setInt(viewpager, itemposition);
        } catch (Exception e) {
            e.printStackTrace();
        }

////然后调用下面的函数刷新数据
//        adapter.notifyDataSetChanged();
//再调用setCurrentItem()函数设置一次
        viewpager.setCurrentItem(itemposition);
    }
}
