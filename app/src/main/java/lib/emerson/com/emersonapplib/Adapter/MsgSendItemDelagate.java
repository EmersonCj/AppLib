package lib.emerson.com.emersonapplib.Adapter;

import android.view.View;

import lib.emerson.com.emersonapplib.Adapter.Base.Listview.Item.ItemViewDelegate;
import lib.emerson.com.emersonapplib.Adapter.Base.Listview.ViewHolder;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.ChatMessage;
import lib.emerson.com.emersonapplib.utils.zhyUtils.ToastUtils;

/**
 * Created by YangJianCong on 2016/10/19.
 */

public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_send_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return !item.isComMeg();
    }

    @Override
    public void convert(final ViewHolder holder, final ChatMessage chatMessage, int position) {
        holder.setText(R.id.chat_send_content, chatMessage.getContent());
        holder.setText(R.id.chat_send_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_send_icon, chatMessage.getIcon());
        holder.setOnClickListener(R.id.layout_chat_linear_id, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(holder.getmContext(),chatMessage.getContent());
            }
        });
    }

}
