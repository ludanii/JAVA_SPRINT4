package br.com.fiap.dao;

import br.com.fiap.dto.Endereco.EnderecoUsuario;

import java.sql.*;
import java.util.ArrayList;

public class EnderecoUsuarioDAO {
    private Connection con;

    public EnderecoUsuarioDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(EnderecoUsuario enderecoUsuario) {
        String sql = "insert into ENDERECOUSUARIO( NR_CEP, NM_ESTADO, NM_CIDADE, NM_BAIRRO, " +
                "NM_LOGRADOURO, TP_ENDERECO, DS_COMPLEMENTO, DS_PONTO_REFERENCIA) values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, enderecoUsuario.cepFormatado(enderecoUsuario.getNR_CEP()));
            ps.setString(2, enderecoUsuario.getNM_ESTADO());
            ps.setString(3, enderecoUsuario.getNM_CIDADE());
            ps.setString(4, enderecoUsuario.getNM_BAIRRO());
            ps.setString(5, enderecoUsuario.logradouroFormatado());
            ps.setString(6, enderecoUsuario.getTP_ENDERECO());
            ps.setString(7, enderecoUsuario.getDS_COMPLEMENTO());
            ps.setString(8, enderecoUsuario.getDS_PONTO_REFERENCIA());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                return "\nErro ao inserir";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String alterar(EnderecoUsuario enderecoUsuario) {
        String sql = "update ENDERECOUSUARIO set NR_CEP = ?, NM_ESTADO = ?, NM_CIDADE = ?, NM_BAIRRO = ?, " +
                "NM_LOGRADOURO = ?, TP_ENDERECO = ?, DS_COMPLEMENTO = ?, DS_PONTO_REFERENCIA = ? where ID_ENDERECO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, enderecoUsuario.cepFormatado(enderecoUsuario.getNR_CEP()));
            ps.setString(2, enderecoUsuario.getNM_ESTADO());
            ps.setString(3, enderecoUsuario.getNM_CIDADE());
            ps.setString(4, enderecoUsuario.getNM_BAIRRO());
            ps.setString(5, enderecoUsuario.logradouroFormatado());
            ps.setString(6, enderecoUsuario.getTP_ENDERECO());
            ps.setString(7, enderecoUsuario.getDS_COMPLEMENTO());
            ps.setString(8, enderecoUsuario.getDS_PONTO_REFERENCIA());
            ps.setInt(9, enderecoUsuario.getID_ENDERECO());
            if (ps.executeUpdate() > 0) {
                return "\nAlterado com sucesso";
            } else {
                return "\nErro ao alterar";
            }
        } catch (SQLException e) {
            return "\nErro de SQL: " + e.getMessage();
        }
    }

    public String deletar(Integer ID_ENDERECO) {
        String sql = "delete from ENDERECOUSUARIO where ID_ENDERECO = ?";
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

    public ArrayList<EnderecoUsuario> ListarTodos() {
        String sql = "select * from ENDERECOUSUARIO order by ID_ENDERECO";
        ArrayList<EnderecoUsuario> listaEnderecoUsuario = new ArrayList<EnderecoUsuario>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    EnderecoUsuario enderecoUsuario = new EnderecoUsuario();
                    enderecoUsuario.setID_ENDERECO(rs.getInt("ID_ENDERECO"));
                    enderecoUsuario.setNR_CEP(rs.getString("NR_CEP"));
                    enderecoUsuario.setNM_ESTADO(rs.getString("NM_ESTADO"));
                    enderecoUsuario.setNM_CIDADE(rs.getString("NM_CIDADE"));
                    enderecoUsuario.setNM_BAIRRO(rs.getString("NM_BAIRRO"));
                    enderecoUsuario.setNM_LOGRADOURO(rs.getString("NM_LOGRADOURO"));
                    enderecoUsuario.setTP_ENDERECO(rs.getString("TP_ENDERECO"));
                    enderecoUsuario.setDS_COMPLEMENTO(rs.getString("DS_COMPLEMENTO"));
                    enderecoUsuario.setDS_PONTO_REFERENCIA(rs.getString("DS_PONTO_REFERENCIA"));
                    listaEnderecoUsuario.add(enderecoUsuario);
                }
                return listaEnderecoUsuario;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("\nErro de SQL: " + e.getMessage());
            return null;
        }
    }

        public EnderecoUsuario buscarPorCEP(String NR_CEP) throws SQLException {
            String sql = "SELECT * FROM enderecousuario WHERE NR_CEP = ?";
            try (PreparedStatement ps = getCon().prepareStatement(sql)) {
                ps.setString(1, NR_CEP);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        EnderecoUsuario enderecoUsuario = new EnderecoUsuario();
                        enderecoUsuario.setNR_CEP(rs.getString("NR_CEP"));
                        enderecoUsuario.setNM_ESTADO(rs.getString("NM_ESTADO"));
                        enderecoUsuario.setNM_CIDADE(rs.getString("NM_CIDADE"));
                        enderecoUsuario.setNM_BAIRRO(rs.getString("NM_BAIRRO"));
                        enderecoUsuario.setNM_LOGRADOURO(rs.getString("NM_LOGRADOURO"));
                        enderecoUsuario.setTP_ENDERECO(rs.getString("TP_ENDERECO"));
                        enderecoUsuario.setDS_COMPLEMENTO(rs.getString("DS_COMPLEMENTO"));
                        enderecoUsuario.setDS_PONTO_REFERENCIA(rs.getString("DS_PONTO_REFERENCIA"));
                        enderecoUsuario.setID_ENDERECO(rs.getInt("ID_ENDERECO"));

                        return enderecoUsuario;
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

