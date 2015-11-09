package com.ifootball.app;

import android.app.Activity;
import android.os.Bundle;

import com.ifootball.app.util.VersionUtil;
import com.myifootball.app.R;

public class VersionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.version_layout);
		VersionUtil.getInstance().update(this);
	}
}
