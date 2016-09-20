package lib.emerson.com.emersonapplib.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/26.
 */
public class TestClass implements Serializable {
    private String name;
    private int num;
    private Boolean flag;
    private data mdata;

    private static class data{
        private String a;
        private String b;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public Boolean getFlag() {
        return flag;
    }

    public data getMdata() {
        return mdata;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void setMdata(data mdata) {
        this.mdata = mdata;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", flag=" + flag +
                ", mdata=" + mdata +
                '}';
    }

}
