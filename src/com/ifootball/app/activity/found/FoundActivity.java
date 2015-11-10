package com.ifootball.app.activity.found;

import android.os.Bundle;

import com.ifootball.app.BaseActivity;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.myifootball.app.R;

public class FoundActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		putContentView(R.layout.activity_found, "", NavigationHelper.FOUND,
				true);
	}
}
