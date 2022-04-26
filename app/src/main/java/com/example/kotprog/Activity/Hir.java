package com.example.kotprog.Activity;

public class Hir
{
    private String cim;
    private String hir;
    private int kep;

    public Hir() {
    }

    public Hir(String cim, String hir, int kep) {
        this.cim = cim;
        this.hir = hir;
        this.kep = kep;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public String getHir() {
        return hir;
    }

    public void setHir(String hir) {
        this.hir = hir;
    }

    public int getKep() {
        return kep;
    }

    public void setKep(int kep) {
        this.kep = kep;
    }
}
