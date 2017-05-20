package jp.anmt.silent.tapsilent;

import android.media.AudioManager;

/**
 * Created by numata on 2017/05/02.
 */

public class Param {
    // デバッグモード有効
    public static final boolean D = false;

    public static final int STREAM_TYPE_MAX = 6;

    public static final int STREAM_TYPE_ALARM = 0;
    public static final int STREAM_TYPE_DTFM = 1;
    public static final int STREAM_TYPE_MUSIC = 2;
    public static final int STREAM_TYPE_NOTIFY = 3;
    public static final int STREAM_TYPE_RING = 4;
    public static final int STREAM_TYPE_SYSTEM = 5;

    public static final int[] AM_STREAM_TYPE = {AudioManager.STREAM_ALARM, AudioManager.STREAM_DTMF, AudioManager.STREAM_MUSIC, AudioManager.STREAM_NOTIFICATION, AudioManager.STREAM_RING, AudioManager.STREAM_SYSTEM};
}
