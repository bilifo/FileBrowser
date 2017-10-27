package dialog;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bean.ImageInfo;
import item.PlayOrPauseItem;

/**
 * 后续改进:
 *      1/要与播放引擎隔离,这样在更换播放引擎框架时,就不需要改该类.
 *          --不对啊,更换引擎就是修改该类啊.该改进作废
 *
 * Created by PanJunLong on 2017/10/18.
 */

public class AudioDialog extends BaseDialogFragment<List<ImageInfo>> {

    PlayOrPauseItem playItem;
    ViewGroup.LayoutParams params;
    List<ImageInfo> datas;

    MediaPlayer player;

    @Override
    View initIncludeView(Context mContext) {
        player =new MediaPlayer();

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
    void initData(List<ImageInfo> data) {
        datas = data;
    }


    @Override
    void initListener() {
        playItem.setOnMadioPlayAndPauseClickListener(
                new PlayOrPauseItem.OnMadioPlayClickListener() {
                    @Override
                    public void onClick(View view) {
//                        player.setDataSource(datas);
                    }
                }, new PlayOrPauseItem.OnMadioPauseClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }
}
