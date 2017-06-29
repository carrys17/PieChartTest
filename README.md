# PieChartTest
这是一个关于MPAndroidChart绘图框架的中的PieChart饼状图的小Demo。
其中的json数据就没有像实际开发那样连接服务器得到，而是直接定义为String，通过gson去使用而已。
布局用的Viewpager和Fragment的结合使用，以及利用了百分比布局。

首先下载关于gson的包
 将.jar包放在libs下，右键add to library
 
在app.build中加入
    compile 'com.github.PhilJay:MPAndroidChart:v2.2.5'
    compile 'com.android.support:percent:25.3.1'


最终效果图

![xiaoguotu1](https://github.com/carrys17/Sucai/blob/master/device-2017-06-29-112617.png)
![xiaoguotu2](https://github.com/carrys17/Sucai/blob/master/device-2017-06-29-112639.png)




