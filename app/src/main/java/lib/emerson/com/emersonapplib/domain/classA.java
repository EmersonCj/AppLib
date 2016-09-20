package lib.emerson.com.emersonapplib.domain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/20.
 */
/*  A类  */
public class classA {
    //接口实例(现在还没有实例，等其他类传过来)
    private MyOnClickListener listener;

    public classA(){

    }


    //接收B类（或其他类）的接口实例
    public void setOnClick(MyOnClickListener listener) {
        this.listener = listener;
    }

    //使用接口中的方法
    public void doClick() {
        //A类不需要实现，因为A类会回调B类中已经实现的方法
        listener.CallckClick();
    }


    public interface  MyOnClickListener {
        public void CallckClick();
    }

}
