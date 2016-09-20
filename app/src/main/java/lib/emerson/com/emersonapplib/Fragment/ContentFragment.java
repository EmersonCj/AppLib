package lib.emerson.com.emersonapplib.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/9/18.
 */
public class ContentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }
}
