package lib.emerson.com.emersonapplib.view.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by YangJianCong on 2016/10/20.
 */

public class ViewGroupTest extends ViewGroup {
    public ViewGroupTest(Context context) {
        super(context);
    }

    public ViewGroupTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /*
     * 在onMeasure中计算childView的测量值以及模式，以及设置自己的宽和高
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
