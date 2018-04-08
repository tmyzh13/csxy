package com.bm.csxy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

public class IntentUtil {
	/**
	 * 启动页面跳转
	 * 
	 * @param mContext
	 * @param cls
	 *            目的类
	 * @param data
	 *            传递数据 void
	 */
	public static void startActivity(Context mContext, Class<?> cls, Bundle data) {
		Intent intent = new Intent(mContext, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		if (data != null) {
			intent.putExtras(data);
		}

		mContext.startActivity(intent);
	}

	/**
	 * 启动页面跳转(有返回处理)
	 * 
	 * @param cls
	 *            目的类
	 * @param data
	 *            传递数据
	 * @param requestCode
	 *            请求编码 void
	 */
	public static void startActivityForResult(Activity mActivity, Class<?> cls,
			Bundle data, int requestCode) {
		Intent intent = new Intent(mActivity, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		if (data != null) {
			intent.putExtras(data);
		}
		// 这个方法，在Activity范围下，不再Context范围下
		mActivity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 安装APK程序代码
	 * 
	 * @param context
	 * @param apkPath
	 */
	public static void ApkInstall(Context context, String apkPath) {
		File fileAPK = new File(apkPath);
		if (fileAPK.exists()
				&& fileAPK.getName().toLowerCase().endsWith(".apk")) {
			Intent install = new Intent();
			install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			install.setAction(Intent.ACTION_VIEW);
			install.setDataAndType(Uri.fromFile(fileAPK),
					"application/vnd.android.package-archive");
			context.startActivity(install);// 安装
		}
	}

	/**
	 * 打电话
	 * 
	 * @param content
	 */
	public static void tell(Context context, String content) {
		Intent intent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + content));
		context.startActivity(intent);
	}

	/**
	 * 
	 * Description: URI转变Path问题
	 * 
	 * @param context
	 * @param uri
	 *            本地图片的uri
	 * @param status
	 *            1表示来源是相册；2表示来源是拍照
	 * @return
	 */
	public static String uri2FilePath(Activity context, Uri uri, int status) {
		Cursor actualimagecursor = null;
		int actual_image_column_index = 0;
		if (status == 1) {
			/**
			 * 相册
			 */
			String[] proj = { MediaStore.Images.Media.DATA };
			actualimagecursor = context.managedQuery(uri, proj, null, null,
					null);
			actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String img_path = actualimagecursor
					.getString(actual_image_column_index);
			return img_path;
		} else if (status == 2) {
			/**
			 * 拍照
			 */
			return uri.getPath();
		}
		return null;
	}

	/************ 选择照片*****************/
	
	/**
	 * 调用相册请求码
	 */
	public static final int REQUEST_GALLERY = 0x00001001;
	/**
	 * 调用照相机请求码
	 */
	public static final int REQUEST_CAMERA = 0x00001002;
	/**
	 * 调用图片裁剪请求码
	 */
	public static final int REQUEST_CROP_IMAGE = 0x00001003;

	// 打开相册
	public static void pickGallery(Activity activity) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			activity.startActivityForResult(intent, REQUEST_GALLERY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 打开相机
	public static void takePhoto(Activity activity,File imageFile) {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                activity.startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                Toast.makeText(activity, "无内置存储空间",Toast.LENGTH_LONG).show();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 打开裁剪 将头像大小剪裁
	public static void cropImage(Activity activity, Uri inUri,Uri outUri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(inUri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
		intent.putExtra("return-data", false);//设置为不返回数据

		activity.startActivityForResult(intent, REQUEST_CROP_IMAGE);
	}

}
