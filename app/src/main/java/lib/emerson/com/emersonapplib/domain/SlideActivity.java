package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SlideActivity extends baseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silde);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, DifferentMenuActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, WeixinDeleteActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, SlideFourActivity.class));
                break;
        }
    }

    /*前三种是使用继承Recycleview的控件完成的
    * 而第四种是通过修改item的，还是用原生的Recycleview的
    * 还有就是使用第三方库，SwipeRecyclerView（请看博客：http://blog.csdn.net/yanzhenjie1003/article/details/52115566）*/

}
