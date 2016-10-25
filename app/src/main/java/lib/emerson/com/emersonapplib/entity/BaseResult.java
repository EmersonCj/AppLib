package lib.emerson.com.emersonapplib.entity;

import java.io.Serializable;

/**
 * Created by YangJianCong on 2016/10/18.
 */

public class BaseResult<T> implements Serializable {

    public String msg;// 提示信息
    public int code;// 错误代码     1成功，其它则为失败
    public T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
