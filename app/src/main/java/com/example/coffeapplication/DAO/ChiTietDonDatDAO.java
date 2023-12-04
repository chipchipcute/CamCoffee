package com.example.coffeapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.coffeapplication.DTO.ChiTietDonDatDTO;
import com.example.coffeapplication.Database.CreateDatabase;

import static com.example.coffeapplication.Database.CreateDatabase.TBL_CHITIETDONDAT;
import static com.example.coffeapplication.Database.CreateDatabase.TBL_CHITIETDONDAT_GHICHU;
import static com.example.coffeapplication.Database.CreateDatabase.TBL_CHITIETDONDAT_MADONDAT;
import static com.example.coffeapplication.Database.CreateDatabase.TBL_CHITIETDONDAT_MAMON;

public class ChiTietDonDatDAO {

    SQLiteDatabase database;
    public ChiTietDonDatDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean KiemTraMonTonTai(int madondat, int mamon){
        String query = "SELECT * FROM " + TBL_CHITIETDONDAT+ " WHERE " + TBL_CHITIETDONDAT_MAMON+
                " = " +mamon+ " AND " + TBL_CHITIETDONDAT_MADONDAT+ " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        return cursor.getCount() != 0;
    }

    public int LaySLMonTheoMaDon(int madondat, int mamon){
        int soluong = 0;
        String query = "SELECT * FROM " + TBL_CHITIETDONDAT+ " WHERE " + TBL_CHITIETDONDAT_MAMON+
                " = " +mamon+ " AND " + TBL_CHITIETDONDAT_MADONDAT+ " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG));
            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSL(ChiTietDonDatDTO chiTietDonDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG, chiTietDonDatDTO.getSoLuong());

        long ktra = database.update(TBL_CHITIETDONDAT,contentValues, TBL_CHITIETDONDAT_MADONDAT+ " = "
                +chiTietDonDatDTO.getMaDonDat()+ " AND " + TBL_CHITIETDONDAT_MAMON+ " = "
                +chiTietDonDatDTO.getMaMon(),null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }


    public boolean CapNhatChiTietDonHang(ChiTietDonDatDTO chiTietDonDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG, chiTietDonDatDTO.getSoLuong());

        return database.update(TBL_CHITIETDONDAT,contentValues, TBL_CHITIETDONDAT_MADONDAT+ " = "
                +chiTietDonDatDTO.getMaDonDat()+ " AND " + TBL_CHITIETDONDAT_MAMON+ " = "
                +chiTietDonDatDTO.getMaMon(),null) > 0;

    }

    public boolean ThemChiTietDonDat(ChiTietDonDatDTO chiTietDonDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG,chiTietDonDatDTO.getSoLuong());
        contentValues.put(TBL_CHITIETDONDAT_MADONDAT,chiTietDonDatDTO.getMaDonDat());
        contentValues.put(TBL_CHITIETDONDAT_MAMON,chiTietDonDatDTO.getMaMon());

        long ktra = database.insert(TBL_CHITIETDONDAT,null,contentValues);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public  boolean deleteMonAn(String madonhang,String mamonan){
        return database.delete(TBL_CHITIETDONDAT,TBL_CHITIETDONDAT_MADONDAT +"=" +madonhang
                + " AND " +TBL_CHITIETDONDAT_MAMON +"=" +mamonan ,null) > 0;
    }

    public boolean ThemGhiChu(ChiTietDonDatDTO chiTietDonDatDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_MADONDAT, chiTietDonDatDTO.getMaDonDat());
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_MAMON, chiTietDonDatDTO.getMaMon());
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG, chiTietDonDatDTO.getSoLuong());
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_GHICHU, chiTietDonDatDTO.getGhiChu());

        long ktra = database.insertWithOnConflict(
                CreateDatabase.TBL_CHITIETDONDAT,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_REPLACE
        );

        if (ktra != -1) {
            Log.d("ChiTietDonDatDAO", "Thêm ghi chú thành công!");
        } else {
            Log.d("ChiTietDonDatDAO", "Thêm ghi chú thất bại!");
        }

        return ktra != -1;
    }








}
