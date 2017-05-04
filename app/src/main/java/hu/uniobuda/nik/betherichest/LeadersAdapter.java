package hu.uniobuda.nik.betherichest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Leaders;
import hu.uniobuda.nik.betherichest.R;

/**
 * Created by Kristof on 2017. 04. 17..
 */

class LeadersAdapter extends BaseAdapter {

    private List<Leaders> leadersList;
    LeadersAdapter(List<Leaders> leadersList){
        this.leadersList = leadersList;
    }

    @Override
    public int getCount() {
        return leadersList==null? 0:leadersList.size();
    }

    @Override
    public Object getItem(int position) {
        return leadersList==null? null: leadersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if ( listItemView ==null){
            listItemView = View.inflate(parent.getContext(),R.layout.listitem_leaderboard,null);
        }
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        TextView moneyTextView = (TextView) listItemView.findViewById(R.id.money);

        Leaders leaders = leadersList.get(position);

        nameTextView.setText(leaders.getName());
        moneyTextView.setText(String.valueOf(leaders.getMoney()));
        return listItemView;
    }
}