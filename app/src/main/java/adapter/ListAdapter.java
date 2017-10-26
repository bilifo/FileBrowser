package adapter;

import android.content.Context;

import java.util.List;

import bean.ImageInfo;
import item.ItemXmlClass;
import item.ListViewItem;

public class ListAdapter extends ComAdapter2<ImageInfo> {
	public ListAdapter(Context context, List<ImageInfo> mData) {
		super(context, mData);
	}

    @Override
    public ItemXmlClass getItemViewImpl(Context context) {
        return new ListViewItem(context);
    }

}
