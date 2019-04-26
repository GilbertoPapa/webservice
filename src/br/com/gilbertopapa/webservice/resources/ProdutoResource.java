package br.com.gilbertopapa.webservice.resources;

import br.com.gilbertopapa.webservice.model.domain.Produto;
import br.com.gilbertopapa.webservice.service.ProdutoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("produtos")
public class ProdutoResource {

    private ProdutoService service = new ProdutoService();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Produto> getProdutos(){
        return service.getProdutos();
    }

    @GET
    @Path("{produtoId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Produto getProduto(@PathParam("produtoId")long id){
        return service.getProduto(id);
    }

    @POST
    public Response save(Produto produto) {
        produto = service.saveProduto(produto);
        return Response.status(Response.Status.CREATED)
                .entity(produto)
                .build();
    }

    @PUT
    @Path("{produtoId}")
    public void update(@PathParam("produtoId") long id, Produto produto) {
        produto.setId(id);
        service.updateProduto(produto);
    }

    @DELETE
    @Path("{produtoId}")
    public void delete(@PathParam("produtoId") long id) {
        service.deleteProduto(id);
    }

}
