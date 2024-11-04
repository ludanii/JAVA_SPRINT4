package br.com.fiap.resource;

import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.RelacionamentoEnderecoDAO;
import br.com.fiap.dto.Endereco.RelacionamentoEndereco;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

    @Path("relacionamento_endereco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RelacionamentoEnderecoResorce {
    private RelacionamentoEnderecoDAO relacionamentoEnderecoDAO;
    private ModelMapper modelMapper;
    public RelacionamentoEnderecoResorce() throws SQLException {
        this.relacionamentoEnderecoDAO = new RelacionamentoEnderecoDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response CadastrarRelacionamento(RelacionamentoEndereco relacionamentoEndereco, @Context UriInfo uriInfo ) throws SQLException {
        relacionamentoEnderecoDAO.associarEnderecoAoUsuario(relacionamentoEndereco);
        return Response.ok().entity(relacionamentoEndereco).build();
    }
    @GET
    @Path("{ID_USUARIO}")
    public RelacionamentoEndereco buscar(@PathParam("ID_USUARIO") Integer ID_USUARIO) throws SQLException {
        return relacionamentoEnderecoDAO.buscarPorId(ID_USUARIO);
    }

    @DELETE
    @Path("{ID_ENDERECO}")
    public void deletar(@PathParam("ID_ENDERECO") int ID_ENDERECO) throws SQLException {
        relacionamentoEnderecoDAO.deletar(ID_ENDERECO);
    }
}
