# 动画概览
## Animate bitmaps（动画位图）
动态变化一个小标志, 用 Animate Drawable Graphics.
## Animate UI visibility and motion
改变布局中的一个view的位置或visibe属性
## Physics-based motion
物理运动
Two common physics-based animations are the following:
* Spring Animation
* Fling Animation、

使用ObjectAnimator API构建的动画相对静止，并且就固定的持续时间。如果想做出更改，则需要先取消动画，使用新的属性设置，然后在重新配置动画（不连贯）。

基于物理的动画API构建的动画DynamicAnimation是由力驱动的，目标值的变化导致力的变化，新力量适用于现有的速度，从而不断向新目标过渡。
## 动画布局更改
可以使用转化框架在当前活动或片段中交换布局时创建动画。只需要指定开始和结束布局，以及要使用的动画类型（有点像幻灯片切换）。
可以用来交换整个UI或仅仅移动/替换一些视图。


起始布局和结束布局均存储在a中Scene，但起始场景通常是从当前布局自动确定的。然后创建一个Transition告诉系统所需的动画类型，然后调用TransitionManager.go()，系统运行动画来交换布局。

## 活动之间的动画（Android5.0 API 21）
创建活动之间转换的动画，基于动画布局更改的相同转换框架，支持在不同活动中的布局之间创建动画。

startActivity()，通过他提供的一组选项ActivityOptions.makeSceneTransitionAnimation(),这个选项可以包括在活动之间共享哪些视图，因此转换框架可以再动画期间连接他们。

#Property Animation Overview
属性动画系统，当想要对属性动画更改，只需要更改他的一些属性值，比如移动到哪里、时间长度等。

属性动画系统允许定义动画的一下特征：
* 持续时间（duration）：指定动画的持续时间，默认长度为300ms
* 时间差值：指定通过时间计算属性值的函数
* 重复计数和行为：指定是否在到达持续时间结束时重复动画及重复动画的次数。还可以指定是否要反向播放动画。
* 动画设置：将动画分组为一起或按顺序或在指定延迟后播放的逻辑集。
* 帧刷新延迟：指定刷新动画帧的频率。默认每10ms刷新一次，但应用程序刷新帧的速度最终取决于系统整体的繁忙程度以及系统为基础计时器提供服务的速度。

## 属性动画工作原理
属性动画的类继承关系。
![示意图](./imgs/valueanimator.png)

在ValueAnimator封装多个成员：
* TimeInterpolator : 定义差值函数
* TypeEvaluator：定义被更改的属性的计算方式

当ValueAnimator计算完成百分比时，他调用当前设置的TimeInterpolator来计算内插分数。

## 视图动画（view animation）和属性动画（property animation）的区别
视图动画仅仅提供为View对象设置动画的功能，因此如果需要为非View对象设置动画，则必须要通过代码实现自己的逻辑。另外，view动画受到约束，因为仅仅暴露了View对象的一些方面。

view动画另一个缺点是，它只修改绘制View的位置，而不是View本身。比如说对于一个按钮在屏幕上移动，该按钮会被正确绘制，但是单击该按钮的实际位置没有改变。

使用属性动画系统，没有这些限制，可以为任何对象的任何属性设置动画，并且实际上修改了对象本身。

# 为可绘制图形添加动画
## Use AnimationDrawable
在xml中定义多帧图像：
```xml
<animation-list xmlns:android = "http://schemas.android.com/apk/res/android"
    android:oneshot = "true" >
    <item android:drawable = "@drawable/rocket_thrust1" android:duration = "200" />   
    <item android:drawable = "@drawable/rocket_thrust2" android:duration = "200" />   
    <item android:drawable = "@drawable/rocket_thrust3" android:duration = "200" />   
</animation-list>
```
然后可以在代码里控制播放（oneshot=true会控制播放一次后，停留在最后一帧，false会循环播放）。
```java
AnimationDrawable rocketAnimation ;

public void onCreate ( Bundle savedInstanceState ) {  
  super . onCreate ( savedInstanceState );
  setContentView ( R . layout . main );

  ImageView rocketImage = ( ImageView ) findViewById ( R . id . rocket_image );
  rocketImage . setBackgroundResource ( R . drawable . rocket_thrust );
  rocketAnimation = ( AnimationDrawable ) rocketImage . getBackground ();

  rocketImage . setOnClickListener ( new View . OnClickListener () {  
      @Override
      public void onClick ( View view ) {  
        rocketAnimation . start ();
      }
  });
}
```
AnimationDrawable的start方法不能在Activity的onCreate方法中运行，因为这时AnimationDrawable还没有完全适配窗口。可以再onStart()方法中调用。

## Use AnimatedVectorDrawable
矢量绘制，不失真。

参考[简书](https://www.jianshu.com/p/53759778284a)文档：
