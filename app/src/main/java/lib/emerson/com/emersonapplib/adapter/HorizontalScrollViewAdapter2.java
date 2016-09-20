package lib.emerson.com.emersonapplib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/4.
 */
public class HorizontalScrollViewAdapter2 extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public HorizontalScrollViewAdapter2(Context context, List<String> mDatas){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.activity_tab_view_item, parent, false);
            viewHolder.mText = (TextView) convertView
                    .findViewById(R.id.main_middle_layout_one);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mText.setText(mDatas.get(position));

        return convertView;
    }


    private class ViewHolder
    {
        private TextView mText;
    }


}
