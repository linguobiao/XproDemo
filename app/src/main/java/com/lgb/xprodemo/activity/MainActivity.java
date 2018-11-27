package com.lgb.xprodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lgb.xprodemo.R;
import com.lgb.xprodemo.xbluetooth.XBluetoothActivity;
import com.lgb.xprodemo.xfragment.XFragmentActivity;

/**
 * Created by LGB on 17/1/13.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{
    //testupload

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_fragment).setOnClickListener(this);
        findViewById(R.id.btn_bluetooth).setOnClickListener(this);
        findViewById(R.id.btn_constraintLayout).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fragment:
                startActivity(new Intent(MainActivity.this, XFragmentActivity.class));
                break;
            case R.id.btn_bluetooth:
                startActivity(new Intent(MainActivity.this, XBluetoothActivity.class));
                break;
            case R.id.btn_constraintLayout:
                startActivity(new Intent(MainActivity.this, ConstraintActivity.class));
                break;
            default:
                break;
        }

    }
}
