package com.example.webscraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.example.webscraper.database.Database;
import com.example.webscraper.models.MateriaLegislativa;
import com.example.webscraper.models.SessaoPlenaria;
import com.example.webscraper.models.Vereador;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.webscraper.constants.Links.*;

public class WebScraper {

    public Document getLinkContent(String link) {
        Document document = null;
        //pass the url as the input argument.
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.getMessage();
        }

        while (document == null) {
            try {Thread.sleep(300000);} catch (InterruptedException e) {};
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return document;
    }

    public HashSet<Vereador> getParlamentares() {
        Document document = this.getLinkContent(PARLAMENTARES_LINK);
        Element parlamentaresTable = document.getElementsByTag("table").get(1);
        Elements rows = parlamentaresTable.getElementsByTag("tr");
        HashSet<Vereador> vereadores = new HashSet<>();
        for (Element row : rows) {
            if (!row.getElementsByTag("td").isEmpty()) {
//                String mostrarVereadorLink = this.getMostrarVereadorLink(row);
//                Document vereadorPage = this.getLinkContent(mostrarVereadorLink);
//                Element textoParlamentar = vereadorPage.getElementById("texto-parlamentar");
//                Elements atributos = textoParlamentar.getElementsByTag("b");
//                atributos.get(0).text()
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
        List<SessaoPlenaria> sessoesTotal = new ArrayList<>();

        Database database = new Database();

        for (Element sessao : sessoesElement) {
            // Date page
            String dataSessao = sessao.getElementsByAttribute("value").text();
            datasSessoes.add(dataSessao);
            Document dataSessaoPage = this.getLinkContent(SESSOES_PLENARIAS_INDEX_LINK + SESSAO_PLENARIA_SULFIX + dataSessao);
            Elements sessoesLinks = dataSessaoPage.getElementsByAttributeValueStarting("href", "agenda_sessao");

            for (Element sessaoLink : sessoesLinks) {
                // Session main page
                Document sessaoPage = this.getLinkContent(SESSAO_PLENARIA_PAGE_LINK + sessaoLink.attr("href"));

                String tipoDaSessaoTemp = (sessaoPage.getElementsContainingOwnText("Tipo da Sessão").parents().get(0).text());
                String aberturaTemp = sessaoPage.getElementsContainingOwnText("Abertura:").parents().get(0).text();
                String encerramentoTemp = sessaoPage.getElementsContainingOwnText("Encerramento:").parents().get(0).text();

                String nomeDaSessao = sessaoPage.getElementsByTag("h3").get(1).text();
                String tipoDaSessao = tipoDaSessaoTemp.substring(tipoDaSessaoTemp.indexOf(":") + 1, tipoDaSessaoTemp.length()).trim();
                String abertura = aberturaTemp.substring(aberturaTemp.indexOf("-") + 1, aberturaTemp.length()).trim();
                String encerramento = encerramentoTemp.substring(encerramentoTemp.indexOf("-") + 1, encerramentoTemp.length()).trim();

                Elements listaDePresencaElements = sessaoPage.getElementsContainingOwnText("Lista de Presença na Sessão").parents().get(0).getElementsByTag("td");
                List<String> listaDePresenca = new ArrayList<>();
                for (Element vereadorPresente : listaDePresencaElements) {
                    listaDePresenca.add(vereadorPresente.text().trim());
                }

                SessaoPlenaria sessaoPlenaria = new SessaoPlenaria(nomeDaSessao, tipoDaSessao, abertura, encerramento, dataSessao, listaDePresenca);

                database.insertSessaoPlenariaToDB(sessaoPlenaria);
                sessoesTotal.add(sessaoPlenaria);
                System.out.println("sessaoPlenaria adicionada na lista");
            }
        }
    }

    public void getMateriasLegislativas() {
//        Document document = this.getLinkContent(MATERIAS_LEGISLATIVAS_FIRST_PAGE_LINK);
        Document document = this.getLinkContent(PROJETOS_DE_RESOLUCAO_FIRST_PAGE_LINK);
        //TODO: Add check to "get(0)"
        String pagesString = Arrays.asList(document.getElementsContainingOwnText("Páginas").get(0).text().split(":")).get(0);
        String numberOfPagesStr = pagesString.substring(pagesString.indexOf("(") + 1, pagesString.indexOf(")"));
        Integer numberOfPages = Integer.parseInt(numberOfPagesStr);
        Integer count = 1;
        Database database = new Database();

        while (numberOfPages > count) {
            String showPageLink = this.getShowPageLink(count);
            Document showPage = this.getLinkContent(showPageLink);
            Elements materiasShowTable = showPage.getElementsByTag("table").get(0).getElementsContainingOwnText("Autor:");
            Elements materiasRows = this.getMateriaRows(materiasShowTable);


            for (Element materiaRow : materiasRows) {
                if (!materiaRow.text().startsWith("Resultado da Pesquisa")) {
                    String materiaLinkQuoted = Arrays.asList(materiaRow.getElementsByAttribute("href").get(0).
                            attributes().toString().split("href=")).get(1);
                    String materiaLink = StringUtils.substringsBetween(materiaLinkQuoted, "\"", "\"")[0];
                    Document materiaPageContent = this.getLinkContent(materiaLink);

                    try {
                        String tipo = getMateriaAttr(materiaPageContent, "Tipo:");
                        String data = getMateriaAttr(materiaPageContent, "Data:");
                        String ementa = getMateriaAttr(materiaPageContent, "Ementa:");
                        String autor = getMateriaAttr(materiaPageContent, "Autor:");

                        MateriaLegislativa materiaLegislativa = new MateriaLegislativa(tipo, data, ementa, autor, materiaLink);
                        try{
                            String textoIntegralLink = materiaPageContent.getElementsContainingOwnText("Texto " +
                                    "Integral:").parents().get(0).getElementsByTag("a").get(0).attributes().get("href");
                            materiaLegislativa.setTextoIntegralLink(textoIntegralLink);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                        database.insertMateriaLegislativaToDB(materiaLegislativa);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    try {Thread.sleep(500);} catch (InterruptedException e) {};


                }
            }
            count = count + 1;
        }
    }

    public String getMateriaAttr(Document materiaPageContent, String attrName) {
        return materiaPageContent.getElementsContainingOwnText(attrName).parents().get(0).text().split(attrName)[1].trim();
    }

    /**
     * Gets base link from materias legislativas and changes to match the actual show page
     *
     * @param newPageNumber show page number
     * @return
     */
    private String getShowPageLink(Integer newPageNumber) {
//        return MATERIAS_LEGISLATIVAS_FIRST_PAGE_LINK.replaceFirst("page=1", "page=" + newPageNumber.toString());
        return PROJETOS_DE_RESOLUCAO_FIRST_PAGE_LINK.replaceFirst("page=1", "page=" + newPageNumber.toString());

    }

    private Elements getMateriaRows(Elements materiaAutors) {
        Elements materiaRows = new Elements();
        for (Element materiaAutor : materiaAutors) {
            materiaRows.add(materiaAutor.parent());
        }
        return materiaRows;
    }

    private String getMostrarVereadorLink(Element vereadorRow) {
        Elements attributesRows = vereadorRow.getElementsByTag("td");
        String mostrarVereadorLinkSulfix = attributesRows.get(0).getElementsByAttribute("href").get(0)
                .attributes().get("href");
        String mostrarVereadorLink = PARLAMENTARES_LINK.replaceAll("parlamentar_index_html", mostrarVereadorLinkSulfix);
        return mostrarVereadorLink;
    }
}
