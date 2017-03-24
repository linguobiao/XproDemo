package com.lgb.xpro.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lgb.xpro.bluetooth.XBleGlobal.ConnectionState;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by LGB on 17/1/12.
 */

public class XBluetoothManager {

    private final String TAG = "XBluetoothManager";

    private Context mContext;

    private XBluetoothCallbackApi xBluetoothCallbackApi;

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;

    private String mBluetoothDeviceAddress;
    private String mBluetoothDeviceName;

    private @ConnectionState int mConnectionState = XBleGlobal.STATE_CONNECTED;

    public @ConnectionState int getConnectionState() {
        return mConnectionState;
    }

    private UUID uuid_service, uuid_characteristic, uuid_descriptor;

    public XBluetoothManager(Context mContext) {
        this.mContext = mContext;
    }

    public void setUUid(UUID uuid_service, UUID uuid_characteristic, UUID uuid_descriptor) {
        this.uuid_service = uuid_service;
        this.uuid_characteristic = uuid_characteristic;
        this.uuid_descriptor = uuid_descriptor;
    }

    /**
     * 蓝牙回调函数
     */
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            xBluetoothCallbackApi.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.i(TAG, "Connected to GATT server.");
                    mConnectionState = XBleGlobal.STATE_CONNECTED;

                    Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());
                } else {
                    Log.w(TAG, "Connect fail, status = " + status);
                    mConnectionState = XBleGlobal.STATE_DISCONNECTED;
                    disconnect();
                }
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "Disconnected from GATT server.");
                mConnectionState = XBleGlobal.STATE_DISCONNECTED;
                close();
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {
                Log.i(TAG, "Disconnected from GATT server.");
                mConnectionState = XBleGlobal.STATE_CONNECTING;
            } else {
                Log.w(TAG, "Connect fail, newState = " + newState + ",   status = " + status);
                mConnectionState = XBleGlobal.STATE_DISCONNECTED;
                disconnect();
            }
//            xBluetoothCallbackApi.onConnectionChanged(mConnectionState, false);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                xBluetoothCallbackApi.onServicesDiscovered();
                Log.i(TAG, "onServicesDiscovered successful");
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                byte[] value = characteristic.getValue();
                Log.i(TAG, "onCharacteristicWrite value = " + Arrays.toString(value));
            } else {
                Log.w(TAG, "onCharacteristicWrite status = " + status);
            }
        }

        @Override
        public synchronized void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            xBluetoothCallbackApi.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                xBluetoothCallbackApi.onConnectionChanged(mConnectionState, true);
                Log.d(TAG, "Write descriptor success, connection is successful");
            } else {
                Log.w(TAG, "onDescriptorWrite status = " + status);
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        }
    };

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address
     *            The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result is
     *         reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device. Try to reconnect. (先前连接的设备。 尝试重新连接)
        // if (mBluetoothDeviceAddress != null
        // && address.equals(mBluetoothDeviceAddress)
        // && mBluetoothGatt != null) {
        // Log.d(TAG,
        // "Trying to use an existing mBluetoothGatt for connection.");
        // if (mBluetoothGatt.connect()) {
        // mConnectionState = STATE_CONNECTING;
        // return true;
        // } else {
        // return false;
        // }
        // }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the
        // autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.   address = " + address );
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be invoked
     * only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;

        return mBluetoothGatt.getServices();
    }

    /**
     * Read the RSSI for a connected remote device.
     * */
    public boolean getRssiVal() {
        if (mBluetoothGatt == null)
            return false;

        return mBluetoothGatt.readRemoteRssi();
    }

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter
        // through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            if (mBluetoothAdapter == null) {
                Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
                return false;
            }
        }

        return true;
    }

    /**
     * 初始化蓝牙
     * @param context
     */
    public BluetoothAdapter initBluebooth(Activity context, XBluetoothCallbackApi callbackApi){
        this.xBluetoothCallbackApi = callbackApi;
        if (mBluetoothAdapter == null) {
            boolean isHaveBle = initialize();
            if (isHaveBle == false) {
                return null;
            }
        }
        // 判断机器是否有蓝牙
        if (!mBluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivityForResult(enableIntent, XBleGlobal.REQUEST_CODE_OPEN_BLUETOOTH);
        }
        return mBluetoothAdapter;
    }
    /**
     * 扫描设备
     *
     * @param start
     */
    public void scan(boolean start) {
        if (mBluetoothAdapter != null) {
            if (start) {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        } else {
            Log.i(TAG, "bluetoothadapter is null");
        }
    }

    /**
     * 扫描设备的回调方法
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            xBluetoothCallbackApi.onScan(device, rssi);
        }
    };

    /**
     * 开启广播
     */
    public void set_notify_true() {
        XBluetoothUtils.setCharactoristicNotifyAndWriteDescriptor(mBluetoothGatt, uuid_service, uuid_characteristic, uuid_descriptor);
    }

    public void sendCommand(byte[] value) {
        XBluetoothUtils.writeCharacteristic(mBluetoothGatt, uuid_service, uuid_characteristic, value);
    }
}
