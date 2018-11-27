package com.lgb.xprodemo.xfragment;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.lgb.xpro.fragment.XFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentUtils {

	public XFragment xFragment;


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
	public void initFragment(FragmentActivity fragmentActivity, @IdRes int layoutRes) {
		if (xFragment == null) {
			List<XFragment.XFragmentBean> tagList = new ArrayList<>();
			// 一级界面
			XFragment.XFragmentBean bean1 = new XFragment.XFragmentBean(XFragment1.class);
			XFragment.XFragmentBean bean2 = new XFragment.XFragmentBean(XFragment2.class);
			XFragment.XFragmentBean bean3 = new XFragment.XFragmentBean(XFragment3.class);
			XFragment.XFragmentBean bean4 = new XFragment.XFragmentBean(XFragment4.class);
			XFragment.XFragmentBean bean5 = new XFragment.XFragmentBean(XFragment5.class);
			// 二级界面
			bean1.add(XFragmentSon.class);

			tagList.add(bean1);
			tagList.add(bean2);
			tagList.add(bean3);
			tagList.add(bean4);
			tagList.add(bean5);
			xFragment = new XFragment(fragmentActivity.getSupportFragmentManager(), layoutRes, tagList);
		}
	}

	public void showFragment(@NonNull Class cls) {xFragment.showFragment(cls);}
	public void showFragment(@NonNull Class cls, Bundle bundle) {xFragment.showFragment(cls, bundle);}
	public void showMainFragment(@NonNull Class cls) {xFragment.showMainFragment(cls);}
	public void returnMainFragment(@NonNull Class cls) {xFragment.returnMainFragment(cls);}
	public void removeFragment(@NonNull Class cls) {xFragment.removeFragment(cls);}
	public Fragment findFragment(@NonNull Class cls) {return xFragment.findFragment(cls);}

	public void clean() {
		xFragment = null;
	}

//	public static final String FRAGMENT_1 = "FRAGMENT_1";
//	public static final String FRAGMENT_1_SON_1 = "FRAGMENT_1_SON_1";
//	public static final String FRAGMENT_2 = "FRAGMENT_2";
//	public static final String FRAGMENT_3 = "FRAGMENT_3";
//	public static final String FRAGMENT_4 = "FRAGMENT_4";
//	public static final String FRAGMENT_5 = "FRAGMENT_5";
}
