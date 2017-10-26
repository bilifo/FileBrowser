package dialog;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.setproject.bilifo.mypictrue.R;

import java.util.List;

import bean.ImageInfo;

/**
 * Created by PanJunLong on 2017/10/18.
 */

public class ImageDialog extends BaseDialogFragment<List<ImageInfo>> {

    //    ImageView image;
    ViewPager viewpager;
    PagerAdapter adapter;
    List<ImageInfo> data;


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
                return view ==object;
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
    }



    @Override
    void initListener() {

    }
}
