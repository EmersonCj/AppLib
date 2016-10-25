package lib.emerson.com.emersonapplib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/10/12.
 */
public class FlikerProgressBar extends View implements Runnable {
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private int DEFAULT_HEIGHT_DP = 35;
    private float MAX_PROGRESS = 100f;
    private Paint textPaint;
    private Paint bgPaint;
    private String progressText;
    private Rect textBouds;

    /**
     * 左右来回移动的滑块
     */
    private Bitmap flikerBitmap;

    /**
     * 滑块移动最左边位置，作用是控制移动
     */
    private float flickerLeft;

    /**
     * 进度条 bitmap ，包含滑块
     */
    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    /**
     * 当前进度
     */
    private float progress;

    private boolean isFinish;

    private boolean isStop;

    /**
     * 下载中颜色
     */
    private int loadingColor;

    /**
     * 暂停时颜色
     */
    private int stopColor;

    /**
     * 进度文本、边框、进度条颜色
     */
    private int progressColor;

    private int textSize;

    private Thread thread;

    public FlikerProgressBar(Context context) {
        this(context, null, 0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    /*
    * 获取自定义属性
    * */
    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FlikerProgressBar);
            textSize = (int) ta.getDimension(R.styleable.FlikerProgressBar_loadtextSize, dp2px(12));
            loadingColor = ta.getColor(R.styleable.FlikerProgressBar_loadingColor, Color.parseColor("#40c4ff"));
            stopColor = ta.getColor(R.styleable.FlikerProgressBar_stopColor, Color.parseColor("#ff9800"));
            ta.recycle();
        }
    }

    /*
    * 设置进度条的宽高，当height设置为wrap_content时设置为默认高度
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
                height = (int) dp2px(DEFAULT_HEIGHT_DP);
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                height = heightSpecSize;
                break;
        }
        setMeasuredDimension(widthSpecSize, height);
        init();
    }

    /*
    * 各种初始化
    * */
    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textBouds = new Rect();

        progressColor = loadingColor;
        flikerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flicker);
        flickerLeft = -flikerBitmap.getWidth();

        pgBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        pgCanvas = new Canvas(pgBitmap);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBorder(canvas);      //边框
        drawProgress();          //进度
        canvas.drawBitmap(pgBitmap, 0, 0, null);    //将绘制好的进度条 画到 当前控件
        drawProgressText(canvas);        //进度text
        drawColorProgressText(canvas);   //变色处理
    }

    /**
     * 边框
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(progressColor);
        bgPaint.setStrokeWidth(dp2px(1));
        canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);
    }

    /**
     * 进度
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void drawProgress() {
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(0);
        bgPaint.setColor(progressColor);

        //当前进度所占的宽度
        float right = (progress / MAX_PROGRESS) * getMeasuredWidth();
        pgCanvas.save(Canvas.CLIP_SAVE_FLAG);   //保存 裁剪的标识位
        pgCanvas.clipRect(0, 0, right, getMeasuredHeight());      //裁剪 当前进度条已下载的宽度
        pgCanvas.drawColor(progressColor);      //绘制画布（即进度条）颜色
        pgCanvas.restore();

        //如果是下载状态，开启动画
        if(!isStop){
            bgPaint.setXfermode(xfermode);      //设置两个图层重叠时的显示模式
            pgCanvas.drawBitmap(flikerBitmap, flickerLeft, 0, bgPaint);     //移动图片
            bgPaint.setXfermode(null);
        }
    }

    /**
     * 进度提示文本
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {
        textPaint.setColor(progressColor);
        progressText = getProgressText();
        //获取文字所在的最小矩形
        textPaint.getTextBounds(progressText, 0, progressText.length(), textBouds);
        //计算文字基线
        int tWidth = textBouds.width();
        int tHeight = textBouds.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;

        canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
    }

    /**
     * 变色处理
     * @param canvas
     */
    private void drawColorProgressText(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        //计算文字基线
        int tWidth = textBouds.width();
        int tHeight = textBouds.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;

        float progressWidth = (progress / MAX_PROGRESS) * getMeasuredWidth();
        //当下载进度进入文字所在矩形，开始变色
        if(progressWidth > xCoordinate){
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            float right = Math.min(progressWidth, xCoordinate + tWidth * 1.1f);
            canvas.clipRect(xCoordinate, 0, right, getMeasuredHeight());            //截取进度条覆盖的文字区域
            canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);     //重新绘制文字，只有截取到的区域才能显示出来
            canvas.restore();
        }

    }

    /*
    * 设置下载进度
    * */
    public void setProgress(float progress){
        if(!isStop){
            this.progress = progress;
            invalidate();
        }
    }

    /*
    * 获取下载进度
    * */
    public float getProgress() {
        return progress;
    }

    /*
    * 停止下载
    * */
    public void setStop(boolean stop) {
        isStop = stop;
        if(isStop){
            progressColor = stopColor;
        } else {
            progressColor = loadingColor;
            thread = new Thread(this);
            thread.start();
        }
        invalidate();
    }

    /*
    * 结束下载
    * */
    public void finishLoad() {
        isFinish = true;
        setStop(true);
    }

    /*
    * 是否正在下载
    * */
    public boolean isStop() {
        return isStop;
    }

    /*
    * 是否结束下载
    * */
    public boolean isFinish() {
        return isFinish;
    }

    /*
    * 开始和暂停的切换
    * */
    public void toggle(){
        if(!isFinish){
            if(isStop){
                setStop(false);
            } else {
                setStop(true);
            }
        }
    }

    @Override
    public void run() {
        int width = flikerBitmap.getWidth();
        while (!isStop){
            flickerLeft += dp2px(5);
            float progressWidth = (progress / MAX_PROGRESS) * getMeasuredWidth();
            if(flickerLeft >= progressWidth){
                flickerLeft = -width;
            }
            postInvalidate();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * 获取进度条文字
    * */
    private String getProgressText() {
        String text= "";
        if(!isFinish){
            if(!isStop){
                text = "下载中" + progress + "%";
            } else {
                text = "继续";
            }
        } else{
            text = "下载完成";
        }

        return text;
    }



    private float dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return dp * density;
    }


}
