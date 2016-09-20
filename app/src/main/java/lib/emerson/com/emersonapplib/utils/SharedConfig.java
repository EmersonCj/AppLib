package lib.emerson.com.emersonapplib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */
/*
*   利用SharedPreferences存储数据
* */
public class SharedConfig {
    //应用上下文
    private Context mContext=null;
    private SharedPreferences mShare=null ;
    //单例模式
    private  static SharedConfig mInstance=null;

    /* 1,先获得sharePreferences对象，最后保存的文件名字为sharedconfig.xml，模式默认选MODE_PRIVATE */
    public SharedConfig(Context context) throws Exception{
        if(context==null){
            throw new Exception("上下文参数不能为空");
        }
        mContext = context;
        mInstance = this;
        mShare = mContext.getSharedPreferences("sharedconfig", Context.MODE_PRIVATE);
        if(mShare==null){
            throw new Exception("获取共享配置失败");
        }
    }

    public static SharedConfig getInstance(Context context) throws Exception{
        if(mInstance == null){
            mInstance = new SharedConfig(context);
        }
        return mInstance;
    }


    /*  2，从sharePreferences对象中获得Editor对象。因为如果要编辑xml文件，就必须有一个editor对象
    * 下面封装了一些方法,实际上还是利用Editor对象*/

    //设置整数值
    public void setIntValue(String keyName,int value){
        if(mShare==null){
            mShare= mContext.getSharedPreferences("sharedconfig", Context.MODE_PRIVATE);
        }
        if(mShare!=null){
            SharedPreferences.Editor edit = mShare.edit();
            edit.putInt(keyName, value);
            edit.commit();
        }
    }

    //获取整数值
    public int getIntValue(String keyName,int defaultValue){
        if(mShare==null){
            mShare= mContext.getSharedPreferences("sharedconfig", Context.MODE_PRIVATE);
        }
        if(mShare!=null){
            defaultValue=mShare.getInt(keyName, defaultValue);
        }
        return defaultValue;
    }

    //设置字符串值
    public void setStringValue(String keyName,String value)
    {
        if(mShare==null){
            mShare= mContext.getSharedPreferences("sharedconfig", Context.MODE_PRIVATE);
        }
        if(mShare!=null){
            SharedPreferences.Editor edit = mShare.edit();
            edit.putString(keyName, value);
            edit.commit();
        }
    }

    //获取字符串值
    public String getStringValue(String keyName,String defaultValue)
    {
        if(mShare==null){
            mShare= mContext.getSharedPreferences("sharedconfig", Context.MODE_PRIVATE);
        }
        if(mShare!=null){
            defaultValue=mShare.getString(keyName, defaultValue);
        }
        return defaultValue;
    }

    /*可根据项目要求去封装方法*/

    //设置用户id
    public void setUserId(String userId,String value)
    {
        if(mShare==null){
            mShare= mContext.getSharedPreferences("sharedconfig", Context.MODE_PRIVATE);
        }
        if(mShare!=null){
            SharedPreferences.Editor edit = mShare.edit();
            edit.putString(userId, value);
            edit.commit();
        }
    }

    //获取用户id
    public String getUserId(String userId,String defaultValue)
    {
        if(mShare==null){
            mShare= mContext.getSharedPreferences("sharedconfig", Context.MODE_PRIVATE);
        }
        if(mShare!=null){
            defaultValue=mShare.getString(userId, defaultValue);
        }
        return defaultValue;
    }

    //一次性将数据写进去
    public boolean save(Context context, Map<String, String> settings, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (String strKey : settings.keySet()) {
            editor.putString(strKey, settings.get(strKey));
        }
        return editor.commit();
    }

    //一次性全部将数据读出来
    @SuppressWarnings("unchecked")
    public Map<String, String> read(Context context,String fileName) {
        Map<String, String> settings = new HashMap<String, String>();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        settings = (Map<String, String>) sp.getAll();
        return settings;
    }



    /**
     * desc:保存对象

     * @param context
     * @param key
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    public static void saveObject(Context context,String key ,Object obj,String filename){
        try {
            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(filename,Context.MODE_PRIVATE).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
            Log.e("TAG", "保存obj成功");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "保存obj失败");
        }
    }
    /**
     * desc:将数组转为16进制
     * @param bArray
     * @return
     * modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * desc:获取保存的Object对象
     * @param context
     * @param key
     * @return
     * modified:
     */
    public Object readObject(Context context,String key,String filename){
        try {
            SharedPreferences sharedata = context.getSharedPreferences(filename,Context.MODE_PRIVATE);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if(TextUtils.isEmpty(string)){
                    return null;
                }else{
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is=new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    Log.e("TAG", "读取obj成功");
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }
    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     * @param data
     * @return
     * modified:
     */
    public static byte[] StringToBytes(String data){
        String hexString=data.toUpperCase().trim();
        if (hexString.length()%2!=0) {
            return null;
        }
        byte[] retData=new byte[hexString.length()/2];
        for(int i=0;i<hexString.length();i++)
        {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if(hex_char1 >= '0' && hex_char1 <='9')
                int_ch1 = (hex_char1-48)*16;   //// 0 的Ascll - 48
            else if(hex_char1 >= 'A' && hex_char1 <='F')
                int_ch1 = (hex_char1-55)*16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if(hex_char2 >= '0' && hex_char2 <='9')
                int_ch2 = (hex_char2-48); //// 0 的Ascll - 48
            else if(hex_char2 >= 'A' && hex_char2 <='F')
                int_ch2 = hex_char2-55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1+int_ch2;
            retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }


}
