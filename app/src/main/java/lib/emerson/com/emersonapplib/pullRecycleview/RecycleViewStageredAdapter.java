package lib.emerson.com.emersonapplib.pullRecycleview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RecycleViewStageredAdapter<T> extends RecyclerView.Adapter<MyViewHolder1>{
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;

    public List<T> mDatas;
    private int headViewid;
    private int headViewSize;
    private boolean isAddHead;
    private int footViewid;
    private int footViewSize;
    private boolean isAddFoot;

    public RecycleViewStageredAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public void addHeadView(int view) {
        headViewid = view;
        headViewSize = 1;
        isAddHead=true;
    }

    public void addFootView(int view) {
        footViewid = view;
        footViewSize = 1;
        isAddFoot=true;
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case TYPE_HEADER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(headViewid, viewGroup, false);
                break;

            case TYPE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_icon, viewGroup, false);
                break;

            case TYPE_FOOT:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(footViewid, viewGroup, false);
                break;
        }
        return new MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 myViewHolder, int i) {
        switch (myViewHolder.getItemViewType()) {
            case TYPE_HEADER:

                // 获取cardview的布局属性，记住这里要是布局的最外层的控件的布局属性，如果是里层的会报cast错误
                StaggeredGridLayoutManager.LayoutParams clp = (StaggeredGridLayoutManager.LayoutParams) myViewHolder.cardview.getLayoutParams();
                // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
                if(clp!=null)
                    clp.setFullSpan(true);
                break;

            case TYPE_ITEM:
                ViewGroup.LayoutParams layoutParams=myViewHolder.cardview.getLayoutParams();
                layoutParams.height= (int) ((i%mDatas.size()+1)*20);
                myViewHolder.cardview.setLayoutParams(layoutParams);
                break;

            case TYPE_FOOT:
                // 获取cardview的布局属性，记住这里要是布局的最外层的控件的布局属性，如果是里层的会报cast错误
                StaggeredGridLayoutManager.LayoutParams clp1 = (StaggeredGridLayoutManager.LayoutParams) myViewHolder.cardview.getLayoutParams();
                // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
                clp1.setFullSpan(true);

                break;
        }
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
    public int getItemCount() {
        return mDatas.size()+headViewSize+footViewSize;
    }
}

class MyViewHolder1 extends RecyclerView.ViewHolder{
    public CardView cardview;
    public MyViewHolder1(View itemView) {
        super(itemView);
        cardview = (CardView) itemView.findViewById(R.id.cv);
    }
}
