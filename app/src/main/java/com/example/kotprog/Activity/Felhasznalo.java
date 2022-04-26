package com.example.kotprog.Activity;

public class Felhasznalo
{
    private String felhasznalonev;
    private String email;
    private String jelszo;
    private String kedvencCsapat;

    public Felhasznalo() {}

    public Felhasznalo(String felhasznalonev, String email, String jelszo, String kedvencCsapat) {
        this.felhasznalonev = felhasznalonev;
        this.email = email;
        this.jelszo = jelszo;
        this.kedvencCsapat = kedvencCsapat;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getKedvencCsapat() {
        return kedvencCsapat;
    }

    public void setKedvencCsapat(String kedvencCsapat) {
        this.kedvencCsapat = kedvencCsapat;
    }
}
