package com.example.weather.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.example.weather.BusProvider;
import com.example.weather.R;
import com.example.weather.baiduStore.LocationService;
import com.example.weather.baiduStore.MyApplication;
import com.example.weather.utils.ScreenShot;

import butterknife.Bind;
import butterknife.ButterKnife;

//// TODO: 2016/6/7 安卓6.0会崩溃
public class HomeActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    public
    Toolbar toolbar;
    @Bind(R.id.HomeLayout)
    public
    RelativeLayout HomeLayout;
    public String City = "武汉";
    @Bind(R.id.mainLayout)
    FrameLayout mainLayout;
    private String Tag = HomeActivity.class.getSimpleName();
    public FragmentManager supportFragmentManager;
    private HomePresenter homePresenter;
    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        homePresenter = new HomePresenter(HomeActivity.this);
        //添加toobar和左边的点击图标事件
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "该功能尚未完成，请完成后使用！", Toast.LENGTH_LONG).show();
               // toolbar.setTitle("aa");
            }
        });

        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, new HomeFragment()).commit();
    }

    public void logMsg(String str) {
        try {
            if (toolbar != null) {
                toolbar.setTitle(str);
                City = str.substring(0, str.length() - 1).toString().trim();
                locationService.unregisterListener(mListener);
                homePresenter.getJsonData(City);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();

    }

    @Override
    protected void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            ScreenShot.share(HomeActivity.this);
        }
        return true;
    }

    /*****
     * @see "copy funtion to you project"
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append(location.getCity());
                logMsg(sb.toString());
                City = sb.toString();
            }
        }
    };


}
