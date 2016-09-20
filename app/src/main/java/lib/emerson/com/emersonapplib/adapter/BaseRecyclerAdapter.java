package lib.emerson.com.emersonapplib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/8.
 */
public abstract class BaseRecyclerAdapter<G> extends RecyclerView.Adapter {
    private Toast toast;
    public Context context;
    public LayoutInflater inflater;
    private OnRecyclerClickListener mListener;


    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置Adapter的Item的监听
     */
    public interface OnRecyclerClickListener {
        void mOnClick(View view, int position);
        void mOnLongClick(View view, int position);
    }

    public void setOnRecyclerClicListenter(OnRecyclerClickListener mListener) {
        this.mListener = mListener;
    }

    public void setUpItemkEnent(final View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.mOnClick(view, position);
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null) {
                    mListener.mOnLongClick(view, position);
                }
                return false;
            }
        });
    }
    public void toast(String text,Context context) {
        if (toast != null) {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
