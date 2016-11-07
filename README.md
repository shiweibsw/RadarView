# A Beautiful RadarView
![Screenshots](https://github.com/shiweibsw/RadarView/blob/master/Screenshots/device-2016-11-07-084135.png) 
![Screenshots](https://github.com/shiweibsw/RadarView/blob/master/Screenshots/device-2016-11-07-093919.png) 
![Screenshots](https://github.com/shiweibsw/RadarView/blob/master/Screenshots/device-2016-11-07-093849.png)

##Gradle
compile 'com.bsw.radarview.library:Library:0.0.1'
##Maven
    <dependency>
      <groupId>com.bsw.radarview.library</groupId>
      <artifactId>Library</artifactId>
      <version>0.0.1</version>
      <type>pom</type>
    </dependency>
##Property Description
| key           | Description   |
| ------------- |:-------------:|
| lineColor      | 背景线条颜色 |
| textColor      | 文字颜色      |
| textSize | 字体大小      |
| pointRadius | 节点半径      |
| shadowColor | 阴影区域颜色      |
| scale |相对父容器的缩放倍率      | 
| maxValue | 相对阴影数据的最大值      | 
##Use
###XML
       <com.bsw.radarview.library.RadarView
           android:id="@+id/radar_view"
           app:lineColor="@color/colorAccent"
           app:textSize="30"
           app:pointRadius="3dip"
           app:scale="0.7"
           app:maxValue="100"
           app:shadowColor="@color/colorAccent"
           app:textColor="@color/colorAccent"
           android:layout_width="match_parent"
           android:layout_height="match_parent"/>
           
###Java
    List<RadarValue> datas=new ArrayList<>();
    mRadarView.setDatas(datas);

