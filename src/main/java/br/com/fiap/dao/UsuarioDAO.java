package br.com.fiap.dao;

import br.com.fiap.dto.Usuario.Usuario;

import java.sql.*;
import java.util.ArrayList;


public class UsuarioDAO {
    private Connection con;

    public UsuarioDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(Usuario usuario) {
        String sql = "insert into usuario(NM_USUARIO, NR_CPF, NR_CNH, DT_NASCIMENTO, NR_IDADE) values(?,?,?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNM_USUARIO());
            ps.setString(2, usuario.getNR_CPF());
            ps.setInt(3, usuario.getNR_CNH());
            ps.setDate(4, Date.valueOf(usuario.getDT_NASCIMENTO()));
            ps.setInt(5, usuario.calcularIdade(usuario.getDT_NASCIMENTO()));
            if (ps.executeUpdate() > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setID_USUARIO(generatedKeys.getInt(1));
                    }
                }
                return "\nInserido com sucesso";
            } else {
                return "\nErro ao inserir";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }


    public void alterar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET NM_USUARIO = ?, NR_CPF = ?, NR_CNH = ?, DT_NASCIMENTO = ?, NR_IDADE = ? WHERE ID_USUARIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, usuario.getNM_USUARIO());
            ps.setString(2, usuario.getNR_CPF());
            ps.setInt(3, usuario.getNR_CNH());
            ps.setDate(4, Date.valueOf(usuario.getDT_NASCIMENTO()));
            ps.setInt(5, usuario.calcularIdade(usuario.getDT_NASCIMENTO()));
            ps.setInt(6, usuario.getID_USUARIO());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Nenhum usuário encontrado com o ID: " + usuario.getID_USUARIO());
            }
        }
    }

    public String deletar(Integer ID_USUARIO) {
        String sql = "delete from usuario where ID_USUARIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_USUARIO);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public ArrayList<Usuario> ListarTodos() {
        String sql = "select * from usuario order by ID_USUARIO";
        ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setID_USUARIO(rs.getInt("ID_USUARIO"));
                    usuario.setNM_USUARIO(rs.getString("NM_USUARIO"));
                    usuario.setNR_CPF(rs.getString("NR_CPF"));
                    usuario.setNR_CNH(rs.getInt("NR_CNH"));
                    usuario.setDT_NASCIMENTO(rs.getDate("DT_NASCIMENTO").toLocalDate());
                    usuario.setNR_IDADE(rs.getInt("NR_IDADE"));
                    listaUsuario.add(usuario);
                }
                return listaUsuario;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }

    }

    public Usuario buscarPorId(String NR_CPF) throws SQLException {
        String sql = "select * from usuario where NR_CPF = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, NR_CPF);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setID_USUARIO(resultSet.getInt("ID_USUARIO")); // Usar o nome da coluna
                    usuario.setNM_USUARIO(resultSet.getString("NM_USUARIO"));
                    usuario.setNR_CPF(resultSet.getString("NR_CPF"));
                    usuario.setNR_CNH(resultSet.getInt("NR_CNH")); // Certifique-se de que NR_CNH é INT no banco
                    usuario.setDT_NASCIMENTO(resultSet.getDate("DT_NASCIMENTO").toLocalDate());
                    usuario.setNR_IDADE(resultSet.getInt("NR_IDADE"));
                    return usuario;
                } else {
                    return null; // Usuário não encontrado
                }
            }
        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }
    }

}
