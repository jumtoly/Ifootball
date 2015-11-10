package com.ifootball.app.activity.green;

import android.os.Bundle;

import com.ifootball.app.BaseActivity;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.myifootball.app.R;

public class GreenActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		putContentView(R.layout.activity_green, "", NavigationHelper.GREEN,
				true);
	}
}
