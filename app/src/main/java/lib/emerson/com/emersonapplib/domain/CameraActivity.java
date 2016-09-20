package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/8.
 *
 * http://www.cnblogs.com/mengdd/archive/2013/03/31/2991932.html
 */
public class CameraActivity extends baseActivity {
    private Button bt;
    private ImageView iv;
    private static final String LOG_TAG = "HelloCamera";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    /*Uri代表要操作的数据，Android上可用的每种资源 - 图像、视频片段等都可以用Uri来表示
    "content://"、数据的路径、标示ID(可选)，例如：content://media/external/images/media/4*/
    private Uri fileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        bt = (Button)findViewById(R.id.camera_activity_bt);
        iv = (ImageView)findViewById(R.id.camera_activity_iv);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1，直接调用系统的相机应用，只需要在Intent对象中传入相应的参数即可
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                /*
                 * 如果想得到这个图像，你必须制定要存储的目标File，并且把它作为URI传给启动的intent，即使用MediaStore.EXTRA_OUTPUT作为关键字。
                 * 这样的话，拍摄出来的照片将会存在这个特殊指定的地方，此时没有thumbnail会被返回给activity的回调函数，所以接收到的Intent data为null。
                */

                //2，创建文件来保存拍照后的图片
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                //3，启动
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 如果是拍照
        if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode){
            if (RESULT_OK == resultCode){
                if (data != null){                          // 没有指定特定存储路径的时候
                    if (data.hasExtra("data")){
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        iv.setImageBitmap(thumbnail);
                    }
                }else{                                      // 指定特定存储路径的时候
                    int width = iv.getWidth();
                    int height = iv.getHeight();
                    // 1，首先第一次解析，将inJustDecodeBounds设置为true，来获取图片大小
                    BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
                    factoryOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(fileUri.getPath(), factoryOptions);

                    //2，计算inSampleSize值
                    int imageWidth = factoryOptions.outWidth;
                    int imageHeight = factoryOptions.outHeight;
                    int scaleFactor = Math.min(imageWidth / width, imageHeight/ height);

                    //3，第二次解析，因为这次图片是要读取出来的，所以inJustDecodeBounds设置为false
                    factoryOptions.inJustDecodeBounds = false;
                    factoryOptions.inSampleSize = scaleFactor;
                    factoryOptions.inPurgeable = true;

                    //将图片显示在Imageview
                    Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),factoryOptions);
                    iv.setImageBitmap(bitmap);
                }
            }else if (resultCode == RESULT_CANCELED){
                Log.e("CANCELED","CANCELED");
            }else{
                Log.e("failed","failed");
            }
        }
    }

    /*获取文件的uri*/
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*获取文件*/
    private static File getOutputMediaFile(int type)
    {
        //1，检测sd是否可用
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.e(LOG_TAG,"SD card is not avaiable/writeable right now.");
            return null;
        }

        //2，获取sd卡的目录
        File mediaStorageDir = null;
        try{
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MyCameraApp");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //3，在sd卡上创建一个文件夹
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        //在上面创建的文件夹下，创建保存图片的文件
        File mediaFile;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }else{
            return null;
        }

        return mediaFile;
    }




}
