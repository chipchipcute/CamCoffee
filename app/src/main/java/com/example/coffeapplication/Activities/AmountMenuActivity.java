package com.example.coffeapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.coffeapplication.DAO.ChiTietDonDatDAO;
import com.example.coffeapplication.DAO.DonDatDAO;
import com.example.coffeapplication.DTO.ChiTietDonDatDTO;
import com.example.coffeapplication.R;

public class AmountMenuActivity extends AppCompatActivity {

    TextInputLayout TXTL_amountmenu_SoLuong;
    Button BTN_amountmenu_DongY;
    int maban, mamon;
    DonDatDAO donDatDAO;
    ChiTietDonDatDAO chiTietDonDatDAO;
    TextInputLayout TXTL_amountmenu_GhiChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_menu_layout);

        //Lấy đối tượng view
        TXTL_amountmenu_SoLuong = findViewById(R.id.txtl_amountmenu_SoLuong);
        TXTL_amountmenu_GhiChu = findViewById(R.id.txtl_amountmenu_GhiChu);
        BTN_amountmenu_DongY = findViewById(R.id.btn_amountmenu_DongY);

        //khởi tạo kết nối csdl
        donDatDAO = new DonDatDAO(this);
        chiTietDonDatDAO = new ChiTietDonDatDAO(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        mamon = intent.getIntExtra("mamon", 0);

        BTN_amountmenu_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra validate cho số lượng và ghi chú
                if (!validateAmount()) {
                    return;
                }

                int madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban, "false");
                boolean ktra = chiTietDonDatDAO.KiemTraMonTonTai(madondat, mamon);

                // Tạo đối tượng ChiTietDonDatDTO
                ChiTietDonDatDTO chiTietDonDatDTO = new ChiTietDonDatDTO();
                chiTietDonDatDTO.setMaDonDat(madondat);
                chiTietDonDatDTO.setMaMon(mamon);
                chiTietDonDatDTO.setSoLuong(Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString()));
                chiTietDonDatDTO.setGhiChu(TXTL_amountmenu_GhiChu.getEditText().getText().toString());

                if (ktra) {
                    // Cập nhật số lượng món đã chọn
                    int sluongcu = chiTietDonDatDAO.LaySLMonTheoMaDon(madondat, mamon);
                    int sluongmoi = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
                    int tongsl = sluongcu + sluongmoi;

                    chiTietDonDatDTO.setSoLuong(tongsl);

                    boolean ktracapnhat = chiTietDonDatDAO.CapNhatSL(chiTietDonDatDTO);
                    if (ktracapnhat) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Thêm số lượng món nếu chưa chọn món này
                    boolean ktracapnhat = chiTietDonDatDAO.ThemGhiChu(chiTietDonDatDTO);
                    if (ktracapnhat) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    // validate số lượng
    private boolean validateAmount() {
        String val = TXTL_amountmenu_SoLuong.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            TXTL_amountmenu_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (!val.matches(("\\d+(?:\\.\\d+)?"))) {
            TXTL_amountmenu_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        } else {
            TXTL_amountmenu_SoLuong.setError(null);
            TXTL_amountmenu_SoLuong.setErrorEnabled(false);
            return true;
        }
    }

}
