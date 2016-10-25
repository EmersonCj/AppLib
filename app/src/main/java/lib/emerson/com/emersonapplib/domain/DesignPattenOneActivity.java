package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;

import lib.emerson.com.emersonapplib.Design.Observer1;
import lib.emerson.com.emersonapplib.Design.SubjectFor3d;
import lib.emerson.com.emersonapplib.Design.SubjectForSSQ;
import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/9/27.
 */
public class DesignPattenOneActivity extends baseActivity {
    SubjectFor3d subjectFor3d = new SubjectFor3d();
    SubjectForSSQ subjectForSSQ = new SubjectForSSQ();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_patten_one);

        Observer1 observer1 = new Observer1();
        observer1.registerSubject(subjectFor3d);
        observer1.registerSubject(subjectForSSQ);

        subjectFor3d.setMsg("hello 3d'nums : 110 ");
        subjectForSSQ.setMsg("ssq'nums : 12,13,31,5,4,3 15");


    }

}
