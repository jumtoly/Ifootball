package com.ifootball.app.framework.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

import com.ifootball.app.util.DisplayUtil;

public class MutiTouchGallery extends Gallery {
	private GestureDetector gestureScanner;
	private GalleryImageView imageView;
	private int screenWidth;
	private int screenHeight;

	public MutiTouchGallery(Context context, Activity activity) {
		super(context);

	}

	public MutiTouchGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MutiTouchGallery(Context context, AttributeSet attrs) {
		super(context, attrs);

		screenHeight =0;// ProductPhotoSpecificationActivity.screenHeight;
		screenWidth = 0;//ProductPhotoSpecificationActivity.screenWidth;

		gestureScanner = new GestureDetector(new MySimpleGesture());
		this.setOnTouchListener(new OnTouchListener() {

			float baseValue;
			float originalScale;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				View view = MutiTouchGallery.this.getSelectedView();
				if (view instanceof GalleryImageView) {
					imageView = (GalleryImageView) view;

					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						baseValue = 0;
						originalScale = imageView.getScale();
					}
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						if (event.getPointerCount() == 2) {
							float x = event.getX(0) - event.getX(1);
							float y = event.getY(0) - event.getY(1);
							float value = (float) Math.sqrt(x * x + y * y);
							if (baseValue == 0) {
								baseValue = value;
							} else {
								float scale = value / baseValue;
								imageView.zoomTo(originalScale * scale, x + event.getX(1), y + event.getY(1));

							}
						}
					}
				}
				return false;
			}

		});
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		View view = MutiTouchGallery.this.getSelectedView();
		if (view instanceof GalleryImageView) {
			imageView = (GalleryImageView) view;

			float v[] = new float[9];
			Matrix m = imageView.getImageMatrix();
			m.getValues(v);
			float left, right;
			float width, height;
			width = (int) (imageView.getScale() * imageView.getImageWidth());
			height = (int) (imageView.getScale() * imageView.getImageHeight());
			
			//allan update
			if ((screenHeight-height)/2>0) {
				distanceY=0;
			}
			
			if ((int) width <= screenWidth && (int) height <= screenHeight) {

				left = v[Matrix.MTRANS_X];
				right = left + width;
				if (left < -screenWidth / 4 || right > screenWidth + screenWidth / 4) {
					if (getSelectedItemPosition() == 0) {
						if (right <= screenWidth) {
							super.onScroll(e1, e2, distanceX, distanceY);
						}
					} else if (getSelectedItemPosition() == (getCount() - 1)) {
						if (left >= 0) {
							super.onScroll(e1, e2, distanceX, distanceY);
						}
					} else {
						super.onScroll(e1, e2, distanceX, distanceY);
					}

				}
				imageView.postTranslate(-distanceX, -distanceY);

			} else {
				left = v[Matrix.MTRANS_X];
				right = left + width;
				Rect r = new Rect();
				imageView.getGlobalVisibleRect(r);

				if (distanceX > 0) {
					if (r.left > 0) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else if (right < screenWidth) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				} else if (distanceX < 0) {
					if (r.right < screenWidth) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else if (left > 0) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				}

			}

		} else {
			super.onScroll(e1, e2, distanceX, distanceY);
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		int event;
		if (isScrollingLeft(e1, e2)) {
			event = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			event = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(event, null);
		return true;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureScanner.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			View view = MutiTouchGallery.this.getSelectedView();
			if (view instanceof GalleryImageView) {
				imageView = (GalleryImageView) view;
				float width = (int) (imageView.getScale() * imageView.getImageWidth());
				float height = (int) (imageView.getScale() * imageView.getImageHeight());

				if (width > screenWidth || height > screenHeight) {
					break;
				}
				float v[] = new float[9];
				Matrix m = imageView.getImageMatrix();
				m.getValues(v);
				float top = v[Matrix.MTRANS_Y];
				float bottom = top + height;
				if (top < 0) {
					imageView.postTranslateDur(-top, 200f);
				}
				if (bottom > (screenHeight - DisplayUtil.getPxByDp(getContext(), 148))) {
					imageView
							.postTranslateDur((screenHeight - DisplayUtil.getPxByDp(getContext(), 148)) - bottom, 200f);
				}

				if (getCount() == 1) {

					float left = v[Matrix.MTRANS_X];
					float right = left + width;

					if (right > screenWidth) {
						imageView.postTranslateDurHorizontal(screenWidth - right, 200f);
					}
					if (left < 0) {
						imageView.postTranslateDurHorizontal(-left, 200f);
					}

				} else if (getSelectedItemPosition() == 0) {
					float left = v[Matrix.MTRANS_X];
					float right = left + width;
					if (right >= screenWidth) {
						imageView.postTranslateDurHorizontal(screenWidth - right, 200f);
					}
				} else if (getSelectedItemPosition() == (getCount() - 1)) {
					float left = v[Matrix.MTRANS_X];
					if (left <= 0) {
						imageView.postTranslateDurHorizontal(-left, 200f);
					}

				}

			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private class MySimpleGesture extends SimpleOnGestureListener {
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			View view = MutiTouchGallery.this.getSelectedView();
			if (view instanceof GalleryImageView) {
				imageView = (GalleryImageView) view;
				imageView.setScaleRate(imageView.getScaleRate() + 0.5f);
				imageView.zoomTo(imageView.getScaleRate(), screenWidth / 2, screenHeight / 2, 200f);
				/*if (imageView.getScale() > imageView.getScaleRate()) {
					imageView.zoomTo(imageView.getScaleRate(), screenWidth / 2, screenHeight / 2, 200f);
				} else {
					imageView.zoomTo(1.0f, screenWidth / 2, screenHeight / 2, 200f);
				}*/

			}
			return true;
		}
	}
}
