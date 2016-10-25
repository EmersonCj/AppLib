package lib.emerson.com.emersonapplib.entity;

import java.io.Serializable;

/**
 * Created by YangJianCong on 2016/9/29.
 */
public class Store implements Serializable {

    private int store_id;
    private String store_name;
    private String area_info;
    private String store_phone;

    public String getStore_name() {
        return store_name;
    }

    public int getStore_id() {
        return store_id;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    @Override
    public String toString() {
        return "Store{" +
                "store_id=" + store_id +
                ", store_name='" + store_name + '\'' +
                ", area_info='" + area_info + '\'' +
                ", store_phone='" + store_phone + '\'' +
                '}';
    }


}
