package lib.emerson.com.emersonapplib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by YangJianCong on 2016/9/14.
 */
public class TestView extends View {

    public TestView(Context context) {
        super(context);
    }

    //重写OnDraw（）函数，在每次重绘时自主实现绘图
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        canvas.drawRGB(0,191,255);

        RectF rect = new RectF(100,100,200,200);
        canvas.drawRect(rect, paint);//使用RectF构造

    }

}
