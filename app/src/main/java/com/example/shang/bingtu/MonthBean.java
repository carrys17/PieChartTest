package com.example.shang.bingtu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by shang on 2017/6/28.
 */

//MonthBean 就是每个月份，里面包含一个日期和obj，obj又包含title和value，都是根据json得来的信息。
//继承parcelable是因为后面用到的时候，把它put为parcelable进bundle对象
public class MonthBean implements Parcelable {
    public String date;
    public ArrayList<PieBean> obj;

    protected MonthBean(Parcel in) {
        date = in.readString();
    }

    public static final Creator<MonthBean> CREATOR = new Creator<MonthBean>() {
        @Override
        public MonthBean createFromParcel(Parcel in) {
            return new MonthBean(in);
        }

        @Override
        public MonthBean[] newArray(int size) {
            return new MonthBean[size];
        }
    };

    @Override
    public String toString() {
        return "MonthBean{" +
                "date='" + date + '\'' +
                ", obj=" + obj.toString() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
    }

    // 得到总的花费值
    public float getSum(){
        float sum = 0;
        for (PieBean pieBean:obj){
            sum+=pieBean.value;
        }
        return sum;
    }

    // 得到前面的总花费值，就是比如点击了娱乐，那值就是外卖的长度了，点击了其他，就是外卖加娱乐的值。因为外卖为第一个元素，json数据决定的
    public float getSum(int index){
        float sum = 0;
        for (int i=0;i<index;i++){
            sum += obj.get(i).value;
        }
        return sum;
    }



    class PieBean {
        public String title;
        public int value;

        @Override
        public String toString() {
            return "PieBean{" +
                    "title='" + title + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}
