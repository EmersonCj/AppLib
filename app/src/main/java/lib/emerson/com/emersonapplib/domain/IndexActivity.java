package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.adapter.SortAdapter;
import lib.emerson.com.emersonapplib.utils.IndexUtils.CharacterParser;
import lib.emerson.com.emersonapplib.utils.IndexUtils.PinyinComparator;
import lib.emerson.com.emersonapplib.utils.IndexUtils.SortModel;
import lib.emerson.com.emersonapplib.view.ClearEditText;
import lib.emerson.com.emersonapplib.view.SideBar;

/**
 * Created by Administrator on 2016/8/9.
 */
public class IndexActivity extends baseActivity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView showTv;     //显示字母的TextView
    private SortAdapter adapter;

    private String[] datas = {
            "北京市","天津市","上海市","重庆市","河北省","河南省","云南省","辽宁省","黑龙江省","湖南省","安徽省",
            "山东省","新疆维吾尔","江苏省","浙江省","江西省","湖北省","广西壮族","甘肃省","山西省","内蒙古","陕西省",
            "吉林省","福建省","贵州省","广东省","青海省","西藏","四川省","宁夏回族","海南省","台湾省","香港特别行政区","澳门特别行政区"
    };

    private ClearEditText mClearEditText;           //自定义搜索框
    private CharacterParser characterParser;        //汉字转换成拼音的类
    private PinyinComparator pinyinComparator;      //自定义比较器（根据拼音来排列ListView里面的数据）
    private List<SortModel> SourceDateList;         //SortModel保存了数据的名字和首字母

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initViews();
    }


    private void initViews() {
        //1，实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        /*2，初始化索引条 */
        sideBar = (SideBar) findViewById(R.id.sidebar);
        showTv = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(showTv);

        //为索引条设置触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //获取该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    //将listview移动到position位置
                    sortListView.setSelection(position);
                }
            }
        });

        /*3，初始化listview */
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        SourceDateList = initData(datas);
        //根据a-z的顺序，对SourceDateList进行排序，其中pinyinComparator要实现compare方法
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);


        /*4，初始化搜索框*/
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 将数据转为SortModel类
     * @param date
     * @return
     */
    private List<SortModel> initData(String [] date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();       //获取拼音的首字母，并转为大写

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {     //如果输入框为空，将更新为原始的数据
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {    //将符合的数据加到filterDateList中，然后准备下一步的排序
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        //将filterDateList的数据，按照a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

}
