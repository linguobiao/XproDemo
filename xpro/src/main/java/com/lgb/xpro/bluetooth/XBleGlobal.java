package com.lgb.xpro.bluetooth;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by LGB on 17/1/12.
 */

public class XBleGlobal {

    @IntDef({STATE_CONNECTED, STATE_CONNECTING, STATE_DISCONNECTED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionState {}
    public static final int STATE_CONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_DISCONNECTED = 2;


    public final static int REQUEST_CODE_OPEN_BLUETOOTH = 10001;
}
