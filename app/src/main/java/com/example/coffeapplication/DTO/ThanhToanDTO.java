package com.example.coffeapplication.DTO;

import java.util.Arrays;

public class ThanhToanDTO {
    String madon;
    String maMon;
    String TenMon;
    int SoLuong, GiaTien;
    byte[] HinhAnh;
    String GhiChu; // Thêm trường GhiChu

    public String getMadon() {
        return madon;
    }

    public void setMadon(String madon) {
        this.madon = madon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
        GiaTien = giaTien;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "ThanhToanDTO{" +
                "madon='" + madon + '\'' +
                ", maMon='" + maMon + '\'' +
                ", TenMon='" + TenMon + '\'' +
                ", SoLuong=" + SoLuong +
                ", GiaTien=" + GiaTien +
                ", HinhAnh=" + Arrays.toString(HinhAnh) +
                ", GhiChu='" + GhiChu + '\'' +
                '}';
    }
}
