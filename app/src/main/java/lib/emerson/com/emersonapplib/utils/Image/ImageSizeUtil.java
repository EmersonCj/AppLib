package lib.emerson.com.emersonapplib.utils.Image;

import java.lang.reflect.Field;

import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
/**
 * http://blog.csdn.net/lmj623565791/article/details/41874561
 * @author zhy
 *
 */
public class ImageSizeUtil
{
	/**
	 * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
	 * 
	 * @param options
	 * @return
	 */
	/*options里面存了图片实际的宽和高；reqWidth和reqHeight就是我们之前得到的想要显示的大小；经过比较，得到一个合适的inSampleSize;*/
	public static int caculateInSampleSize(Options options, int reqWidth,
			int reqHeight)
	{
		int width = options.outWidth;
		int height = options.outHeight;

		int inSampleSize = 1;

		if (width > reqWidth || height > reqHeight)
		{
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);

			inSampleSize = Math.max(widthRadio, heightRadio);
		}

		return inSampleSize;
	}

	/**
	 * 根据ImageView控件获适当的压缩的宽和高
	 * 
	 * @param imageView
	 * @return
	 */
	public static ImageSize getImageViewSize(ImageView imageView){
		ImageSize imageSize = new ImageSize();
		DisplayMetrics displayMetrics = imageView.getContext().getResources()
				.getDisplayMetrics();
		LayoutParams lp = imageView.getLayoutParams();


		/*  如何获得ImageView控件的高度，宽度
		1,首先企图通过getWidth获取显示的宽；有些时候，这个getWidth返回的是0；
		2,那么我们再去看看它有没有在布局文件中书写宽；
		3,如果布局文件中也没有精确值，那么我们再去看看它有没有设置最大值；
		4,如果最大值也没设置，那么我们只有拿出我们的终极方案，使用我们的屏幕宽度；
			总之，不能让它任性，我们一定要拿到一个合适的显示值。*/

		int width = imageView.getWidth();	//1, 获取imageview的实际宽度
		if (width <= 0)
		{
			width = lp.width;// 获取imageview在layout中声明的宽度
		}
		if (width <= 0)
		{
			 //width = imageView.getMaxWidth();// 检查最大值
			width = getImageViewFieldValue(imageView, "mMaxWidth");
		}
		if (width <= 0)
		{
			width = displayMetrics.widthPixels;
		}

		int height = imageView.getHeight();//2, 获取imageview的实际高度
		if (height <= 0)
		{
			height = lp.height;// 获取imageview在layout中声明的宽度
		}
		if (height <= 0)
		{
			height = getImageViewFieldValue(imageView, "mMaxHeight");// 检查最大值
		}
		if (height <= 0)
		{
			height = displayMetrics.heightPixels;
		}
		imageSize.width = width;
		imageSize.height = height;

		return imageSize;
	}

	public static class ImageSize
	{
		int width;
		int height;
	}
	
	/**
	 * 通过反射获取imageview的某个属性值
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static int getImageViewFieldValue(Object object, String fieldName)
	{
		int value = 0;
		try{
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = field.getInt(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
				value = fieldValue;
			}
		} catch (Exception e)
		{
		}
		return value;

	}

	
}
