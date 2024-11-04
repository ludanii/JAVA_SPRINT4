package br.com.fiap.resource;

import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.dto.Usuario.AtualizarUsuario;
import br.com.fiap.dto.Usuario.CadastroUsuario;
import br.com.fiap.dto.Usuario.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    private UsuarioDAO usuarioDAO;
    private ModelMapper modelMapper;
    public UsuarioResource() throws SQLException {
        this.usuarioDAO = new UsuarioDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response cadastrarUsuario(CadastroUsuario dto, @Context UriInfo uriInfo) throws SQLException {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        usuarioDAO.inserir(usuario);
        UriBuilder uri = uriInfo.getBaseUriBuilder();
        uri.path(String.valueOf(usuario.getID_USUARIO()));
        return Response.created(uri.build()).entity(usuario).build();
    }
    @GET
    @Path("{NR_CPF}")
    public Usuario buscar(@PathParam("NR_CPF") String NR_CPF) throws SQLException {
        return usuarioDAO.buscarPorId(NR_CPF);
    }

    @GET
    public List<Usuario> listar() throws SQLException {
        return usuarioDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_USUARIO}")
    public Response atualizar(AtualizarUsuario dto, @PathParam("ID_USUARIO") int ID_USUARIO) {
        try {
            Usuario usuario = modelMapper.map(dto, Usuario.class);
            usuario.setID_USUARIO(ID_USUARIO);
            usuarioDAO.alterar(usuario);
            return Response.ok().entity(modelMapper.map(usuario, Usuario.class)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar o usuário: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("{ID_USUARIO}")
    public void deletar(@PathParam("ID_USUARIO") int ID_USUARIO) throws SQLException {
        usuarioDAO.deletar(ID_USUARIO);
    }
}
