package com.lgb.xprodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lgb.xprodemo.R;
import com.lgb.xprodemo.fragment.FragmentUtils;

/**
 * Created by LGB on 17/1/13.
 */

public class ConstraintActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        initView();
    }

    private void initView() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }

    }
}
