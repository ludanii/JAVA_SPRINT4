package br.com.fiap.dao;

import br.com.fiap.dto.Automovel.RelacionamentoAutomovel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelacionamentoAutomovelDAO {
    private Connection con;

    public RelacionamentoAutomovelDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String associarAutomovelAoUsuario(RelacionamentoAutomovel relacionamentoAutomovel) {
        String sql = "INSERT INTO usuario_automovel (ID_USUARIO, ID_AUTOMOVEL) VALUES (?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, relacionamentoAutomovel.getID_USUARIO());
            ps.setInt(2, relacionamentoAutomovel.getID_AUTOMOVEL());

            int rows = ps.executeUpdate();
            return rows > 0 ? "Associação realizada com sucesso" : "Falha na associação";
        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }

    public RelacionamentoAutomovel buscarPorId(Integer ID_USUARIO) throws SQLException {
        String sql = "SELECT * FROM usuario_automovel WHERE ID_USUARIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_USUARIO);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RelacionamentoAutomovel relacionamentoAutomovel = new RelacionamentoAutomovel();
                    relacionamentoAutomovel.setID_AUTOMOVEL(rs.getInt("ID_AUTOMOVEL"));
                    return relacionamentoAutomovel;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }
    }
    public String deletar(Integer ID_AUTOMOVEL) {
        String sql = "delete from usuario_automovel where ID_AUTOMOVEL = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_AUTOMOVEL);
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
