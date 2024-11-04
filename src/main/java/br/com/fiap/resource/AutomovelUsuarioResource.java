package br.com.fiap.resource;

import br.com.fiap.dao.AutomovelUsuarioDAO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dto.Automovel.AutomovelUsuario;
import br.com.fiap.dto.Automovel.CadastroAutomovel;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("automovel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutomovelUsuarioResource {
    private AutomovelUsuarioDAO automovelUsuarioDAO;
    private ModelMapper modelMapper;
    public AutomovelUsuarioResource() throws SQLException {
        this.automovelUsuarioDAO = new AutomovelUsuarioDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response cadastrarAutomovel(CadastroAutomovel dto, @Context UriInfo uriInfo) {
            AutomovelUsuario automovelUsuario = modelMapper.map(dto, AutomovelUsuario.class);
            automovelUsuarioDAO.inserir(automovelUsuario);
            UriBuilder uri = uriInfo.getBaseUriBuilder();
            uri.path(String.valueOf(automovelUsuario.getID_AUTOMOVEL()));
            return Response.created(uri.build()).entity(automovelUsuario).build();
    }

    @GET
    @Path("{ID_AUTOMOVEL}")
    public AutomovelUsuario buscar(@PathParam("ID_AUTOMOVEL") Integer ID_AUTOMOVEL) throws SQLException {
        return automovelUsuarioDAO.buscarPorId(ID_AUTOMOVEL);
    }

    @GET
    public List<AutomovelUsuario> listar() throws SQLException {
        return automovelUsuarioDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_AUTOMOVEL}")
    public Response atualizar(CadastroAutomovel dto, @PathParam("ID_AUTOMOVEL") int ID_AUTOMOVEL) {
        AutomovelUsuario automovelUsuario = modelMapper.map(dto, AutomovelUsuario.class);
        automovelUsuario.setID_AUTOMOVEL(ID_AUTOMOVEL);
        automovelUsuarioDAO.alterar(automovelUsuario);
        return Response.ok().entity(modelMapper.map(automovelUsuario, AutomovelUsuario.class)).build();
    }

    @DELETE
    @Path("{ID_AUTOMOVEL}")
    public void deletar(@PathParam("ID_AUTOMOVEL") int ID_AUTOMOVEL) throws SQLException {
        automovelUsuarioDAO.deletar(ID_AUTOMOVEL);
    }
}
