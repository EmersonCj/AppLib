package lib.emerson.com.emersonapplib.Adapter.Base.Listview;

import android.content.Context;

import java.util.List;

import lib.emerson.com.emersonapplib.Adapter.Base.Listview.Item.ItemViewDelegate;


/**
 * Created by YangJianCong on 2016/10/19.
 */

public abstract class BaseLvAdapter<T> extends MultiItemTypeAdapter<T> {
    public BaseLvAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);

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
            public void convert(ViewHolder holder, T t, int position) {
                BaseLvAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
