package br.com.fiap.dao;

import br.com.fiap.dto.Diagnostico.AutomovelDiagnostico;
import br.com.fiap.dto.Manutencao.AutomovelManutencao;

import java.sql.*;
import java.util.ArrayList;

public class AutomovelManutencaoDAO {
    private Connection con;

    public AutomovelManutencaoDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(AutomovelManutencao automovelManutencao) {
        String sql = "insert into AUTOMOVELMANUTENCAO(ID_AUTOMOVEL, ID_MANUTENCAO, TP_MANUTENCAO, VL_MANUTENCAO, DT_MANUTENCAO, " +
                "DS_MANUTENCAO, NM_OFICINA, TP_FEEDBACK_OFICINA, DS_FEEDBACK_OFICINA ) values(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, automovelManutencao.getID_AUTOMOVEL());
            ps.setInt(2, automovelManutencao.getID_MANUTENCAO());
            ps.setString(3, automovelManutencao.getTP_MANUTENCAO());
            ps.setInt(4, automovelManutencao.getVL_MANUTENCAO());
            if (automovelManutencao.getDT_MANUTENCAO() != null) {
                ps.setDate(5, Date.valueOf(automovelManutencao.getDT_MANUTENCAO()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, automovelManutencao.getDS_MANUTENCAO());
            ps.setString(7, automovelManutencao.getNM_OFICINA());
            ps.setString(8, automovelManutencao.getTP_FEEDBACK_OFICINA());
            ps.setString(9, automovelManutencao.getDS_FEEDBACK_OFICINA());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                return "\nErro ao inserir";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String alterar(AutomovelManutencao automovelManutencao) {
        String sql = "update AUTOMOVELMANUTENCAO set ID_AUTOMOVEL = ?, TP_MANUTENCAO = ?, VL_MANUTENCAO = ?, DT_MANUTENCAO = ?, " +
                "DS_MANUTENCAO = ?, NM_OFICINA = ?, TP_FEEDBACK_OFICINA = ?, DS_FEEDBACK_OFICINA = ?  where ID_MANUTENCAO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, automovelManutencao.getID_AUTOMOVEL());
            ps.setString(2, automovelManutencao.getTP_MANUTENCAO());
            ps.setInt(3, automovelManutencao.getVL_MANUTENCAO());
            if (automovelManutencao.getDT_MANUTENCAO() != null) {
                ps.setDate(4, Date.valueOf(automovelManutencao.getDT_MANUTENCAO()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, automovelManutencao.getDS_MANUTENCAO());
            ps.setString(6, automovelManutencao.getNM_OFICINA());
            ps.setString(7, automovelManutencao.getTP_FEEDBACK_OFICINA());
            ps.setString(8, automovelManutencao.getDS_FEEDBACK_OFICINA());
            ps.setInt(9, automovelManutencao.getID_MANUTENCAO());
            if (ps.executeUpdate() > 0) {
                return "\nAlterado com sucesso";
            } else {
                return "\nErro ao alterar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String deletar(int ID_MANUTENCAO) {
        String sql = "delete from AUTOMOVELMANUTENCAO where ID_MANUTENCAO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_MANUTENCAO);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public ArrayList<AutomovelManutencao> ListarTodos() {
        String sql = "select * from AUTOMOVELMANUTENCAO order by ID_MANUTENCAO";
        ArrayList<AutomovelManutencao> listaAutomovelManutencao = new ArrayList<>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    AutomovelManutencao automovelManutencao = new AutomovelManutencao();
                    automovelManutencao.setID_AUTOMOVEL(rs.getInt(1));
                    automovelManutencao.setTP_MANUTENCAO(rs.getString(2));
                    automovelManutencao.setVL_MANUTENCAO(rs.getInt(3));
                    Date DT_MANUTENCAO = rs.getDate(4);
                    if (DT_MANUTENCAO != null) {
                        automovelManutencao.setDT_MANUTENCAO(DT_MANUTENCAO.toLocalDate());
                    }
                    automovelManutencao.setDS_MANUTENCAO(rs.getString(5));
                    automovelManutencao.setNM_OFICINA(rs.getString(6));
                    automovelManutencao.setTP_FEEDBACK_OFICINA(rs.getString(7));
                    automovelManutencao.setDS_FEEDBACK_OFICINA(rs.getString(8));
                    automovelManutencao.setID_MANUTENCAO(rs.getInt(9));
                    listaAutomovelManutencao.add(automovelManutencao);
                }
                return listaAutomovelManutencao;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }

    }

    public AutomovelManutencao buscarPorId(Integer ID_AUTOMOVEL) throws SQLException {
        String sql = "select * from AUTOMOVELMANUTENCAO where ID_AUTOMOVEL = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_AUTOMOVEL);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AutomovelManutencao automovelManutencao = new AutomovelManutencao();
                    automovelManutencao.setTP_MANUTENCAO(rs.getString("ID_MANUTENCAO"));
                    automovelManutencao.setVL_MANUTENCAO(rs.getInt("VL_MANUTENCAO"));
                    Date DT_MANUTENCAO = rs.getDate(4);
                    if (DT_MANUTENCAO != null) {
                        automovelManutencao.setDT_MANUTENCAO(DT_MANUTENCAO.toLocalDate());
                    }
                    automovelManutencao.setDS_MANUTENCAO(rs.getString("DS_MANUTENCAO"));
                    automovelManutencao.setNM_OFICINA(rs.getString("NM_OFICINA"));
                    automovelManutencao.setTP_FEEDBACK_OFICINA(rs.getString("TP_FEEDBACK_OFICINA"));
                    automovelManutencao.setDS_FEEDBACK_OFICINA(rs.getString("DS_FEEDBACK_OFICINA"));
                    automovelManutencao.setID_MANUTENCAO(rs.getInt("ID_MANUTENCAO"));
                    automovelManutencao.setID_AUTOMOVEL(rs.getInt("ID_AUTOMOVEL"));
                    return  automovelManutencao;
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
