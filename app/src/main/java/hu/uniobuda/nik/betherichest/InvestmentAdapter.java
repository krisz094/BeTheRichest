package hu.uniobuda.nik.betherichest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;

/**
 * Created by Szabi on 2017.04.15..
 */

public class InvestmentAdapter extends BaseAdapter {

    private List<Investment> items;
    Game game;

    public InvestmentAdapter(List<Investment> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View listItemView = view;
        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_investment, null);
        }
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.price);
        TextView dpsTextView = (TextView) listItemView.findViewById(R.id.dps);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        Investment investment = items.get(position);

        nameTextView.setText(investment.getName());
        priceTextView.setText(String.valueOf(investment.getPrice()) + " $");
        imageView.setBackgroundResource(investment.getImageResource());
        dpsTextView.setText(String.valueOf(investment.getMoneyPerSec())+ " $/sec");


        return listItemView;
    }
}
