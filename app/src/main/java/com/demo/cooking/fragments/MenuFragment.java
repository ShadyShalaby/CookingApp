package com.demo.cooking.fragments;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.cooking.R;
import com.demo.cooking.utilities.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    TextView tv_name;
    TextView tv_email;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.vNavigation);
        View header = navigationView.getHeaderView(0);

        tv_name = (TextView) header.findViewById(R.id.menu_header_user_name);
        tv_email= (TextView) header.findViewById(R.id.menu_header_email);

        tv_name.setText(PreferenceManager.getCustomerData(getContext()).getUserName());
        tv_email.setText(PreferenceManager.getCustomerData(getContext()).getEmail());

        return view;
    }

}
