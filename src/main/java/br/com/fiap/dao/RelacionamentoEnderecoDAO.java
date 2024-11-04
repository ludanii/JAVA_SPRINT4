package br.com.fiap.dao;

import br.com.fiap.dto.Endereco.RelacionamentoEndereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelacionamentoEnderecoDAO {
    private Connection con;

    public RelacionamentoEnderecoDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String associarEnderecoAoUsuario(RelacionamentoEndereco relacionamentoEndereco) {
        String sql = "INSERT INTO usuario_endereco (ID_USUARIO, ID_ENDERECO) VALUES (?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, relacionamentoEndereco.getID_ENDERECO());
            ps.setInt(2, relacionamentoEndereco.getID_USUARIO());

            int rows = ps.executeUpdate();
            return rows > 0 ? "Associação realizada com sucesso" : "Falha na associação";
        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }
    public RelacionamentoEndereco buscarPorId(Integer ID_USUARIO) throws SQLException {
        String sql = "SELECT * FROM usuario_endereco WHERE ID_USUARIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_USUARIO);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RelacionamentoEndereco relacionamentoEndereco  = new RelacionamentoEndereco();
                    relacionamentoEndereco.setID_ENDERECO(rs.getInt("ID_ENDERECO"));
                    return relacionamentoEndereco;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }
    }
    public String deletar(Integer ID_ENDERECO) {
        String sql = "delete from usuario_endereco where ID_ENDERECO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_ENDERECO);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

}
