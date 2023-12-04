package com.example.coffeapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffeapplication.DTO.BanAnDTO;
import com.example.coffeapplication.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    SQLiteDatabase database;
    public BanAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    //Hàm thêm bàn ăn mới
    public boolean ThemBanAn(String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN,tenban);
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG,"false");

        long ktra = database.insert(CreateDatabase.TBL_BAN,null,contentValues);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm xóa bàn ăn theo mã
//    public boolean XoaBanTheoMa(int maban){
//        long ktra =database.delete(CreateDatabase.TBL_BAN,CreateDatabase.TBL_BAN_MABAN+" = "+maban,null);
//        if(ktra != 0){
//            return true;
//        }else {
//            return false;
//        }
//    }

    public boolean XoaBanTheoMa(int maban) {
        // Kiểm tra xem bàn có đơn hàng không
        if (KiemTraBanCoDonHang(maban)) {
            // Bàn có đơn hàng, không được xóa
            return false;
        }

        // Nếu không có đơn hàng, thực hiện xóa bàn
        long ktra = database.delete(CreateDatabase.TBL_BAN, CreateDatabase.TBL_BAN_MABAN + " = " + maban, null);

        // Kiểm tra kết quả xóa
        return ktra != 0; // Trả về true nếu xóa thành công, ngược lại trả về false
    }

    // Hàm kiểm tra xem bàn có đơn hàng không
    private boolean KiemTraBanCoDonHang(int maban) {
        String query = "SELECT COUNT(*) FROM " + CreateDatabase.TBL_DONDAT +
                " WHERE " + CreateDatabase.TBL_DONDAT_MABAN + " = " + maban;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            try {
                cursor.moveToFirst();
                int count = cursor.getInt(0);

                // Nếu có ít nhất một đơn hàng liên kết với bàn, trả về true
                return count > 0;
            } finally {
                cursor.close();
            }
        }

        return false; // Trả về false nếu có lỗi khi thực hiện truy vấn
    }


    //Sửa tên bàn
    public boolean CapNhatTenBan(int maban, String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN,tenban);

        long ktra = database.update(CreateDatabase.TBL_BAN,contentValues,CreateDatabase.TBL_BAN_MABAN+ " = '"+maban+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm lấy ds các bàn ăn đổ vào gridview
    public List<BanAnDTO> LayTatCaBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_BAN;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN)));

            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maban){
        String tinhtrang="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BAN + " WHERE " +CreateDatabase.TBL_BAN_MABAN+ " = '" +maban+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG,tinhtrang);

        long ktra = database.update(CreateDatabase.TBL_BAN,contentValues,CreateDatabase.TBL_BAN_MABAN+ " = '"+maban+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public String LayTenBanTheoMa(int maban){
        String tenban="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BAN + " WHERE " +CreateDatabase.TBL_BAN_MABAN+ " = '" +maban+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenban = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN));
            cursor.moveToNext();
        }

        return tenban;
    }
    public List<String> getAllTables() {
        List<String> tableList = new ArrayList<>();
        String query = "SELECT " + CreateDatabase.TBL_BAN_TENBAN + " FROM " + CreateDatabase.TBL_BAN;
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tableList.add(cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();
        return tableList;
    }
    public boolean changeTable(String fromTable, String toTable) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TBL_BAN_TENBAN, toTable);

        // Cập nhật tên bàn mới cho bàn hiện tại
        int updateResult = database.update(CreateDatabase.TBL_BAN, values, CreateDatabase.TBL_BAN_TENBAN + " = ?", new String[]{fromTable});

        if (updateResult > 0) {
            // Cập nhật thành công
            return true;
        } else {
            // Cập nhật không thành công
            return false;
        }
    }
    public int getTableIDByName(String tableName) {
        int tableID = -1; // Giả sử trả về -1 nếu không tìm thấy tên bàn

        String query = "SELECT " + CreateDatabase.TBL_BAN_MABAN + " FROM " + CreateDatabase.TBL_BAN +
                " WHERE " + CreateDatabase.TBL_BAN_TENBAN + " = '" + tableName + "'";

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            tableID = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN));
        }

        cursor.close();
        return tableID;
    }
    public boolean updateTableStatus(int tableID, boolean isPaid) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TBL_BAN_TINHTRANG, isPaid ? "true" : "false");

        // Cập nhật trạng thái của bàn dựa trên mã bàn
        int updateResult = database.update(CreateDatabase.TBL_BAN, values, CreateDatabase.TBL_BAN_MABAN + " = ?", new String[]{String.valueOf(tableID)});

        return updateResult > 0;
    }


}
