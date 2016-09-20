package lib.emerson.com.emersonapplib.utils.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by YangJianCong on 2016/8/26.
 * 参考：http://blog.csdn.net/jdsjlzx/article/details/44228935
 */
public class BitmapUtils {
    /*
    * 将bitmap按照指定的格式和压缩比，写入到指定的数据流（即保存到文件）中。
    * bitmap.compress(CompressFormat format, int quality, OutputStream stream)
    *   format：压缩图像的格式。（JPEG，JPG.....）
    *   quality：第二个参数为压缩率（ 0 - 100 ），如果不压缩是100，表示压缩率为0%
    *   stream：要保存的文件的数据流。
    *   注意：当调用bitmap.compress(CompressFormat.JPEG, 100, fos);保存为图片时发现图片背景为黑色
             这时只需要改成用png保存就可以了，bitmap.compress(CompressFormat.PNG, 100, fos);
    * */

    private static BitmapUtils mbitmapUtils;

    public static BitmapUtils getInstance(){
        if (mbitmapUtils == null){
            synchronized (ImageLoaderUtils.class){
                if (mbitmapUtils == null){
                    mbitmapUtils = new BitmapUtils();
                }
            }
        }
        return mbitmapUtils;
    }

    /**
     * 从指定路径 imgPath 生成 bitmap（没有进行压缩）
     */
    public Bitmap getBitmapFromPath(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * 将bitmap 存储到指定的路径outPath中
     */
    public void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }



    /*----------------------------------------------------常用的压缩方式--------开始------------------------------------------------------------*/
    /* 一，(尺寸压缩) 将图片从本地File--------(进行压缩)--------->读到内存Bitmap, ,即图片从File形式变为Bitmap形式
    * 特点: 通过设置采样率, 减少图片的像素, 达到对内存中的Bitmap进行压缩，图片文件File大小也会改变*/


    /**1.1
     * 通过改变图片文件imgPath的宽度/高度（pixelW/pixelH），对图片进行像素的压缩（属于尺寸压缩）
     *  注意：该方法就是对Bitmap形式的图片进行压缩, 也就是通过设置采样率, 减少Bitmap的像素, 从而减少了它所占用的内存
     */
    public Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //1，开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath,newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {              //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {       //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;      //设置缩放比例
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);    // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了

        return bitmap;
    }

    /**
     * 和上面的方法一样，只是源文件也是Bitmap，即源Bitmap-------（尺寸压缩）-------> 返回Bitmap
     */
    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if( os.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        return bitmap;
    }

    /*按照图片尺寸进行压缩*/
    public static void compressPicture(String srcPath, String desPath) {
        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;

        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        float hh = 1024f;//
        float ww = 1024f;//
        // 最长宽度或高度1024
        float be = 1.0f;
        if (w > h && w > ww) {
            be = (float) (w / ww);
        } else if (w < h && h > hh) {
            be = (float) (h / hh);
        }
        if (be <= 0) {
            be = 1.0f;
        }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            fos = new FileOutputStream(desPath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    /*二，(质量压缩) 将图片Bitmap---------(进行压缩)---------->保存到本地File, 即将图片从Bitmap形式变为File形式时进行压缩
    * 特点：File形式的图片确实被压缩了, 但是当你重新读取压缩后的file为 Bitmap是,它占用的内存并没有改变 */

    /** 2.1
     * 将图像Bitmap按质量进行压缩，并生成图像文件保存到指定的路径outPath，并注意maxSize意思：即目标最后压缩后生成的文件的最大值（kb）
     * 这种方式压缩得图片，可以减少文件的存储体积，有利于文件上传。
     */
    public void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;                                          //设置默认压缩比
        image.compress(Bitmap.CompressFormat.JPEG, options, os);    //将位图存储到输出流中，没进行压缩
        while ( os.toByteArray().length / 1024 > maxSize) {         //循环压缩，直到图片的大小  < 最大值，此时就达到要求
            os.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);    //压缩
        }

        FileOutputStream fos = new FileOutputStream(outPath);           //获取outPath文件的数据流。
        fos.write(os.toByteArray());                                    //对数据流进行写入操作，生成压缩图像文件
        fos.flush();
        fos.close();
    }


    /**
     * 将源文件imgPath------(getBitmap)--->转为Bitmap------(进行质量压缩)-----> 保存到指定路径outPath
     * needsDelete：是否要删除源文件imgPath
     */
    public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmapFromPath(imgPath), outPath, maxSize);

        // Delete original file
        if (needsDelete) {
            File file = new File (imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
    /*----------------------------------------------------常用的压缩方式---------结束-------------------------------------------------------------*/


    /**
     * 源图像image-------（尺寸压缩）-------> 返回Bitmap------（保存到指定路径）----> outPath
     */
    public void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage( bitmap, outPath);
    }

    /**
     * Ratio and generate thumb to the path specified
     * @param imgPath
     * @param outPath
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @param needsDelete Whether delete original file after compress
     * @throws FileNotFoundException
     */
    public void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
        storeImage( bitmap, outPath);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /*
    * 获取Bitmap图片大小
    * */
    public int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){       //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

}
