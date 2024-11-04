package br.com.fiap.dao;

import br.com.fiap.dto.Telefone.TelefoneUsuario;
import br.com.fiap.dto.Usuario.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class TelefoneUsuarioDAO {
    private Connection con;

    public TelefoneUsuarioDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(TelefoneUsuario telefoneUsuario) {
        String sql = "insert into TELEFONEUSUARIO(ID_USUARIO, NR_DDI, NR_DDD, NR_TELEFONE, NR_TELEFONE_COMPLETO, TP_TELEFONE, ST_TELEFONE) values(?,?,?,?,?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, telefoneUsuario.getID_USUARIO());
            ps.setInt(2, telefoneUsuario.getNR_DDI());
            ps.setInt(3, telefoneUsuario.getNR_DDD());
            ps.setString(4, telefoneUsuario.telefoneFormatado(telefoneUsuario.getNR_TELEFONE())); // Formatar telefone aqui
            ps.setString(5, telefoneUsuario.getTelefoneCompleto());
            ps.setString(6, telefoneUsuario.getTP_TELEFONE());
            ps.setString(7, telefoneUsuario.getST_TELEFONE());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                return "\nErro ao inserir";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String alterar(TelefoneUsuario telefoneUsuario) {
        String sql = "update TELEFONEUSUARIO set  ID_USUARIO = ?, NR_DDI = ?, NR_DDD = ?,NR_TELEFONE = ?, NR_TELEFONE_COMPLETO = ?, TP_TELEFONE = ?, ST_TELEFONE = ? where ID_TELEFONE = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, telefoneUsuario.getID_USUARIO());
            ps.setInt(2, telefoneUsuario.getNR_DDI());
            ps.setInt(3, telefoneUsuario.getNR_DDD());
            ps.setString(4, telefoneUsuario.telefoneFormatado(telefoneUsuario.getNR_TELEFONE()));
            ps.setString(5, telefoneUsuario.getTelefoneCompleto());
            ps.setString(6, telefoneUsuario.getTP_TELEFONE());
            ps.setString(7, telefoneUsuario.getST_TELEFONE());
            ps.setInt(8, telefoneUsuario.getID_TELEFONE());
            if (ps.executeUpdate() > 0) {
                return "\nAlterado com sucesso";
            } else {
                return "\nErro ao alterar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String deletar(Integer ID_TELEFONE) {
        String sql = "delete from TELEFONEUSUARIO where ID_TELEFONE = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_TELEFONE);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public ArrayList<TelefoneUsuario> ListarTodos() {
        String sql = "select * from TELEFONEUSUARIO order by ID_TELEFONE";
        ArrayList<TelefoneUsuario> listaTelefoneUsuario = new ArrayList<TelefoneUsuario>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    TelefoneUsuario telefoneUsuario = new TelefoneUsuario();
                    telefoneUsuario.setID_TELEFONE(rs.getInt(1));
                    telefoneUsuario.setID_USUARIO(rs.getInt(2));
                    telefoneUsuario.setNR_DDI(rs.getInt(3));
                    telefoneUsuario.setNR_DDD(rs.getInt(4));
                    telefoneUsuario.setNR_TELEFONE(rs.getString(5));
                    telefoneUsuario.setNR_TELEFONE_COMPLETO(rs.getString(6));
                    telefoneUsuario.setTP_TELEFONE(rs.getString(7));
                    telefoneUsuario.setST_TELEFONE(rs.getString(8));
                    listaTelefoneUsuario.add(telefoneUsuario);
                }
                return listaTelefoneUsuario;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }

    }
    public TelefoneUsuario buscarPorId(Integer ID_USUARIO) throws SQLException {
        String sql = "select * from telefoneusuario where ID_USUARIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_USUARIO);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    TelefoneUsuario telefoneUsuario = new TelefoneUsuario();
                    telefoneUsuario.setID_TELEFONE(resultSet.getInt("ID_TELEFONE"));
                    telefoneUsuario.setID_USUARIO(resultSet.getInt("ID_USUARIO"));
                    telefoneUsuario.setNR_DDI(resultSet.getInt("NR_DDI"));
                    telefoneUsuario.setNR_DDD(resultSet.getInt("NR_DDD"));
                    telefoneUsuario.setNR_TELEFONE(resultSet.getString("NR_TELEFONE"));
                    telefoneUsuario.setNR_TELEFONE_COMPLETO(resultSet.getString("NR_TELEFONE_COMPLETO"));
                    telefoneUsuario.setTP_TELEFONE(resultSet.getString("TP_TELEFONE"));
                    telefoneUsuario.setST_TELEFONE(resultSet.getString("ST_TELEFONE"));
                    return telefoneUsuario;
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
