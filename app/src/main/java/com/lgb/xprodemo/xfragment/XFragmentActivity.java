package com.lgb.xprodemo.xfragment;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.lgb.xprodemo.R;
import com.lgb.xprodemo.activity.BaseActivity;

public class XFragmentActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_fragment);
        FragmentUtils.getInstance().initFragment(this, R.id.ly_fragment_content);
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
                    FragmentUtils.getInstance().showMainFragment(XFragment1.class);
                }
                break;
            case R.id.radio_2:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(XFragment2.class);
                }
                break;
            case R.id.radio_3:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(XFragment3.class);
                }
                break;
            case R.id.radio_4:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(XFragment4.class);
                }
                break;
            case R.id.radio_5:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(XFragment5.class);
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
