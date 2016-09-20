package lib.emerson.com.emersonapplib.domain;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/8.
 *          http://blog.csdn.net/harvic880925/article/details/49272285
 */
public class PopUpWindowActivity extends baseActivity implements  View.OnClickListener {
    private Button btOne,btTwo;
    private String[] numbers = {"1","2","3","4","5","6","7","8","9","","0",""};
    private String[] numEngs = {"","ABC","DEF","GHI","JKL","MNO","PQRS","TUV","WXYZ","","",""};
    private View convertView;
    private List<String> list = new ArrayList<>();
    private GridView gridView;
    private TextView tvNumber;
    private StringBuilder sb ;      //字符串变量


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow);
        btOne = (Button)findViewById(R.id.popupwindow_bt_one);
        btTwo = (Button)findViewById(R.id.popupwindow_bt_two);
        btOne.setOnClickListener(this);
        btTwo.setOnClickListener(this);
        init();
    }

    private void init() {
        convertView = View.inflate(this,R.layout.popup_view,null);
        gridView = (GridView) convertView.findViewById(R.id.my_gv);
        gridView.setAdapter(new MyAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 11){                     //1，删除键
                    if(sb.length() == 0){
                        return;
                    }
                    sb.deleteCharAt(sb.length()-1);     //从字符串中删除最后一位数字
                    tvNumber.setText(sb.toString());
                    return;
                }

                if(TextUtils.isEmpty(numbers[position])){  //2，判断是否为空键
                    return;                                //不做任何处理
                }

                if(sb.length() == 0 && "0".equals(numbers[position])){  //避免重复输入0，造成0000000
                    return;
                }

                sb.append(numbers[position]);           //默认从字符串后面追加数字
                tvNumber.setText(sb.toString());
            }
        });

        tvNumber = (TextView) findViewById(R.id.tv_number);
        sb = new StringBuilder("");

        for (int i = 0;i < 20;i++){
            list.add(i+"");
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.popupwindow_bt_one){
            showPopupWindow();
        }else {
            showPopup();
        }
    }

    private PopupWindow myPop;
    public void showPopup(){
        myPop = new PopupWindow(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true); // 设置布局
        // myPop.setOutsideTouchable(true);
        myPop.setBackgroundDrawable(new BitmapDrawable()); // 背景颜色
        myPop.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画效果
        myPop.showAtLocation(View.inflate(this,R.layout.popup_view,null), Gravity.RIGHT|Gravity.BOTTOM, 0,0); // 显示在哪个位置
    }


    private PopupWindow popupWindow;
    private void showPopupWindow() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null);
        //View contentView = LayoutInflater.from(this).inflate(R.layout.popup_assess, null);
        //1，构造出PopupWindow实例
        popupWindow =  new PopupWindow (contentView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        //2，设置显示位置[这里我们设置为相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移]
        View parentview = LayoutInflater.from(PopUpWindowActivity.this).inflate(R.layout.activity_popupwindow, null);
        popupWindow.showAtLocation(parentview, Gravity.CENTER,0,0);
        contentView.findViewById(R.id.yunfei_layout_zhidaol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //3,监听返回键
        contentView.setFocusable(true);                 //设置view能够接听事件，标注1
        contentView.setFocusableInTouchMode(true);      //设置view能够接听事件 标注2
        contentView.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK){
                    if(popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });

        //4，为popupWindow设置动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }




    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return numbers.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            Holder holder = null;
            if(convertView == null){
                view = View.inflate(PopUpWindowActivity.this,R.layout.item_gv,null);
                holder = new Holder();
                holder.tvNum = (TextView) view.findViewById(R.id.number);
                holder.tvEng = (TextView) view.findViewById(R.id.tv_eng);
                holder.ivClear = (ImageView) view.findViewById(R.id.iv_clear);
                holder.llNum = (LinearLayout) view.findViewById(R.id.ll_num);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (Holder) view.getTag();
            }

            holder.tvNum.setText(numbers[position]);
            holder.tvEng.setText(numEngs[position]);
            if(position == numbers.length - 1){
                holder.ivClear.setVisibility(View.VISIBLE);
                holder.llNum.setVisibility(View.INVISIBLE);
            }

            return view;
        }

        class Holder{
            TextView tvNum;
            TextView tvEng;
            ImageView ivClear;
            LinearLayout llNum;
        }
    }




}
