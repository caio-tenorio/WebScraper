package com.example.webscraper.models;

public class Vereador {
    private String nomeCivil;
    private String nomeParlamentar;
    private String partido;
    private boolean ativo;

    public Vereador(String nomeCivil, String nomeParlamentar, String partido, boolean ativo) {
        this.nomeCivil = nomeCivil;
        this.nomeParlamentar = nomeParlamentar;
        this.partido = partido;
        this.ativo = ativo;
    }

    public String getNomeCivil() {
        return nomeCivil;
    }

    public void setNomeCivil(String nomeCivil) {
        this.nomeCivil = nomeCivil;
    }

    public String getNomeParlamentar() {
        return nomeParlamentar;
    }

    public void setNomeParlamentar(String nomeParlamentar) {
        this.nomeParlamentar = nomeParlamentar;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
