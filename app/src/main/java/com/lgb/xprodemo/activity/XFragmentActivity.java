package com.lgb.xprodemo.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.lgb.xprodemo.R;
import com.lgb.xprodemo.fragment.FragmentUtils;

public class XFragmentActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_fragment);
        FragmentUtils.getInstance().initFragment(this);
        initView();
    }

    private void initView() {
        ((RadioButton)findViewById(R.id.radio_1)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_2)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_3)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_4)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_5)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_1)).setChecked(true);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.radio_1:
                if (isChecked) {
                    FragmentUtils.getInstance().xFragmentManager.showMainFragment(FragmentUtils.FRAGMENT_1);
                }
                break;
            case R.id.radio_2:
                if (isChecked) {
                    FragmentUtils.getInstance().xFragmentManager.showMainFragment(FragmentUtils.FRAGMENT_2);
                }
                break;
            case R.id.radio_3:
                if (isChecked) {
                    FragmentUtils.getInstance().xFragmentManager.showMainFragment(FragmentUtils.FRAGMENT_3);
                }
                break;
            case R.id.radio_4:
                if (isChecked) {
                    FragmentUtils.getInstance().xFragmentManager.showMainFragment(FragmentUtils.FRAGMENT_4);
                }
                break;
            case R.id.radio_5:
                if (isChecked) {
                    FragmentUtils.getInstance().xFragmentManager.showMainFragment(FragmentUtils.FRAGMENT_5);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        FragmentUtils.getInstance().clean();
        super.onDestroy();
    }
}
