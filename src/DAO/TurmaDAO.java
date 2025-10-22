/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Turma;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PICHAU
 */
public class TurmaDAO {

    private Connection con;

    public TurmaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public void adiciona(Turma turma) {
        String sql = "INSERT INTO turma(nome, sala) VALUES(?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getSala());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) turma.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Turma buscarPorId(Long id) {
        if (id == null) return null;
        String sql = "SELECT id, nome, sala FROM turma WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Turma t = new Turma();
                    t.setId(rs.getLong("id"));
                    t.setNome(rs.getString("nome"));
                    t.setSala(rs.getString("sala"));
                    return t;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Turma> buscarPorNome(String nomeBusca) {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT id, nome, sala FROM turma WHERE nome LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + (nomeBusca == null ? "" : nomeBusca) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Turma t = new Turma();
                    t.setId(rs.getLong("id"));
                    t.setNome(rs.getString("nome"));
                    t.setSala(rs.getString("sala"));
                    lista.add(t);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public List<Turma> listarTodos() {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT id, nome, sala FROM turma ORDER BY nome";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getLong("id"));
                t.setNome(rs.getString("nome"));
                t.setSala(rs.getString("sala"));
                lista.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public boolean atualizar(Turma turma) {
        if (turma == null || turma.getId() == null) {
            throw new IllegalArgumentException("Turma ou id nulo");
        }
        String sql = "UPDATE turma SET nome = ?, sala = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getSala());
            stmt.setLong(3, turma.getId());
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletar(Long id) {
        if (id == null) return false;
        String sql = "DELETE FROM turma WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
