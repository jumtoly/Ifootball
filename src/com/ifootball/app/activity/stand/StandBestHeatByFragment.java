package com.ifootball.app.activity.stand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifootball.app.BaseFragment;
import com.myifootball.app.R;

public class StandBestHeatByFragment extends BaseFragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view = inflater.inflate(R.layout.activity_bestheat, null);
	}

	@Override
	protected void onInVisible() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void installView() {
		// TODO Auto-generated method stub

	}
}
