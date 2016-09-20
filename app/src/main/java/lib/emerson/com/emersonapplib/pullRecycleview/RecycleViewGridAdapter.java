package lib.emerson.com.emersonapplib.pullRecycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RecycleViewGridAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;

    public List<T> mDatas;
    private View headView;
    private View footView;
    private int headViewSize = 0;
    private int footViewSize = 0;
    private ChangeGridLayoutManagerSpance changeGridLayoutManager;
    private boolean isAddFoot=false;
    private boolean isAddHead=false;




    public interface ChangeGridLayoutManagerSpance{
        public void change(int size, boolean isAddHead, boolean isAddFoot);
    }
    //提供接口给 让LayoutManager根据添加尾部 头部与否来做判断 显示头部与底部的SpanSize要在添加头部和尾部之后
    public void setChangeGridLayoutManager(ChangeGridLayoutManagerSpance changeGridLayoutManager){
        this.changeGridLayoutManager=changeGridLayoutManager;
        changeGridLayoutManager.change(getItemCount()-1,isAddHead,isAddFoot);
    }

    public RecycleViewGridAdapter(List<T> datas) {
        mDatas = datas;
    }

    public void addHeadView(View view) {
        headView = view;
        headViewSize = 1;
        isAddHead=true;
    }

    public void addFootView(View view) {
        footView = view;
        footViewSize = 1;
        isAddFoot=true;
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;

        if (headViewSize==1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footViewSize==1 && position == getItemCount()-1) {
            //最后一个位置
            type = TYPE_FOOT;
        }
        return type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case TYPE_HEADER:
                view = headView;
                break;

            case TYPE_ITEM:
                view = View.inflate(viewGroup.getContext(), R.layout.item_icon, null);
                break;

            case TYPE_FOOT:
                view =footView;
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return mDatas.size() + headViewSize + footViewSize;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    public MyViewHolder(View itemView) {
        super(itemView);
    }
}
