package lib.emerson.com.emersonapplib.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import lib.emerson.com.emersonapplib.adapter.HorizontalScrollViewAdapter;
import lib.emerson.com.emersonapplib.adapter.HorizontalScrollViewAdapter2;

/**
 * Created by Administrator on 2016/7/4.
 */
public class MyHorizontalScrollView2 extends HorizontalScrollView implements View.OnClickListener {
    private int mScreenWitdh;
    private LinearLayout mContainer;
    private HorizontalScrollViewAdapter2 mAdapter;
    private int mChildWidth;
    private int mChildHeight;
    private int mCountOneScreen;
    private Map<View, Integer> mViewPos = new HashMap<View, Integer>();
    private int mCurrentIndex;



    public interface OnItemClickListener
    {
        void onClick(View view, int pos);
    }

    private OnItemClickListener mOnClickListener;

    public MyHorizontalScrollView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // 获得屏幕宽度
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWitdh = outMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mContainer = (LinearLayout) getChildAt(0);
    }


    /**
     * 初始化数据，设置数据适配器
     *
     * @param mAdapter
     */
    public void initDatas(HorizontalScrollViewAdapter2 mAdapter)
    {
        this.mAdapter = mAdapter;
        mContainer = (LinearLayout) getChildAt(0);
        // 获得适配器中第一个View
        final View view = mAdapter.getView(0, null, mContainer);
        mContainer.addView(view);

        // 强制计算当前View的宽和高
        if (mChildWidth == 0 && mChildHeight == 0)
        {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            mChildHeight = view.getMeasuredHeight();
            mChildWidth = view.getMeasuredWidth();
            Log.e("TAG", view.getMeasuredWidth() + "," + view.getMeasuredHeight());
            mChildHeight = view.getMeasuredHeight();
            // 计算每次加载多少个View
            mCountOneScreen = mScreenWitdh / mChildWidth + 4;
            Log.e("TAG", "mCountOneScreen = " + mCountOneScreen
                    + " ,mChildWidth = " + mChildWidth
                    + " ,mScreenWitdh = " + mScreenWitdh);

        }
        //初始化第一屏幕的元素
        initFirstScreenChildren(mCountOneScreen);
    }

    public void initFirstScreenChildren(int mCountOneScreen)
    {
        mContainer = (LinearLayout) getChildAt(0);
        mContainer.removeAllViews();
        mViewPos.clear();

        for (int i = 0; i < mCountOneScreen; i++)
        {
            View view = mAdapter.getView(i, null, mContainer);
            view.setOnClickListener(this);
            mContainer.addView(view);
            mViewPos.put(view, i);
            mCurrentIndex = i;
        }
    }


    @Override
    public void onClick(View v) {
        if (mOnClickListener != null)
        {
            for (int i = 0; i < mContainer.getChildCount(); i++)
            {
                mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
            mOnClickListener.onClick(v, mViewPos.get(v));
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnClickListener)
    {
        this.mOnClickListener = mOnClickListener;
    }

}
