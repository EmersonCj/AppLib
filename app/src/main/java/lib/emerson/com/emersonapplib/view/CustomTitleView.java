package lib.emerson.com.emersonapplib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.nostra13.universalimageloader.utils.L;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import lib.emerson.com.emersonapplib.R;


/**
 * Created by Administrator on 2016/7/5.
 */
public class CustomTitleView extends View {
    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;    //矩形
    private Paint mPaint;   //画笔

    public CustomTitleView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context)
    {
        this(context, null);
    }

    /**
     * 获得我自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomTitleView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        /**
         * obtainStyledAttributes（ AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes）
         *  set：属性值的集合
      　　　　attrs：我们要获取的属性的资源ID的一个数组，就是从一堆属性中我们希望查询什么属性的值
      　　　　defStyleAttr：这个是当前Theme中的一个attribute，是指向style的一个引用，当在layout xml中和style中都没有为View指定属性时，
                          会从Theme中这个attribute指向的Style中查找相应的属性值，这就是defStyle的意思，如果没有指定属性值，就用这个值，
                          所以是默认值，但这个attribute要在Theme中指定，且是指向一个Style的引用，如果这个参数传入0表示不向Theme中搜索默认值

      　　　　defStyleRes：这个也是指向一个Style的资源ID，但是仅在defStyleAttr为0或defStyleAttr不为0但Theme中没有为defStyleAttr属性赋值时起作用
         */

        /*
        * 使用 TypedArray 来获取 XML layout 中的属性值
        * 获得我们所定义的自定义样式属性集合
        * */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor2:
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }

        }
        //最后一定要调用recycle(),回收 TypedArray,用于后续调用时可复用之。当调用该方法后，不能再操作该变量。
        a.recycle();

        /**
         * 获得要绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        // mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText,0, mTitleText.length(), mBound);        //mBound：包裹文字的最小矩形

        this.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                mTitleText = randomText();
                postInvalidate();            //刷新view
            }

        });
    }


    /*  调用顺序：onMeasure()-------> onLayout() --------> onDraw()
    * 1.View本身大小多少，这由onMeasure()决定；
    * 2.View在ViewGroup中的位置如何，这由onLayout()决定；
    * 3.绘制View，onDraw()定义了如何绘制这个View。*/


    /*
    * 视图大小的控制是由父视图、布局文件、以及视图本身共同完成的，
    * 父视图会提供给子视图参考的大小（），而开发人员可以在XML文件中指定视图的大小，然后视图本身会对最终的大小进行拍板。*/
    @Override
    //widthMeasureSpec和heightMeasureSpec来自父视图，这两个值都是由父视图经过计算后传递给子视图的-我们自定义view的父控件的边界参数（即view宽高的最大值）
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;


        /**
         * 设置自定义view的宽度
         * 边界参数是以整数的方式传入。所以在它们使用之前，
         * 要做的是使用MeasureSpec类的静态方法getMode和getSize来译解。
         */

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        /*
        * 用户在布局中对view宽/高度进行设置时，根据传入的参数specMode，我们会对view的宽高进行设置，避免超出最大值
        * EXACTLY：父视图希望子视图的大小应该是由specSize的值来决定的。
        * AT_MOST：子视图最多只能是specSize中指定的大小。
        * */
        switch (specMode){
            case MeasureSpec.EXACTLY:       //当view的大小设置为精确值时，容器传入的是EXACTLY。所以此时的specSize 代表的是精确的尺寸
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:      // 当view的大小设置WARP_CONTENT时，容器传入的是AT_MOST。所以此时的specSize代表的是最大可获得的空间，view可以任意设置，但不能超过specSize
                width = getPaddingLeft() + getPaddingRight() + mBound.width();
                break;
        }

        /**
         * 设置自定义view的高度
         */
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode){
            case MeasureSpec.EXACTLY:// 明确指定了
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:// 一般为WARP_CONTENT
                height = getPaddingTop() + getPaddingBottom() + mBound.height();
                break;
        }

        //告诉父控件，我（view）需要多大地方放置。一定要设置
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //画View的背景图
        mPaint.setColor(Color.YELLOW);
        //传入矩形的四个点
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //画View上的文字
        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }


}
