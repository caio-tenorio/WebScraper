package com.example.webscraper.models;

import java.util.ArrayList;
import java.util.List;

public class SessaoPlenaria {
    private String nomeDaSessao;
    private String tipoDaSessao;
    private String abertura;
    private String encerramento;
    private String data;
    private List<String> listaDePresenca;

    public SessaoPlenaria(String nomeDaSessao, String tipoDaSessao, String abertura, String encerramento, String data, List<String> listaDePresenca) {
        this.nomeDaSessao = nomeDaSessao;
        this.tipoDaSessao = tipoDaSessao;
        this.abertura = abertura;
        this.encerramento = encerramento;
        this.data = data;
        this.listaDePresenca = listaDePresenca;
    }

    public String getNomeDaSessao() {
        return nomeDaSessao;
    }

    public void setNomeDaSessao(String nomeDaSessao) {
        this.nomeDaSessao = nomeDaSessao;
    }

    public String getTipoDaSessao() {
        return tipoDaSessao;
    }

    public void setTipoDaSessao(String tipoDaSessao) {
        this.tipoDaSessao = tipoDaSessao;
    }

    public String getAbertura() {
        return abertura;
    }

    public void setAbertura(String abertura) {
        this.abertura = abertura;
    }

    public String getEncerramento() {
        return encerramento;
    }

    public void setEncerramento(String encerramento) {
        this.encerramento = encerramento;
    }

    public List<String> getListaDePresenca() {
        return listaDePresenca;
    }

    public void setListaDePresenca(List<String> listaDePresenca) {
        this.listaDePresenca = listaDePresenca;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
