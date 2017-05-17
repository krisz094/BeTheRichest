package hu.uniobuda.nik.betherichest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.uniobuda.nik.betherichest.R;

/**
 * Created by Szabi on 2017.05.14..
 */

public class HelpFragment extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.help_fragment, container, false);
        return rootView;
    }
}
