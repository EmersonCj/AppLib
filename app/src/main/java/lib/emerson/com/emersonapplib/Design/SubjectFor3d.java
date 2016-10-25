package lib.emerson.com.emersonapplib.Design;

import java.util.Observable;

/**
 * Created by YangJianCong on 2016/9/27.
 */
public class SubjectFor3d extends Observable {

    private String msg;


    public String getMsg() {
        return msg;
    }


    /**
     * 主题更新消息
     *
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
        setChanged();
        notifyObservers();
    }


}
