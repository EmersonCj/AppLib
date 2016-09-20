package lib.emerson.com.emersonapplib.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.domain.RefreshActivity;

/**
 * Created by Administrator on 2016/7/6.
 */

public class MyAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> listItem = null;
    private LayoutInflater mInflater;
    private Context mcontext;

    public MyAdapter(Context context, ArrayList<HashMap<String, Object>> listItem){
        this.mcontext = context;
        this.listItem = listItem;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            holder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.push_item_layout, null);
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.info = (TextView)convertView.findViewById(R.id.info);
            convertView.setTag(holder);
        }else {

            holder = (ViewHolder)convertView.getTag();
        }

        //holder.img.setImageBitmap(((RefreshActivity)mcontext).getHome((String)listItem.get(position).get("img")));
        holder.name.setText((String)listItem.get(position).get("name"));
        holder.info.setText((String)listItem.get(position).get("info"));

        return convertView;
    }


    public final class ViewHolder{
        public ImageView img;
        public TextView name;
        public TextView info;
    }


}
