package lib.emerson.com.emersonapplib.domain;

import android.app.Fragment;
import android.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.Locale;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/9/8.
 * 简单：http://blog.csdn.net/elinavampire/article/details/41477525
 * 进阶：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0925/1713.html
 */
public class DrawerlayoutActivity extends baseActivity{

    
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;    //抽屉图标

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //设置，当DrawerLayout打开时,DrawerLayout的背景
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        /*设置左边菜单的Listview*/
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        //开启：app图标会随着ActionBar的动作而切换
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                       /* host Activity */
                mDrawerLayout,              /* DrawerLayout object */
                drawerArrow,                /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,       /* "开放抽屉" 可访问性的描述 */
                R.string.drawer_close       /* "关闭抽屉" 可访问性的说明 */
        ) {
            public void onDrawerClosed(View view) {     //关闭DrawerLayout
                getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu();    // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {   //打开DrawerLayout
                getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu();    // creates call to onPrepareOptionsMenu()
            }
        };

        /*
        * 监听drawerLayout菜单（左侧或右侧）的展开与隐藏事件，通常是使用DrawerListener类的
        * 但是如果activity中有actionbar的话，建议你用ActionBarDrawerToggle来监听。
        *   原因：ActionBarDrawerToggle实现了DrawerListener，所以他也能做DrawerListener可以做的任何事情，
        *   同时他还能将drawerLayout的展开和隐藏与actionbar的app图标关联起来，当展开与隐藏的时候图标有一定的平移效果，点击图标的时候还能展开或者隐藏菜单。
        * */
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }



    /*
    * 让app图标点击的时候能够展开或者隐藏侧边菜单
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    private void selectItem(int position) {
        /*通过更换碎片，来更新页面的内容*/
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        //更新选定的项目和标题，然后关闭抽屉
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    
    /**
     * 使用ActionBarDrawerToggle的时候，你必须调用它的 onpostcreate()和onconfigurationchanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //同步开关状态.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //通过任何的配置来更改的抽屉toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * 设置Fragment，来显示主图
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

}