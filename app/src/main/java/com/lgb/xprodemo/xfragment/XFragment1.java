package com.lgb.xprodemo.xfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgb.xprodemo.R;

/**
 * Created by linguobiao on 16/11/8.
 */

public class XFragment1 extends Fragment implements View.OnClickListener{

    private TextView tv_1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_x, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_1 = (TextView) view.findViewById(R.id.tv_1);
        tv_1.setText("销售feafafeafafdafewafafefa");
        tv_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                FragmentUtils.getInstance().showFragment(XFragmentSon.class);
                break;
        }
    }
}
