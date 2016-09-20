package lib.emerson.com.emersonapplib.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import java.io.File;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类，一些不太好归类的常用操作
 * 包括判断是否有网络连接，是否是在wifi环境下使用
 * 弹出、收起软键盘等操作
 *
 * @author tarena.sunwei
 *
 */
public class CommonUtils {

	/**
	 * 检查当前设备是否有网络连接
	 * 无论是哪种形式的连接（3G，Wifi等等），只要有连接就算有网络连接
	 * @param context
	 * @return
	 */

	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			return info.isAvailable();
		}
		return false;
	}

	/**
	 * 当前是否处于Wifi下使用
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI)
				return true;
		}
		return false;
	}

	/**
	 * 是否使用3G网络
	 *
	 * @param context
	 * @return
	 */
	public static boolean isMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE)
				return true;
		}
		return false;
	}

	/**
	 * 获得描述当前设备网络状况的NetworkInfo对象
	 * @param context
	 * @return
	 */
	private static NetworkInfo getNetworkInfo(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/**
	 * 检查当前设备是否有SD卡
	 * 在进行语音文件的录制和下载时，以及使用相机拍照时，使用百度地图截图文件时
	 * 都需要SD来保存这些内容
	 * @return
	 */

	public static boolean checkSdCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
	/**
	 * 判定字符串是否为“空”
	 * 功能与TextUtils.isEmpty一样
	 * @param str
	 * @return
	 */
	public static boolean isValidate(String str){
		return TextUtils.isEmpty(str);
	}
	/**
	 * 如果软键盘处于弹出状态，将其隐藏
	 * @param activity
	 */
	public static void hiddenInput(Activity activity){
		InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN){
			if (activity.getCurrentFocus() != null)
				imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	/**
	 * 如果软键盘处于不可见状态，让其弹出
	 * @param activity
	 * @param view
	 */
	public static void showInput(Activity activity, View view){
		InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE){
			if (activity.getCurrentFocus() != null)
				imm.showSoftInput(view, 0);
		}

	}
	/**
	 * 获取设备屏幕的宽度
	 * @param act
	 * @return
	 */
	public static int getScreenWidth(Activity act){
		DisplayMetrics outMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(outMetrics );
		return outMetrics.widthPixels;
	}
	/**
	 * 获取设备屏幕的高度
	 * @param act
	 * @return
	 */
	public static int getScreenHeight(Activity act){
		DisplayMetrics outMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(outMetrics );
		return outMetrics.heightPixels;
	}

	/**
	 * 将目标字符串使用MD5算法加密
	 * @param str 目标字符串
	 * @return 使用MD5加密后的字符串
	 */
	public static String getMD5String(String str){
		String result=null;
		try{
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] bytes = str.getBytes();
			md.update(bytes);
			byte[] md5bytes = md.digest();
			StringBuffer sb = new StringBuffer();
			String temp=null;
			for(int i=0;i<md5bytes.length;i++){
				temp= Integer.toHexString(md5bytes[i]&0xFF);
				if(temp.length()==1){
					sb.append("0");
				}else{
					sb.append(temp);
				}
			}
			result = sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 在指定位置（文件夹中）创建新文件
	 * 既然是在指定文件夹中创建文件，就首先要保证目标文件夹存在
	 * 然后判断文件夹中是否已经有同名的文件，如果没有，就创建指定名称的文件
	 *
	 * @param dir 指定的文件夹
	 * @param fileName 指定的文件名称
	 * @param extName 文件的扩展名
	 * @param md5 文件名（不包含扩展名）是否使用MD5加密
	 * @return 创建好的文件
	 */
	public static File makeFile(String dir, String fileName, String extName, boolean md5){
		File file = null;
		try{
			//如果指定的文件夹不存在，先创建文件夹
			File fileDir = new File(dir);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
			String f = fileName;
			if(md5)
				f= getMD5String(fileName);
			if(!TextUtils.isEmpty(extName))
				f += extName;
			file = new File(fileDir,f);
			if(!file.exists()){
				file.createNewFile();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return file;
	}


	/**
	 * 检验手机号码格式
	 * @param mobiles
	 * @return
     */
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}


	/*
	* 验证密码格式
	* 6-13位,必须包含数字和英文
	*
	* */
	public static boolean isPassword(String Code){
		Pattern pt = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,13}$");
		Matcher mc = pt.matcher(Code);
		return mc.matches();
	}

	/**
	 * 打开软键盘
	 *
	 */
	public static void openKeybord(EditText mEditText, Context mContext){
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 */
	public static void closeKeybord(EditText mEditText, Context mContext){
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}


}
