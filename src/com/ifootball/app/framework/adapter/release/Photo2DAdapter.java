package com.ifootball.app.framework.adapter.release;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.ifootball.app.activity.release.ReleaseImageActivity;
import com.ifootball.app.framework.widget.release.ViewHolder;
import com.myifootball.app.R;

public class Photo2DAdapter extends CommonAdapter<String> {
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new LinkedList<String>();

	/**
	 * 文件夹路径
	 */
	private String mDirPath;
	private Context mContext;

	public Photo2DAdapter(Context context, List<String> mDatas,
			int itemLayoutId, String dirPath) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.mContext = context;
	}

	@Override
	public void convert(ViewHolder helper, final String item, int position) {
		if (position == 0) {
			// 设置no_pic
			helper.setImageResource(R.id.release_image_item_iv,
					R.drawable.camera);
			((ImageView) helper.getView(R.id.release_image_item_select))
					.setVisibility(View.GONE);
			((ImageView) helper.getView(R.id.release_image_item_iv))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							//TODO 
						}
					});
		} else {
			// 设置no_pic
			helper.setImageResource(R.id.release_image_item_iv,
					R.drawable.pictures_no);
			// 设置no_selected
			helper.setImageResource(R.id.release_image_item_select,
					R.drawable.picture_unselected);
			// 设置图片
			helper.setImageByUrl(R.id.release_image_item_iv, mDirPath + "/"
					+ item);

			final ImageView mImageView = helper
					.getView(R.id.release_image_item_iv);
			final ImageView mSelect = helper
					.getView(R.id.release_image_item_select);
			mSelect.setVisibility(View.VISIBLE);
			mSelect.setColorFilter(null);
			// 设置ImageView的点击事件
			mSelect.setOnClickListener(new OnClickListener() {
				// 选择，则将图片变暗，反之则反之
				@Override
				public void onClick(View v) {

					// 已经选择过该图片
					if (mSelectedImage.contains(mDirPath + "/" + item)) {
						mSelectedImage.remove(mDirPath + "/" + item);
						mSelect.setImageResource(R.drawable.picture_unselected);
						mImageView.setColorFilter(null);
					} else if ((ReleaseImageActivity.picCount + mSelectedImage
							.size()) > 11) {
						Toast.makeText(mContext, "最多只能添加" + 12 + "张",
								Toast.LENGTH_SHORT).show();
						return;
					} else {

						// 未选择该图片
						mSelectedImage.add(mDirPath + "/" + item);
						mSelect.setImageResource(R.drawable.pictures_selected);
						mImageView.setColorFilter(Color.parseColor("#77000000"));
					}
				}
			});

			/**
			 * 已经选择过的图片，显示出选择过的效果
			 */
			if (mSelectedImage.contains(mDirPath + "/" + item)) {
				mSelect.setImageResource(R.drawable.pictures_selected);
				mImageView.setColorFilter(Color.parseColor("#77000000"));
			}
		}

	}

	public static void clear() {
		mSelectedImage.clear();
	}

}
