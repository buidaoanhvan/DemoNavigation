package com.example.demonavigation.model;
import com.google.firebase.firestore.Exclude;
public class SinhVien {
    private String documentId;
    private String ten;
    private String ma;

    public SinhVien() {
    }

    public SinhVien(String ten, String ma) {
        this.ten = ten;
        this.ma = ma;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }
}
