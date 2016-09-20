package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.db.SqlManager;

/**
 * Created by Administrator on 2016/6/23.
 */
public class DatasActivity extends Activity{
    @ViewInject(R.id.insert)
    private Button insertButton;
    @ViewInject(R.id.update)
    private Button updateButton;
    @ViewInject(R.id.select)
    private Button selectButton;
    @ViewInject(R.id.delete)
    private Button deleteButton;
    @ViewInject(R.id.show_data)
    private ListView lv;

    private SqlManager sql = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas);
        x.view().inject(this);
        sql = new SqlManager(DatasActivity.this);
        sql.open();

    }

    private void show() {
        Cursor cursor = sql.fetchAllData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String Name = cursor.getString(cursor.getColumnIndex("num"));
                String Data = cursor.getString(cursor.getColumnIndex("data"));
                Log.e("TAG","_id =" + _id + " name = " + Name+ " data=" + Data);
            }
        }
    }

    @Event(value = R.id.insert,type = View.OnClickListener.class)
    private void click_1(View view){
        sql.insertData(10,"A");
        this.show();
    }

    @Event(value = R.id.update,type = View.OnClickListener.class)
    private void click_2(View view){
        sql.updateData(1,10,"B");
        this.show();
    }

    @Event(value = R.id.select,type = View.OnClickListener.class)
    private void click_3(View view){
        show();
    }

    @Event(value = R.id.delete,type = View.OnClickListener.class)
    private void click_4(View view){
        sql.deleteData(1);
        this.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sql.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sql.close();
    }
}
