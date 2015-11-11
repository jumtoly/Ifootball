package com.ifootball.app.activity.career;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ifootball.app.BaseActivity;
import com.ifootball.app.activity.stand.FirstStartAppActivity;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.AppManager;
import com.ifootball.app.util.ExitAppUtil;
import com.myifootball.app.R;

public class CareerActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		putContentView(R.layout.activity_career, "", NavigationHelper.CAREER,
				true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
			am.killBackgroundProcesses(getPackageName());
		}
		return super.onKeyDown(keyCode, event);
	}

}
