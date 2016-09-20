package lib.emerson.com.emersonapplib.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/8.
 */
public class Product implements Serializable{
    private int img;
    private String title;

    public Product(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Product{" +
                "img=" + img +
                ", title='" + title + '\'' +
                '}';
    }
}
