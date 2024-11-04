package br.com.fiap.resource;

import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.RelacionamentoAutomovelDAO;
import br.com.fiap.dto.Automovel.RelacionamentoAutomovel;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

@Path("relacionamento_automovel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RelacionamentoAutomovelResource {
    private RelacionamentoAutomovelDAO relacionamentoAutomovelDAO;
    private ModelMapper modelMapper;
    public RelacionamentoAutomovelResource() throws SQLException {
        this.relacionamentoAutomovelDAO = new RelacionamentoAutomovelDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response CadastrarRelacionamento(RelacionamentoAutomovel relacionamentoAutomovel, @Context UriInfo uriInfo ) throws SQLException {
        relacionamentoAutomovelDAO.associarAutomovelAoUsuario(relacionamentoAutomovel);
        return Response.ok().entity(relacionamentoAutomovel).build();
    }
    @GET
    @Path("{ID_USUARIO}")
    public RelacionamentoAutomovel buscar(@PathParam("ID_USUARIO") Integer ID_USUARIO) throws SQLException {
        return relacionamentoAutomovelDAO.buscarPorId(ID_USUARIO);
    }

    @DELETE
    @Path("{ID_AUTOMOVEL}")
    public void deletar(@PathParam("ID_AUTOMOVEL") int ID_AUTOMOVEL) throws SQLException {
        relacionamentoAutomovelDAO.deletar(ID_AUTOMOVEL);
    }
}
