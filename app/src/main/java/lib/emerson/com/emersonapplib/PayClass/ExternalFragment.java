package lib.emerson.com.emersonapplib.PayClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.OrderResultClass;

public class ExternalFragment extends Fragment {
	@ViewInject(R.id.product_subject)
	private TextView tvProductSubject;
	@ViewInject(R.id.product_details)
	private TextView tvProductDeatils;
	@ViewInject(R.id.product_price)
	private TextView tvProdectPrice;
	/**
	 * 订单数据
	 */
	private OrderResultClass orderresultc;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pay_external, container, false);
		x.view().inject(this,view);
		/* 接收传递过来的数据，并显示在页面上*/
		orderresultc = (OrderResultClass) getActivity().getIntent().getBundleExtra("COMMIT_RESULT_TAG").getSerializable("data");
		if(orderresultc!=null){
			tvProdectPrice.setText(orderresultc.getTotal_fee());
			tvProductDeatils.setText(orderresultc.getBody());
			tvProductSubject.setText(orderresultc.getSubject());
		}
		return view;
	}
}
