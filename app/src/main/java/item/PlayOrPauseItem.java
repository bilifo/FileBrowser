package item;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.setproject.bilifo.mypictrue.R;

/**
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

    public abstract static class OnMadioPlayClickListener implements OnClickListener {
    }

    ;

    public abstract static class OnMadioPauseClickListener implements OnClickListener {
    }

    ;

    public abstract static class OnMadioNextClickListener implements OnClickListener {
    }

    ;

    public abstract static class OnMadioPrevClickListener implements OnClickListener {
    }

    ;

    public abstract static class OnMadioRewClickListener implements OnClickListener {
    }

    ;

    public abstract static class OnMadioFfClickListener implements OnClickListener {
    }

    ;

   public void setOnMadioPlayAndPauseClickListener(final OnMadioPlayClickListener playClick, final OnMadioPauseClickListener pauseClick){
       play.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               if (playFlag == true) {
                   play.getDrawable().setLevel(1);
                   playClick.onClick(view);
                   playFlag=false;
                   return;
               } else {
                   play.getDrawable().setLevel(0);
                   pauseClick.onClick(view);
                   playFlag=true;
                   return;
               }
           }
       });

    }


    public void setOnMadioNextClickListener(OnMadioNextClickListener listener){
        next.setOnClickListener(listener);
    }

    public void setOnMadioPrevClickListener(OnMadioPrevClickListener listener){
        prev.setOnClickListener(listener);
    }

    public void setOnMadioRewClickListener(OnMadioRewClickListener listener){
        rew.setOnClickListener(listener);
    }

    public void setOnMadioFfClickListener(OnMadioFfClickListener listener){
        ff.setOnClickListener(listener);
    }
}
