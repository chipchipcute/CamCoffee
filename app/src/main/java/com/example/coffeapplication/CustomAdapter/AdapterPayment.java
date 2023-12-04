package com.example.coffeapplication.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapplication.DTO.ChiTietDonDatDTO;
import com.example.coffeapplication.DTO.ThanhToanDTO;
import com.example.coffeapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.ViewHolder>{
    private  List<ThanhToanDTO> thanhToanDTOList;
    private final OnclickItem clickListener;
    private final Context mContext;

    public AdapterPayment(OnclickItem clickListener, Context mContext) {
        this.clickListener = clickListener;
        this.mContext = mContext;
    }

    public void setDta(List<ThanhToanDTO> thanhToanDTOList){
        this.thanhToanDTOList = thanhToanDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View studentView =
                inflater.inflate(R.layout.custom_layout_paymentmenu, parent, false);
        return new ViewHolder(studentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ThanhToanDTO thanhToanDTO = thanhToanDTOList.get(position);
        viewHolder.txt_custompayment_TenMon.setText(thanhToanDTO.getTenMon());
        viewHolder.txt_custompayment_SoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolder.txt_custompayment_GiaTien.setText(thanhToanDTO.getGiaTien()*thanhToanDTO.getSoLuong() +" đ");
        viewHolder.txt_custompayment_GhiChu.setText(ChiTietDonDatDTO.getGhiChu());
        byte[] paymentImage = thanhToanDTO.getHinhAnh();
        if (paymentImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(paymentImage, 0, paymentImage.length);
            viewHolder.img_custompayment_HinhMon.setImageBitmap(bitmap);
        } else {
            // Nếu không có hình ảnh, bạn có thể hiển thị một hình ảnh mặc định hoặc ẩn điều này.
            viewHolder.img_custompayment_HinhMon.setImageResource(R.drawable.cafe_americano);
        }

        viewHolder.back.setOnClickListener(view ->{
            clickListener.OnClickBack(thanhToanDTO);
        });
        viewHolder.next.setOnClickListener(view ->{
            clickListener.OnClickNext(thanhToanDTO);
        });
        viewHolder.itemView.setOnLongClickListener(views ->{
            clickListener.OnLongClick(thanhToanDTO);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return thanhToanDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_custompayment_HinhMon;
        ImageButton back,next;
        TextView txt_custompayment_TenMon, txt_custompayment_SoLuong, txt_custompayment_GiaTien,txt_custompayment_GhiChu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_custompayment_HinhMon = (CircleImageView)itemView.findViewById(R.id.img_custompayment_HinhMon);
            txt_custompayment_TenMon = (TextView)itemView.findViewById(R.id.txt_custompayment_TenMon);
            txt_custompayment_SoLuong = (TextView)itemView.findViewById(R.id.txt_custompayment_SoLuong);
            txt_custompayment_GiaTien = (TextView)itemView.findViewById(R.id.txt_custompayment_GiaTien);
            txt_custompayment_GhiChu = (TextView)itemView.findViewById(R.id.TextViewGhiChu);
            back = (ImageButton)itemView.findViewById(R.id.btnback);
            next = (ImageButton)itemView.findViewById(R.id.btnnext);

        }
    }
}
