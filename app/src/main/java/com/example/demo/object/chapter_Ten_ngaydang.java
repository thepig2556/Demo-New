package com.example.demo.object;

public class chapter_Ten_ngaydang {
    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public chapter_Ten_ngaydang()
    {

    }
    public chapter_Ten_ngaydang(String tenChap, String ngayDang) {
        this.tenChap = tenChap;
        this.ngayDang = ngayDang;
    }

    private String tenChap,ngayDang;

}
