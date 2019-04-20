# MWidget
Android自定义常用控件，力争做到使用简洁高效
### v1.0.0
1.MProgressBar
```java
<cn.coderdream.mwidget.progressbar.MProgressBar
        android:layout_width="300dp"
        android:layout_height="20dp"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="30dp"
        app:mProgressBar_background_color="@color/colorPrimaryDark"
        app:mProgressBar_progress_background_color="@color/colorAccent"
        app:mProgressBar_corners="10dp"
        app:mProgressBar_currentSize="60"
        app:mProgressBar_stroke="2dp"/>
```
其中对应的属性如下：
```java
  <declare-styleable name="MProgressBar">
        <!--进度条的最底层颜色-->
        <attr name="mProgressBar_background_color" format="color"/>
        <!--进度条的进度的颜色-->
        <attr name="mProgressBar_progress_background_color" format="color"/>
        <!--进度条的边框颜色-->
        <attr name="mProgressBar_stroke" format="dimension"/>
        <!--进度条的圆角-->
        <attr name="mProgressBar_corners" format="dimension"/>
        <!--进度条的目标值-->
        <attr name="mProgressBar_currentSize" format="integer"/>
        <!--进度条的最大值-->
        <attr name="mProgressBar_totalSize" format="integer"/>
    </declare-styleable>
```