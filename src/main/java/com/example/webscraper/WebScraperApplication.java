package com.example.webscraper;

import com.example.webscraper.database.Database;
import com.example.webscraper.models.SessaoPlenaria;
import com.example.webscraper.models.Vereador;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class WebScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebScraperApplication.class, args);
		WebScraper webScraper = new WebScraper();
//		HashSet<Vereador> vereadores = webScraper.getParlamentares();
//        Database database = new Database();
//		database.insertListIntoVereadoresTable(vereadores);
//        List<SessaoPlenaria> sessoes = webScraper.getSessoesPlenarias();
        webScraper.getMateriasLegislativas();
//		webScraper.getPropostas();


    }
}
