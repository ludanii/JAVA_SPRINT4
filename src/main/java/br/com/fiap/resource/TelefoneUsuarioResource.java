package br.com.fiap.resource;

import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.TelefoneUsuarioDAO;
import br.com.fiap.dto.Telefone.CadastrarTelefone;
import br.com.fiap.dto.Telefone.TelefoneUsuario;
import br.com.fiap.dto.Usuario.CadastroUsuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("telefone")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelefoneUsuarioResource {
    private TelefoneUsuarioDAO telefoneUsuarioDAO;
    private ModelMapper modelMapper;
    public TelefoneUsuarioResource() throws SQLException {
        this.telefoneUsuarioDAO = new TelefoneUsuarioDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response cadastrarTelefone(CadastrarTelefone dto, @Context UriInfo uriInfo) throws SQLException {
        TelefoneUsuario telefoneUsuario = modelMapper.map(dto, TelefoneUsuario.class);
        telefoneUsuarioDAO.inserir(telefoneUsuario);

        UriBuilder uri = uriInfo.getBaseUriBuilder();
        uri.path(String.valueOf(telefoneUsuario.getID_TELEFONE())); // Supondo que o ID seja gerado no banco
        return Response.created(uri.build()).entity(telefoneUsuario).build();
    }
    @GET
    @Path("{ID_USUARIO}")
    public TelefoneUsuario buscar(@PathParam("ID_USUARIO") Integer ID_USUARIO) throws SQLException {
        return telefoneUsuarioDAO.buscarPorId(ID_USUARIO);
    }

    @GET
    public List<TelefoneUsuario> listar() throws SQLException {
        return telefoneUsuarioDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_TELEFONE}")
    public Response atualizar(CadastrarTelefone dto, @PathParam("ID_TELEFONE") int ID_TELEFONE)  {
        TelefoneUsuario telefoneUsuario = modelMapper.map(dto, TelefoneUsuario.class);
        telefoneUsuario.setID_TELEFONE(ID_TELEFONE);
        telefoneUsuarioDAO.alterar(telefoneUsuario);
        return Response.ok().entity(modelMapper.map(telefoneUsuario, TelefoneUsuario.class)).build();
    }

    @DELETE
    @Path("{ID_TELEFONE}")
    public void deletar(@PathParam("ID_TELEFONE") int ID_TELEFONE) throws SQLException {
        telefoneUsuarioDAO.deletar(ID_TELEFONE);
    }
}
