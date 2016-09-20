package lib.emerson.com.emersonapplib.Test;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/5.
 */
public class Student implements Serializable{
    private int id;
    private String name;
    private Date birthDay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "Student [birthDay=" + birthDay + ", id=" + id + ", name="
                + name + "]";
    }
}
