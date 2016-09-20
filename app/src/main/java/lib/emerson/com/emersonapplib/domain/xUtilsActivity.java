package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.StudentInfo;

/**
 * Created by Administrator on 2016/7/19.
 */

/*4个模块：DbUtils、HttpUtils、ViewUtils、BitmapUtils*/
//viewUtils加载Activity布局,使用了这句之后oncreate里面不需要调用setcontentView
@ContentView(R.layout.activity_xutils)
public class xUtilsActivity extends baseActivity {
        // 使用ViewUtils绑定控件
        @ViewInject(R.id.txtv)
        private TextView txtv;
        @ViewInject(R.id.imgv)
        private ImageView imgv;
        // 图片地址
        private String imagUrl = "http://pic8.nipic.com/20100709/4752803_210430061441_2.jpg";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 把注入viewinject
            x.view().inject(this);
        }

        /**
         * 使用BitmapUtils显示图片
         *
         * 使用ViewUtils设置按钮的点击事件,方法必须要私有化， 参数格式和type的参数一致,为了混淆方便，方法名要以Event或者Click结尾
         * type可以不写，默认是点击事件类型
         */
        @Event(value = R.id.btn, type = View.OnClickListener.class)
        private void btnClick(View view) {
            // 设置加载图片的参数
            ImageOptions options = new ImageOptions.Builder()
                    // 是否忽略GIF格式的图片
                    .setIgnoreGif(false)
                    // 图片缩放模式
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    // 下载中显示的图片
                    .setLoadingDrawableId(R.drawable.loadlose)
                    // 下载失败显示的图片
                    .setFailureDrawableId(R.drawable.loadlose)
                    // 得到ImageOptions对象
                    .build();
            // 加载图片

            x.image().bind(imgv, imagUrl, options, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable arg0) {
                    LogUtil.e("下载成功");
                }

                @Override
                public void onFinished() {
                    LogUtil.e("下载完成");
                }

                @Override
                public void onError(Throwable arg0, boolean arg1) {

                    LogUtil.e("下载出错，" + arg0.getMessage());
                }

                @Override
                public void onCancelled(CancelledException arg0) {
                    LogUtil.e("下载取消");
                }
            });
            // 加载本地图片
            // x.image().bind(imgv, "assets://test.gif", options);
            // x.image().bind(iv_big_img, new
            // File("/sdcard/test.gif").toURI().toString(), imageOptions);
            // x.image().bind(iv_big_img, "/sdcard/test.gif", imageOptions);
            // x.image().bind(iv_big_img, "file:///sdcard/test.gif", imageOptions);
            // x.image().bind(iv_big_img, "file:/sdcard/test.gif", imageOptions);
        }

        /**
         * httpUtils演示，加载百度首页到TextView上面
         */
        @Event(R.id.btnHttp)
        private void showEvent(View view) {
            // 请求参数
            RequestParams params = new RequestParams("http://www.baidu.com");
            x.http().get(params, new Callback.CommonCallback<String>() {

                @Override
                public void onCancelled(CancelledException arg0) {

                }

                @Override
                public void onError(Throwable arg0, boolean arg1) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public void onSuccess(String arg0) {
                    // 成功下载，显示到txtv上面
                    txtv.setText(arg0);
                }
            });
        }

        /**
         * 演示DbUtils，使用实体类Studentinfo生成表，数据库保存在data/data/包名/databases下面
         */
        @Event(R.id.btnDB)
        private void createDbEvent(View view) {
            DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                    // 数据库的名字
                    .setDbName("SudentInfo")
                    // 保存到指定路径
                    // .setDbDir(new
                    // File(Environment.getExternalStorageDirectory().getAbsolutePath()))
                    // 数据库的版本号
                    .setDbVersion(1)
                    // 数据库版本更新监听
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager arg0, int arg1, int arg2) {
                            LogUtil.e("数据库版本更新了！");
                        }
                    });
            DbManager manager = x.getDb(daoConfig);
            try {
                StudentInfo info = new StudentInfo();
                info.setAge(16);
                info.setName("小花");
                manager.saveOrUpdate(info);
            } catch (DbException e) {
                e.printStackTrace();
            }

        }

    }
