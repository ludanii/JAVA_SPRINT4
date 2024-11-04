package br.com.fiap.dao;

import br.com.fiap.dto.Login.LoginUsuario;
import br.com.fiap.dto.Telefone.TelefoneUsuario;

import java.sql.*;
import java.util.ArrayList;

public class LoginUsuarioDAO {
    private Connection con;

    public LoginUsuarioDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(LoginUsuario loginUsuario) {
        String sql = "insert into LOGINUSUARIO(ID_USUARIO,DS_USUARIO, DS_SENHA ) values(?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, loginUsuario.getID_USUARIO());
            ps.setString(2, loginUsuario.getDS_USUARIO());
            ps.setString(3, loginUsuario.getDS_SENHA());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                return "\nErro ao inserir";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String alterar(LoginUsuario loginUsuario) {
        String sql = "update LOGINUSUARIO set ID_USUARIO = ?, DS_USUARIO = ?, DS_SENHA = ? where ID_LOGIN = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, loginUsuario.getID_USUARIO());
            ps.setString(2, loginUsuario.getDS_USUARIO());
            ps.setString(3, loginUsuario.getDS_SENHA());
            ps.setInt(4, loginUsuario.getID_LOGIN());
            if (ps.executeUpdate() > 0) {
                return "\nAlterado com sucesso";
            } else {
                return "\nErro ao alterar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String deletar(Integer ID_LOGIN) {
        String sql = "delete from LOGINUSUARIO where ID_LOGIN = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_LOGIN);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public ArrayList<LoginUsuario> ListarTodos() {
        String sql = "select * from LOGINUSUARIO order by ID_LOGIN";
        ArrayList<LoginUsuario> listaLoginUsuario = new ArrayList<>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    LoginUsuario loginUsuario = new LoginUsuario();
                    loginUsuario.setID_LOGIN(rs.getInt("ID_LOGIN"));
                    loginUsuario.setID_USUARIO(rs.getInt("ID_USUARIO"));
                    loginUsuario.setDS_USUARIO(rs.getString("DS_USUARIO"));
                    loginUsuario.setDS_SENHA(rs.getString("DS_SENHA"));
                    listaLoginUsuario.add(loginUsuario);
                }
                return listaLoginUsuario;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }

    }
    public LoginUsuario buscarPorUser(String DS_USUARIO) throws SQLException {
        String sql = "select * from loginusuario where DS_USUARIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, DS_USUARIO);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LoginUsuario loginUsuario = new LoginUsuario();
                    loginUsuario.setID_USUARIO(rs.getInt("ID_USUARIO"));
                    loginUsuario.setDS_SENHA(rs.getString("DS_SENHA"));
                    loginUsuario.setID_LOGIN(rs.getInt("ID_LOGIN"));
                    loginUsuario.setDS_USUARIO(rs.getString("DS_USUARIO"));
                    return loginUsuario;
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
