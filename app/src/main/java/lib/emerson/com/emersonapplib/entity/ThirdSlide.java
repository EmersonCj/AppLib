package lib.emerson.com.emersonapplib.entity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lib.emerson.com.emersonapplib.R;


public class ThirdSlide extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.thirdslide, container, false);
        return v;
    }
}
