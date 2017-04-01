package hu.uniobuda.nik.betherichest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Szabi on 2017.03.31..
 */
public class InvestmentAdapter extends BaseAdapter {

    private List<Investment> investments;

    public InvestmentAdapter(List<Investment> investments) {
        this.investments = investments;
    }

    @Override
    public int getCount() {
        return investments != null ? investments.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return investments != null ? investments.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) convertView;
        if (textView == null)
            textView = (TextView) View.inflate(
                    parent.getContext(),
                    R.layout.listitem_investment,
                    null
            );
        Investment investment = (Investment) getItem(position);
        textView.setText(investment.getName() + " " + investment.basePrice);
        return textView;

    }
}
