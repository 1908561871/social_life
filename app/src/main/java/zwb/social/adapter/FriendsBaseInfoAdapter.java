package zwb.social.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zwb.social.entity.bean.UserInfo;
import zwb.social.social.R;

/**
 * Created by zhangweibo on 2015/8/9.
 */
public class FriendsBaseInfoAdapter extends BaseAdapter{
    private Context context;

    private List<UserInfo> userInfos;

    public FriendsBaseInfoAdapter(Context context, List<UserInfo> userInfos) {
        this.context = context;
        this.userInfos = userInfos;
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return userInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        UserInfo userInfo=userInfos.get(i);
        if (view!=null){
            viewHolder= (ViewHolder) view.getTag();
        }else{
            view= LayoutInflater.from(context).inflate(R.layout.fragment_friends_item,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.tv_user_name.setText(userInfo.getUserName());
        viewHolder.tv_user_descrip.setText(userInfo.getUserDescrip());
        viewHolder.user_logo.setImageURI(Uri.parse(userInfo.getUserLogo()));
        return view;
    }

    class ViewHolder{
        @Bind(R.id.tv_user_name)
        TextView tv_user_name;
        @Bind(R.id.tv_user_descrip)
        TextView tv_user_descrip;
        @Bind(R.id.user_logo)
        SimpleDraweeView user_logo;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
