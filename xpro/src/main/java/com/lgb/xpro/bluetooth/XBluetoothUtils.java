package com.lgb.xpro.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import java.util.UUID;

/**
 * Created by LGB on 17/1/12.
 */

public class XBluetoothUtils {

    private final static String TAG = "XBluetoothUtils";

    /**
     * 设置通知属性为true，并写上descriptor
     *
     * @param uuid_service
     * @param uuid_characteristic
     * @param uuid_descriptor
     */
    public static void setCharactoristicNotifyAndWriteDescriptor(BluetoothGatt mBluetoothGatt, UUID uuid_service, UUID uuid_characteristic, UUID uuid_descriptor) {
        // service
        BluetoothGattService mBluetoothGattService = getBluetoothGattService(mBluetoothGatt, uuid_service);
        // characteristic
        BluetoothGattCharacteristic mBluetoothGattCharacteristic = getBluetoothGattCharacteristic(mBluetoothGattService, uuid_characteristic);

        if (mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
            // 设置
            mBluetoothGatt.setCharacteristicNotification(mBluetoothGattCharacteristic, true);

            if (uuid_descriptor != null){
                BluetoothGattDescriptor bluetoothGattDescriptor = mBluetoothGattCharacteristic.getDescriptor(uuid_descriptor);
                if (bluetoothGattDescriptor != null) {
                    bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
                }
            }
        } else if (mBluetoothGatt == null) {
            Log.i(TAG, "mBluetoothGatt is null");
        } else if (mBluetoothGattCharacteristic == null) {
            Log.i(TAG, "mBluetoothGattCharacteristic is null");
        }
    }

    public static synchronized void writeCharacteristic(BluetoothGatt mBluetoothGatt, UUID uuid_service, UUID uuid_character, byte[] value) {
        // service
        BluetoothGattService mBluetoothGattService = getBluetoothGattService(mBluetoothGatt, uuid_service);
        // characteristic
        BluetoothGattCharacteristic mBluetoothGattCharacteristic = getBluetoothGattCharacteristic(mBluetoothGattService, uuid_character);

        if (mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
            mBluetoothGattCharacteristic.setValue(value);
//			mBluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
            mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);

        } else if (mBluetoothGatt == null) {
            Log.i(TAG, "mBluetoothGatt is null");
        } else if (mBluetoothGattCharacteristic == null) {
            Log.i(TAG, "mBluetoothGattCharacteristic is null");
        }
    }

    /**
     * 获取bluetoothGattService
     *
     * @param mBluetoothGatt
     * @param UUID_SERVICE
     * @return
     */
    private static BluetoothGattService getBluetoothGattService(BluetoothGatt mBluetoothGatt, UUID UUID_SERVICE) {
        if (mBluetoothGatt != null) {
            BluetoothGattService mBluetoothGattServer = mBluetoothGatt.getService(UUID_SERVICE);
            if (mBluetoothGattServer != null) {
                return mBluetoothGattServer;
            } else {
                Log.i(TAG, "getBluetoothGattService, bluetoothgatt get service uuid:" + UUID_SERVICE + " is null");
            }
        } else {
            Log.i(TAG, "mBluetoothGatt is null");
        }

        return null;
    }

    /**
     * 获取bluetoothGattCharacteristic
     *
     * @param mBluetoothGattService
     * @param UUID_CHARACTERISTIC
     * @return
     */
    private static BluetoothGattCharacteristic getBluetoothGattCharacteristic(BluetoothGattService mBluetoothGattService, UUID UUID_CHARACTERISTIC) {
        if (mBluetoothGattService != null) {
            BluetoothGattCharacteristic mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(UUID_CHARACTERISTIC);

            if (mBluetoothGattCharacteristic != null) {
                return mBluetoothGattCharacteristic;
            } else {
                Log.i(TAG, "getBluetoothGattCharacteristic, bluetoothGattServer get characteristic uuid:" + UUID_CHARACTERISTIC + " is null");
            }
        } else {
            Log.i(TAG, "mBluetoothGattServer is null");
        }

        return null;
    }
}
