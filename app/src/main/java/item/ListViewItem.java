package item;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.setproject.bilifo.mypictrue.R;

import bean.ImageInfo;

/**
 * Created by PanJunLong on 2017/10/19.
 */

public class ListViewItem extends ItemXmlClass<ImageInfo> {

    ImageView imageview;
    TextView textview;


    public ListViewItem(Context context){
        super(context);
    }

    @Override
    public void initView(View rootview) {
        imageview = rootview.findViewById(R.id.fragment_list_item_imageview);
        textview =rootview.findViewById(R.id.fragment_list_item_text);
    }

    @Override
    public void setData(ImageInfo data) {
        Glide.with(getContext())
                .load(data.getPath()).error(R.mipmap.ic_launcher).override(300,300).skipMemoryCache(true).into(imageview);
        textview.setText(data.getName());
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_list_item;
    }


}
