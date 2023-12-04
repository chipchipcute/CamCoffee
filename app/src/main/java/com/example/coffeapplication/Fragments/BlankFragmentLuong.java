package com.example.coffeapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.coffeapplication.Activities.HomeActivity;
import com.example.coffeapplication.CustomAdapter.AdapterLuong;
import com.example.coffeapplication.DAO.LuongDAO;
import com.example.coffeapplication.DTO.LuongDTO;
import com.example.coffeapplication.R;

import java.util.List;


public class BlankFragmentLuong extends Fragment {
    private AdapterLuong adapterLuong;
    private GridView gridView;
    private LuongDAO luongDAO;
    private List<LuongDTO> luongDTOList;

    // khởi tạo fragment và layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank_luong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Lương Thống kê");
        setHasOptionsMenu(true);
        gridView = view.findViewById(R.id.gvLuong);
        luongDAO = new LuongDAO(getActivity());
        HienThiDSNV(HomeActivity.maquyen);
    }
    // hiển thị danh sách mục lương
    private void HienThiDSNV(int quyen){
       if(quyen == 1){
           luongDTOList = luongDAO.getAll();
       }else{
           luongDTOList = luongDAO.getListLuong(HomeActivity.manv);
       }
       // hiển thị lương lên gridview
        adapterLuong = new AdapterLuong(getActivity(),R.layout.customer_luong,luongDTOList);
        gridView.setAdapter(adapterLuong);
        adapterLuong.notifyDataSetChanged();
    }
}