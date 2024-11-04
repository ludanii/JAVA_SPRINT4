package br.com.fiap.dao;

import br.com.fiap.dto.Diagnostico.AutomovelDiagnostico;
import br.com.fiap.dto.Telefone.TelefoneUsuario;

import java.sql.*;
import java.util.ArrayList;

public class AutomovelDiagnosticoDAO {
    private Connection con;

    public AutomovelDiagnosticoDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(AutomovelDiagnostico automovelDiagnostico) {
        String sql = "insert into AUTOMOVELDIAGNOSTICO(ID_AUTOMOVEL, ID_DIAGNOSTICO, DS_PROBLEMA, VL_ORCAMENTO_PREVISTO, TP_FEEDBACK_DIAGNOSTICO, " +
                "DS_FEEDBACK_DIAGNOSTICO, DT_INICIO, NR_HORARIO ) values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, automovelDiagnostico.getID_DIAGNOSTICO());
            ps.setInt(2, automovelDiagnostico.getID_AUTOMOVEL());
            ps.setString(3, automovelDiagnostico.getDS_PROBLEMA());
            ps.setInt(4, automovelDiagnostico.getVL_ORCAMENTO_PREVISTO());
            ps.setString(5, automovelDiagnostico.getTP_FEEDBACK_DIAGNOSTICO());
            ps.setString(6, automovelDiagnostico.getDS_FEEDBACK_DIAGNOSTICO());
            if (automovelDiagnostico.getDT_INICIO() != null) {
                ps.setDate(7, Date.valueOf(automovelDiagnostico.getDT_INICIO()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            ps.setString(8, automovelDiagnostico.getNR_HORARIO());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                return "\nErro ao inserir";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String alterar(AutomovelDiagnostico automovelDiagnostico) {
        String sql = "update AUTOMOVELDIAGNOSTICO set ID_AUTOMOVEL = ?, DS_PROBLEMA = ?, VL_ORCAMENTO_PREVISTO = ?, TP_FEEDBACK_DIAGNOSTICO = ?, " +
                "DS_FEEDBACK_DIAGNOSTICO = ? , DT_INICIO = ?, NR_HORARIO = ? where ID_DIAGNOSTICO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, automovelDiagnostico.getID_AUTOMOVEL());
            ps.setString(2, automovelDiagnostico.getDS_PROBLEMA());
            ps.setInt(3, automovelDiagnostico.getVL_ORCAMENTO_PREVISTO());
            ps.setString(4, automovelDiagnostico.getTP_FEEDBACK_DIAGNOSTICO());
            ps.setString(5, automovelDiagnostico.getDS_FEEDBACK_DIAGNOSTICO());
            if (automovelDiagnostico.getDT_INICIO() != null) {
                ps.setDate(6, Date.valueOf(automovelDiagnostico.getDT_INICIO()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setString(7, automovelDiagnostico.getNR_HORARIO());
            ps.setInt(8, automovelDiagnostico.getID_DIAGNOSTICO());
            if (ps.executeUpdate() > 0) {
                return "\nAlterado com sucesso";
            } else {
                return "\nErro ao alterar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String deletar(int ID_DIAGNOSTICO) {
        String sql = "delete from AUTOMOVELDIAGNOSTICO where ID_DIAGNOSTICO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_DIAGNOSTICO);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public ArrayList<AutomovelDiagnostico> ListarTodos() {
        String sql = "select * from AUTOMOVELDIAGNOSTICO order by ID_DIAGNOSTICO";
        ArrayList<AutomovelDiagnostico> listaAutomovelDiagnostico = new ArrayList<>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AutomovelDiagnostico automovelDiagnostico = new AutomovelDiagnostico();
                    automovelDiagnostico.setID_AUTOMOVEL(rs.getInt(1));
                    automovelDiagnostico.setDS_PROBLEMA(rs.getString(2));
                    automovelDiagnostico.setVL_ORCAMENTO_PREVISTO(rs.getInt(3));
                    automovelDiagnostico.setTP_FEEDBACK_DIAGNOSTICO(rs.getString(4));
                    automovelDiagnostico.setDS_FEEDBACK_DIAGNOSTICO(rs.getString(5));
                    Date DT_INICIO = rs.getDate(6);
                    if (DT_INICIO != null) {
                        automovelDiagnostico.setDT_INICIO(DT_INICIO.toLocalDate());
                    }
                    automovelDiagnostico.setNR_HORARIO(rs.getString(7));
                    automovelDiagnostico.setID_DIAGNOSTICO(rs.getInt(8));
                    listaAutomovelDiagnostico.add(automovelDiagnostico);
                }
                return listaAutomovelDiagnostico;

            } catch (SQLException e) {
                System.out.println("\nErro de SQL: " + e.getMessage());
                return null;
            }

    }
    public AutomovelDiagnostico buscarPorId(Integer ID_AUTOMOVEL) throws SQLException {
        String sql = "select * from AUTOMOVELDIAGNOSTICO where ID_AUTOMOVEL = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_AUTOMOVEL);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AutomovelDiagnostico automovelDiagnostico = new AutomovelDiagnostico();
                    automovelDiagnostico.setID_AUTOMOVEL(rs.getInt("ID_AUTOMOVEL"));
                    automovelDiagnostico.setDS_PROBLEMA(rs.getString("DS_PROBLEMA"));
                    automovelDiagnostico.setVL_ORCAMENTO_PREVISTO(rs.getInt("VL_ORCAMENTO_PREVISTO"));
                    automovelDiagnostico.setTP_FEEDBACK_DIAGNOSTICO(rs.getString("TP_FEEDBACK_DIAGNOSTICO"));
                    automovelDiagnostico.setDS_FEEDBACK_DIAGNOSTICO(rs.getString("DS_FEEDBACK_DIAGNOSTICO"));
                    Date DT_INICIO = rs.getDate(6);
                    if (DT_INICIO != null) {
                        automovelDiagnostico.setDT_INICIO(DT_INICIO.toLocalDate());
                    }
                    automovelDiagnostico.setNR_HORARIO(rs.getString("NR_HORARIO"));
                    automovelDiagnostico.setID_DIAGNOSTICO(rs.getInt("ID_DIAGNOSTICO"));
                    return  automovelDiagnostico;
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
