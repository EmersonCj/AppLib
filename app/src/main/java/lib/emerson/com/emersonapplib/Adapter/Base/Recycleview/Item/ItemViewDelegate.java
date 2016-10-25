package lib.emerson.com.emersonapplib.Adapter.Base.Recycleview.Item;


import lib.emerson.com.emersonapplib.Adapter.Base.Recycleview.RvViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RvViewHolder holder, T t, int position);

}
