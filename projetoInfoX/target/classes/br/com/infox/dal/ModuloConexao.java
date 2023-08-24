/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infox.dal;

import java.sql.*;

/**
 *
 * @author pallo
 */
public class ModuloConexao {

//método responsável por estabelecer a conexão com o banco
    public static Connection conector() {
        Connection conexao = null;

// a linha abaixo "chama" o driver
        String driver = "com.mysql.cj.jdbc.Driver";

        //Armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
        String user = "dba";
        String password = "474f4b55";

        // Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            // a linha abaixo serve de apoio para esclarecer o erro
            //System.out.println(e);
            return null;
        }
    }
}
