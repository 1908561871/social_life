package zwb.social.exception;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zwb.social.config.Constant;
import zwb.social.entity.bean.UserInfo;
import zwb.social.util.StringUtils;

/**
 * @author yyx
 * @2015-5-15
 * @desperation:全局Application
 * 
 */
public class CrashApplication extends Application {
	private static  String TAG="CrashApplication";
	public List<Activity> activityManager;
	public HashMap<String, Object> session;
	private static CrashApplication instance;
	public String deviceid;  // 设备ID
	public String osVersion; // 操作系统版本
	public String mobileType;// 手机型号
	public String version;   // app的versionName
	public int versionCode;  // app的versionCode
	@Override
	public void onCreate() {
		super.onCreate();
	    init();
        //测试
        test();
	}

    /**
     * 单例，返回一个实例
     * @return
     */
    public static CrashApplication getInstance() {
        if (instance == null) {

        }
        return instance;
    }
	public void init(){
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
        //fresco初始化
		Fresco.initialize(getApplicationContext());
		resignEntity();
		//初始化数据存储
		AVOSCloud.initialize(this, Constant.IM_APPID, Constant.IM_KEY);
		instance = this;
		session = new HashMap<String, Object>();
		activityManager = new ArrayList<Activity>();
		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			deviceid = getDeviceId();
			osVersion = Build.VERSION.RELEASE;
			mobileType = Build.MODEL;
			if (null != info) {
				version = info.versionName;
				versionCode = info.versionCode;
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 初始化实体
	 */
	public  void  resignEntity(){
		AVObject.registerSubclass(UserInfo.class);


	}



    public void test(){
    }


	/**
	 * 获取手机的设备号（imei）
	 *
	 * @return
	 */
	private String getDeviceId() {
		TelephonyManager mphonemanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = Constant.SPECIAL_IMEI;
		if (mphonemanger != null) {
			imei = mphonemanger.getDeviceId();
		}

		if (StringUtils.isBlank(imei)) {
			imei = Constant.SPECIAL_IMEI;
		}
		return imei;
	}




}
