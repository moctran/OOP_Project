package com.test;
import java.util.*;

public class SuKienLichSu {
    private String ten;
    private String thoiGian;
    private String diaDiem;
    private String ketQua;
    private Set<String> nhanVatLienQuan;
    private Set<String> diaDiemLienQuan;
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }

    public Set<String> getNhanVatLienQuan() {
        return nhanVatLienQuan;
    }

    public void setNhanVatLienQuan(Set<String> nhanVatLienQuan) {
        this.nhanVatLienQuan = nhanVatLienQuan;
    }

    public Set<String> getDiaDiemLienQuan() {
        return diaDiemLienQuan;
    }

    public void setDiaDiemLienQuan(Set<String> diaDiemLienQuan) {
        this.diaDiemLienQuan = diaDiemLienQuan;
    }

    public void display(){
        System.out.println("Tên: " + this.ten);
        System.out.println("Thời gian: " + this.thoiGian);
        System.out.println("Địa điểm: " + this.diaDiem);
        System.out.println("Kết quả: " + this.ketQua);
        System.out.println("Các nhân vật lịch sử liên quan: " + this.nhanVatLienQuan);
        System.out.println("Các địa điểm liên quan: " + this.diaDiemLienQuan);
    }

}
