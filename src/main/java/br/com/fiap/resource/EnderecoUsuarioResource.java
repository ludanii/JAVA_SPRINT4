package br.com.fiap.resource;

import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.EnderecoUsuarioDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.dto.Endereco.CadastrarEndereco;
import br.com.fiap.dto.Endereco.EnderecoUsuario;
import br.com.fiap.dto.Usuario.AtualizarUsuario;
import br.com.fiap.dto.Usuario.CadastroUsuario;
import br.com.fiap.dto.Usuario.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("endereco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoUsuarioResource {

    private EnderecoUsuarioDAO enderecoUsuarioDAO;
    private ModelMapper modelMapper;
    public EnderecoUsuarioResource() throws SQLException {
        this.enderecoUsuarioDAO = new EnderecoUsuarioDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }
    @POST
    public Response cadastrarEndereco(CadastrarEndereco dto, @Context UriInfo uriInfo) throws SQLException {
        EnderecoUsuario enderecoUsuario = modelMapper.map(dto, EnderecoUsuario.class);
        enderecoUsuarioDAO.inserir(enderecoUsuario);
        UriBuilder uri = uriInfo.getBaseUriBuilder();
        uri.path(String.valueOf(enderecoUsuario.getID_ENDERECO()));
        return Response.created(uri.build()).entity(enderecoUsuario).build();
    }
    @GET
    @Path("{NR_CEP}")
    public EnderecoUsuario buscar(@PathParam("NR_CEP") String NR_CEP) throws SQLException {
        return enderecoUsuarioDAO.buscarPorCEP(NR_CEP);
    }

    @GET
    public List<EnderecoUsuario> listar() throws SQLException {
        return enderecoUsuarioDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_ENDERECO}")
    public Response atualizar(CadastrarEndereco dto, @PathParam("ID_ENDERECO") int ID_ENDERECO) throws SQLException {
        EnderecoUsuario enderecoUsuario = modelMapper.map(dto, EnderecoUsuario.class);
        enderecoUsuario.setID_ENDERECO(ID_ENDERECO);
        String resultado =  enderecoUsuarioDAO.alterar(enderecoUsuario);
        if (resultado.contains("sucesso")) {
            return Response.ok().entity(modelMapper.map(enderecoUsuario, EnderecoUsuario.class)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(resultado).build();
        }    }

    @DELETE
    @Path("{ID_ENDERECO}")
    public void deletar(@PathParam("ID_ENDERECO") int ID_ENDERECO) throws SQLException {
        enderecoUsuarioDAO.deletar(ID_ENDERECO);
    }
}
