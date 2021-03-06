package com.ifootball.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifootball.app.util.SystemBarTintManager;
import com.myifootball.app.R;

public abstract class BaseFragment extends Fragment {

	private int index;
	/**
	 * 界面是否是当前可见
	 */
	protected boolean isCurrentVisible = false;
	/**
	 * 是否加载完界面
	 */
	protected boolean isPrepared = false;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isCurrentVisible = true;
			onVisible();
		} else {
			isCurrentVisible = false;
			onInVisible();
		}
	}

	/**
	 * Fragment不可见
	 */
	protected abstract void onInVisible();

	/**
	 * Fragment可见
	 */
	private void onVisible() {

		getData();
	}

	/**
	 * 加载数据
	 */
	protected abstract void getData();

	/**
	 * put数据到布局
	 */
	protected abstract void installView();

}
