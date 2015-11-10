package com.ifootball.app.activity.home;

import android.app.Activity;
import android.os.Bundle;

import com.ifootball.app.entity.SettingsUtil;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.listener.CountDownTimerListener;
import com.ifootball.app.util.CountDownTimerUtil;
import com.ifootball.app.util.ExitAppUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.util.VersionUtil;
import com.myifootball.app.R;

public class HomeStartActivity extends Activity {
	private CountDownTimerUtil mCountDownTimerUtil;
	private static final String FRIST_START_KEY = "FRIST_START";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.drawable.bg);
		init();
	}

	private void init() {
		if (SettingsUtil.getVersionCheckNotify()) {
			VersionUtil.getInstance().checkVersionUpdate();
		}
		ExitAppUtil.isRemember();
		setCountDownTimer();
		mCountDownTimerUtil.start();
	}

	private void setCountDownTimer() {
		mCountDownTimerUtil = new CountDownTimerUtil(2000, 1000,
				new CountDownTimerListener() {

					@Override
					public void onFinish() {
						if (MySharedCache.get(FRIST_START_KEY, true)) {
							MySharedCache.put(FRIST_START_KEY, false);
							IntentUtil.redirectToNextActivity(
									HomeStartActivity.this,
									FirstStartAppActivity.class);
						} else {
							IntentUtil.redirectToNextActivity(
									HomeStartActivity.this, HomeActivity.class);
						}
						finish();
					}

					@Override
					public void onTick(long millisUntilFinished) {

					}
				});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCountDownTimerUtil != null) {
			mCountDownTimerUtil.cancel();
			mCountDownTimerUtil = null;
		}
	}
}
