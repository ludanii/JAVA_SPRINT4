package br.com.fiap.resource;

import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.LoginUsuarioDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.dto.Login.CadastroLogin;
import br.com.fiap.dto.Login.LoginUsuario;
import br.com.fiap.dto.Usuario.AtualizarUsuario;
import br.com.fiap.dto.Usuario.CadastroUsuario;
import br.com.fiap.dto.Usuario.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("info_login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginUsuarioResource {

    private LoginUsuarioDAO loginUsuarioDAO;
    private ModelMapper modelMapper;
    public LoginUsuarioResource() throws SQLException {
        this.loginUsuarioDAO = new LoginUsuarioDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response cadastrarLogin(CadastroLogin cadastroLogin, @Context UriInfo uriInfo) throws SQLException {
        LoginUsuario loginUsuario = modelMapper.map(cadastroLogin, LoginUsuario.class);
        String resultado = loginUsuarioDAO.inserir(loginUsuario);
        UriBuilder uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(loginUsuario.getID_LOGIN()));
        return Response.created(uri.build()).entity(resultado).build();
    }

    @GET
    @Path("{DS_USUARIO}")
    public LoginUsuario buscar(@PathParam("DS_USUARIO") String DS_USUARIO) throws SQLException {
        return loginUsuarioDAO.buscarPorUser(DS_USUARIO);
    }

    @GET
    public List<LoginUsuario> listar() throws SQLException {
        return loginUsuarioDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_LOGIN}")
    public Response atualizar(CadastroLogin cadastroLogin, @PathParam("ID_LOGIN") int ID_LOGIN){
        LoginUsuario loginUsuario = modelMapper.map(cadastroLogin, LoginUsuario.class);
        loginUsuario.setID_LOGIN(ID_LOGIN);
        loginUsuarioDAO.alterar(loginUsuario);
        return Response.ok().entity(modelMapper.map(loginUsuario, LoginUsuario.class)).build();
    }

    @DELETE
    @Path("{ID_LOGIN}")
    public void deletar(@PathParam("ID_LOGIN") int ID_LOGIN) throws SQLException {
        loginUsuarioDAO.deletar(ID_LOGIN);
    }
}
