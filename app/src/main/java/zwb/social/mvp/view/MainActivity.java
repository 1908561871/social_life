package zwb.social.mvp.view;

import android.content.res.ColorStateList;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import zwb.social.app.BaseActivity;
import zwb.social.entity.bean.MainTab;
import zwb.social.mvp.view.iview.IMainView;
import zwb.social.mvp.presenter.MainPresenter;
import zwb.social.social.R;

public class MainActivity extends BaseActivity implements IMainView, TabHost.OnTabChangeListener {
    @Bind(R.id.navigation)
   NavigationView navigation;
    //内容显示区
    @Bind(R.id.fl_content)
    FrameLayout fl_content;
    @Bind(R.id.fth_tabhost)
    FragmentTabHost tabHost;
    private MainPresenter mainPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTab();
        mainPresenter=new MainPresenter(this);
        mainPresenter.IMinit(this);
        navigation.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.secondary_text)));
    }




    public void initTab(){
        if (android.os.Build.VERSION.SDK_INT > 10) {
            tabHost.getTabWidget().setShowDividers(0);
        }
        MainTab []tabs=MainTab.values();
        for(int i = 0; i <tabs.length; i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(getString(tabs[i].getTitle()));
            tabSpec.setIndicator(getTabItemView(tabs[i]));
            //将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec, tabs[i].getClz(), null);
        }
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(this);
    }


    public View getTabItemView(MainTab tab){
         View  view= getLayoutInflater().inflate(R.layout.activity_tab_item,null);
         TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
         ImageView  iv_icon= (ImageView) view.findViewById(R.id.iv_icon);
         tv_title.setText(getString(tab.getTitle()));
         iv_icon.setImageDrawable(getResources().getDrawable(tab.getDrawable()));
         return view;
    }


    @Override
    public void onTabChanged(String s) {

    }
}
