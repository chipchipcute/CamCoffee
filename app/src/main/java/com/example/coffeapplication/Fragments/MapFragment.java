package com.example.coffeapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeapplication.R;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment {
    private MapViewModel mapViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_map, container, false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        LayoutInflater inflator = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customTitleView = inflator.inflate(R.layout.custom_action_bar_title, null);
        TextView actionBarTitle = customTitleView.findViewById(R.id.action_bar_title);
        actionBarTitle.setText("Vị trí"); // Đặt nội dung mới ở đây

        actionBar.setCustomView(customTitleView);
        actionBar.setDisplayShowCustomEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        mapViewModel.getMap(mapFragment);
        return view;
    }

}
