package zwb.social.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import de.greenrobot.event.EventBus;
import zwb.social.event.NetChangeEvent;
import zwb.social.util.SAFUtils;

public class NetChangeReceiver extends BroadcastReceiver {
    public NetChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            EventBus.getDefault().post(new NetChangeEvent(SAFUtils.checkNetworkStatus(context)));
        }
    }

}

