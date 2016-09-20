package lib.emerson.com.emersonapplib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SweepView extends ViewGroup {

	private View mContent;
	private View mDetele;
	private int deteleWidth;
	private ViewDragHelper mHelper;
	private boolean isOpened;
	private int moveY=0;

	public SweepView(Context context) {
		this(context, null);
	}

	public SweepView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SweepView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mHelper = ViewDragHelper.create(this, new MyHelperCallBack());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// view的位置初始化
		mContent.layout(0, 0, mContent.getMeasuredWidth(),
				mContent.getMeasuredHeight());
		mDetele.layout(mContent.getMeasuredWidth(), 0,
				mContent.getMeasuredWidth() + mDetele.getMeasuredWidth
						(),
				mDetele.getMeasuredHeight());

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 测量内容
		mContent.measure(widthMeasureSpec, heightMeasureSpec);
		// 测量删除
		int deletewidthMeasureSpec = MeasureSpec.makeMeasureSpec(deteleWidth,
				MeasureSpec.EXACTLY);
		mDetele.measure(deletewidthMeasureSpec, heightMeasureSpec);
		// 确定自己的高度
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onFinishInflate() {
		mContent = getChildAt(0);
		mDetele = getChildAt(1);

		deteleWidth = mDetele.getLayoutParams().width;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mHelper.processTouchEvent(event);
		return true;
	}

	class MyHelperCallBack extends ViewDragHelper.Callback {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			// 是否分析view的Touch
			// child:触摸的view,pointerId:touch的ID
			// down时调用tryCaptureView
			if (isOpened == false && sweepDownListener != null) {
				sweepDownListener.onSweepDownOpend(isOpened);
			}
			return child == mContent || child == mDetele;//返回是否分析view的结果
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			// 当Touch垂直移动后调用
			if (child==mContent){
				moveY=top;//记录mContent移动的Y值
			}
			return super.clampViewPositionVertical(child, top, dy);
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// 当Touch水平移动后调用
			if (child == mContent) {
				if (left < 0 && -left > deteleWidth) {
					return -deteleWidth;
				} else if (left > 0) {
					return 0;
				}
			} else if (child == mDetele) {
				int measuredWidth = mContent.getMeasuredWidth();
				if (left < measuredWidth - deteleWidth) {
					return measuredWidth - deteleWidth;
				} else if (left > measuredWidth) {
					return measuredWidth;
				}
			}
			// 确定要移动多少
			return left;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
										  int dx, int dy) {
			ViewCompat.postInvalidateOnAnimation(SweepView.this);
			int cWidth = mContent.getMeasuredWidth();
			int cHeight = mContent.getMeasuredHeight();
			int dHeight = mDetele.getMeasuredHeight();
			// 当控件位置移动时调用方法
			if (changedView == mContent) {
				mDetele.layout(cWidth + left, 0, cWidth + left +
								deteleWidth,
						dHeight);
			}
			if (changedView == mDetele) {
				mContent.layout(left - cWidth, 0, left, cHeight);
			}
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			// 松开的回调
			// float xvel, float 速率/加速度
			int left = mContent.getLeft();
			if (-left < deteleWidth / 2f) {
				// 关闭
				close();
			} else {
				// 打开
				open();
			}
			if (left == 0 && moveY == 0 && isOpened == false) {
				//当点击sv松开后x和y没有移动时
				if (releasedChild == mContent && sweepDownListenerJum != null) {
					sweepDownListenerJum.onSweepDownJum();
				}
			}
			moveY=0;//清空当前sv的movey值
		}

	}

	public void open() {
		isOpened = true;
		if (sweepListener != null) {
			sweepListener.onSweepChanged(SweepView.this, isOpened);
		}
		// 滑动展开
		int cWidth = mContent.getMeasuredWidth();
		int cHeight = mContent.getMeasuredHeight();
		int dWidth = mDetele.getMeasuredWidth();

		mHelper.smoothSlideViewTo(mContent, -dWidth, 0);
		mHelper.smoothSlideViewTo(mDetele, cWidth - dWidth, 0);

		ViewCompat.postInvalidateOnAnimation(SweepView.this);
	}

	public void close() {
		isOpened = false;
		if (sweepListener != null) {
			sweepListener.onSweepChanged(SweepView.this, isOpened);
		}
		// 滑动关闭

		// 数据模拟
		mHelper.smoothSlideViewTo(mContent, 0, 0);
		mHelper.smoothSlideViewTo(mDetele, mContent.getMeasuredWidth(), 0);

		ViewCompat.postInvalidateOnAnimation(SweepView.this);
	}

	@Override
	public void computeScroll() {
		if (mHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(SweepView.this);
		}
	}

	public void setOnSweepViewListener(SweepViewOnListener sweepListener) {
		this.sweepListener = sweepListener;
	}

	private SweepViewOnListener sweepListener;

	public interface SweepViewOnListener {
		void onSweepChanged(SweepView sv, boolean isOpened);
	}

	// Down的回调
	public void setSweepViewOnDownListener(
			SweepViewOnDownListener sweepDownListener) {
		this.sweepDownListener = sweepDownListener;
	}

	private SweepViewOnDownListener sweepDownListener;

	public interface SweepViewOnDownListener {
		void onSweepDownOpend(boolean isOpened);
	}

	// Down跳转的回调
	public void setSweepViewOnDownListenerJum(
			SweepViewOnDownListenerJum sweepDownListenerJum) {
		this.sweepDownListenerJum = sweepDownListenerJum;
	}
	private SweepViewOnDownListenerJum sweepDownListenerJum;

	public interface SweepViewOnDownListenerJum {
		void onSweepDownJum();
	}

}