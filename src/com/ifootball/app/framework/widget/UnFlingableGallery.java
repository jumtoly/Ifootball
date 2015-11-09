package com.ifootball.app.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class UnFlingableGallery extends Gallery {

	public UnFlingableGallery(Context context) {
		super(context);
	}

	public UnFlingableGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UnFlingableGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		int event;
		if ((Math.abs(e2.getX() - e1.getX()) > 80)) {

			if (isScrollLeft(e1, e2)) {
				event = KeyEvent.KEYCODE_DPAD_LEFT;
			} else {
				event = KeyEvent.KEYCODE_DPAD_RIGHT;
			}
			onKeyDown(event, null);
		}
		return true;
	}

	private boolean isScrollLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}
}
