package com.silencedut.knowweather.broadcast.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

/**
 * 闹钟时间到了之后弹出的Activity
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/19
 */

public class AlarmTimeOutActivity extends Activity {
    private Vibrator vibrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        // 更新闹钟状态
        createAlertDialog();
        startVibrator();
    }

    private void createAlertDialog() {
        final Ringtone defaultRingtone = getDefaultRingtone(this, RingtoneManager.TYPE_RINGTONE);


        defaultRingtone.play();
        defaultRingtone.isPlaying();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("闹钟响了");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (defaultRingtone.isPlaying()) {
                    defaultRingtone.stop();
                    vibrator.cancel();
                    finish();
                }
            }
        });
        builder.show();
    }

    private void startVibrator() {
        /**
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
         *
         */
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {500, 1000, 500, 1000}; // 停止 开启 停止 开启
        vibrator.vibrate(pattern, 0);
    }

    /**
     * 获取的是铃声的Uri
     *
     * @param ctx
     * @param type
     * @return
     */
    public static Uri getDefaultRingtoneUri(Context ctx, int type) {

        return RingtoneManager.getActualDefaultRingtoneUri(ctx, type);

    }

    /**
     * 获取的是铃声相应的Ringtone
     *
     * @param ctx
     * @param type
     */
    public Ringtone getDefaultRingtone(Context ctx, int type) {

        return RingtoneManager.getRingtone(ctx,
                RingtoneManager.getActualDefaultRingtoneUri(ctx, type));

    }

    /**
     * 播放铃声
     *
     * @param ctx
     * @param type
     */

    public static void PlayRingTone(Context ctx, int type) {
        MediaPlayer mMediaPlayer = MediaPlayer.create(ctx,
                getDefaultRingtoneUri(ctx, type));
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

    }
}
