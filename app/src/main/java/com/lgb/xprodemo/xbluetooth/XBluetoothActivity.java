package com.lgb.xprodemo.xbluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lgb.xpro.bluetooth.XBleGlobal;
import com.lgb.xpro.bluetooth.XBluetoothCallbackApi;
import com.lgb.xprodemo.R;
import com.lgb.xprodemo.activity.BaseActivity;
import com.lgb.xprodemo.utils.BluetoothUtils;

import java.util.Arrays;

/**
 * Created by LGB on 17/1/13.
 */

public class XBluetoothActivity extends BaseActivity implements View.OnClickListener{

    private final String TAG = "XBluetoothActivity";

    // 寻卡
    byte bysCmdFind[] = { (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, 0x69, 0x00, 0x03, 0x20, 0x01, 0x22 };
    // 选卡
    byte bysCmdSelect[] = { (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, 0x69, 0x00, 0x03, 0x20, 0x02, 0x21 };
    // 读卡
    byte bysCmdRead[] = { (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, 0x69, 0x00, 0x03, 0x30, 0x10, 0x23 };
    int typeCmd;

    private Button btn_scan, btn_connect, btn_disconnect, btn_sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_bluetooth);
        boolean isBluetoothOk = BluetoothUtils.getInstance().initBluetooth(this, callbackApi);
        initView(isBluetoothOk);
    }

    private void initView(boolean isBluetoothOk) {
        btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(this);
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_connect.setOnClickListener(this);
        btn_disconnect = (Button) findViewById(R.id.btn_disconnect);
        btn_disconnect.setOnClickListener(this);
        btn_sync = (Button) findViewById(R.id.btn_sync);
        btn_sync.setOnClickListener(this);
//        if (!isBluetoothOk) {
//            btn_scan.setEnabled(false);
//        }
//        btn_connect.setEnabled(false);
//        btn_disconnect.setEnabled(false);
//        btn_sync.setEnabled(false);
    }

    private XBluetoothCallbackApi callbackApi = new XBluetoothCallbackApi() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_scan.setEnabled(true);
                    }
                });
                isReconnect = false;
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                if (isReconnect) {
                    BluetoothUtils.getInstance().xBluetoothManager.scan(true);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_scan.setEnabled(true);
                        }
                    });

                }
                isReconnect = false;
            }
        }

        @Override
        public void onConnectionChanged(int state, boolean isConnectedSuccessful) {

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.i(TAG, Arrays.toString(characteristic.getValue()));
            if (typeCmd == 1) {
                typeCmd = 2;
                BluetoothUtils.getInstance().xBluetoothManager.sendCommand(bysCmdSelect);
            } else if (typeCmd == 2) {
                typeCmd = 3;
                BluetoothUtils.getInstance().xBluetoothManager.sendCommand(bysCmdRead);
            } else if (typeCmd == 3) {
                Log.i(TAG, "读取成功");
            }
        }

        @Override
        public void onServicesDiscovered() {
            BluetoothUtils.getInstance().xBluetoothManager.set_notify_true();
        }

        @Override
        public void onScan(BluetoothDevice device, int rssi) {
            Log.d(TAG, "name = " + device.getName() + ",   address = " + device.getAddress() + ",   rssi = " + rssi);
            String name = device.getName();
            if ("HX-BLE".equals(name)) {
                BluetoothUtils.getInstance().xBluetoothManager.scan(false);
                deviceAddress = device.getAddress();
                BluetoothUtils.getInstance().xBluetoothManager.connect(deviceAddress);
            }
        }
    };

    String deviceAddress;
    boolean isReconnect = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                btn_scan.setEnabled(false);
                deviceAddress = null;
                isReconnect = true;
                if (BluetoothUtils.getInstance().xBluetoothManager.getConnectionState() == XBleGlobal.STATE_CONNECTED) {
                    BluetoothUtils.getInstance().xBluetoothManager.disconnect();
                } else {
                    BluetoothUtils.getInstance().xBluetoothManager.scan(true);
                }

                break;
            case R.id.btn_connect:
                if (deviceAddress != null) {
                    BluetoothUtils.getInstance().xBluetoothManager.scan(false);
                    BluetoothUtils.getInstance().xBluetoothManager.connect(deviceAddress);
                } else {
                    BluetoothUtils.getInstance().xBluetoothManager.disconnect();
                    BluetoothUtils.getInstance().xBluetoothManager.scan(true);
                }
                break;
            case R.id.btn_disconnect:
                BluetoothUtils.getInstance().xBluetoothManager.disconnect();
                break;
            case R.id.btn_sync:
                typeCmd = 1;
                BluetoothUtils.getInstance().xBluetoothManager.sendCommand(bysCmdFind);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothUtils.getInstance().xBluetoothManager.scan(false);
        BluetoothUtils.getInstance().xBluetoothManager.disconnect();
    }
}
