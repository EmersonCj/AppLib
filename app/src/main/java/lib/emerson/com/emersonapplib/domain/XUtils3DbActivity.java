package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;

import lib.emerson.com.emersonapplib.App.MyApplication;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.PersonTable;

/**
 * Created by Administrator on 2016/7/12.
 */
public class XUtils3DbActivity extends baseActivity {
    //  http://blog.csdn.net/it1039871366/article/details/50607775
    private DbManager.DaoConfig daoConfig ;
    private DbManager db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils_db);
        /*xUtil数据库的初始化在MyApplication中已经做了，那么如何创建表呢？
        只需要一下几步:
            1.DaoConfig daoConfig=XUtil.getDaoConfig();
            2.DbManager db = x.getDb(daoConfig);*/
        daoConfig = MyApplication.getDaoConfig();
        db = x.getDb(daoConfig);
        Log.e("xx",db.getDatabase().toString());
        /*
        * 通过DbManager这个类我们知道主要它做了以下几件事情:
            1.getDaoConfig 获取数据库的配置信息
            2.getDatabase 获取数据库实例
            3.saveBindingId saveOrUpdate save 插入数据的3个方法(保存数据)
            4.replace 只有存在唯一索引时才有用 慎重
            5.delete操作的4种方法(删除数据)
            6.update操作的2种方法(修改数据)
            7.find操作6种方法(查询数据)
            8.dropTable 删除表
            9.addColumn 添加一列
            10.dropDb 删除数据库
         */
        insert();
        query();

    }

    private void query() {
        try {
            PersonTable person = db.findById(PersonTable.class, "1");
            Log.e("person",person.toString());

            List<PersonTable> persons = db.findAll(PersonTable.class);
            Log.e("persons", persons.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void insert() {
        try {
            //PersonTable 中已经声明了表名和字段
            for(int i  = 0;i < 5 ;i++) {
                PersonTable person = new PersonTable();
                person.setName("小丽");
                person.setAge(19 + i);
                person.setSex("woman");
                person.setSalary("4000");
                db.save(person);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
