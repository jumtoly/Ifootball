package com.ifootball.app.framework.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myifootball.app.R;

public class NavigationHelper {

	public static final int HOME = 10;
	public static final int SEARCH = 11;
	public static final int CATEGORY = 12;
	public static final int CART = 13;
	public static final int MORE = 14;
	/**
	 * the page has navigation bar ,but no tab selected.
	 */
	public static final int DEFAULT = 0;
	/**
	 * the page has no navigation bar.
	 */
	public static final int NONE = -1;

	private Activity mActivity;
	private BroadcastReceiver mBroadcastReceiver;

	private int mItemTextColor;
	private int mItemTextPressedColor;
	private int mItemLinePressedColor;;
	private int mItemLineDefaultColor;

	private Button mHomeActionView;
	private Button mSearchActionView;
	private Button mCategoryActionView;
	private Button mCartActionView;
	private Button mMoreActionView;

	private Button mPreviousSelectedActionView;

	public NavigationHelper(Activity activity) {

		mActivity = activity;
	}

	public BroadcastReceiver getCartItemsCountChangedReceiver() {

		if (mBroadcastReceiver == null) {
			mBroadcastReceiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {

					String action = intent.getAction();
					//					int quantity = intent.getIntExtra(CartUtil.NUMBER, 0);
					//					if (action.equals(CartUtil.NUMBER_CHANGED_ACTION)) {
					//
					//						setCartItemsCount(quantity);
					//					}
				}
			};
		}

		return mBroadcastReceiver;
	}

	public void setCartItemsCount(int quantity) {

		TextView cartItemsCountTextView = (TextView) mActivity
				.findViewById(R.id.cart_items_count_textview);
		if (cartItemsCountTextView == null) {
			return;
		}

		if (quantity == 0) {
			cartItemsCountTextView.setVisibility(View.GONE);
		} else {
			cartItemsCountTextView.setVisibility(View.VISIBLE);
			//if qty >=100 ,then always show 99+ only.
			if (quantity >= 100) {
				cartItemsCountTextView.setText("99+");
			} else {
				cartItemsCountTextView.setText(String.valueOf(quantity));
			}
		}
	}

	public void setNavigationActionEvent() {

		int qty = 0;//CartUtil.getQuantity();

		setCartItemsCount(qty);

		mItemTextColor = mActivity.getResources().getColor(
				R.color.navigation_bar_item_text);
		mItemTextPressedColor = mActivity.getResources().getColor(
				R.color.navigation_bar_item_text_pressed);
		mItemLinePressedColor = mActivity.getResources().getColor(
				R.color.navigation_bar_line);
		mItemLineDefaultColor = mActivity.getResources().getColor(
				R.color.navigation_bar_line);

		mHomeActionView = (Button) mActivity
				.findViewById(R.id.navigation_bar_item_home);
		mSearchActionView = (Button) mActivity
				.findViewById(R.id.navigation_bar_item_search);
		mCategoryActionView = (Button) mActivity
				.findViewById(R.id.navigation_bar_item_category);
		mCartActionView = (Button) mActivity
				.findViewById(R.id.navigation_bar_item_cart);
		mMoreActionView = (Button) mActivity
				.findViewById(R.id.navigation_bar_item_more);

		ActionEventResponser actionEventResponser = new ActionEventResponser(
				mActivity);

		if (mHomeActionView != null) {
			mHomeActionView.setOnClickListener(actionEventResponser);
		}
		if (mSearchActionView != null) {
			mSearchActionView.setOnClickListener(actionEventResponser);
		}
		if (mCategoryActionView != null) {
			mCategoryActionView.setOnClickListener(actionEventResponser);
		}
		if (mCartActionView != null) {
			mCartActionView.setOnClickListener(actionEventResponser);
		}
		if (mMoreActionView != null) {
			mMoreActionView.setOnClickListener(actionEventResponser);
		}
	}

	/**
	 * Set current selected navigation tab
	 * 
	 * @param tab 
	 *   NavigationHelper.HOME,NavigationHelper.CATEGORY,
	 *   NavigationHelper.SEARCH,NavigationHelper.CART
	 *   NavigationHelper.MORE,NavigationHelper.DEFAULT
	 */
	public void setSelectedNavigationTab(int tab) {

		restoreViewState();

		switch (tab) {

		case HOME:

			mActivity.findViewById(R.id.navigation_bar_item_home_line)
					.setBackgroundColor(mItemLinePressedColor);

			mHomeActionView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.tab_home02, 0, 0);
			mHomeActionView.setTextColor(mItemTextPressedColor);
			mHomeActionView.setClickable(false);
			break;
		case SEARCH:

			mActivity.findViewById(R.id.navigation_bar_item_search_line)
					.setBackgroundColor(mItemLinePressedColor);

			mSearchActionView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.tab_search02, 0, 0);
			mSearchActionView.setTextColor(mItemTextPressedColor);
			mSearchActionView.setClickable(false);
			break;
		case CATEGORY:

			mActivity.findViewById(R.id.navigation_bar_item_category_line)
					.setBackgroundColor(mItemLinePressedColor);

			mCategoryActionView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.tab_category02, 0, 0);
			mCategoryActionView.setTextColor(mItemTextPressedColor);
			mCategoryActionView.setClickable(false);
			break;
		case CART:

			mActivity.findViewById(R.id.navigation_bar_item_cart_line)
					.setBackgroundColor(mItemLinePressedColor);

			mCartActionView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.tab_cart02, 0, 0);
			mCartActionView.setTextColor(mItemTextPressedColor);
			mCartActionView.setClickable(false);
			break;
		case MORE:

			mActivity.findViewById(R.id.navigation_bar_item_more_line)
					.setBackgroundColor(mItemLinePressedColor);

			mMoreActionView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.tab_account02, 0, 0);
			mMoreActionView.setTextColor(mItemTextPressedColor);
			mMoreActionView.setClickable(false);
			break;
		default:
			break;
		}
	}

	private void restoreViewState() {

		if (mPreviousSelectedActionView != null) {

			switch (mPreviousSelectedActionView.getId()) {

			case R.id.navigation_bar_item_home:
				mActivity.findViewById(R.id.navigation_bar_item_home_line)
						.setBackgroundColor(mItemLineDefaultColor);

				mPreviousSelectedActionView
						.setCompoundDrawablesWithIntrinsicBounds(0,
								R.drawable.tab_home01, 0, 0);
				mPreviousSelectedActionView.setTextColor(mItemTextColor);
				break;

			case R.id.navigation_bar_item_search:
				mActivity.findViewById(R.id.navigation_bar_item_search_line)
						.setBackgroundColor(mItemLineDefaultColor);

				mPreviousSelectedActionView
						.setCompoundDrawablesWithIntrinsicBounds(0,
								R.drawable.tab_search01, 0, 0);
				mPreviousSelectedActionView.setTextColor(mItemTextColor);

				break;
			case R.id.navigation_bar_item_category:
				mActivity.findViewById(R.id.navigation_bar_item_category_line)
						.setBackgroundColor(mItemLineDefaultColor);

				mPreviousSelectedActionView
						.setCompoundDrawablesWithIntrinsicBounds(0,
								R.drawable.tab_category01, 0, 0);
				mPreviousSelectedActionView.setTextColor(mItemTextColor);

				break;
			case R.id.navigation_bar_item_cart:
				mActivity.findViewById(R.id.navigation_bar_item_cart_line)
						.setBackgroundColor(mItemLineDefaultColor);

				mPreviousSelectedActionView
						.setCompoundDrawablesWithIntrinsicBounds(0,
								R.drawable.tab_cart01, 0, 0);
				mPreviousSelectedActionView.setTextColor(mItemTextColor);

				break;
			case R.id.navigation_bar_item_more:
				mActivity.findViewById(R.id.navigation_bar_item_more_line)
						.setBackgroundColor(mItemLineDefaultColor);

				mPreviousSelectedActionView
						.setCompoundDrawablesWithIntrinsicBounds(0,
								R.drawable.tab_account01, 0, 0);
				mPreviousSelectedActionView.setTextColor(mItemTextColor);

				break;
			}
		}
	}

	private class ActionEventResponser implements View.OnClickListener {

		private Activity mActivity;

		public ActionEventResponser(Activity mActivity) {

			this.mActivity = mActivity;
		}

		@Override
		public void onClick(View v) {

			Button button = (Button) v;
			restoreViewState();
			mPreviousSelectedActionView = button;

			switch (v.getId()) {

			case R.id.navigation_bar_item_home:

				//				redirect(HomeActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);
				break;
			case R.id.navigation_bar_item_search:

				//				redirect(SearchActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);	
				break;
			case R.id.navigation_bar_item_category:

				//				redirect(CategoryActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);				
				break;
			case R.id.navigation_bar_item_cart:

				//				redirect(CartActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);			
				break;
			case R.id.navigation_bar_item_more:

				//				redirect(MyAccountActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);	
				break;
			}
		}

		/**
		 * redirect to target page
		 * 
		 * @param targetActivity
		 * 			target page
		 * @param flags
		 * 			for activity self parameter
		 * @param enterAnim
		 * 			0 if no need anim
		 * @param exitAnim
		 * 			0 if no need anim
		 */
		@SuppressWarnings("rawtypes")
		private void redirect(Class targetActivity, int flags, int enterAnim,
				int exitAnim) {

			Intent intent = new Intent(mActivity, targetActivity);
			intent.setFlags(flags);
			mActivity.startActivity(intent);
			mActivity.overridePendingTransition(enterAnim, exitAnim);
		}
	}
}
