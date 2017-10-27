package item;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.setproject.bilifo.mypictrue.R;

/**
 * 对应R.layout.dialog_audio  xml文件的itemXml类.
 * 它是一个带"播放/暂停""上/下一曲""快进/快退"的媒体控制条.
 * 作用:
 *
 * 使用方法:
 *          1/
 * 后续改进:
 *         1/争取做成动态类,动态的布局和控件,从而省掉xml文件的依赖,使之能嵌入到其他类中使用
 *
 * Created by PanJunLong on 2017/10/23.
 */

public class PlayOrPauseItem<T> extends ItemXmlClass<T> {
    ImageView play,
            next,//下一首
            prev,//上一首
            rew,//快退
            ff;//快进

    boolean playFlag = false;//播放标记

    public PlayOrPauseItem(Context context) {
        super(context);
    }

    @Override
    public void initView(View rootview) {
        play = rootview.findViewById(R.id.item_media_play);
        next = rootview.findViewById(R.id.item_media_next);
        prev = rootview.findViewById(R.id.item_media_previous);
        rew = rootview.findViewById(R.id.item_media_rew);
        ff = rootview.findViewById(R.id.item_media_ff);

        initListener();
    }

    private void initListener() {
    }

    @Override
    public void setData(T data) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_audio;
    }

    /**
     * 播放事件监听
     */
    public abstract static class OnMadioPlayClickListener implements OnClickListener {
    }

    /**
     * 暂停事件监听
     */
    public abstract static class OnMadioPauseClickListener implements OnClickListener {
    }

    /**
     * 下一曲事件监听
     */
    public abstract static class OnMadioNextClickListener implements OnClickListener {
    }

    /**
     * 上一曲事件监听
     */
    public abstract static class OnMadioPrevClickListener implements OnClickListener {
    }

    /**
     * 快退事件监听
     */
    public abstract static class OnMadioRewClickListener implements OnClickListener {
    }

    /**
     * 快进事件监听
     */
    public abstract static class OnMadioFfClickListener implements OnClickListener {
    }

    /**
     * 设置播放事件监听
     */
   public void setOnMadioPlayAndPauseClickListener(final OnMadioPlayClickListener playClick, final OnMadioPauseClickListener pauseClick){
       play.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               if (playFlag == true) {//当前是播放状态
                   play.getDrawable().setLevel(1);//点击背景图改为暂停
                   pauseClick.onClick(view);//执行暂停事件
                   playFlag=false;//状态设置为暂停
                   return;
               } else {//当前是暂停状态
                   play.getDrawable().setLevel(0);
                   playClick.onClick(view);
                   playFlag=true;
                   return;
               }
           }
       });

    }

    /**
     * 设置下一曲事件监听
     */
    public void setOnMadioNextClickListener(OnMadioNextClickListener listener){
        next.setOnClickListener(listener);
    }
    /**
     * 设置上一曲事件监听
     */
    public void setOnMadioPrevClickListener(OnMadioPrevClickListener listener){
        prev.setOnClickListener(listener);
    }
    /**
     * 设置快退事件监听
     */
    public void setOnMadioRewClickListener(OnMadioRewClickListener listener){
        rew.setOnClickListener(listener);
    }
    /**
     * 设置快进事件监听
     */
    public void setOnMadioFfClickListener(OnMadioFfClickListener listener){
        ff.setOnClickListener(listener);
    }
}
