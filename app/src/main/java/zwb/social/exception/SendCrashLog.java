package zwb.social.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author yyx
 * @2015-5-13
 * @desperation:
 * 
 */
public class SendCrashLog extends AsyncTask<String, String, Boolean> {
	public SendCrashLog() {
	}

	@Override
	protected Boolean doInBackground(String... params) {
		if (params[0].length() == 0)
			return false;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://www.mythou/getlog.php");
		// 这里把相关的异常信息转为http post请求的数据参数
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("model", params[0]));
			nameValuePairs.add(new BasicNameValuePair("device", params[1]));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		Log.d("SendCrashLog", "Device model sent.");
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
	}
}
