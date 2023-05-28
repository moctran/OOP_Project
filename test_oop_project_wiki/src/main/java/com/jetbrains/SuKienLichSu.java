package com.jetbrains;
import java.util.*;
public class SuKienLichSu {
    private String thoiKy;
    private String ten;
    private ArrayList<String> lucLuong;
    private String doiPhuong;
    private ArrayList<String> ketQua;

    public String getThoiKy() {
        return thoiKy;
    }

    public void setThoiKy(String thoiKy) {
        this.thoiKy = thoiKy;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public ArrayList<String> getLucLuong() {
        return lucLuong;
    }

    public void setLucLuong(ArrayList<String> lucLuong) {
        this.lucLuong = lucLuong;
    }

    public String getDoiPhuong() {
        return doiPhuong;
    }

    public void setDoiPhuong(String doiPhuong) {
        this.doiPhuong = doiPhuong;
    }

    public ArrayList<String> getKetQua() {
        return ketQua;
    }

    public void setKetQua(ArrayList<String> ketQua) {
        this.ketQua = ketQua;
    }

    public void display()
    {
        System.out.println("Tên: " + ten);
        for (String ll : lucLuong)
        {
            System.out.println(ll);
        }
        System.out.println("Đối phương: " + doiPhuong);
        for (String kq : ketQua)
        {
            System.out.println(kq);
        }
    }
}
