package com.example.webscraper.models;

public class MateriaLegislativa {
    private String tipo;
    private String data;
    private String ementa;
    private String autor;
    private String textoIntegralLink;
    private String link;

    public MateriaLegislativa(String tipo, String data, String ementa, String autor, String link) {
        this.tipo = tipo;
        this.data = data;
        this.ementa = ementa;
        this.autor = autor;
        this.link = link;

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTextoIntegralLink() {
        return textoIntegralLink;
    }

    public void setTextoIntegralLink(String textoIntegralLink) {
        this.textoIntegralLink = textoIntegralLink;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
