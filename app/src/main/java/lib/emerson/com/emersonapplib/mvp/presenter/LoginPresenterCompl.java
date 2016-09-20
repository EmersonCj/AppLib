package lib.emerson.com.emersonapplib.mvp.presenter;

import lib.emerson.com.emersonapplib.mvp.model.User;
import lib.emerson.com.emersonapplib.mvp.view.ILoginView;
import lib.emerson.com.emersonapplib.mvp.view.LoginActivity;

/**
 * Created by YangJianCong on 2016/9/9.
 *
 * 创建 IPresenter 接口（Activity所有的业务逻辑的接口都放在这里）
 *
 * 然后创建该接口的实现类（PresenterCompl）：
 *      在这里可以方便地查看业务功能，由于接口可以有多种实现所以也方便写单元测试）。
 *
 *
 */
public class LoginPresenterCompl implements ILoginPresenter {
    private ILoginView loginView ;
    private User user ;

    public LoginPresenterCompl(ILoginView loginView) {      //接收View的实例
        this.loginView = loginView;
        this.user = new User("张三","123456");
    }


    @Override
    public void clear() {
        loginView.onClearText();
    }

    @Override
    public void doLogin(String name, String password) {
        boolean result = false ;
        int code = 0 ;
        if(name.equals(user.getName()) && password.equals(user.getPassword())){
            result = true ;
            code = 1 ;
        }else{
            result = false ;
            code = 0 ;
        }
        loginView.onLoginResult(result,code);
    }

}
