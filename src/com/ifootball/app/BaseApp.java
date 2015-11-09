package com.ifootball.app;

import android.app.Application;
import android.content.Context;

import com.ifootball.app.framework.cache.MyFileCache;
import com.ifootball.app.framework.http.BetterHttp;
import com.ifootball.app.util.CrashHandler;
import com.ifootball.app.util.ImageUrlUtil;
import com.ifootball.app.util.VersionUtil;
import com.myifootball.app.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApp extends Application {
	private static String mUserAgent;
	private static BaseApp instance;

	private static int mNum = 0;

	@Override
	public void onCreate() {
		super.onCreate();

		if (mNum == 0) {
			instance = this;

			initImageLoader(this);
			MyFileCache.install(getApplicationContext());

			mUserAgent = getResources().getString(R.string.user_agent,
					VersionUtil.getCurrentVersion());

			setupBetterHttp();

			ImageUrlUtil.setContext(this);

			CrashHandler crashHandler = CrashHandler.getInstance();
			crashHandler.init(this);

			mNum = mNum + 1;
		}
	}

	public static BaseApp instance() {

		return instance;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	private static String getUserAgent() {
		return mUserAgent;
	}

	private void setupBetterHttp() {
		BetterHttp.setContext(this);
		BetterHttp.setupHttpClient();
		BetterHttp.setUserAgent(getUserAgent());
		int densityDpi = getResources().getDisplayMetrics().densityDpi;
		BetterHttp.setDefaultHeader("X-HighResolution",
				String.valueOf(densityDpi));
		BetterHttp.setDefaultHeader("X-OSVersion",
				android.os.Build.VERSION.RELEASE);

		BetterHttp.enableGZIPEncoding();
	}

	private static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not
																				// necessary
																				// in
																				// common
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}