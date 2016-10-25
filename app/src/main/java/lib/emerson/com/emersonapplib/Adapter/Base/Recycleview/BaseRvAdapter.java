package lib.emerson.com.emersonapplib.Adapter.Base.Recycleview;

import android.content.Context;
import android.view.LayoutInflater;


import java.util.List;

import lib.emerson.com.emersonapplib.Adapter.Base.Recycleview.Item.ItemViewDelegate;


/**
 * Created by zhy on 16/4/9.
 */
public abstract class BaseRvAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public BaseRvAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(RvViewHolder holder, T t, int position) {
                BaseRvAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(RvViewHolder holder, T t, int position);


}
