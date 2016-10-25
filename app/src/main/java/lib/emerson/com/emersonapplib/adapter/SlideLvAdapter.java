package lib.emerson.com.emersonapplib.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.SweepView;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SlideLvAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<String> mdata;
    private List<SweepView> svOpeneds = new ArrayList<SweepView>();
    private String TAG = "SlideLvAdapter";


    public SlideLvAdapter(Context context,List<String> data){
        this.context = context;
        this.mdata = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 获得显示的子View数量
     */
    @Override
    public int getCount() {
        return mdata.size();
    }

    /**
     * 获得某一位置的数据，即返回一个子View
     */
    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewTestHolder holder = null;

        if (convertView == null){
            holder = new ViewTestHolder();
            convertView = layoutInflater.inflate(R.layout.slide_four_lv_item, null);
            holder.iv = (TextView) convertView.findViewById(R.id.tv_contant);
            holder.sweepView  = (SweepView) convertView.findViewById(R.id.sv_view);
            holder.rlDetele  = (RelativeLayout) convertView.findViewById(R.id.sweep_detele);
            convertView.setTag(holder);
        }else {
            holder = (ViewTestHolder) convertView.getTag();
        }
        holder.iv.setText((String)mdata.get(position));
        holder.iv.setGravity(Gravity.CENTER);

        /**
         *左滑删除按钮点击事件
         */
        holder.rlDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoLeftDeleted != null) {
                    mGoLeftDeleted.onItemDeleted(position);     //调用用户实现的方法
                }
            }
        });

        /*监听滑动的状态*/
        holder.sweepView.setOnSweepViewListener(new SweepView.SweepViewOnListener() {

            @Override
            public void onSweepChanged(SweepView sv, boolean isOpened) {
                Log.e(TAG,"onSweepChanged");
                // 记录打开的SweepView
                if (isOpened) {
                    if (!svOpeneds.contains(sv)) {
                        svOpeneds.add(sv);
                    }
                } else {
                    svOpeneds.remove(sv);
                }
            }
        });


        holder.sweepView.setSweepViewOnDownListener(new SweepView.SweepViewOnDownListener() {
            @Override
            public void onSweepDownOpend(boolean isOpened) {
                Log.e(TAG,"onSweepDownOpend");
                // 监听是否触摸了非打开的SweepView
                if (!isOpened && svOpeneds.size() != 0) {
                    allClose();
                }
            }
        });

        //item点击事件
        holder.sweepView.setSweepViewOnDownListenerJum(new SweepView.SweepViewOnDownListenerJum() {
            @Override
            public void onSweepDownJum() {
                Log.e(TAG,"onSweepDownJum");
                Toast.makeText(context,mdata.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public final class ViewTestHolder {
        TextView iv;
        SweepView sweepView;
        RelativeLayout rlDetele;
    }



    /**
     * 关闭所有打开的SweepView
     */
    public void allClose() {
        ListIterator<SweepView> iterator = svOpeneds.listIterator();
        while (iterator.hasNext()) {
            SweepView view = iterator.next();
            view.close();
        }
    }

    /**
     * 左滑删除的接口
     */
    public interface GoLeftDeleted {
        void onItemDeleted(int position);
    }

    private GoLeftDeleted mGoLeftDeleted;

    public void setGoLeftDeleted(GoLeftDeleted goLeftDeleted) {
        this.mGoLeftDeleted = goLeftDeleted;
    }

    public void deleteGood(int pos){
        mdata.remove(pos);
        notifyDataSetChanged();
    }

}
