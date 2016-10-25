package lib.emerson.com.emersonapplib.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.http.annotation.HttpResponse;

/**
 * Created by YangJianCong on 2016/9/29.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class MyCallBack <ResultType> implements Callback.CommonCallback<ResultType>{

    @Override
    public void onSuccess(ResultType result) {
        //可以根据公司的需求进行统一的请求成功的逻辑处理
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //可以根据公司的需求进行统一的请求网络失败的逻辑处理
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }


}
