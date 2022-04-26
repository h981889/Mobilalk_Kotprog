package com.example.kotprog.Activity;

import android.media.Image;

public class Csapat
{
    private String Nev;
    private String Stadion;
    private String Edzo;
    private String Alapitva;
    private int Cimer;

    public Csapat(String nev, String stadion, String edzo, String alapitva, int cimer) {
        this.Nev = nev;
        this.Stadion = stadion;
        this.Edzo = edzo;
        this.Alapitva = alapitva;
        this.Cimer = cimer;
    }


    public Csapat() {
    }

    public String getNev() {
        return Nev;
    }

    public void setNev(String nev) {
        Nev = nev;
    }

    public String getStadion() {
        return Stadion;
    }

    public void setStadion(String stadion) {
        Stadion = stadion;
    }

    public String getEdzo() {
        return Edzo;
    }

    public void setEdzo(String edzo) {
        Edzo = edzo;
    }

    public String getAlapitva() {
        return Alapitva;
    }

    public void setAlapitva(String alapitva) {
        Alapitva = alapitva;
    }

    public int getCimer() {
        return Cimer;
    }

    public void setCimer(int cimer) {
        Cimer = cimer;
    }
}
