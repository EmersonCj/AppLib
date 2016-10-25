package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/7.
 * 在Android动画中，总共有两种类型的动画View Animation(视图动画)和Property Animator(属性动画)；
 * <p>
 * 其中
 * View Animation包括Tween Animation（补间动画）     如下面介绍的Alpha、Scale、Rotate、Translate
 * Frame Animation(帧动画)        图片资源过多，很少使用
 * Property Animator包括ValueAnimator和ObjectAnimation；
 */
public class AnimationActivity extends baseActivity {
    //http://blog.csdn.net/yanbober/article/details/46481171
    //http://www.it165.net/pro/html/201505/41670.html

    //http://blog.csdn.net/harvic880925/article/details/50995268
    @ViewInject(R.id.ball)
    private ImageView mBlueBall;
    @ViewInject(R.id.animation_show)
    private ImageView show;
    @ViewInject(R.id.animation_linear)
    private LinearLayout container;

    private int width;
    private int height;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        x.view().inject(this);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
    }


    /*
    * 一《视图动画》：通过XML或Android代码定义，建议使用XML文件定义，因为它更具可读性、可重用性。
    *１、Alpha：淡入淡出效果
　　  ２、Scale：缩放效果
　　  ３、Rotate：旋转效果
　　  ４、Translate：移动效果

     #视图动画的基类属性#
        android:duration            动画持续时间，以毫秒为单位
        android:fillAfter           如果设置为true，控件动画结束时，将保持动画最后时的状态
        android:fillBefore          如果设置为true,控件动画结束时，还原到开始动画前的状态
        android:fillEnabled         与android:fillBefore 效果相同，都是在动画结束时，将控件还原到初始化状态
        android:repeatCount         重复次数
        android:repeatMode	        重复类型，有reverse和restart两个值，reverse表示倒序回放，restart表示重新放一遍，
                                    必须与repeatCount一起使用才能看到效果。因为这里的意义是重复的类型，即回放时的动作。
        android:interpolator        设定插值器，其实就是指定的动作效果，比如弹跳效果等

     #set标签——定义动作合集#
     对指定的控件定义动作合集，Set标签就可以将几个不同的动作定义成一个组；
     set标签自已是没有属性的，他的属性都是从Animation继承而来，但当它们用于Set标签时，就会对Set标签下的所有子控件都产生作用。

        使用XML去写动画请看：view_anim.xml的介绍

    * */
    @Event(value = R.id.view_animation, type = View.OnClickListener.class)
    private void click_1(View view) {
        Animation animation = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.view_anim);
        // 启动动画
        show.startAnimation(animation);
    }


    /*二《属性动画》：故名思议就是通过动画的方式改变对象的属性
        Duration动画的持续时间，默认300ms。
        Time interpolation：时间差值，定义动画的变化率。
        Repeat count and behavior：重复次数、以及重复模式；可以定义重复多少次；重复时从头开始，还是反向。
        Animator sets: 动画集合，你可以定义一组动画，一起执行或者顺序执行。
        Frame refresh delay：帧刷新延迟，对于你的动画，多久刷新一次帧；默认为10ms，基本不用管。
        */

    @Event(value = R.id.ObjectAnimator, type = View.OnClickListener.class)
    private void click_2(View view) {
        /*  对于ObjectAnimator
            1、提供了ofInt、ofFloat、ofObject，这几个方法都是设置动画作用的元素、作用的属性、动画开始、结束、以及中间的任意个属性值。
        */
        /*ofFloat()方法的第一个参数表示动画操作的对象，
         第二个参数表示操作对象的属性名字（只要是对象有的属性都可以,例如：alpha，otationX，scaleX），
         第三个参数之后就是动画过渡值。当然过度值可以有一个到N个，如果是一个值的话默认这个值是动画过渡值的结束值。如果有N个值，动画就在这N个值之间过渡。*/
        ObjectAnimator animator = ObjectAnimator.ofFloat(show, "alpha", 1.0f, 0.3f, 1.0F).setDuration(500);

        animator.setDuration(2000);                         //设置动画执行时间
        animator.setInterpolator(new BounceInterpolator()); //设置动画插值
        animator.setRepeatCount(2);                        //设置动画重复次数
        animator.setRepeatMode(ValueAnimator.RESTART);      //动画重复模式
        animator.setStartDelay(0);                       //动画延时执行
        animator.start();

    }


    @Event(value = R.id.ValueAnimator, type = View.OnClickListener.class)
    private void click_3(View view) {


        /*ValueAnimator animator = ValueAnimator.ofFloat(0, height - mBlueBall.getHeight());
        animator.setTarget(mBlueBall);
        animator.setDuration(1000).start();
//      animator.setInterpolator(value)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                float value = (float) animation.getAnimatedValue();
                //可以根据自己的需要来获取动画更新值。
                Log.e("TAG","the animation value is" + value);
                mBlueBall.setTranslationY((Float) animation.getAnimatedValue());
            }
        });*/


        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                Log.e("TAG", fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                mBlueBall.setX(point.x);
                mBlueBall.setY(point.y);

            }
        });
        valueAnimator.start();


    }


    @Event(value = R.id.AnimatorSet, type = View.OnClickListener.class)
    private void click_4(View view) {
        /*组合动画有两种方式，
             一种是通过AnimatorSet：
                after(Animator anim)    将现有动画插入到传入的动画之后执行
                after(long delay)       将现有动画延迟指定毫秒后执行
                before(Animator anim)   将现有动画插入到传入的动画之前执行
                with(Animator anim)     将现有动画和传入的动画同时执行
        */
        ObjectAnimator animator = ObjectAnimator.ofInt(container, "backgroundColor", 0xFFFF0000, 0xFFFF00FF);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(show, "translationX", 0.0f, 200.0f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(show, "scaleX", 1.0f, 2.0f, 1.0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(show, "rotationX", 0.0f, 90.0f, 0.0F);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(show, "alpha", 1.0f, 0.2f, 1.0F);
        AnimatorSet set = new AnimatorSet();
        ((set.play(animator).with(animator1).before(animator2)).before(animator3)).after(animator4);
        set.setDuration(1000);
        set.start();


        /*---------------------------------------------------------------------------------------*/
        //第二种就是使用PropertyValuesHolder类来实现组合动画，使用PropertyValuesHolder类只能多个动画一起执行。
        /*PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(show, pvhX, pvhY,pvhZ).setDuration(1000).start();
*/
    }


    /*动画监听器
        很多时候我们可能要在某一个动画执行之前 或者动画结束之后进行一些其他的操作，
        比如：动画结束之后进行网络数据请求等。那么我们就需要去获得该动画的一些状态，幸好，android系统给我们提供了一个动画监听器接口，来监听不同状态下的动画情况。
        1，调用animator.addListener(AnimatorListener listener)方法给动画添加监听器就好了，然后实现AnimatorListener接口即可。
        2，Android系统,提供了一个更加精确的方法来时刻监听当前动画的执行情况。addUpdateListener(AnimatorUpdateListener listener)方法了。
          调用该方法只需实现AnimatorUpdateListener接口就可以读取到动画的每个更新值了。
    */

    /*
    * 三，《布局动画》：主要使用LayoutTransition为布局的容器设置动画，当容器中的视图层次发生变化时存在过渡的动画效果。
    * */
    @Event(value = R.id.layout_animations, type = View.OnClickListener.class)
    private void click_5(View view) {
        Intent intent = new Intent();
        intent.setClass(AnimationActivity.this, LayoutAniActivity.class);
        jump(AnimationActivity.this, intent, false);
    }


}
