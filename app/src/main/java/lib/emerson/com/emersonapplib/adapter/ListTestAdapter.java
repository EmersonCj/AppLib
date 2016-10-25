package lib.emerson.com.emersonapplib.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/8/31.
 */
public class ListTestAdapter extends ListAdapter<String> {
    /*
    * 三个不同的ViewHolder对应着三个不同的布局,也就是ListView中每一项的布局（TYPE_1-3是三种不同的布局）
    * 在使用不同布局的时候,getItemViewType（）和getViewType（）一定要重写*/
    final int VIEW_NUM = 3;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;


    public ListTestAdapter(Context context, List<String> datasource, ImageLoader imageLoader) {
        super(context, datasource, imageLoader);
    }

    // 每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        int p = position%3;
        if (p == 0)
            return TYPE_1;
        else if (p == 1)
            return TYPE_2;
        else
            return TYPE_3;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_NUM;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            // 按当前所需的样式，确定new的布局
            switch (type) {
                case TYPE_1:

                    convertView = inflater.inflate(R.layout.item_list_one,parent, false);
                    holder1 = new ViewHolder1();
                    holder1.iv1 = (ImageView) convertView.findViewById(R.id.list_item_one_im);
                    holder1.tv1 = (TextView) convertView.findViewById(R.id.list_item_one_tv);
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    convertView = inflater.inflate(R.layout.item_list_two,parent, false);
                    holder2 = new ViewHolder2();
                    holder2.iv2 = (ImageView) convertView.findViewById(R.id.list_item_two_im);
                    holder2.tv2 = (TextView) convertView.findViewById(R.id.list_item_two_tv);
                    convertView.setTag(holder2);
                    break;
                case TYPE_3:
                    convertView = inflater.inflate(R.layout.item_list_three,parent, false);
                    holder3 = new ViewHolder3();
                    holder3.iv3 = (ImageView) convertView.findViewById(R.id.list_item_three_im);
                    holder3.tv3 = (TextView) convertView.findViewById(R.id.list_item_three_tv);
                    convertView.setTag(holder3);
                    break;
                default:
                    break;
            }

        } else {
            switch (type) {
                case TYPE_1:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case TYPE_3:
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;
            }
        }
        // 设置资源
        switch (type) {
            case TYPE_1:
                holder1.tv1.setText(getItem(position));
                break;
            case TYPE_2:
                holder2.tv2.setText(getItem(position));
                break;
            case TYPE_3:
                holder3.tv3.setText(getItem(position));
                break;
        }

        return convertView;
    }

    public class ViewHolder1 {
        ImageView iv1;
        TextView tv1;
    }

    public class ViewHolder2 {
        ImageView iv2;
        TextView tv2;
    }

    public class ViewHolder3 {
        ImageView iv3;
        TextView tv3;
    }

}
