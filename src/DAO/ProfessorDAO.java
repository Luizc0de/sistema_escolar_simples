/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Professor;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private Connection con;

    public ProfessorDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public void adiciona(Professor prof) {
        String sql = "INSERT INTO professor(nome, cpf, email, telefone) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, prof.getNome());
            stmt.setString(2, prof.getCpf());
            stmt.setString(3, prof.getEmail());
            stmt.setString(4, prof.getTelefone());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) prof.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Professor buscarPorId(Long id) {
        if (id == null) return null;
        String sql = "SELECT id, nome, cpf, email, telefone FROM professor WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Professor p = new Professor();
                    p.setId(rs.getLong("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    p.setEmail(rs.getString("email"));
                    p.setTelefone(rs.getString("telefone"));
                    return p;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Professor> buscarPorNome(String nomeBusca) {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, email, telefone FROM professor WHERE nome LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + (nomeBusca == null ? "" : nomeBusca) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Professor p = new Professor();
                    p.setId(rs.getLong("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    p.setEmail(rs.getString("email"));
                    p.setTelefone(rs.getString("telefone"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public List<Professor> listarTodos() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, email, telefone FROM professor ORDER BY nome";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public boolean atualizar(Professor prof) {
        if (prof == null || prof.getId() == null) {
            throw new IllegalArgumentException("Professor ou id nulo");
        }
        String sql = "UPDATE professor SET nome = ?, cpf = ?, email = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, prof.getNome());
            stmt.setString(2, prof.getCpf());
            stmt.setString(3, prof.getEmail());
            stmt.setString(4, prof.getTelefone());
            stmt.setLong(5, prof.getId());
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletar(Long id) {
        if (id == null) return false;
        String sql = "DELETE FROM professor WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
