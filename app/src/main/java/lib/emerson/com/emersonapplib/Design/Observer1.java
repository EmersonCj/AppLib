package lib.emerson.com.emersonapplib.Design;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by YangJianCong on 2016/9/27.
 */
public class Observer1 implements Observer {
    private String TAG = "Observer1";

    public void registerSubject(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SubjectFor3d) {
            SubjectFor3d subjectFor3d = (SubjectFor3d) o;
            Log.e(TAG,"subjectFor3d's msg -- >" + subjectFor3d.getMsg());
        }

        if (o instanceof SubjectForSSQ) {
            SubjectForSSQ subjectForSSQ = (SubjectForSSQ) o;
            Log.e(TAG,"subjectForSSQ's msg -- >" + subjectForSSQ.getMsg());
        }
    }
}
