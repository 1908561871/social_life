package zwb.social.exception;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yyx
 * @2015-5-15
 * @desperation:
 * 
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "ChrashHandler -->>";
	// 系统默认的UncaughtException
	private UncaughtExceptionHandler defaultHandler;
	// CrashHandler实例
	private static CrashHandler crashHandler = new CrashHandler();
	private Context context;
	private Map<String, String> infos = new HashMap<String, String>();
	// 格式化日期时间
	private DateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

	private CrashHandler() {
	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: TODO(获取CrashHandler单例)
	 * @author: yyx
	 * @version: 2015-5-15 下午6:56:13
	 */
	public static CrashHandler getInstance() {
		return crashHandler;
	}

	public void init(Context context) {
		this.context = context;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 发生异常时转入该函数进行处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (handleException(ex) && defaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			defaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, e.toString());
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	private boolean handleException(Throwable throwable) {
		if (throwable == null) {
			return false;
		}
		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(context, "程序出现异常，即将退出.....", Toast.LENGTH_SHORT)
						.show();
				Looper.loop();
			};
		}.start();
		collectDeviceInfo(context);
		saveInfo(throwable);
		return true;
	}

	/**
	 * 
	 * @Title: collectDeviceInfo
	 * @Description: TODO(收集设备信息)
	 * @author: yyx
	 * @throws
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 
	 * @Title: saveCrashInfo2File
	 * @Description: TODO(保存错误信息到本地文件中)
	 * @author: yyx
	 * @throws
	 */
	@SuppressLint("SdCardPath")
	private String saveInfo(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = time + "-" + timestamp + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/crash/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}
}
