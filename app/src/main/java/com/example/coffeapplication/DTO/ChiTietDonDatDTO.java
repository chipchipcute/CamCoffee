package com.example.coffeapplication.DTO;

public class ChiTietDonDatDTO {

    int MaDonDat, MaMon, SoLuong;
    static String GhiChu; // Thêm trường GhiChu

    public int getMaDonDat() {
        return MaDonDat;
    }

    public void setMaDonDat(int maDonDat) {
        MaDonDat = maDonDat;
    }

    public int getMaMon() {
        return MaMon;
    }

    public void setMaMon(int maMon) {
        MaMon = maMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public static String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}
