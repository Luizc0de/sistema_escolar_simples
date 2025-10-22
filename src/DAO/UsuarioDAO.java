
package DAO;

import Modelo.Usuario;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO {

    private Connection con;
    Long id;
    String nome;
    String cpf;
    String email;
    String telefone;

    public UsuarioDAO() {
        this.con = new ConnectionFactory().getConnection();

    }

    public void adiciona(Usuario usuario) {
        String sql
                = "INSERT INTO usuario(nome,cpf,email,telefone) VALUES(?,?,?,?)";

        try {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    // busca lista de usuários pelo nome (LIKE)
    public List<Usuario> buscarPorNome(String nomeBusca) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, email, telefone FROM usuario WHERE nome LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + (nomeBusca == null ? "" : nomeBusca) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNome(rs.getString("nome"));
                    u.setCpf(rs.getString("cpf"));
                    u.setEmail(rs.getString("email"));
                    u.setTelefone(rs.getString("telefone"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    // deletar usuário por id
    public boolean deletar(Long id) {
        if (id == null) return false;
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // novo: atualizar usuário
    public boolean atualizar(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Usuario ou id nulo");
        }
        String sql = "UPDATE usuario SET nome = ?, cpf = ?, email = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setLong(5, usuario.getId());
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
