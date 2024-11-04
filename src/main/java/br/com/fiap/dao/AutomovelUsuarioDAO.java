package br.com.fiap.dao;

import br.com.fiap.dto.Automovel.AutomovelUsuario;

import java.sql.*;
import java.util.ArrayList;

public class AutomovelUsuarioDAO {
    private Connection con;

    public AutomovelUsuarioDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(AutomovelUsuario automovelUsuario) {
        String sql = "insert into AUTOMOVELUSUARIO(TP_AUTOMOVEL, DS_PLACA, DS_MODELO, DS_MARCA, " +
                "NR_ANO, DS_HISTORICO_AUTOMOVEL, NR_QUILOMETRAGEM, DS_AUTOMOVEL) values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, automovelUsuario.getTP_AUTOMOVEL() );
            ps.setString(2, automovelUsuario.getDS_PLACA());
            ps.setString(3, automovelUsuario.getDS_MODELO());
            ps.setString(4, automovelUsuario.getDS_MARCA());
            ps.setInt(5, automovelUsuario.getNR_ANO());
            ps.setString(6, automovelUsuario.getDS_HISTORICO_AUTOMOVEL());
            ps.setInt(7, automovelUsuario.getNR_QUILOMETRAGEM());
            ps.setString(8, automovelUsuario.getDS_AUTOMOVEL());
            if (ps.executeUpdate() > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        automovelUsuario.setID_AUTOMOVEL(generatedKeys.getInt(1));
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

    public String alterar(AutomovelUsuario automovelUsuario) {
        String sql = "update AUTOMOVELUSUARIO set TP_AUTOMOVEL = ?, DS_PLACA = ?,DS_MODELO = ?, DS_MARCA = ?, NR_ANO = ?, " +
                "DS_HISTORICO_AUTOMOVEL = ?, NR_QUILOMETRAGEM = ?, DS_AUTOMOVEL = ? where ID_AUTOMOVEL = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, automovelUsuario.getTP_AUTOMOVEL() );
            ps.setString(2, automovelUsuario.getDS_PLACA() );
            ps.setString(3, automovelUsuario.getDS_MODELO() );
            ps.setString(4, automovelUsuario.getDS_MARCA() );
            ps.setInt(5, automovelUsuario.getNR_ANO());
            ps.setString(6, automovelUsuario.getDS_HISTORICO_AUTOMOVEL());
            ps.setInt(7, automovelUsuario.getNR_QUILOMETRAGEM());
            ps.setString(8, automovelUsuario.getDS_AUTOMOVEL());
            ps.setInt(9, automovelUsuario.getID_AUTOMOVEL());
            if (ps.executeUpdate() > 0) {
                return "\nAlterado com sucesso";
            } else {
                return "\nErro ao alterar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String deletar(int ID_AUTOMOVEL) {
        String sql = "delete from AUTOMOVELUSUARIO where ID_AUTOMOVEL = ?";
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

    public ArrayList<AutomovelUsuario>ListarTodos() {
        String sql = "select * from AUTOMOVELUSUARIO order by ID_AUTOMOVEL";
        ArrayList<AutomovelUsuario> listaAutomovelUsuario = new ArrayList<>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    AutomovelUsuario automovelUsuario = new AutomovelUsuario();
                    automovelUsuario.setTP_AUTOMOVEL(rs.getString("TP_AUTOMOVEL"));
                    automovelUsuario.setDS_PLACA(rs.getString("DS_PLACA"));
                    automovelUsuario.setDS_MODELO(rs.getString("DS_MODELO"));
                    automovelUsuario.setDS_MARCA(rs.getString("DS_MARCA"));
                    automovelUsuario.setNR_ANO(rs.getInt("NR_ANO"));
                    automovelUsuario.setNR_QUILOMETRAGEM(rs.getInt("NR_QUILOMETRAGEM"));
                    automovelUsuario.setDS_HISTORICO_AUTOMOVEL(rs.getString("DS_HISTORICO_AUTOMOVEL"));
                    automovelUsuario.setDS_AUTOMOVEL(rs.getString("DS_AUTOMOVEL"));
                    automovelUsuario.setID_AUTOMOVEL(rs.getInt("ID_AUTOMOVEL"));
                    listaAutomovelUsuario.add(automovelUsuario);
                }
                return listaAutomovelUsuario;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }

    }

    public AutomovelUsuario buscarPorId(Integer ID_AUTOMOVEL) throws SQLException {
        String sql = "SELECT * FROM automovelusuario WHERE ID_AUTOMOVEL = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_AUTOMOVEL);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AutomovelUsuario automovelUsuario = new AutomovelUsuario();
                    automovelUsuario.setTP_AUTOMOVEL(rs.getString("TP_AUTOMOVEL"));
                    automovelUsuario.setDS_PLACA(rs.getString("DS_PLACA"));
                    automovelUsuario.setDS_MODELO(rs.getString("DS_MODELO"));
                    automovelUsuario.setDS_MARCA(rs.getString("DS_MARCA"));
                    automovelUsuario.setNR_ANO(rs.getInt("NR_ANO"));
                    automovelUsuario.setNR_QUILOMETRAGEM(rs.getInt("NR_QUILOMETRAGEM"));
                    automovelUsuario.setDS_HISTORICO_AUTOMOVEL(rs.getString("DS_HISTORICO_AUTOMOVEL"));
                    automovelUsuario.setDS_AUTOMOVEL(rs.getString("DS_AUTOMOVEL"));
                    automovelUsuario.setID_AUTOMOVEL(rs.getInt("ID_AUTOMOVEL"));

                    return automovelUsuario;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }
    }
}
