package com.example.coffeapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.example.coffeapplication.Activities.AddCategoryActivity;
import com.example.coffeapplication.Activities.HomeActivity;
import com.example.coffeapplication.CustomAdapter.AdapterRecycleViewCategory;
import com.example.coffeapplication.CustomAdapter.AdapterRecycleViewStatistic;
import com.example.coffeapplication.DAO.DonDatDAO;
import com.example.coffeapplication.DAO.LoaiMonDAO;
import com.example.coffeapplication.DTO.DonDatDTO;
import com.example.coffeapplication.DTO.LoaiMonDTO;
import com.example.coffeapplication.R;

import java.util.List;

public class DisplayHomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcv_displayhome_LoaiMon, rcv_displayhome_DonTrongNgay,  rcv_displayhome_LoaiMon1;
    RelativeLayout layout_displayhome_ThongKe,layout_displayhome_XemBan, layout_displayhome_XemMenu, layout_displayhome_XemNV;
    TextView txt_displayhome_ViewAllCategory, txt_displayhome_ViewAllStatistic;
    LoaiMonDAO loaiMonDAO;
    DonDatDAO donDatDAO;
    List<LoaiMonDTO> loaiMonDTOList;
    List<DonDatDTO> donDatDTOS;
    AdapterRecycleViewCategory adapterRecycleViewCategory;
    AdapterRecycleViewStatistic adapterRecycleViewStatistic;
    LinearLayout thongke,quanly,thucdon,themban;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayhome_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Trang chủ");
        setHasOptionsMenu(true);
        thongke = view.findViewById(R.id.linearThongke);
        quanly = view.findViewById(R.id.linearQuanlY);
        thucdon = view.findViewById(R.id.linearThucdon);
        themban = view.findViewById(R.id.linearXemban);
        //region Lấy dối tượng view
  //      rcv_displayhome_LoaiMon = (RecyclerView)view.findViewById(R.id.rcv_displayhome_LoaiMon);
        rcv_displayhome_LoaiMon1 = (RecyclerView)view.findViewById(R.id.rcv_displayhome_LoaiMon1);
//        rcv_displayhome_DonTrongNgay = (RecyclerView)view.findViewById(R.id.rcv_displayhome_DonTrongNgay);
//        layout_displayhome_ThongKe = (RelativeLayout)view.findViewById(R.id.layout_displayhome_ThongKe);
        layout_displayhome_XemBan = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemBan);
        layout_displayhome_XemMenu = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemMenu);
        layout_displayhome_XemNV = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemNV);
        txt_displayhome_ViewAllCategory = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllCategory);
//        txt_displayhome_ViewAllStatistic = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllStatistic);
        //endregion
        if(HomeActivity.maquyen !=1){
            thongke.setVisibility(View.GONE);
            quanly.setVisibility(View.GONE);
            thucdon.setVisibility(View.GONE);
            themban.setVisibility(View.GONE);
        }
        //khởi tạo kết nối
        loaiMonDAO = new LoaiMonDAO(getActivity());
        donDatDAO = new DonDatDAO(getActivity());

        //HienThiDSLoai();
        HienThiDSLoai1();
//        HienThiDonTrongNgay();

//        layout_displayhome_ThongKe.setOnClickListener(this);
        layout_displayhome_XemBan.setOnClickListener(this);
        layout_displayhome_XemMenu.setOnClickListener(this);
        layout_displayhome_XemNV.setOnClickListener(this);
        txt_displayhome_ViewAllCategory.setOnClickListener(this);
//        txt_displayhome_ViewAllStatistic.setOnClickListener(this);

        ImageView imageViewOpenMap = view.findViewById(R.id.imageViewOpenMap);
        imageViewOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo FragmentTransaction để thêm hoặc thay thế MapFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                // Thay thế fragment hiện tại bằng MapFragment
                transaction.replace(R.id.contentView, new MapFragment());

                // Thêm fragment vào back stack để cho phép người dùng quay lại trạng thái trước đó
                transaction.addToBackStack(null);

                // Thực hiện giao dịch
                transaction.commit();
            }
        });


        return view;
    }

    private void HienThiDSLoai1(){
        rcv_displayhome_LoaiMon1.setHasFixedSize(true);
        rcv_displayhome_LoaiMon1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        loaiMonDTOList = loaiMonDAO.LayDSLoaiMon();
        adapterRecycleViewCategory = new AdapterRecycleViewCategory(getActivity(),R.layout.custom_layout_displaycategory,loaiMonDTOList);
        rcv_displayhome_LoaiMon1.setAdapter(adapterRecycleViewCategory);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navigation_view_trangchu);
        switch (id){

            case R.id.layout_displayhome_XemBan:
                FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                tranDisplayTable.addToBackStack(null);
                tranDisplayTable.commit();
                navigationView.setCheckedItem(R.id.nav_table);

                break;

            case R.id.layout_displayhome_XemMenu:
                Intent iAddCategory = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(iAddCategory);
                navigationView.setCheckedItem(R.id.nav_category);

                break;
            case R.id.layout_displayhome_XemNV:
                FragmentTransaction tranDisplayStaff= getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayStaff.replace(R.id.contentView,new DisplayStaffFragment());
                tranDisplayStaff.addToBackStack(null);
                tranDisplayStaff.commit();
                navigationView.setCheckedItem(R.id.nav_staff);

                break;

            case R.id.txt_displayhome_ViewAllCategory:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory.replace(R.id.contentView,new DisplayCategoryFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();
                navigationView.setCheckedItem(R.id.nav_category);

                break;

        }
    }
}

