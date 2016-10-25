package lib.emerson.com.emersonapplib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by YangJianCong on 2016/9/29.
 */
public class MyView extends View {
    private Path mPath = new Path();
    private float mPreX, mPreY;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {          //手指按下

                mPath.moveTo(event.getX(), event.getY());        //将Path的初始位置设置到手指的触点处
                mPreX = event.getX();                            //记录手指前一个点（贝塞尔曲线控制点）
                mPreY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {          //手指移动

                float endX = (mPreX + event.getX()) / 2;            //贝塞尔曲线结束点
                float endY = (mPreY + event.getY()) / 2;
                mPath.quadTo(mPreX, mPreY, endX, endY);            //绘制贝塞尔曲线
                mPreX = event.getX();                              //把终点前一个手指位置做为控制点
                mPreY = event.getY();
                invalidate();
            }
            break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);
        canvas.drawPath(mPath, paint);
    }

    public void reset() {
        mPath.reset();
        postInvalidate();
    }
}
