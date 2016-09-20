package lib.emerson.com.emersonapplib.utils.common;

import android.content.Context;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/22.
 * 在Android中的文件放在不同位置，它们的读取方式也有一些不同。
    本文对android中对资源文件的读取、数据区文件的读取、 SD卡文件的读取及RandomAccessFile的方式和方法进行了整理

 总结：
     1、apk中有两种资源文件，使用两种不同的方式进行打开使用。
         raw使用InputStream in = getResources().openRawResource(R.raw.test);
         asset使用InputStream in = getResources().getAssets().open(fileName);
         ##这些数据只能读取，不能写入。更重要的是该目录下的文件大小不能超过1M。
         同时，需要注意的是，在使用InputStream的时候需要在函数名称后加上throws IOException。

     2、SD卡中的文件使用FileInputStream和FileOutputStream进行文件的操作。
     3、存放在数据区(/data/data/..)的文件只能使用openFileOutput和openFileInput进行操作。
     注意不能使用FileInputStream和FileOutputStream进行文件的操作。
     4、RandomAccessFile类仅限于文件的操作，不能访问其他IO设备。它可以跳转到文件的任意位置，从当前位置开始读写。
     5、InputStream和FileInputStream都可以使用skip和read(buffre,offset,length)函数来实现文件的随机读取。
 */
public class FileUtils {
    private Context mcontext;

    public FileUtils(Context context) {
        this.mcontext = context;
    }

    /*(一)资源文件的读取*/
    public String getFromRaw(){
        try {
            InputStreamReader inputReader = new InputStreamReader( mcontext.getResources().openRawResource(R.raw.beep));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public String getFromAssets(String fileName){
        String res="";
        try{

            //得到资源中的asset数据流
            InputStream in = mcontext.getResources().getAssets().open(fileName);

            int length = in.available();
            byte [] buffer = new byte[length];

            in.read(buffer);
            in.close();
            res = EncodingUtils.getString(buffer, "UTF-8");

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


    /*二、读写/data/data/<应用程序名>目录上的文件*/
    //写数据
    public void writeFile(String fileName,String writestr) throws IOException{
        try{
            FileOutputStream fout = mcontext.openFileOutput(fileName, mcontext.MODE_PRIVATE);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //读数据
    public String readFile(String fileName) throws IOException {
        String res="";
        try{
            FileInputStream fin = mcontext.openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


    /*三、读写SD卡中的文件。也就是/mnt/sdcard/目录下面的文件 */
    //写数据到SD中的文件
    public void writeFileSdcardFile(String fileName,String write_str) throws IOException{
        try{
            FileOutputStream fout = new FileOutputStream(fileName);
            byte [] bytes = write_str.getBytes();
            fout.write(bytes);
            fout.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //读SD中的文件
    public String readFileSdcardFile(String fileName) throws IOException{
        String res="";
        try{
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


    /*四、使用File类进行文件的读写：
    *   String Name = File.getName();  //获得文件或文件夹的名称：
        String parentPath = File.getParent();  //获得文件或文件夹的父目录
        String path = File.getAbsoultePath();//绝对路经
        String path = File.getPath();//相对路经
        File.createNewFile();//建立文件
        File.mkDir(); //建立文件夹
        File.isDirectory(); //判断是文件或文件夹
        File[] files = File.listFiles();  //列出文件夹下的所有文件和文件夹名
        File.renameTo(dest);  //修改文件夹和文件名
        File.delete();  //删除文件夹或文件*/

    //读文件
    public String readSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        int length = fis.available();
        byte [] buffer = new byte[length];
        fis.read(buffer);
        String res = EncodingUtils.getString(buffer, "UTF-8");
        fis.close();
        return res;
    }

    //写文件
    public void writeSDFile(String fileName, String write_str) throws IOException{
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte [] bytes = write_str.getBytes();
        fos.write(bytes);
        fos.close();
    }


}
