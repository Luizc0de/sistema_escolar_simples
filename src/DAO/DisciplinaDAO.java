/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Disciplina;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    private Connection con;

    public DisciplinaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public void adiciona(Disciplina d) {
        String sql = "INSERT INTO disciplina(nome, hora) VALUES(?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getHora());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Disciplina buscarPorId(Long id) {
        if (id == null) return null;
        String sql = "SELECT id, nome, hora FROM disciplina WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Disciplina d = new Disciplina();
                    d.setId(rs.getLong("id"));
                    d.setNome(rs.getString("nome"));
                    d.setHora(rs.getString("hora"));
                    return d;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Disciplina> buscarPorNome(String nomeBusca) {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT id, nome, hora FROM disciplina WHERE nome LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + (nomeBusca == null ? "" : nomeBusca) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Disciplina d = new Disciplina();
                    d.setId(rs.getLong("id"));
                    d.setNome(rs.getString("nome"));
                    d.setHora(rs.getString("hora"));
                    lista.add(d);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public List<Disciplina> listarTodos() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT id, nome, hora FROM disciplina ORDER BY nome";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getLong("id"));
                d.setNome(rs.getString("nome"));
                d.setHora(rs.getString("hora"));
                lista.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public boolean atualizar(Disciplina d) {
        if (d == null || d.getId() == null) {
            throw new IllegalArgumentException("Disciplina ou id nulo");
        }
        String sql = "UPDATE disciplina SET nome = ?, hora = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getHora());
            stmt.setLong(3, d.getId());
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletar(Long id) {
        if (id == null) return false;
        String sql = "DELETE FROM disciplina WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
