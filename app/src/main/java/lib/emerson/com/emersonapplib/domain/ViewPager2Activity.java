package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.MyViewPager;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ViewPager2Activity extends baseActivity {
    private ViewPager viewPager;
    private MyViewPager viewPager2;
    private View view1,view2,view3;
    private View viewOne,viewTwo,viewThree;
    private List<View> viewList,viewList2;//view数组

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_test);
        viewPager = (ViewPager) findViewById(R.id.test_view_pager_1);

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);   //（外部）蓝，红，橙
        view2 = inflater.inflate(R.layout.layout2,null);
        view3 = inflater.inflate(R.layout.layout3,null);
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setAdapter(new PagerAdapter(viewList));
        initMiddleView();
    }

    private void initMiddleView() {
        viewPager2 = (MyViewPager) view1.findViewById(R.id.test_view_pager_2);
        LayoutInflater inflate2 = getLayoutInflater();
        viewOne = inflate2.inflate(R.layout.layout_1,null);     //（内部）灰，绿，紫
        viewTwo = inflate2.inflate(R.layout.layout_2,null);
        viewThree = inflate2.inflate(R.layout.layout_3,null);
        viewList2 = new ArrayList<View>();
        viewList2.add(viewOne);
        viewList2.add(viewTwo);
        viewList2.add(viewThree);
        viewPager2.setAdapter(new PagerAdapter(viewList2));
        viewPager2.setOnSingleTouchListener(new MyViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch(View v) {
                toast("touch" + v.getId());
            }
        });
    }


    public class PagerAdapter extends android.support.v4.view.PagerAdapter {
        private List<View> dataList;

        public  PagerAdapter(List<View> list){
            this.dataList = list;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,Object object) {
            container.removeView(dataList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(dataList.get(position));
            return dataList.get(position);
        }
    };


}
