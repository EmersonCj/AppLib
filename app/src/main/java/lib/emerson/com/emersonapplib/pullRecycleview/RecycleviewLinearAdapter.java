package lib.emerson.com.emersonapplib.pullRecycleview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RecycleviewLinearAdapter<T> extends RecyclerView.Adapter<RecycleviewLinearAdapter.LinearViewHolder> {
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    public List<T> mDatas;
    private View headView;
    private View footView;
    private int headViewSize = 0;
    private int footViewSize = 0;
    private boolean isAddFoot = false;
    private boolean isAddHead = false;

    public RecycleviewLinearAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public void addHeadView(View view) {
        headView = view;
        headViewSize = 1;
        isAddHead = true;
    }

    public void addFootView(View view) {
        footView = view;
        footViewSize = 1;
        isAddFoot = true;
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (headViewSize == 1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footViewSize == 1 && position == getItemCount() - 1) {
            //最后一个位置
            type = TYPE_FOOT;
        }
        return type;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = headView;
                break;

            case TYPE_ITEM:
                view = View.inflate(parent.getContext(), R.layout.item_icon, null);
                break;

            case TYPE_FOOT:
                view = footView;
                break;
        }
        return new LinearViewHolder(view);
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final RecycleviewLinearAdapter.LinearViewHolder holder, final int position) {
        holder.tv.setText(mDatas.get(position) + "");
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + headViewSize + footViewSize;
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public LinearViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_show);
        }
    }

    private OnItemClickLitener mOnItemClickLitener;

    //为用户提供回调接口，用户需要实现
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}


