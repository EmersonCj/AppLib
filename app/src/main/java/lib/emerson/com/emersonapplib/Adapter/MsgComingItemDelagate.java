package lib.emerson.com.emersonapplib.Adapter;

import lib.emerson.com.emersonapplib.Adapter.Base.Listview.Item.ItemViewDelegate;
import lib.emerson.com.emersonapplib.Adapter.Base.Listview.ViewHolder;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.ChatMessage;

/**
 * Created by YangJianCong on 2016/10/19.
 */

public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_from_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position) {
        holder.setText(R.id.chat_from_content, chatMessage.getContent());
        holder.setText(R.id.chat_from_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_from_icon, chatMessage.getIcon());
    }

}
