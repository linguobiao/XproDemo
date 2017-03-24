package com.lgb.xprodemo.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.lgb.xpro.bluetooth.XBluetoothCallbackApi;
import com.lgb.xpro.bluetooth.XBluetoothManager;

import java.util.UUID;

/**
 * Created by LGB on 17/1/16.
 */

public class BluetoothUtils {

    private final UUID uuid_service = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    private final UUID uuid_characteristic = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");

    private static class BluetoothUtilsInstance {
        public final static BluetoothUtils instance = new BluetoothUtils();
    }

    public static BluetoothUtils getInstance() {
        return BluetoothUtilsInstance.instance;
    }

    public BluetoothUtils() {

    }

    public XBluetoothManager xBluetoothManager;
    public boolean initBluetooth(Context context, XBluetoothCallbackApi callbackApi) {
        xBluetoothManager = new XBluetoothManager(context);
        xBluetoothManager.setUUid(uuid_service, uuid_characteristic, null);
        BluetoothAdapter bluetoothAdapter = xBluetoothManager.initBluebooth((Activity)context, callbackApi);
        if (bluetoothAdapter == null) {
            return false;
        }
        return true;
    }

}
