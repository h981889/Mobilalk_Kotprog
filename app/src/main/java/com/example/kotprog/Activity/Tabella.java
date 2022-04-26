package com.example.kotprog.Activity;

public class Tabella
{
    private int helyezes;
    private String csapat;
    private int pontok;

    public Tabella() {
    }

    public Tabella(int helyezes, String csapat, int pontok) {
        this.helyezes = helyezes;
        this.csapat = csapat;
        this.pontok = pontok;
    }

    public int getHelyezes() {
        return helyezes;
    }

    public void setHelyezes(int helyezes) {
        this.helyezes = helyezes;
    }

    public String getCsapat() {
        return csapat;
    }

    public void setCsapat(String csapat) {
        this.csapat = csapat;
    }

    public int getPontok() {
        return pontok;
    }

    public void setPontok(int pontok) {
        this.pontok = pontok;
    }
}
