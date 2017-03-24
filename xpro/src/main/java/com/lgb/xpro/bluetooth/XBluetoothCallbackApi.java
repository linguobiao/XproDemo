package com.lgb.xpro.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by LGB on 17/1/12.
 */

public interface XBluetoothCallbackApi {

    void onConnectionStateChange(BluetoothGatt gatt, int status, int newState);

    void onConnectionChanged(int state, boolean isConnectedSuccessful);

    void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);

    void onServicesDiscovered();

    void onScan(BluetoothDevice device, int rssi);
}
