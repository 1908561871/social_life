package zwb.social.net;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2015/7/26.
 */
abstract class Callback implements com.squareup.okhttp.Callback{
    @Override
    public void onFailure(Request request, IOException e) {
        onFailure("");
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (response.isSuccessful()){
            onSuccess(response.body().string());
        }else{
            onFailure("");
        }
    }

    abstract void onFailure(String body);


    abstract void onSuccess(String body);


}
