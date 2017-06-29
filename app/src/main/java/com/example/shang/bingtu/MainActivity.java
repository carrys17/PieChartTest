package com.example.shang.bingtu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager mViewPager;

    private String mJson =
            "[{\"date\":\"2016年5月\",\"obj\":[{\"title\":\"外卖\",\"value\":34},{\"title\":\"娱乐\",\"value\":21},{\"title\":\"其他\",\"value\":45}]}," +
            "{\"date\":\"2016年6月\",\"obj\":[{\"title\":\"外卖\",\"value\":32},{\"title\":\"娱乐\",\"value\":22},{\"title\":\"其他\",\"value\":42}]}," +
            "{\"date\":\"2016年7月\",\"obj\":[{\"title\":\"外卖\",\"value\":34},{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":24}]}," +
            "{\"date\":\"2016年8月\",\"obj\":[{\"title\":\"外卖\",\"value\":145},{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":124}]}]";
    private ArrayList<MonthBean> mData;
    private Button mPreButton;
    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager)findViewById(R.id.id_viewpager);
        mPreButton = (Button) findViewById(R.id.id_btn_pre);
        mNextButton = (Button) findViewById(R.id.id_btn_next);
        mPreButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        initData();
        initView();

    }

    private void initData() {
        Gson gson = new Gson();
        mData = gson.fromJson(mJson,new TypeToken<ArrayList<MonthBean>>(){}.getType());
    }

    private void initView() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MyFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });

        upDateBtnText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_pre:
                if (mViewPager.getCurrentItem()!=0){
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
                }
                break;
            case R.id.id_btn_next:
                if (mViewPager.getCurrentItem()!=mViewPager.getAdapter().getCount()-1){
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                }
                break;
        }

        upDateBtnText();
    }

    private void upDateBtnText() {

        if (mViewPager.getCurrentItem()!=0){
            mPreButton.setText(handleText(mData.get(mViewPager.getCurrentItem()-1).date));
        }else {
            mPreButton.setText("没有了！");
        }

        if (mViewPager.getCurrentItem()!=mViewPager.getAdapter().getCount()-1){
            mNextButton.setText(handleText(mData.get(mViewPager.getCurrentItem()+1).date));
        }else {
            mNextButton.setText("没有了！");
        }

    }

    private CharSequence handleText(String date) {
        return date.substring(date.indexOf("年")+1);
    }
}
