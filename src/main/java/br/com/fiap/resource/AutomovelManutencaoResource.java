package br.com.fiap.resource;

import br.com.fiap.dao.AutomovelManutencaoDAO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dto.Manutencao.AutomovelManutencao;
import br.com.fiap.dto.Manutencao.CadastrarManutencao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("manutencao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutomovelManutencaoResource {
    private AutomovelManutencaoDAO automovelManutencaoDAO;
    private ModelMapper modelMapper;
    public AutomovelManutencaoResource() throws SQLException {
        this.automovelManutencaoDAO = new AutomovelManutencaoDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response cadastrarManutencao(CadastrarManutencao dto, @Context UriInfo uriInfo) throws SQLException {
        AutomovelManutencao automovelManutencao = modelMapper.map(dto, AutomovelManutencao.class);
        automovelManutencaoDAO.inserir(automovelManutencao);
        UriBuilder uri = uriInfo.getBaseUriBuilder();
        uri.path(String.valueOf(automovelManutencao.getID_MANUTENCAO()));
        return Response.created(uri.build()).entity(automovelManutencao).build();
    }
    @GET
    @Path("{ID_AUTOMOVEL}")
    public AutomovelManutencao buscar(@PathParam("ID_AUTOMOVEL") Integer ID_AUTOMOVEL) throws SQLException {
        return automovelManutencaoDAO.buscarPorId(ID_AUTOMOVEL);
    }

    @GET
    public List<AutomovelManutencao> listar() throws SQLException {
        return automovelManutencaoDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_MANUTENCAO}")
    public Response atualizar(CadastrarManutencao dto, @PathParam("ID_MANUTENCAO") int ID_MANUTENCAO) throws SQLException {
        AutomovelManutencao automovelManutencao = modelMapper.map(dto, AutomovelManutencao.class);
        automovelManutencao.setID_MANUTENCAO(ID_MANUTENCAO);
        String resultado =  automovelManutencaoDAO.alterar(automovelManutencao);
        if (resultado.contains("sucesso")) {
            return Response.ok().entity(modelMapper.map(automovelManutencao, AutomovelManutencao.class)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(resultado).build();
        }    }

    @DELETE
    @Path("{ID_MANUTENCAO}")
    public void deletar(@PathParam("ID_MANUTENCAO") int ID_MANUTENCAO) throws SQLException {
        automovelManutencaoDAO.deletar(ID_MANUTENCAO);
    }
}
