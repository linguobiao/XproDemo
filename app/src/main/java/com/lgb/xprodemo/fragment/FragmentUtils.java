package com.lgb.xprodemo.fragment;


import android.support.v4.app.FragmentActivity;

import com.lgb.xpro.fragment.XFragmentManager;
import com.lgb.xprodemo.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentUtils {

	public XFragmentManager xFragmentManager;


	public static class FragmentUtilsInstance {
		private static final FragmentUtils instance = new FragmentUtils();

	}

	public static FragmentUtils getInstance() {
		return FragmentUtilsInstance.instance;
	}

	public FragmentUtils() {

	}

	/**
	 * 初始化fragment
	 * @param fragmentActivity
	 */
	public void initFragment(FragmentActivity fragmentActivity) {
		if (xFragmentManager == null) {
			List<XFragmentManager.XFragmentBean> tagList = new ArrayList<>();
			// 一级界面
			XFragmentManager.XFragmentBean bean1 = new XFragmentManager.XFragmentBean(XFragment.class, FRAGMENT_1);
			XFragmentManager.XFragmentBean bean2 = new XFragmentManager.XFragmentBean(XFragment2.class, FRAGMENT_2);
			XFragmentManager.XFragmentBean bean3 = new XFragmentManager.XFragmentBean(XFragment3.class, FRAGMENT_3);
			XFragmentManager.XFragmentBean bean4 = new XFragmentManager.XFragmentBean(XFragment4.class, FRAGMENT_4);
			XFragmentManager.XFragmentBean bean5 = new XFragmentManager.XFragmentBean(XFragment5.class, FRAGMENT_5);
			// 二级界面
			bean1.add(XFragmentSon.class, FRAGMENT_1_SON_1);

			tagList.add(bean1);
			tagList.add(bean2);
			tagList.add(bean3);
			tagList.add(bean4);
			tagList.add(bean5);
			xFragmentManager = new XFragmentManager(fragmentActivity, fragmentActivity.getSupportFragmentManager(), "ly_fragment_content", tagList);
		}
	}

	public void clean() {
		xFragmentManager = null;
	}

	public static final String FRAGMENT_1 = "FRAGMENT_1";
	public static final String FRAGMENT_1_SON_1 = "FRAGMENT_1_SON_1";
	public static final String FRAGMENT_2 = "FRAGMENT_2";
	public static final String FRAGMENT_3 = "FRAGMENT_3";
	public static final String FRAGMENT_4 = "FRAGMENT_4";
	public static final String FRAGMENT_5 = "FRAGMENT_5";
}
