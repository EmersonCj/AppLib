package lib.emerson.com.emersonapplib.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/6/2.
 */
public class listviewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<String> mdata;


    public listviewAdapter(Context context,List<String> data){
        this.context = context;
        this.mdata = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 获得显示的子View数量
     */
    @Override
    public int getCount() {
        return mdata.size();
    }

    /**
     * 获得某一位置的数据，即返回一个子View
     */
    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 显示优化（只要之前显示过的就可以不再再次从布局文件读取，直接从缓存中读取——ViewHolder的作用）
        // 其实是setTag和getTag中Tag的作用
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_listview, null);
            holder.iv = (TextView) convertView.findViewById(R.id.item_listview_iv);
            convertView.setTag(holder);
        }else {// 如果之前已经显示过该页面，则用viewholder中的缓存直接刷屏
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv.setText((String)mdata.get(position));
        holder.iv.setGravity(Gravity.CENTER);
        return convertView;
    }

    public final class ViewHolder {
        public TextView iv;

    }

}
