package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Test.Student;

/**
 * Created by Administrator on 2016/7/5.
 */
public class JSONActivity extends baseActivity {
    private static final String JSON =
            "{" +
                    "\"phone\" : [\"12345678\", \"87654321\"]," +
                    "\"name\" : \"yuanzhifei89\"," +
                    "\"age\" : 100," +
                    "\"address\" : {\"country\" :\"china\", \"province\" : \"jiangsu\" }," +
                    "\"married\" : false" +
                    "}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        //createJson();
        //analyzeJson();
        GsonTest();
    }

    private void GsonTest() {
        Gson gson = new Gson();
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("李坤");
        student1.setBirthDay(new Date());
        // //////////////////////////////////////////////////////////
        Log.e("GSON","----------简单对象之间的转化-------------");
        // 简单的bean转为json
        String s1 = gson.toJson(student1);
        // json转为简单Bean
        Student student = gson.fromJson(s1, Student.class);
        // //////////////////////////////////////////////////////////

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("曹贵生");
        student2.setBirthDay(new Date());

        Student student3 = new Student();
        student3.setId(3);
        student3.setName("柳波");
        student3.setBirthDay(new Date());

        List<Student> list = new ArrayList<Student>();
        list.add(student1);
        list.add(student2);
        list.add(student3);

        // //////////////////////////////////////////////////////////
        Log.e("GSON","----------带泛型的List之间的转化-------------");
        // 带泛型的list转化为json
        String s2 = gson.toJson(list);
        Log.e("GSON","带泛型的list转化为json==" + s2);

        // json转为带泛型的list
        List<Student> retList = gson.fromJson(s2,new TypeToken<List<Student>>() {}.getType());
        for (Student stu : retList) {
            Log.e("GSON",stu+"");
        }
        // //////////////////////////////////////////////////////////


    }

    private void analyzeJson() {
        try {

            JSONObject person = new JSONObject(JSON);
            // 接下来的就是JSON对象的操作了
            person.getJSONArray("phone");
            Log.e("1",person.getString("name"));
            Log.e("2",person.getInt("age")+"");
            //.......
        } catch (JSONException ex) {
            // 异常处理代码
            Log.e("error",ex+"");
        }
    }


    /*假设现在要创建这样一个json文本
      {
          "phone" : ["12345678", "87654321"],   // 数组
          "name" : "yuanzhifei89",              // 字符串
          "age" : 100,                          // 数值
          "address" : { "country" : "china", "province" : "jiangsu" }, // 对象
          "married" : false                     // 布尔值
      } */
    private void createJson() {
        // 首先最外层是{}，是创建一个对象
        try {
            // 首先最外层是{}，是创建一个对象
            JSONObject person = new JSONObject();
            // 第一个键phone的值是数组，所以需要创建数组对象
            JSONArray phone = new JSONArray();
            phone.put("12345678").put("87654321");
            person.put("phone", phone);

            person.put("name", "yuanzhifei89");
            person.put("age", 100);

            // 键address的值是对象，所以又要创建一个对象
            JSONObject address = new JSONObject();
            address.put("country", "china");
            address.put("province", "jiangsu");
            person.put("address", address);
            person.put("married", false);
            Log.e("JSON = ",person + "");

        } catch (JSONException ex) {
            // 键为null或使用json不支持的数字格式(NaN, infinities)
            throw new RuntimeException(ex);
        }

    }


    //解析这样的数据（实际上是一个对象） "address" : { "country" : "china", "province" : "jiangsu" }, // 对象
    private void getJsonObj(String response) throws JSONException {
        JSONObject json = new JSONObject(response);
        JSONObject obj = json.getJSONObject("data");
        Iterator iterator = obj.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = obj.getString(key);
        }
    }

}
