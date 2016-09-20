package lib.emerson.com.emersonapplib.domain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.CircleImageView;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CircleimageviewActivity extends baseActivity implements View.OnClickListener {
    private LinearLayout mHeaderLayoutLeftviewContainer;
    private RelativeLayout rlDialogCustomView;
    private Button bt;
    private CircleImageView circleImageView;

    /* 头像文件名:传入的用户ID+head.jpg----- 存储路径：不用改 */
    private String headShotName = "avator.png";
    private final String headShotDirectoryString = Environment.getExternalStorageDirectory() + File.separator + "Hoheiya" + File.separator + "headPic";
    private final File headShotDirectory = new File(headShotDirectoryString);   //头像文件夹

    //页面请求识别码
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    //保存拍照生成的图片 的路径及文件名
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA); // 格式化时间
    private final String IMAGE_FILE_NAME = format.format(date) + ".png";

    private String headShotString = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera";
    private final File cameraShotDirectory = new File(headShotString);

    // 剪裁后的图片宽、高
    private static int output_X = 250;
    private static int output_Y = 250;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_imageview);
        x.view().inject(this);
        init();
    }

    private void init() {
        rlDialogCustomView = (RelativeLayout) findViewById(R.id.dialog_container);
        bt = (Button) findViewById(R.id.circle_bt);
        circleImageView = (CircleImageView) findViewById(R.id.circleImageView);
        mkDirs();
    }

    /**
     * 点击更新头像
     *
     */
    @Event(value = R.id.circle_bt)
    private void UploadAvatarOnClick(View view) {

        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);
        findViewById(R.id.gallery).setOnClickListener(this);
        rlDialogCustomView.setVisibility(View.VISIBLE);
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
        rlDialogCustomView.setVisibility(View.GONE);
    }


    /*****
     * 实现跳转到系统图库。 intent.setType( )显式指定Intent的数据类型MIME 。
     * intent.setAction()指Intent要完成的动作
     */
    private void choseHeadImageFromGallery() {
        //第一种跳转在4.4系统上存在bug。请用第二种
         /*Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image*//*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);*/

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
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri .fromFile(new File(cameraShotDirectory, IMAGE_FILE_NAME)));
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
            case CODE_GALLERY_REQUEST:      //从图库页面回来
                // 获取图像处理后的已经实现Parcelable接口的图片信息作为参数
                Uri url = intent.getData();
                Log.e("url",url.toString());
                cropRawPhoto(url);
                break;

            case CODE_CAMERA_REQUEST:       //从相机页面回来
                if (hasSdcard()) {
                    //文件名：cameraShotDirectory/IMAGE_FILE_NAME
                    File tempFile = new File(cameraShotDirectory, IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "SDCard不存在，无法存储图像!",Toast.LENGTH_SHORT).show();
                }
                break;
            case CODE_RESULT_REQUEST:       //图片裁剪处理后返回
                if (intent != null) {
                    setImageToHeadView(intent);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /***********
     * 跳转至可处理图像
     *
     * @param uri
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            /***
             * 对象实现android 提供的 parcelable接口， 就可以使用在Activity之间传递。。。。
             * putExtra(String name,Parcelable value) -->
             * getExtras().getParceable(String name)
             */
            intent.putExtra("", extras);
            Bitmap photo = extras.getParcelable("data");
            /*********************
             * 显示和保存图片
             */
            saveBitmap(photo);
        }
    }

    private String path = "";
    /**
     * 保存图片到头像到服务器和本地
     */
    public void saveBitmap(Bitmap bm) {
        headShotName = IMAGE_FILE_NAME;
        //headShotDirectory为路径，headShotName为文件名
        File f = new File(headShotDirectory, headShotName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            path = f.getAbsolutePath();
            Log.e("TAG", "成功保存图片到头像文件夹:" + path);
            //成功保存图片到本地之后，对头像进行替换和上传服务器
            if(bm!=null){
                circleImageView.setImageBitmap(bm);
            }
            // uploadFile(path);    //保存图片到服务器
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mkDirs() {
        if (!cameraShotDirectory.exists()) {
            cameraShotDirectory.mkdirs();
        }
        if (!headShotDirectory.exists()) {
            headShotDirectory.mkdirs();
        }
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
