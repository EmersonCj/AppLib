package lib.emerson.com.emersonapplib.utils.Image;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.widget.ImageView;

/**
 * http://blog.csdn.net/lmj623565791/article/details/41874561
 * @author zhy
 *
 * 网络图片的下载和压缩（硬盘缓存是否开启，即能否保存在本地）
		a、如果开启了，就直接下载存到sd卡，然后采用本地的压缩方案，这里只有下载，压缩还要调用ImageLoaderUtils中的decodeSampledBitmapFromPath方法（第一种）
		b、如果没有开启，就使用BitmapFactory.decodeStream(is, null, opts);    该函数有图片的下载和压缩功能（第二种）

	 BitmapFactory这个类提供了多个解析图片的方法(decodeByteArray, decodeFile, decodeResource等)用于创建Bitmap对象，
	我们应该根据图片的来源选择合适的方法。比如SD卡中的图片可以使用decodeFile方法，
	网络上的图片可以使用decodeStream方法，资源文件中的图片可以使用decodeResource方法

 */
public class DownloadImgUtils
{

	/**
	 * 根据url下载图片，并保存到指定的文件
	 * 
	 * @param urlStr
	 * @param file
	 * @return
	 */
	public static boolean downloadImgByUrl(String urlStr, File file){
		FileOutputStream fos = null;
		InputStream is = null;
		try
		{
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			is = conn.getInputStream();
			fos = new FileOutputStream(file);
			byte[] buf = new byte[512];
			int len = 0;
			while ((len = is.read(buf)) != -1)
			{
				fos.write(buf, 0, len);
			}
			fos.flush();
			return true;

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
			}

			try
			{
				if (fos != null)
					fos.close();
			} catch (IOException e)
			{
			}
		}

		return false;

	}

	/**
	 * 根据url下载图片，并显示到指定的ImageView控件上
	 * 
	 * @param urlStr
	 * @return
	 */
	public static Bitmap downloadImgByUrl(String urlStr, ImageView imageview)
	{
		FileOutputStream fos = null;
		InputStream is = null;
		try
		{
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			is = new BufferedInputStream(conn.getInputStream());
			is.mark(is.available());

			//将图片转为bitmap
			//1，首先第一次解析，将inJustDecodeBounds设置为true，来获取图片的宽和高，但此时并不把图片加载到内存中
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);

			ImageSizeUtil.ImageSize imageViewSize = ImageSizeUtil.getImageViewSize(imageview);		//获取imageview想要显示的宽和高
			//2，计算inSampleSize值
			opts.inSampleSize = ImageSizeUtil.caculateInSampleSize(opts,
					imageViewSize.width, imageViewSize.height);

			//3，第二次解析，使用获得到的InSampleSize再次解析图片，因为这次图片是要读取出来的，所以inJustDecodeBounds设置为false
			opts.inJustDecodeBounds = false;
			is.reset();
			bitmap = BitmapFactory.decodeStream(is, null, opts);

			conn.disconnect();
			return bitmap;

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
			}

			try
			{
				if (fos != null)
					fos.close();
			} catch (IOException e)
			{
			}
		}

		return null;

	}

}
