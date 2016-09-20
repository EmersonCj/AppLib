package lib.emerson.com.emersonapplib.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ShowGrounpUtil {
	private static String TAG="YangUtils";

	/**
	 * 测量item所有的高度，并把它设置ListView的高度上
	 * 也就是把所以子控件都展开
	 * @author tarena
	 *
	 */
	public static int  MeasureListViewHeight(ListView lv){
		int totalHeight=0;
		if(lv!=null){
			ListAdapter adapter=lv.getAdapter();
			//计算listview里面所有的子item的高度 加起来
			for (int i = 0; i < adapter.getCount(); i++) {
				View v=adapter.getView(i,null, lv);
				v.measure(0,0);
				totalHeight+=v.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params=lv.getLayoutParams();
			//归零
			params.height=0;
			//设置布局高度
			params.height=totalHeight+(lv.getDividerHeight()*lv.getCount()-1);
			lv.setLayoutParams(params);
		}
		return totalHeight;
	}
	
	/**
	 * 测量ListView的高度，根据他的子控件的高度重新在绘制一次，
	 * （默认情况下在ListView放ListView只能显示一个Item）
	 * 测量item的高度，，并把它设置为listView的高度
	 * @author
	 * 只能最多显示两个view
	 */
	public static int MeasureListViewHeightOnlyTwo(ListView lv, int num){
		int value=0;
		ListAdapter adapter=lv.getAdapter();
		if(adapter==null){
			return -1;
		}
		int totalHeight=0;
		View childView=null;
		if(adapter.getCount()>=num){
			value=num;
		}else {
			value=adapter.getCount();
		}
		
		for (int i = 0; i < value; i++) {
			childView=adapter.getView(i, null,lv);
			childView.measure(0,0);
			totalHeight+=childView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params=lv.getLayoutParams();
		params.height=0;
		params.height+=totalHeight+(lv.getDividerHeight()*adapter.getCount()-1);
		lv.setLayoutParams(params);
		return totalHeight;
	}
	
	/**
	 * 测量item所以的高度，并把它设置ListView的高度上
	 * 也就是把所以子控件都展开
	 * @author tarena
	 *
	 */
	public static void  MeasureGridViewHeight(GridView gv){
		int totalHeight=0;
		if(gv!=null){
			ListAdapter adapter=gv.getAdapter();
			
			Log.e(TAG, adapter.getCount()+":gv.getNumColumns():"+gv.getNumColumns());
			for (int i = 0; i < adapter.getCount(); i+=gv.getNumColumns()) {
				View v=adapter.getView(i,null, gv);
				v.measure(0,0);
				totalHeight+=v.getMeasuredHeight();
			}
			
			ViewGroup.LayoutParams params=gv.getLayoutParams();
			params.height=0;
			params.height=totalHeight;
			Log.e(TAG,params.height+"");
			((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
			Log.i(TAG, params.height+"");
			gv.setLayoutParams(params);
		}
	}
	
	public static void measure(AbsListView lv, int count)
	{
		if(lv instanceof GridView)
		{
			ListAdapter adapter=lv.getAdapter();
			int chlidCount=adapter.getCount();
			int height=0;
			int column=(int) Math.ceil(chlidCount/count);
			for(int i=0;i<column;i++)
			{
				View chilt=adapter.getView(i, null, lv);
				chilt.measure(0, 0);
				height+=chilt.getMeasuredHeight()+chilt.getPaddingBottom()+chilt.getPaddingTop();
			}
			ViewGroup.LayoutParams param=lv.getLayoutParams();
			param.height=height;
			lv.setLayoutParams(param);
		}
	}

	public static void autoSetListviewHeight(ListView listview){
		ListAdapter listAdapter = listview.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listview);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listview.getLayoutParams();
		params.height = totalHeight + (listview.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams)params).setMargins(10, 10, 10, 10);
		listview.setLayoutParams(params);
	}
}
