package dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import item.PlayOrPauseItem;

/**
 * Created by PanJunLong on 2017/10/18.
 */

public class AudioDialog extends BaseDialogFragment {

    PlayOrPauseItem playItem;
    ViewGroup.LayoutParams params;

    @Override
    View initIncludeView(Context mContext) {
        playItem = new PlayOrPauseItem(mContext);
//        Window window = this.getDialog().getWindow();
//        DisplayMetrics dm2 = getResources().getDisplayMetrics();
//        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (dm2.heightPixels * 0.6); // 改变的是dialog框在屏幕中的位置而不是大小
//        p.width = (int) (dm2.widthPixels * 0.65); // 宽度设置为屏幕的0.65
//        window.setAttributes(p);
//
//        playItem.getRootView().setLayoutParams(new ViewGroup.LayoutParams(p.height, p.width));
        return playItem.getRootView();
    }

    @Override
    void initData(Object data) {

    }


    @Override
    void initListener() {
        playItem.setOnMadioPlayAndPauseClickListener(
                new PlayOrPauseItem.OnMadioPlayClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }, new PlayOrPauseItem.OnMadioPauseClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }
}
