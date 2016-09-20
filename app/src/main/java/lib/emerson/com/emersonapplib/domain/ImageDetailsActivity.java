package lib.emerson.com.emersonapplib.domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.ZoomImageView;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ImageDetailsActivity extends baseActivity {

    private ZoomImageView zoomImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_details);
        zoomImageView = (ZoomImageView) findViewById(R.id.zoom_image_view);
        String imagePath = getIntent().getStringExtra("image_path");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        zoomImageView.setImageBitmap(bitmap);
    }


}