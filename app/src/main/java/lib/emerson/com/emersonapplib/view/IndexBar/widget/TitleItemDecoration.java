package lib.emerson.com.emersonapplib.view.IndexBar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import lib.emerson.com.emersonapplib.view.IndexBar.bean.BaseIndexTagBean;


/**
 * 有分类title的 ItemDecoration
 * Created by zhangxutong .
 * Date: 16/08/28
 *
 * http://blog.csdn.net/zxt0601/article/details/52355199
 */

public class TitleItemDecoration extends RecyclerView.ItemDecoration {
    private List<? extends BaseIndexTagBean> mDatas;
    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect

    private LayoutInflater mInflater;

    private int mTitleHeight;//title的高
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF999999");
    private static int mTitleFontSize;//title字体大小


    public TitleItemDecoration(Context context, List<? extends BaseIndexTagBean> datas) {
        super();
        mDatas = datas;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
        mInflater = LayoutInflater.from(context);
        Log.d("mTitleHeight = ",mTitleHeight + "");
    }


    public TitleItemDecoration setmTitleHeight(int mTitleHeight) {
        this.mTitleHeight = mTitleHeight;
        return this;
    }


    public TitleItemDecoration setColorTitleBg(int colorTitleBg) {
        COLOR_TITLE_BG = colorTitleBg;
        return this;
    }

    public TitleItemDecoration setTitleFontSize(int mTitleFontSize) {
        mPaint.setTextSize(mTitleFontSize);
        return this;
    }

    public TitleItemDecoration setmDatas(List<? extends BaseIndexTagBean> mDatas) {
        this.mDatas = mDatas;
        return this;
    }

    /*
    * 继承ItemDecoration必须要重写下面三个方法：调用顺序 getItemOffsets ----> onDraw -----> onDrawOvers
    * */

    /*
    * 利用 parent和state变量，来获取需要的辅助信息(例如postion)， 最终调用outRect.set()方法，为ItemView设置四个方向上的padding值。
    * */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (mDatas == null ||mDatas.isEmpty() || position > mDatas.size() - 1) {    //pos为1，size为1，1>0? true
            return; //越界
        }
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            } else {//其他的通过判断
                if (null != mDatas.get(position).getBaseIndexTag() && !mDatas.get(position).getBaseIndexTag().equals(mDatas.get(position - 1).getBaseIndexTag())) {
                    outRect.set(0, mTitleHeight, 0, 0);//不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

    /*
    * 在ItemView上面进行内容的绘制，此时绘制出的内容是在ItemView的下层
    * */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (mDatas == null ||mDatas.isEmpty() || position > mDatas.size() - 1) {//pos为1，size为1，1>0? true
                return;//越界
            }
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);

                } else {//其他的通过判断
                    if (null != mDatas.get(position).getBaseIndexTag() && !mDatas.get(position).getBaseIndexTag().equals(mDatas.get(position - 1).getBaseIndexTag())) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }
    }


    /*
    * 上面2个方法已经完成了分类列表title的绘制，而onDrawOver可以实现顶部悬停title效果。
    * onDrawOver也是进行内容的绘制，和onDraw不同是：绘制的内容是在RecyclerView的最上层，所以会遮挡住ItemView，我们让title一直显示在顶部就实现悬停效果。
    * */
    @Override
    public void onDrawOver(Canvas c, final RecyclerView parent, RecyclerView.State state) {//最后调用 绘制在最上层
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        if (mDatas == null ||mDatas.isEmpty() || pos > mDatas.size() - 1) {//pos为1，size为1，1>0? true
            return;//越界
        }

        String tag = mDatas.get(pos).getBaseIndexTag();
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;//出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView

        /* 悬停头部切换“动画效果” 开始 */
        boolean flag = false;   //定义一个flag，Canvas是否位移过的标志
        if ((pos + 1) < mDatas.size()) {    //防止数组越界（一般情况不会出现）
            if (null != tag && !tag.equals(mDatas.get(pos + 1).getBaseIndexTag())) {//当前第一个可见的Item的tag，不等于其后一个item的tag，说明悬浮的View要切换了
                //child是最上面的itemView，当getTop开始变负，表示此开始移出屏幕。[它的绝对值，是第一个可见的Item移出屏幕的距离]
                Log.d("zxt", "Itemview高度：" + child.getHeight() +"  ||| 顶部Itemview距离屏幕顶部距离：" + child.getTop());
                if (child.getHeight() + child.getTop() < mTitleHeight) {//当第一个可见的item在屏幕中还剩的高度小于title区域的高度时，我们也该开始做悬浮Title的“交换动画”
                    c.save();   //每次绘制前 保存当前Canvas状态，
                    flag = true;

                    //一种头部折叠起来的视效，个人觉得也还不错~
                    //可与123行 c.drawRect 比较，只有bottom参数不一样，由于 child.getHeight() + child.getTop() < mTitleHeight，所以绘制区域是在不断的减小，有种折叠起来的感觉
                    //c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + child.getHeight() + child.getTop());

                    //类似饿了么点餐时,商品列表的悬停头部切换“动画效果”
                    //把当前画布canvas的原点移到(0,child.getHeight() + child.getTop() - mTitleHeight),所以后面canvas 画出来的Rect和Text都上移了，有种切换的“动画”感觉
                    c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);
                }
            }
        }
        /* 悬停头部切换“动画效果” 结束*/

        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        c.drawText(tag, child.getPaddingLeft(),
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
                mPaint);
        if (flag)
            c.restore();//恢复画布到之前保存的状态

    }




    /**
     * 绘制Title区域背景和文字的方法
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        //先绘制背景区域
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);

        //然后绘制文字区域
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(mDatas.get(position).getBaseIndexTag(), 0, mDatas.get(position).getBaseIndexTag().length(), mBounds);  //mBounds：包裹文字的最小矩形
        c.drawText(mDatas.get(position).getBaseIndexTag(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }
}
