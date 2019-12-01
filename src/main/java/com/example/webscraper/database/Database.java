package com.example.webscraper.database;
import com.example.webscraper.models.MateriaLegislativa;
import com.example.webscraper.models.SessaoPlenaria;
import com.example.webscraper.models.Vereador;

import java.sql.*;
import java.util.HashSet;
import java.util.List;


public class Database {

    Connection conn = this.connect();

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/camaradb";
            String user = "postgres";
            String password = "testingPassword";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * Inserts a list of {@link Vereador} to DB
     * @param vereadores vereadores list
     */
    public void insertListIntoVereadoresTable(HashSet<Vereador> vereadores) {
        for (Vereador vereador : vereadores) {
           this.insertVereadorToDB(vereador);
        }
    }

    /**
     * Inserts a vereador to DB
     * @param vereador {@link Vereador}
     */
    public void insertVereadorToDB(Vereador vereador) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO vereadores (nome_civil, nome_parlamentar, partido, ativo) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, vereador.getNomeCivil());
            preparedStatement.setString(2, vereador.getNomeParlamentar());
            preparedStatement.setString(3, vereador.getPartido());
            preparedStatement.setString(4, String.valueOf(vereador.isAtivo()));

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSessaoPlenariaToDB(SessaoPlenaria sessaoPlenaria) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO sessoes (nome, tipo, abertura, encerramento, data_sessao, lista_de_presenca) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, sessaoPlenaria.getNomeDaSessao());
            preparedStatement.setString(2, sessaoPlenaria.getTipoDaSessao());
            preparedStatement.setString(3, sessaoPlenaria.getAbertura());
            preparedStatement.setString(4, sessaoPlenaria.getEncerramento());
            preparedStatement.setString(5, sessaoPlenaria.getData());

            List<String> presenca = sessaoPlenaria.getListaDePresenca();
            String[] data = presenca.toArray(new String[presenca.size()]);
            java.sql.Array sqlArray = conn.createArrayOf("varchar", data);
            preparedStatement.setArray(6, sqlArray);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMateriaLegislativaToDB(MateriaLegislativa materiaLegislativa) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO materias (tipo, data_materia, ementa, autor, texto_integral_link, link) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, materiaLegislativa.getTipo());
            preparedStatement.setString(2, materiaLegislativa.getData());
            preparedStatement.setString(3, materiaLegislativa.getEmenta());
            preparedStatement.setString(4, materiaLegislativa.getAutor());
            preparedStatement.setString(5, materiaLegislativa.getTextoIntegralLink());
            preparedStatement.setString(6, materiaLegislativa.getLink());

            preparedStatement.execute();
            preparedStatement.close();
            System.out.println("Materia inserted successfully!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
