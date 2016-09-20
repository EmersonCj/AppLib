package lib.emerson.com.emersonapplib.domain;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ContentProviderActivity extends baseActivity implements View.OnClickListener {
    private TextView tv = null;
    private Button bt = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        tv = (TextView)findViewById(R.id.content_tv);
        bt = (Button)findViewById(R.id.content_bt);
        bt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //getImage();
        //getVideo();
        String sb = getContacts();
        tv.setText(sb);
    }




    /**
     * 获取图像列表
     */
    void getImage() {
        String[] projection = { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA };
        String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri,projection, orderBy);
    }

    /**
     * 获取视频列表
     */
    void getVideo() {
        String []projection = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA};
        String orderBy = MediaStore.Video.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri,projection, orderBy);
    }

    /**
     * 获取音频列表
     */
    void getAudio() {
        String []projection = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE};
        String orderBy = MediaStore.Audio.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri,projection, orderBy);
    }


    public void getContentProvider(Uri uri,String[] projection, String orderBy) {
        List<HashMap<String, String>> listImage = new ArrayList<HashMap<String, String>>();
        /*查询数据
           URL
           projection是我们要查询的列；
        　　table是根据Uri确定的数据库表；
        　　selection使我们自己的查询条件；
        　　order是我们想要的排序方式。*/
        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return;
        }

        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            for(int i=0;i<projection.length;i++){
                map.put(projection[i], cursor.getString(i));
            }
            listImage.add(map);
        }
        tv.setText(listImage.toString());
    }



    /**
     * 获取通讯录中联系人
     */
    public String getContacts(){
        StringBuilder sb = new StringBuilder();
        ContentResolver contentResolver = this.getContentResolver();
        Uri uri = Uri.parse(String.valueOf(ContactsContract.Contacts.CONTENT_URI));
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while(cursor.moveToNext()){
            // 获取联系人姓名
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            sb.append("contactId=").append(contactId).append(",name=").append(name);

            //获取联系人手机号码
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                    null, null);
            while(phones.moveToNext()){
                String phone = phones.getString(phones.getColumnIndex("data1"));
                sb.append(",phone=").append(phone);
            }

            //获取联系人email
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null);
            while(emails.moveToNext()){
                String email = emails.getString(emails.getColumnIndex("data1"));
                sb.append(",email=").append(email);
            }
            sb.append("\n");
        }
        return sb.toString();
    }



}
