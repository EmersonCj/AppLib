package lib.emerson.com.emersonapplib.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/6/8.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<String> mDatas;
    private LayoutInflater mInflater;
    private List<Integer> mHeights;
    private OnItemClickLitener mOnItemClickLitener;


    public RecyclerViewAdapter(Context context, List<String> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++)
        {
            mHeights.add( (int) (100 + Math.random() * 300));
        }
    }

    //为用户提供回调接口，用户需要实现
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("onCreateViewHolder","onCreateViewHolder");
        MyViewHolder holder = new MyViewHolder(mInflater.inflate
                (R.layout.item_recycview,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, int position) {
        Log.e("onBindViewHolder","onBindViewHolder");
        LayoutParams lp = holder.tv.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.tv.setLayoutParams(lp);
        holder.tv.setText(mDatas.get(position));
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
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
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("getItemViewType","getItemViewType");
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
