package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.Image.BitmapUtils;

/**
 * Created by Administrator on 2016/8/26.
 * 参考：http://blog.csdn.net/jdsjlzx/article/details/44228935
 * Bitmap工具类：
 */
public class BitmapUtilsActivity extends baseActivity implements View.OnClickListener {
    @ViewInject(R.id.bitmap_utils_bt)
    private Button bt;
    @ViewInject(R.id.bitmap_utils_iv)
    private ImageView iv;
    @ViewInject(R.id.bitmap_utils_dialog)
    private RelativeLayout dialog;


    //页面请求识别码
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;

    //保存拍照生成的图片 的路径及文件名
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA); // 格式化时间
    private final String IMAGE_FILE_NAME = format.format(date) + ".png";
    private String headShotString = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera";
    private final File cameraShotDirectory = new File(headShotString);

    private BitmapUtils bitmapUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_utils);
        bitmapUtils = BitmapUtils.getInstance();
        x.view().inject(this);

    }

    /**
     * 点击更新头像
     *
     */
    @Event(value = R.id.bitmap_utils_bt)
    private void getBitmapOnClick(View view) {
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);
        findViewById(R.id.gallery).setOnClickListener(this);
        dialog.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                toast("取消");
                break;
            case R.id.camera:
                //从相机拍照选择
                choseHeadImageFromCameraCapture();
                toast("相机");
                break;
            case R.id.gallery:
                //从图库中选择
                choseHeadImageFromGallery();
                toast("相册");
                break;
            default:
                break;
        }
        dialog.setVisibility(View.GONE);
    }


    /*****
     * 实现跳转到系统图库。 intent.setType( )显式指定Intent的数据类型MIME 。
     * intent.setAction()指Intent要完成的动作
     */
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }


    /*******
     * 实现跳转到相机
     */
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储是否可用
        if (hasSdcard()) {
            // 带参（新建拍照后的文件(路径、文件名)）跳转
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraShotDirectory, IMAGE_FILE_NAME)));
        }
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }


    /***
     * 处理跳转的返回结果
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:          //从图库页面回来   (图片信息保存在intent中)
                Uri uri = intent.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Log.e("size",bitmapUtils.getBitmapSize(bitmap)/1024 + " kb");
                    Bitmap newBitmap = bitmapUtils.ratio(bitmap,640,480);
                    Log.e("size",bitmapUtils.getBitmapSize(newBitmap)/1024 + " kb");
                    iv.setImageBitmap(newBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case CODE_CAMERA_REQUEST:       //从相机页面回来   (图片保存在传过去的参数cameraShotDirectory/IMAGE_FILE_NAME的文件中)
                if (hasSdcard()) {
                    //文件名：cameraShotDirectory/IMAGE_FILE_NAME
                    //File tempFile = new File(cameraShotDirectory,IMAGE_FILE_NAME);
                    Bitmap camerabitmap = bitmapUtils.getBitmapFromPath(cameraShotDirectory + "/" +IMAGE_FILE_NAME);
                    Log.e("size",bitmapUtils.getBitmapSize(camerabitmap)/1024 + " kb");
                    Bitmap newBitmap1 = bitmapUtils.ratio(camerabitmap,640,480);
                    Log.e("size",bitmapUtils.getBitmapSize(newBitmap1)/1024 + " kb");
                    iv.setImageBitmap(camerabitmap);
                } else {
                    Toast.makeText(getApplication(), "SDCard不存在，无法存储图像!",Toast.LENGTH_SHORT).show();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }




    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


















    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
}
