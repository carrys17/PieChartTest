package com.example.shang.bingtu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shang on 2017/6/28.
 */

public class MyFragment extends Fragment implements OnChartValueSelectedListener {

    private static final String DATA_KEY = "com.example.shang.MyFragment";
    private MonthBean mData;
    private com.example.shang.bingtu.PieChart mChart;
    TextView tv;
    LinearLayout ll;
    TextView tv_month;

    public static MyFragment newInstance(MonthBean data) {

        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY,data);

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mData = arguments.getParcelable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart,null);
        mChart = (com.example.shang.bingtu.PieChart) view.findViewById(R.id.id_chart);
        tv = (TextView) view.findViewById(R.id.id_tx);
        ll = (LinearLayout) view.findViewById(R.id.id_ll);
        tv_month = (TextView) view.findViewById(R.id.id_month);

        initView();
        return view;

    }

    private void initView() {
        setData();
        mChart.setCenterText(getCenterText());
        mChart.setDrawSliceText(false); //将饼状图的标题title去掉
        mChart.getData().getDataSet().setDrawValues(false); // 将饼状图的价格也就是value去掉
        mChart.getLegend().setEnabled(false); //左下角各个分布大小不显示
        mChart.setDescription(""); // 右下角详情text设置为""，相当于空
        mChart.setRotationEnabled(false); //把旋转去掉，为了不跟viewpager的切换争夺监听事件
        mChart.setOnChartValueSelectedListener(this); // 设置饼状图的点击监听
        ll.setVisibility(View.INVISIBLE);
        tv_month.setText(mData.date.toString());
    }

    private void setData() {
        List<String> titles = new ArrayList<>();
        List<Entry> entrys = new ArrayList<>();
        for (int i = 0; i < mData.obj.size(); i++) {
            MonthBean.PieBean pieBean = mData.obj.get(i);
            titles.add(pieBean.title);
            entrys.add(new Entry(pieBean.value, i));
        }
        PieDataSet dataSet = new PieDataSet(entrys,"");
        dataSet.setColors(new int[]{Color.rgb(216, 77, 719), Color.rgb(183, 56, 63), Color.rgb(247, 85, 47)}); // 饼状图颜色
        PieData pieData = new PieData(titles, dataSet);
        pieData.setValueTextSize(22); // 设置饼状图中的文字大小
        mChart.setData(pieData);
    }


    // 点击了饼状图
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        float proportion = 360f/mData.getSum();
        // 使得被点击的选项旋转到最下方
        float angle = 90 - proportion*(mData.obj.get(e.getXIndex()).value)/2 - mData.getSum(e.getXIndex())*proportion;
        mChart.setRotationAngle(angle);

        upDateText(e.getXIndex());

    }

    private void upDateText(int index) {
        ll.setVisibility(View.VISIBLE);
        tv.setText(mData.obj.get(index).title+":"+mData.obj.get(index).value);

    }

    // 收回饼状图
    @Override
    public void onNothingSelected() {
        ll.setVisibility(View.INVISIBLE);
    }

    public CharSequence getCenterText() {
        CharSequence centerText = "总支出\n" + mData.getSum() + "元";
        //SpannableString类型可以达到跟html一样的效果
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(64, true), 3, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
