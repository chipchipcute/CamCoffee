package com.example.coffeapplication.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeapplication.DAO.BanAnDAO;
import com.example.coffeapplication.DAO.DonDatDAO;
import com.example.coffeapplication.DTO.DonDatDTO;
import com.example.coffeapplication.R;

import java.util.List;

public class ChangeTableActivity extends AppCompatActivity {
    private Spinner spinnerTables, toTableSpinner;
    private Button btnChangeTable;
    private BanAnDAO banAnDAO;
    private DonDatDAO donDatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose_table);

        toTableSpinner = findViewById(R.id.toTableSpinner);
        spinnerTables = findViewById(R.id.spinner_tables);
        btnChangeTable = findViewById(R.id.btn_doiban);

        banAnDAO = new BanAnDAO(this);
        donDatDAO = new DonDatDAO(this);


        // Load danh sách bàn từ cơ sở dữ liệu và hiển thị trong Spinner
        loadTables();
        btnChangeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromTable = spinnerTables.getSelectedItem().toString();
                String toTable = toTableSpinner.getSelectedItem().toString();

                if (!fromTable.equals(toTable)) { // Chỉ chuyển bàn khi bàn nguồn và bàn đích khác nhau
                    // Thực hiện việc chuyển bàn trong cơ sở dữ liệu
                    boolean success = changeTableInDatabase(fromTable, toTable);

                    if (success) {
                        // Cập nhật thành công
                        Toast.makeText(ChangeTableActivity.this, "Chuyển bàn từ " + fromTable + " đến " + toTable + " thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        // Cập nhật không thành công
                        Toast.makeText(ChangeTableActivity.this, "Chuyển bàn không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Hiển thị thông báo khi bàn nguồn và bàn đích giống nhau
                    Toast.makeText(ChangeTableActivity.this, "Bàn nguồn và bàn đích giống nhau", Toast.LENGTH_SHORT).show();
                }

                // Kết thúc hoạt động
                finish();
            }
        });
    }
    private void loadTables() {
        // Thực hiện lấy danh sách bàn từ cơ sở dữ liệu và hiển thị trong Spinner
        List<String> tables = banAnDAO.getAllTables();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTables.setAdapter(adapter);
        toTableSpinner.setAdapter(adapter);
    }

    private boolean changeTableInDatabase(String fromTable, String toTable) {
        try {
            if (isValidTableName(fromTable) && isValidTableName(toTable)) {
                int maBanNguon = banAnDAO.getTableIDByName(fromTable);
                int maBanDich = banAnDAO.getTableIDByName(toTable);

                // Lấy danh sách đơn đặt hàng dựa trên mã bàn nguồn
                List<DonDatDTO> donDatDTOList = donDatDAO.LayDSDonDatTheoMaBan(maBanNguon);

                if (!donDatDTOList.isEmpty()) {
                    // Cập nhật trạng thái của bàn đích (bàn mới)
                    banAnDAO.updateTableStatus(maBanDich, true);   // Bàn đích trở thành bàn đỏ

                    // Cập nhật mã bàn mới cho các đơn đặt hàng
                    for (DonDatDTO donDatDTO : donDatDTOList) {
                        donDatDTO.setMaBan(maBanDich);
                        boolean updateResult = donDatDAO.capNhatMaBanDonDat(donDatDTO);
                        Log.d("ChangeTableActivity", "Cập nhật đơn đặt hàng: " + updateResult);
                    }

                    // Xóa đơn hàng từ bàn nguồn
                    boolean deleteResult = donDatDAO.xoaDonDatTheoMaBan(maBanNguon);
                    // Thực hiện cập nhật trạng thái của bàn
                    banAnDAO.updateTableStatus(maBanNguon, false);
                    Log.d("ChangeTableActivity", "Xóa đơn đặt hàng từ bàn nguồn: " + deleteResult);

                    return true;
                } else {
                    // Không có đơn đặt hàng trên bàn nguồn
                    Log.d("ChangeTableActivity", "Không có đơn đặt hàng trên bàn nguồn");
                    return false;
                }
            } else {
                // Dữ liệu đầu vào không hợp lệ
                Log.d("ChangeTableActivity", "Dữ liệu đầu vào không hợp lệ");
                return false;
            }
        } catch (Exception e) {
            // Xử lý lỗi (nếu có) và trả về false
            Log.e("ChangeTableActivity", "Lỗi: " + e.getMessage());
            return false;
        }
    }



    private boolean isValidTableName(String tableName) {
        // Kiểm tra xem tên bàn có hợp lệ không (ví dụ: không null, không trống, không trùng lặp, vv.)
        // Điều này tùy thuộc vào yêu cầu cụ thể của ứng dụng của bạn.
        return !tableName.isEmpty(); // Ví dụ đơn giản, hãy cải thiện kiểm tra này dựa trên nhu cầu của bạn.
    }
}
