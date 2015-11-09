package com.ifootball.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ifootball.app.framework.widget.CustomTitleManager;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.DialogUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.util.VersionUtil;

/**
 * This Class include Title Bar ,Navigation Bar and some other Broadcast
 * Receiver components.
 * 
 */
public class BaseActivity extends Activity {

	private CustomTitleManager mCustomTitleManager;
	private NavigationHelper mNavigationHelper;

	private BroadcastReceiver mCartNumberChangedBroadcastReceiver;

	private int mCurrentSelectedTab = NavigationHelper.DEFAULT;
	private ProgressDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mNavigationHelper = new NavigationHelper(this);

		/*mCartNumberChangedBroadcastReceiver = mNavigationHelper
				.getCartItemsCountChangedReceiver();*/
	}

	/**
	 * Set content view with Title bar and Navigation bar
	 * 
	 * @param layoutId
	 *            Page Content Layout
	 * @param pageTitle
	 *            Page Title
	 * @param tab
	 *            The Current Selected Tab,
	 *            NavigationHelper.HOME,NavigationHelper.SEARCH
	 *            NavigationHelper.CATEGORY,NavigationHelper.CART
	 *            NavigationHelper.MORE
	 * 
	 */
	public void putContentView(int layoutId, String pageTitle, int tab) {

		Boolean noTitle = pageTitle == null || pageTitle.equals("");
		mCustomTitleManager = new CustomTitleManager(this, noTitle);
		setContentView(layoutId);
		if (!noTitle) {
			mCustomTitleManager.setUp();
			mCustomTitleManager.setTitle(pageTitle);
		}

		mCurrentSelectedTab = tab;
		if (mCurrentSelectedTab != NavigationHelper.NONE) {

			mNavigationHelper.setNavigationActionEvent();
			mNavigationHelper.setSelectedNavigationTab(mCurrentSelectedTab);
		} else {

			unregisterReceiver();
		}
	}

	/**
	 * Set content view
	 * 
	 * @param layoutId
	 * @param pageTitle
	 * @param tab
	 *            NavigationHelper.HOME,NavigationHelper.SEARCH
	 *            NavigationHelper.CATEGORY,NavigationHelper.CART
	 *            NavigationHelper.MORE
	 * 
	 */
	public void putContentView(int layoutId, int pageTitle, int tab) {

		Boolean noTitle = pageTitle <= 0;

		mCustomTitleManager = new CustomTitleManager(this, noTitle);
		setContentView(layoutId);
		if (!noTitle) {
			mCustomTitleManager.setUp();
			mCustomTitleManager.setTitle(pageTitle);
		}

		mCurrentSelectedTab = tab;

		if (mCurrentSelectedTab != NavigationHelper.NONE) {

			mNavigationHelper.setNavigationActionEvent();
			mNavigationHelper.setSelectedNavigationTab(mCurrentSelectedTab);
		} else {

			unregisterReceiver();
		}
	}

	public void putContentView(int layoutId, String pageTitle) {

		putContentView(layoutId, pageTitle, NavigationHelper.DEFAULT);
	}

	public void putContentView(int layoutId, int pageTitle) {

		putContentView(layoutId, pageTitle, NavigationHelper.DEFAULT);
	}

	public void setPageTitle(int pageTitle) {

		if (mCustomTitleManager != null) {
			mCustomTitleManager.setTitle(pageTitle);
		}
	}

	public void setPageTitle(String pageTitle) {

		if (mCustomTitleManager != null) {
			mCustomTitleManager.setTitle(pageTitle);
		}
	}

	public void showBackButton() {
		if (mCustomTitleManager != null) {
			mCustomTitleManager.showBackButton(true);
		}
	}

	public LinearLayout showRightNormalButton(String title,
			View.OnClickListener listener) {
		if (mCustomTitleManager != null) {
			return mCustomTitleManager.showRightNormalButton(true, title,
					listener);
		}
		return null;
	}

	public LinearLayout showRightIconButton(int drawable,
			View.OnClickListener listener) {
		if (mCustomTitleManager != null) {
			return mCustomTitleManager.showRightIconButton(true, drawable,
					listener);
		}
		return null;
	}

	public ImageButton getRightIconButton() {
		if (mCustomTitleManager != null) {
			return mCustomTitleManager.getRightIconButton();
		}
		return null;
	}

	private void registerReceiver() {

		if (mCurrentSelectedTab != NavigationHelper.NONE
				&& mCartNumberChangedBroadcastReceiver != null) {

			//			IntentFilter filter = new IntentFilter(CartUtil.NUMBER_CHANGED_ACTION);
			//			this.registerReceiver(mCartNumberChangedBroadcastReceiver, filter);
		}
	}

	private void unregisterReceiver() {

		if (mCurrentSelectedTab != NavigationHelper.NONE
				&& mCartNumberChangedBroadcastReceiver != null) {

			//			this.unregisterReceiver(mCartNumberChangedBroadcastReceiver);
		}
	}

	@Override
	protected void onRestart() {

		if (mCurrentSelectedTab != NavigationHelper.NONE
				&& mNavigationHelper != null) {

			mNavigationHelper.setSelectedNavigationTab(mCurrentSelectedTab);
			//			mNavigationHelper.setCartItemsCount(CartUtil.getQuantity());
		}

		super.onRestart();
	}

	@Override
	protected void onPause() {

		unregisterReceiver();
		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver();
		if (VersionUtil.getInstance().IsUpdate()) {
			IntentUtil.redirectToNextActivity(this, VersionActivity.class);
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	public void showLoading(String tips) {
		closeLoading();
		try {
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}
			mLoadingDialog = DialogUtil.getProgressDialog(this, tips);
			mLoadingDialog.show();
		} catch (Exception e) {
		}
	}

	public void showLoading(int id) {
		showLoading(this.getResources().getString(id));
	}

	public void closeLoading() {
		try {

			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}
		} catch (Exception e) {
		}
	}
}