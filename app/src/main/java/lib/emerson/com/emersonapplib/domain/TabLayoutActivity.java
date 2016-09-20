package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.Fragment.ListFragment;
import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/9/19.
 */
public class TabLayoutActivity extends baseActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> titles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initTabLayout();
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        titles = new ArrayList<>();
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");
        titles.add("购物");
        titles.add("明星");
        titles.add("视频");
        titles.add("健康");
        titles.add("励志");
        titles.add("图文");
        titles.add("本地");
        titles.add("动漫");
        titles.add("搞笑");
        titles.add("精选");
        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        /*设置背景    setBackgroundColor
        设置下划线颜色 setIndicatorColor
        设置字体颜色   setTextColors
        添加图标      addIcons
        设置滑动动画   setTransformation*/
        initViewPager();

    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            fragments.add(new ListFragment());
        }
        FragmentAdapter mFragmentAdapteradapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapteradapter);                 //给ViewPager设置适配器

        mTabLayout.setupWithViewPager(mViewPager);                      //将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapteradapter);    //给TabLayout设置适配器

    }


    public class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;
        private List<String> mTitles;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }


}
