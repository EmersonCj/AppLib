package lib.emerson.com.emersonapplib.mvp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.domain.baseActivity;
import lib.emerson.com.emersonapplib.mvp.model.User;
import lib.emerson.com.emersonapplib.mvp.presenter.ILoginPresenter;
import lib.emerson.com.emersonapplib.mvp.presenter.LoginPresenterCompl;

/**
 * Created by YangJianCong on 2016/9/9.
 *
 *  首先将UI逻辑抽象成View接口ILoginView（主要是一些界面的处理逻辑，例如刷新界面，更新界面）
 *
 *  然后在Activity的功能就是响应生命周期和显示界面，还有就是实现view接口
 *
 */
public class LoginActivity extends baseActivity implements View.OnClickListener,ILoginView {

    private Button mLogin ;
    private Button mClear ;
    private EditText mName ;
    private EditText mPassWord ;

    private ILoginPresenter loginPresenter ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);

        mLogin = (Button) findViewById(R.id.btn_login);
        mClear = (Button) findViewById(R.id.btn_clear);
        mName = (EditText) findViewById(R.id.et_name);
        mPassWord = (EditText) findViewById(R.id.et_password);

        mLogin.setOnClickListener(this);
        mClear.setOnClickListener(this);

        loginPresenter = new LoginPresenterCompl(this);     //传入View接口的实现类

    }


    @Override
    public void onClick(View v) {
        int id = v.getId() ;
        String name = mName.getText().toString() ;
        String password = mPassWord.getText().toString() ;

        switch (id){
            case R.id.btn_login :
                loginPresenter.doLogin(name,password);
                break ;
            case R.id.btn_clear :
                loginPresenter.clear();
                break;
        }
    }

    @Override
    public void onClearText() {
        mName.setText("");
        mPassWord.setText("");
        Toast.makeText(this,"clear",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        mLogin.setEnabled(true);
        mClear.setEnabled(true);

        if(result){
            Toast.makeText(this,"登陆成功: "+code,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"登陆失败："+code,Toast.LENGTH_SHORT).show();
        }
    }
}
