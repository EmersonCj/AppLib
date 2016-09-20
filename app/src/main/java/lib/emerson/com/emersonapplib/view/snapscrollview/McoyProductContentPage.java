package lib.emerson.com.emersonapplib.view.snapscrollview;

import android.content.Context;
import android.view.View;

import lib.emerson.com.emersonapplib.R;

public class McoyProductContentPage implements McoySnapPageLayout.McoySnapPage {
	
	private Context context;

	private View rootView = null;
	private McoyScrollView mcoyScrollView = null;

	public McoyProductContentPage(Context context,View rootView) {
		this.context = context;
		this.rootView = rootView;
		mcoyScrollView = (McoyScrollView) this.rootView
				.findViewById(R.id.content_scrollview);
	}

	@Override
	public View getRootView() {
		return rootView;
	}

	@Override
	public boolean isAtTop() {
		int scrollY = mcoyScrollView.getScrollY();	//视图内容 相对于 视图起始坐标的偏移量
		int height = mcoyScrollView.getHeight();	//mcoyScrollView占屏幕的高度
		int scrollViewMeasuredHeight = mcoyScrollView.getChildAt(0).getMeasuredHeight();	//mcoyScrollView的实际高度

		if ((scrollY + height) >= scrollViewMeasuredHeight) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isAtBottom() {
		return false;
	}

}
