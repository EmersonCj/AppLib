package lib.emerson.com.emersonapplib.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.tscenter.biz.rpc.vkeydfp.result.BaseResult;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Test.GoodsBean;
import lib.emerson.com.emersonapplib.entity.MyBitmapImageViewTarget;

/**
 * Created by Administrator on 2016/7/8.
 */
public class GoodsAdapter extends BaseRecyclerAdapter<GoodsAdapter.GoodsViewHolder>{
    /*
    *   getItemCount --->  getItemViewType(在这里可根据position去设置标志位) ----> onCreateViewHolder(根据标志位加载不同布局) ----> onBindViewHolder
    */
    private int itemType = 0;
    public List<GoodsBean> datasource;

    public static enum ITEM_TYPE {
        ITEM_TYPE_ONE,
        ITEM_TYPE_TWO
    }

    public GoodsAdapter(Context context,List<GoodsBean> data) {
        super(context);
        if (data == null) {
            this.datasource = new ArrayList<>();
        }else {
            this.datasource = data;
        }
    }

    //根据position返回相应的Item   （在这里可以设置ITEM类型，自由发挥)
    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        return itemType == 0 ? ITEM_TYPE.ITEM_TYPE_ONE.ordinal() : ITEM_TYPE.ITEM_TYPE_TWO.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        View view = null;
        Log.e("TAG","viewType = " + viewType);
        if (viewType == ITEM_TYPE.ITEM_TYPE_ONE.ordinal()) {
            view = inflater.inflate(R.layout.new_item_home_list_one, parent, false);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_TWO.ordinal()) {
            view = inflater.inflate(R.layout.new_item_home_list_two, parent, false);
        }
        GoodsViewHolder holder = new GoodsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsBean goods = datasource.get(position);
        if (holder instanceof GoodsViewHolder) {
            GoodsViewHolder viewHolder= (GoodsViewHolder) holder;
            Glide.with(context).load(goods.getImgUrl()).asBitmap().centerCrop().placeholder(R.drawable.fruit).into(new MyBitmapImageViewTarget(viewHolder.ivIcon));
            viewHolder.tvName.setText(goods.getGoodsName());
            viewHolder.tvPrice.setText(goods.getPrice());
            viewHolder.tvSupermarketPrice.setText("超市￥"+goods.getSupermarketPrice());
            viewHolder.tvSupermarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //超市价格信息画中划线
            clickListener(viewHolder,position);
        }
    }

    @Override
    public int getItemCount() {
        return datasource == null ? 0 : datasource.size();
    }

    //两个布局的控件都是一样，所以不用写两个ViewHolder
    public class GoodsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvPrice,tvSupermarketPrice,tvBuy;
        private ImageView ivIcon;
        private LinearLayout llBuyCarInfoOne;
        private CardView cvHomeGoodsItem;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.new_item_tv_one_goods_name);//商品名
            tvPrice = (TextView) itemView.findViewById(R.id.new_item_tv_one_goods_price);//商品单价
            tvSupermarketPrice = (TextView) itemView.findViewById(R.id.new_item_tv_one_goods_old_price);//超市商品价格
            tvBuy = (TextView) itemView.findViewById(R.id.new_item_iv_one_buy);//商品点击购买
            ivIcon = (ImageView) itemView.findViewById(R.id.new_item_iv_one_icon);//商品图标
            llBuyCarInfoOne = (LinearLayout) itemView.findViewById(R.id.new_item_ll_one_goods_particulars_one);//有购物车有商品的信息布局
            cvHomeGoodsItem = (CardView) itemView.findViewById(R.id.new_item_home_list);//监听item
        }
    }

    /**
     * 监听事件
     * @param holder
     * @param position
     */
    private void clickListener(GoodsViewHolder holder, int position) {
        setUpItemkEnent(holder.cvHomeGoodsItem,position);//item监听
        holder.tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("购买",context);
            }
        });
    }

    /**
     * 设置item的布局类型
     * @param pos
     */
    public void setItmeType(int pos) {
        itemType = pos;
    }


}
