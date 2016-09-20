package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/10.
 */
public class WeixinDeleteActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener,
        SlideView.OnSlideListener {

    private static final String TAG = "WeixinDeleteActivity";

    private ListViewCompat mListView;

    private List<MessageItem> mMessageItems = new ArrayList<WeixinDeleteActivity.MessageItem>();

    private SlideAdapter mSlideAdapter;

    // 上次处于打开状态的SlideView
    private SlideView mLastSlideViewWithStatusOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_delete);
        initView();
    }

    private void initView() {
        mListView = (ListViewCompat) findViewById(R.id.list);

        for (int i = 0; i < 20; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.iconRes = R.drawable.default_qq_avatar;
                item.title = "腾讯新闻";
                item.msg = "青岛爆炸满月：大量鱼虾死亡";
                item.time = "晚上18:18";
            } else {
                item.iconRes = R.drawable.wechat_icon;
                item.title = "微信团队";
                item.msg = "欢迎你使用微信";
                item.time = "12月18日";
            }
            mMessageItems.add(item);
        }
        mSlideAdapter = new SlideAdapter();
        mListView.setAdapter(mSlideAdapter);
        mListView.setOnItemClickListener(this);
    }

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                // 这里是我们的item
                View itemView = mInflater.inflate(R.layout.list_item, null);

                slideView = new SlideView(WeixinDeleteActivity.this);
                // 这里把item加入到slideView
                slideView.setContentView(itemView);
                // 下面是做一些数据缓存
                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(WeixinDeleteActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(WeixinDeleteActivity.this);
            return slideView;
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 这里处理ListItem的点击事件
        Log.e(TAG, "onItemClick position=" + position);
    }

    @Override
    public void onSlide(View view, int status) {
        // 如果当前存在已经打开的SlideView，那么将其关闭
        if (mLastSlideViewWithStatusOn != null
                && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }
        // 记录本次处于打开状态的view
        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        // 这里处理删除按钮的点击事件，可以删除对话
        if (v.getId() == R.id.holder) {
            int position = mListView.getPositionForView(v);
            if (position != ListView.INVALID_POSITION) {
                mMessageItems.remove(position);
                mSlideAdapter.notifyDataSetChanged();
            }
            Log.e(TAG, "onClick v=" + v);
        }
    }
}

