package hu.uniobuda.nik.betherichest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 2017.03.31..
 */
public class InvestmentListFragment extends Fragment {
    View rootView;

    public static InvestmentListFragment newInstance() {

        Bundle args = new Bundle();

        InvestmentListFragment fragment = new InvestmentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_investment_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Investment> investments = InvestmentFactory.CreateInvestments();

        final InvestmentAdapter adapter = new InvestmentAdapter(investments);
    }
}
