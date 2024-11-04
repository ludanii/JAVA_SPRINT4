package br.com.fiap.resource;

import br.com.fiap.dao.AutomovelDiagnosticoDAO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dto.Diagnostico.AutomovelDiagnostico;
import br.com.fiap.dto.Diagnostico.CadastrarDiagnostico;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("diagnostico")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutomovelDiagnosticoResource {

    private AutomovelDiagnosticoDAO automovelDiagnosticoDAO;
    private ModelMapper modelMapper;
    public AutomovelDiagnosticoResource() throws SQLException {
        this.automovelDiagnosticoDAO = new AutomovelDiagnosticoDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response cadastrarDiagnostico(CadastrarDiagnostico dto, @Context UriInfo uriInfo) throws SQLException {
        AutomovelDiagnostico automovelDiagnostico = modelMapper.map(dto, AutomovelDiagnostico.class);
        automovelDiagnosticoDAO.inserir(automovelDiagnostico);
        UriBuilder uri = uriInfo.getBaseUriBuilder();
        uri.path(String.valueOf(automovelDiagnostico.getID_DIAGNOSTICO()));
        return Response.created(uri.build()).entity(automovelDiagnostico).build();
    }
    @GET
    @Path("{ID_AUTOMOVEL}")
    public AutomovelDiagnostico buscar(@PathParam("ID_AUTOMOVEL") Integer ID_AUTOMOVEL) throws SQLException {
        return automovelDiagnosticoDAO.buscarPorId(ID_AUTOMOVEL);
    }

    @GET
    public List<AutomovelDiagnostico> listar() throws SQLException {
        return automovelDiagnosticoDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_DIAGNOSTICO}")
    public Response atualizar(CadastrarDiagnostico dto, @PathParam("ID_DIAGNOSTICO") int ID_DIAGNOSTICO) throws SQLException {
        AutomovelDiagnostico automovelDiagnostico = modelMapper.map(dto, AutomovelDiagnostico.class);
        automovelDiagnostico.setID_DIAGNOSTICO(ID_DIAGNOSTICO);
        String resultado =  automovelDiagnosticoDAO.alterar(automovelDiagnostico);
        if (resultado.contains("sucesso")) {
            return Response.ok().entity(modelMapper.map(automovelDiagnostico, AutomovelDiagnostico.class)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(resultado).build();
        }    }

    @DELETE
    @Path("{ID_DIAGNOSTICO}")
    public void deletar(@PathParam("ID_DIAGNOSTICO") int ID_DIAGNOSTICO) throws SQLException {
        automovelDiagnosticoDAO.deletar(ID_DIAGNOSTICO);
    }
}
