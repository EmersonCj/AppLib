package lib.emerson.com.emersonapplib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import lib.emerson.com.emersonapplib.R;


public class ClearEditText extends EditText implements OnFocusChangeListener,TextWatcher {
	
	private Drawable mClearDrawable;
	
	private boolean hasFoucs;

	private Paint mPaint;
	private int currentSize = 2;

	public boolean isUnderline;

	public ClearEditText(Context context) {
		this(context, null);
	}

	public ClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	@Override
	public void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		// 画底线
		if (isUnderline){
			canvas.drawLine(0, this.getHeight() - 10, this.getWidth() - 20, this.getHeight() - 10, mPaint);
		}
	}

	private void init(AttributeSet attrs, int defStyle) {
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.parseColor("#e6e6e6"));
		mPaint.setStrokeWidth(currentSize);
		
		mClearDrawable = getCompoundDrawables()[2];
		if (mClearDrawable == null) {
			
			mClearDrawable = getResources().getDrawable(R.drawable.clear);
		}
		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
				mClearDrawable.getIntrinsicHeight());
	
		setClearIconVisible(false);
		
		setOnFocusChangeListener(this);
		
		addTextChangedListener(this);

		TypedArray array=getContext().obtainStyledAttributes(attrs,R.styleable.ClearEditText);
		isUnderline=array.getBoolean(R.styleable.ClearEditText_edittext_underline,true);//是否设置编辑框的下划线,默认设置
		array.recycle();
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));

				if (touchable) {
					this.setText("");
				}
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFoucs = hasFocus;
		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	
	protected void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	
	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		if (hasFoucs) {
			setClearIconVisible(s.length() > 0);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after) {
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	public void setShakeAnimation() {
		this.setAnimation(shakeAnimation(5));
	}

	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

}