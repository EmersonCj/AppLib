package lib.emerson.com.emersonapplib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/24.
 */
public class SqlManager {
    // 用于打印log
    private static final String TAG = "SqlManager";
    private Context mContext = null;

    // 表中一条数据的名称
    public static final String KEY_ID = "_id";
    // 表中一条数据的内容
    public static final String KEY_NUM = "num";
    // 表中一条数据的id
    public static final String KEY_DATA = "data";
    // 数据库名称为 DB_NAME
    private static final String DB_NAME = "Examples_06_06.db";
    // 数据库表名 DB_TABLE
    private static final String DB_TABLE = "table1";
    // 数据库版本
    private static final int DB_VERSION = 1;

    //创建一个表sql语句
    private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NUM + " INTERGER,"+ KEY_DATA + " TEXT)";

    // 执行open（）打开数据库时，保存返回的数据库对象
    private SQLiteDatabase mSQLiteDatabase = null;

    // 自定义SQLiteOpenHelper工具类
    private MySQLiteOpenHelper mSQLiteOpenHelper = null;


    /* 构造函数-取得Context */
    public SqlManager(Context context){

        mContext = context;
    }


    // 打开数据库，返回数据库对象
    public void open() throws SQLException{

        mSQLiteOpenHelper = new MySQLiteOpenHelper(mContext,DB_NAME,null,DB_VERSION,DB_CREATE,DB_TABLE);
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }


    // 关闭数据库
    public void close(){

        mSQLiteOpenHelper.close();
    }

    /* 插入一条数据 */
    public long insertData(int num, String data){

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NUM, num);
        initialValues.put(KEY_DATA, data);

        return mSQLiteDatabase.insert(DB_TABLE, KEY_ID, initialValues);
    }

    /* 删除一条数据 */
    public boolean deleteData(long rowId){

        return mSQLiteDatabase.delete(DB_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    /* 通过Cursor查询所有数据 */
    public Cursor fetchAllData(){

        return mSQLiteDatabase.query(DB_TABLE, new String[] { KEY_ID, KEY_NUM, KEY_DATA }, null, null, null, null, null);
    }

    /* 查询指定数据 */
    public Cursor fetchData(long rowId) throws SQLException{

        Cursor mCursor = mSQLiteDatabase.query(true, DB_TABLE, new String[] { KEY_ID, KEY_NUM, KEY_DATA }, KEY_ID + "=" + rowId, null, null, null, null, null);

        if (mCursor != null){

            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /* 更新一条数据 */
    public boolean updateData(long rowId, int num, String data){

        ContentValues args = new ContentValues();
        args.put(KEY_NUM, num);
        args.put(KEY_DATA, data);

        return mSQLiteDatabase.update(DB_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
    }
}
