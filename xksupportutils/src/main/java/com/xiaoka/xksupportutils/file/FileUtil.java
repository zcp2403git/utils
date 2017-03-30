package com.xiaoka.xksupportutils.file;

import android.os.Environment;
import android.text.TextUtils;

import com.xiaoka.xksupportutils.app.ApplicationUtil;
import com.xiaoka.xksupportutils.secret.MD5Util;

import java.io.File;

/**
 * @author yuefeng
 * @version 1.2.0
 * @date 15/7/29
 */
public class FileUtil {
	public static File getBasePath() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			//有sdcard时存在/mnt/sdcard/Andorid/data/包名/cache目录下
			return ApplicationUtil.getContext().getExternalCacheDir();
		} else {
			//如果SDCARD不存在的情况。/data/data/包名/cache
			return ApplicationUtil.getContext().getCacheDir();
		}
	}

	public static File getPhotoPath(String pPicDirectoryName) {
		File _picFile = new File(getBasePath().getPath() + File.separator + pPicDirectoryName);
		if (!_picFile.exists()) {
			_picFile.mkdirs();
		}
		return _picFile;
	}

	public static String getPhotoFileName(String pPicLocalPath, String pFilePostfix) {
		return MD5Util.getMD5(pPicLocalPath) + pFilePostfix + ".jpg";
	}

	/**
	 * 删除SD卡或者手机的缓存图片和目录
	 */
	public static void deleteFile(String path) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}

		dirFile.delete();
	}


}
