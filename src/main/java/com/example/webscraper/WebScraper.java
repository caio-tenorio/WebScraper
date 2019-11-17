package com.example.webscraper;

import java.util.ArrayList;
import java.util.HashSet;

import com.example.webscraper.models.Vereador;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import static com.example.webscraper.constants.Links.*;

public class WebScraper {

    private HashSet<String> links;

    public WebScraper() {
        links = new HashSet<>();
    }

    public Document getLinkContent(String link) {
        Document document = null;
        //pass the url as the input argument.
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e){
            e.getMessage();
        }
        return document;
    }

    public HashSet<Vereador> getParlamentares(){
        Document document = this.getLinkContent(PARLAMENTARES_LINK);
        Element parlamentaresTable = document.getElementsByTag("table").get(1);
        Elements rows = parlamentaresTable.getElementsByTag("tr");
        HashSet<Vereador> vereadores = new HashSet<>();
        for (Element row : rows) {
            if (!row.getElementsByTag("td").isEmpty()) {
                Elements attributes = row.getElementsByTag("td");
                String nomeCivil = attributes.get(0).text();
                String nomeParlamentar = attributes.get(1).text();
                String partido = attributes.get(2).text();
                String ativo = attributes.get(3).text();
                boolean ativoBoolean = true;

                if (ativo.equals("SIM")) {
                    ativoBoolean = true;
                } else {
                    ativoBoolean = false;
                }

                Vereador vereador = new Vereador(nomeCivil, nomeParlamentar, partido, ativoBoolean);
                vereadores.add(vereador);
            }
        }

        return vereadores;
    }

    public void getSessoesPlenarias() {
        Document document = this.getLinkContent(SESSOES_PLENARIAS_INDEX_LINK);
        Elements sessoesElement = document.getElementsByTag("option");
        List<String> datasSessoes = new ArrayList<>();
        for (Element sessao : sessoesElement) {
            String dataSessao = sessao.getElementsByAttribute("value").text();
            datasSessoes.add(dataSessao);
            Document dataSessaoPage = this.getLinkContent(SESSOES_PLENARIAS_INDEX_LINK + SESSAO_PLENARIA_SULFIX + dataSessao);
            Elements sessoesLinks = dataSessaoPage.getElementsByAttributeValueStarting("href", "agenda_sessao");
            for (Element sessaoLink : sessoesLinks) {
                Document sessaoPage = this.getLinkContent(SESSAO_PLENARIA_PAGE_LINK + sessaoLink.attr("href"));

            }
        }
    }
}
