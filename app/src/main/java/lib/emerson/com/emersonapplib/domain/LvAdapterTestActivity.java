package lib.emerson.com.emersonapplib.domain;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import java.util.List;

import lib.emerson.com.emersonapplib.Adapter.Base.Listview.BaseLvAdapter;
import lib.emerson.com.emersonapplib.Adapter.Base.Listview.MultiItemTypeAdapter;
import lib.emerson.com.emersonapplib.Adapter.MsgComingItemDelagate;
import lib.emerson.com.emersonapplib.Adapter.MsgSendItemDelagate;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.ChatMessage;

/**
 * Created by YangJianCong on 2016/10/19.
 */
public class LvAdapterTestActivity extends baseActivity {
    private ListView mListView;
    private BaseLvAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_adapter_test);
        mListView = (ListView) findViewById(R.id.id_listview_list);

        mListView.setDivider(null);
        mListView.setAdapter(new ChatAdapter(this, ChatMessage.MOCK_DATAS));
    }


    public class ChatAdapter extends MultiItemTypeAdapter<ChatMessage> {
        public ChatAdapter(Context context, List<ChatMessage> datas) {
            super(context, datas);

            //加载不同的布局
            addItemViewDelegate(new MsgSendItemDelagate());
            addItemViewDelegate(new MsgComingItemDelagate());
        }

    }


}
