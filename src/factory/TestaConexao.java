/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author PICHAU
 */
public class TestaConexao {
    public static void main(String[] args) throws SQLException {
        try (Connection con = new ConnectionFactory().getConnection()) {
            System.out.println("Conexo aberta!");
            System.out.println("Fechando... conexao!");
            con.close();
        }catch(SQLException excecao) {
            throw new RuntimeException(excecao);
        
        }  
    }
}
