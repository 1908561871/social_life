package zwb.social.net;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import zwb.social.config.Constant;

/**
 * Created by Administrator on 2015/7/25.
 */
public class HttpUtil {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static{
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }
    /**
     * �ò��Ὺ���첽�̡߳�
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException{
        return mOkHttpClient.newCall(request).execute();
    }
    /**
     * �����첽�̷߳�������
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback){
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }
    /**
     * �����첽�̷߳�������, �Ҳ����ⷵ�ؽ��ʵ�ֿ�callback��
     * @param request
     */
    public static void enqueue(Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {


            @Override
            void onFailure(String body) {

            }

            @Override
            void onSuccess(String body) {

            }
        });
    }
    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
   // private static final String CHARSET_NAME = "UTF-8";
    private static final String CHARSET_NAME = "GBK";

    /**
     * ����ʹ����HttpClinet��API��ֻ��Ϊ�˷���
     * @param params
     * @return
     */
    public static String formatParams(List<BasicNameValuePair> params){
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }
    /**
     * ΪHttpGet �� url �������Ӷ��name value ����
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params){
        return url + "?" + formatParams(params);
    }
    /**
     * ΪHttpGet �� url ��������1��name value ����
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value){
        return url + "?" + name + "=" + value;
    }

    /**
     * �ύjson���
     * @param url
     * @param json
     * @param callback
     */

    public static void post(String url,String json,Callback callback){

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
       mOkHttpClient.newCall(request).enqueue(callback);

    }


      public static void post(String url,RequestBody body,Callback callback){


          Request request = new Request.Builder()
                  .url(url)
                  .post(body)
                  .build();

          mOkHttpClient.newCall(request).enqueue(callback);

      }


    public void get(String url,Callback callback){
        Request request = new Request.Builder()
                .url(url).get()
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);

    }

    /**
     *
     * @param url  ���ʵĲ���
     * @param params  ���ʵĲ����
     * @param callback �ص�
     */
    public static void get(String url,HashMap<String,String> params,Callback callback){
        if (params!=null){

            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                url=attachHttpGetParam(url,key.toString(),val.toString());
               }
        }
        Request request=new Request.Builder().url(url).addHeader("apikey", Constant.WEATHER_KEY).get().build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

}