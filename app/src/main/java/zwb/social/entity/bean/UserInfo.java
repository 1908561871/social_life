package zwb.social.entity.bean;


import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by zhangweibo on 2015/8/23.
 */
@AVClassName("UserInfo")
public class UserInfo extends AVObject {

    public static final Creator CREATOR = AVObjectCreator.instance;
    public  static final  String userName="userName";


    public UserInfo() {
    }

    public UserInfo(Parcel in){
        super(in);
    }

    public String getUserName() {
        return getString("userName");
    }

    public void setUserName(String userName) {
        put("userName",userName);
    }

    public String getUserDescrip() {
        return getString("userDescrip");
    }

    public void setUserDescrip(String userDescrip) {
        put("userDescrip",userDescrip);
    }

    public String getUserLogo() {
        return getString("userLogo");
    }

    public void setUserLogo(String userLogo) {
        put("userLogo",userLogo);
    }

    public String getUserNick() {
        return getString("userNick");
    }

    public void setUserNick(String userNick) {
        put("userNick",userNick);
    }
}
