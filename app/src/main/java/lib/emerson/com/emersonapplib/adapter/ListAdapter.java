package lib.emerson.com.emersonapplib.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.domain.baseActivity;

/**
 * Created by YangJianCong on 2016/8/31.
 */
public abstract class ListAdapter<T> extends BaseAdapter {
    public Context context;             //Listview所在的Activity
    public List<T> datasource;          //Listview数据源
    public LayoutInflater inflater;
    public ImageLoader imageLoader;     //Listview图片源

    public ListAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ListAdapter(Context context, List<T> datasource) {
        this.context = context;
        if (datasource != null) {
            this.datasource = datasource;
        } else {
            this.datasource = new ArrayList<T>();
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public ListAdapter(Context context, List<T> datasource, ImageLoader imageLoader) {
        super();
        this.context = context;
        if (datasource != null) {
            this.datasource = datasource;
        } else {
            this.datasource = new ArrayList<T>();
        }
        this.imageLoader = imageLoader;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return datasource.size();
    }

    @Override
    public T getItem(int position) {
        return datasource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    /*抽象方法，一定要实现*/
    public abstract View getItemView(int position, View convertView, ViewGroup parent);

    public List<T> getDataSource() {
        return datasource;
    }

    public void setDataSource(List<T> result) {
        datasource.clear();
        datasource.addAll(result);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public Context getContext() {
        return context;
    }

    public void remove(T t) {
        datasource.remove(t);
        notifyDataSetChanged();
    }

    public void remove(int idx) {
        datasource.remove(idx);
        notifyDataSetChanged();
    }

    public void add(T t) {
        datasource.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> result) {
        datasource.addAll(result);
        notifyDataSetChanged();
    }

    public void toast(String text) {
        if (context instanceof baseActivity) {
            ((baseActivity) context).toast(text);
        }
    }


}
