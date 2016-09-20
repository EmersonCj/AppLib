package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.CustomButton;
import lib.emerson.com.emersonapplib.view.MyLayout;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ViewActivity extends Activity{
    private MyLayout myLayout;
    private Button button1;
    private Button button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);


        myLayout = (MyLayout) this.findViewById(R.id.my_layout);
        button1 = (Button) this.findViewById(R.id.button1);
        button2 = (Button) this.findViewById(R.id.button2);


        /*
            1. Android事件分发是先传递到ViewGroup，再由ViewGroup传递到View的。
            2. 在ViewGroup中可以通过onInterceptTouchEvent方法对事件传递进行拦截，
                onInterceptTouchEvent方法返回true代表不允许事件继续向子View传递，返回false代表不对事件进行拦截，默认返回false。
            3. 子View中如果将传递的事件消费掉，ViewGroup中将无法接收到任何事件。
        */
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG", "myLayout on touch");
                return false;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "You clicked button1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "You clicked button2");
            }
        });
    }



}




/*
@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TAG", "--dispatchTouchEvent--");
        return super.dispatchTouchEvent(ev);
    }

    *//*
    dispatchTouchEvent方法先派发down事件，完事后调用onTouch，完事后调用onTouchEvent返回true，同时dispatchTouchEvent返回true，
    然后dispatchTouchEvent继续派发move或者up事件，循环，直到onTouchEvent处理up事件时调用onClick事件，完事返回true，同时dispatchTouchEvent返回true；
    一次完整的View事件派发流程结束。
    * dispatchTouchEvent -----> onTouch(false) -----> onTouchEvent -----> onClick
    * *//*

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        *//*event.getAction()：
        * ACTION_DOWN       按下     0
        * ACTION_UP         抬起     1
        * ACTION_MOVE       滑动     2
        * *//*
        Log.e("TAG", "--onTouch-- action="+event.getAction());
        return false;  // 返回true则onTouchEvent不会被调用
    }

    @Override
    public void onClick(View v) {
        Log.e("TAG", "--onClick--");
    }

    *//*
    综合得出Android View的触摸屏事件传递机制有如下特征：
        1,触摸控件（View）首先执行dispatchTouchEvent方法。
        2,在dispatchTouchEvent方法中先执行onTouch方法，后执行onClick方法（onClick方法在onTouchEvent中执行）。
        3,在dispatchTouchEvent方法中，会去判断mOnTouchListener 是否为空（控件可以设置setOnTouchListener），控件是否为enable，onTouch方法里返回是true还是false。
            前两个通常都成立，主要看onTouch的返回值：
            1.1如果控件（View）的onTouch返回false，就会调用onTouchEvent，dispatchTouchEvent返回值与onTouchEvent返回一样。       (会触发onClick)
            1.2如果控件（View）的onTouch返回true，dispatchTouchEvent直接返回true，不会调用onTouchEvent方法。                    (不会触发onClick)
            1.3如果控件没有设置enable，onTouch方法也不会执行，此时只能通过重写控件的onTouchEvent方法处理，dispatchTouchEvent返回值与onTouchEvent返回一样。
        4,onClick的调用肯定是在onTouchEvent(event)方法中的。
        5,当dispatchTouchEvent在进行事件分发的时候，只有前一个action返回true，才会触发下一个action（也就是说dispatchTouchEvent返回true才会进行下一次action派发）。
          如果你在执行ACTION_DOWN的时候返回了false，后面一系列其它的action(ACTION_MOVE，ACTION_UP等)就不会再得到执行了。
    */







/*private void init() {
            // 获得屏幕宽度
            WindowManager wm = (WindowManager) ViewActivity.this.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            mScreenWitdh = outMetrics.widthPixels;

            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) tv1.getLayoutParams(); //取控件textView当前的布局参数
            linearParams.height = 50;// 控件的高强制设成20
            linearParams.width = mScreenWitdh / 3;// 控件的宽强制设成30
            tv1.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

            linearParams = (LinearLayout.LayoutParams) tv2.getLayoutParams(); //取控件textView当前的布局参数
            linearParams.height = 50;// 控件的高强制设成20
            linearParams.width = mScreenWitdh / 3;// 控件的宽强制设成30
            tv2.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

            linearParams = (LinearLayout.LayoutParams) tv3.getLayoutParams(); //取控件textView当前的布局参数
            linearParams.height = 50;// 控件的高强制设成20
            linearParams.width = mScreenWitdh / 3;// 控件的宽强制设成30
            tv3.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }*/