package dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import bean.ImageInfo;
import item.PlayOrPauseItem;

/**
 * Created by PanJunLong on 2017/10/30.
 */

public class VedioDialog extends BaseDialogFragment<List<ImageInfo>> {
    PlayOrPauseItem playItem;
    TextView textView;

    ViewGroup.LayoutParams params;
    List<ImageInfo> datas;

    MediaPlayer player;
    SurfaceView mSurfaceView;

    int itemposition = -1;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isDataLoaded == true && itemposition != -1) {
                        textView.setText(datas.get(itemposition).getName());
                        try {
                            player.reset();
                            player.setDataSource(datas.get(itemposition).getPath());
                            player.prepare();
                            player.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (isDataLoaded == true && itemposition == -1) {
                        textView.setText(datas.get(itemposition).getName());
                        try {
                            player.reset();
                            player.setDataSource(datas.get(0).getPath());
                            player.prepare();
                            player.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (isDataLoaded != true && itemposition == -1) {
                        mHandler.removeMessages(1);
                        mHandler.sendMessage(mHandler.obtainMessage(1));
                    } else {
                        mHandler.removeMessages(1);
                        mHandler.sendMessage(mHandler.obtainMessage(1));
                    }
                    break;
            }
        }
    };
    boolean isDataLoaded = false;

    @Override
    View initIncludeView(Context mContext) {

        player = new MediaPlayer();

        LinearLayout layout=new LinearLayout(mContext);
        layout.setBackgroundColor(Color.BLACK);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        textView=new TextView(mContext);
        textView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams mparams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
        mparams.weight=1;
        textView.setLayoutParams(mparams);
        layout.addView(textView);

        mSurfaceView=new SurfaceView(mContext);
        LinearLayout.LayoutParams mparams2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
        mparams2.weight=5;
        mSurfaceView.setLayoutParams(mparams2);
        mSurfaceView.getHolder().addCallback(callback);
        layout.addView(mSurfaceView);

        playItem = new PlayOrPauseItem(mContext);
        playItem.getRootView().setLayoutParams(mparams);
        layout.addView(playItem.getRootView());

        return layout.getRootView();
    }

    @Override
    void initData(List<ImageInfo> data) {
        datas = data;
        isDataLoaded = true;
        mHandler.removeMessages(1);
        mHandler.sendMessage(mHandler.obtainMessage(1));

    }


    @Override
    void initListener() {
        playItem.setOnMadioPlayAndPauseClickListener(
                new PlayOrPauseItem.OnMadioPlayClickListener() {
                    @Override
                    public void onClick(View view) {
                        player.start();
                    }
                }, new PlayOrPauseItem.OnMadioPauseClickListener() {
                    @Override
                    public void onClick(View view) {
                        player.pause();
                    }
                });

        playItem.setOnMadioNextClickListener(new PlayOrPauseItem.OnMadioNextClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (itemposition < datas.size())
                        itemposition++;
                    player.reset();
                    player.setDataSource(datas.get(itemposition).getPath());
                    player.prepare();
                    player.start();
                    textView.setText(datas.get(itemposition).getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        playItem.setOnMadioPrevClickListener(new PlayOrPauseItem.OnMadioPrevClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (itemposition > 0)
                        itemposition--;
                    player.reset();
                    player.setDataSource(datas.get(itemposition).getPath());
                    player.prepare();
                    player.start();
                    textView.setText(datas.get(itemposition).getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goItemPositin(int position) {
        itemposition = position;
        mHandler.removeMessages(1);
        mHandler.sendMessage(mHandler.obtainMessage(1));
    }

    SurfaceHolder.Callback callback=new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            player.setDisplay(surfaceHolder);

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        player.reset();
    }
}
