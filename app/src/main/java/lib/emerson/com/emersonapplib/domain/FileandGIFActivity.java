package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

import org.xutils.image.GifDrawable;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.common.FileUtils;
import lib.emerson.com.emersonapplib.view.GifView;

/**
 * Created by Administrator on 2016/7/22.
 */
public class FileandGIFActivity extends baseActivity {
    @ViewInject(R.id.file_bt)
    private Button bt;
    @ViewInject(R.id.file_tv)
    private TextView tv;
    @ViewInject(R.id.gifview)
    private GifView gv;
    private FileUtils fileUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        x.view().inject(this);
        gv.setMovieResource(R.drawable.test);

        fileUtil = new FileUtils(this);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  str = fileUtil.getFromAssets("my_home_friends.txt");
                tv.setText(str);
            }
        });
    }



}
