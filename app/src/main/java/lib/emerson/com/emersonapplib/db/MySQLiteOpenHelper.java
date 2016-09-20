package lib.emerson.com.emersonapplib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/24.
 */
/**
 * SQLiteOpenHelper是一个辅助类，用来管理数据库的创建和版本他，它提供两个方面的功能
 * 第一，getReadableDatabase()、getWritableDatabase()可以获得SQLiteDatabase对象，通过该对象可以对数据库进行操作
 * 第二，提供了onCreate()、onUpgrade()两个回调函数，允许我们在创建和升级数据库时，进行自己的操作
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private Context mContext = null;
    private String str = "";
    private String table = "";


    /**
     * 在SQLiteOpenHelper的子类当中，必须有该构造函数
     * @param context   上下文对象
     * @param name      数据库名称
     * @param factory
     * @param version   当前数据库的版本，值必须是整数并且是递增的状态
     */
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version,String str,String table) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
        this.mContext = context;
        this.str = str;
        this.table = table;
    }


    //该函数是在第一次创建的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL用于执行SQL语句
        Log.e("TAG","TAG");
        db.execSQL(str);
        Log.e("TAG","create a database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+table);
        this.onCreate(db);
        Log.e("TAG","onUpgrade a database");
    }


}
