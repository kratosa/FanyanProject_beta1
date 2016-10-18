package com.example.fanyan.fanyanproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String[] AD_URLS = {
            "http://img3.3lian.com/2013/v9/87/118.jpg",
            "http://img3.3lian.com/2013/c2/43/95.jpg",
            "http://img3.3lian.com/2013/c2/43/97.jpg",
            "http://img3.3lian.com/2013/v9/87/104.jpg"
    };

    private Button btnMainlog;
    private List<String> datas;
    //适配器
    private ArrayAdapter<String> testAdapter;
    //ListView
    private ListView testLv;

    //刷新的布局
    private SwipeRefreshLayout refreshLayout;



    //置顶按钮
    private ImageView backTopBtn;


    private boolean isExit;
    private Handler handler;
    //广告条View集合
    private List<ImageView> adViews;

    //广告适配器
    private ADAdapter adAdapter;

    //广告ViewPager
    private ViewPager adViewPager;

    //广告圆点的布局
    private LinearLayout adLayout;

    //广告切换到下一页的Handler
    private Handler adHandler = new Handler();

    //让当前页面往后走一页
    private Runnable adRunnable = new Runnable() {
        @Override
        public void run() {
            //获得当前画面的位置
            int position = adViewPager.getCurrentItem();
            position++;
            //重新设置位置
            adViewPager.setCurrentItem(position);

            //无限自动轮播实现了
            adHandler.postDelayed(adRunnable, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initData();
        initViewPager();
        initADDot();


        initData1();
        initView1();
        initSwipeRefreshLayout1();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isExit = false;
            }
        };

        findViewById(R.id.where_btn).setOnClickListener(this);
        findViewById(R.id.login_view).setOnClickListener(this);
        findViewById(R.id.first_page).setOnClickListener(this);
        findViewById(R.id.second_page).setOnClickListener(this);
        findViewById(R.id.third_page).setOnClickListener(this);
        findViewById(R.id.add_info).setOnClickListener(this);
        findViewById(R.id.third_page).setOnClickListener(this);


        btnMainlog = (Button) findViewById(R.id.login_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.where_btn:
                Intent intent_where = new Intent(MainActivity.this,WhereAt.class);
                startActivity(intent_where);
                break;
            case R.id.login_view:
                Intent intent_login = new Intent(MainActivity.this,Login.class);
                startActivity(intent_login);
                break;
            case R.id.first_page:
                LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.second_show);
                linearLayout1.setVisibility(View.GONE);
                LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.first_show);
                linearLayout2.setVisibility(View.VISIBLE);
                LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.third_show);
                linearLayout3.setVisibility(View.GONE);

                break;
            case R.id.second_page:
                LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.second_show);
                linearLayout4.setVisibility(View.VISIBLE);
                LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.first_show);
                linearLayout5.setVisibility(View.GONE);
                LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.third_show);
                linearLayout6.setVisibility(View.GONE);

                break;
            case R.id.third_page:
                LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.first_show);
                linearLayout7.setVisibility(View.GONE);
                LinearLayout linearLayout8 = (LinearLayout) findViewById(R.id.second_show);
                linearLayout8.setVisibility(View.GONE);
                LinearLayout linearLayout9 = (LinearLayout) findViewById(R.id.third_show);
                linearLayout9.setVisibility(View.VISIBLE);



                break;

            case R.id.add_info:
                Intent intent_add = new Intent(MainActivity.this,AddInfo.class);
                startActivity(intent_add);
                break;

        }
    }


    private void initSwipeRefreshLayout1() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_refresh);
        // 刷新监听
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("Tag", "-------------> onRefresh");
                //启动异步任务，实现网络下载数据
                new DownloadTask().execute();
            }
        });

    }

    private void initView1() {
        testAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        testLv = (ListView) findViewById(R.id.lv_test);
        testLv.setAdapter(testAdapter);
    }

    private void initData1() {
        datas = new ArrayList<>();
        for (int i =0 ; i< 10; i++) {
            datas.add("招聘信息" + i);
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(!isExit){
                isExit = true;
                handler.sendEmptyMessageDelayed(0, 1500);
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止无限轮播
        adHandler.removeCallbacks(adRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //自动轮播
        adHandler.postDelayed(adRunnable, 2000);
    }

    //初始化广告圆点
    private void initADDot() {
        adLayout = (LinearLayout) findViewById(R.id.layout_ad_dot);
        for (int i=0; i<AD_URLS.length; i++) {
            //动态生成View
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 20;
            iv.setLayoutParams(layoutParams);
            if (i == 0) {
                iv.setImageResource(R.drawable.onselect);
            } else {
                iv.setImageResource(R.drawable.outselect);
            }
            adLayout.addView(iv);
        }

    }

    private void initViewPager() {
        adAdapter = new ADAdapter(adViews);
        adViewPager = (ViewPager) findViewById(R.id.vp_ad);
        adViewPager.setAdapter(adAdapter);
        adViewPager.setCurrentItem(Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2 % adViews.size());

        //ViewPager滑动监听
        adViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                //重新设置圆点状态
                setADDot(position % adViews.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //ViewPager的触屏处理，解决触屏时停止无限轮播
        adViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int actiton = motionEvent.getAction();
                switch (actiton) {
                    case MotionEvent.ACTION_DOWN: //按下
                        adHandler.removeCallbacks(adRunnable);
                        break;
                    case MotionEvent.ACTION_UP: //放开
                        adHandler.postDelayed(adRunnable, 4000);
                        break;
                }
                return false;
            }
        });
    }

    //初始化广告条View集合
    private void initData() {
        adViews = new ArrayList<>();
        for (int i=0; i<AD_URLS.length; i++) {
            //动态生成View
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //使用Picasso加载网络图片
            Picasso.with(this).load(AD_URLS[i]).into(iv);
            adViews.add(iv);
        }
    }

    //广告圆点高亮显示
    private void setADDot (int position) {
        for (int i=0; i<adLayout.getChildCount(); i++) {
            ImageView iv = (ImageView) adLayout.getChildAt(i);
            if (i == position) {
                iv.setImageResource(R.drawable.onselect);
            } else {
                iv.setImageResource(R.drawable.outselect);
            }
        }
    }




    class DownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //模拟网络加载耗时
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String data = "招聘信息" + datas.size();
            datas.add(0, data);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //刷新UI
            testAdapter.notifyDataSetChanged();
            //结束刷新
            refreshLayout.setRefreshing(false);
        }
    }
}
