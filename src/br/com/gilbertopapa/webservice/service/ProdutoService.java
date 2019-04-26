package br.com.gilbertopapa.webservice.service;

import br.com.gilbertopapa.webservice.model.dao.ProdutoDAO;
import br.com.gilbertopapa.webservice.model.domain.Produto;

import java.util.List;

public class ProdutoService {
     private ProdutoDAO produtoDAO = new ProdutoDAO();

     public List<Produto> getProdutos (){
         return produtoDAO.getAll();
     }

     public Produto getProduto (long id){
         return produtoDAO.getById(id);
     }

     public Produto saveProduto(Produto produto){
         return produtoDAO.save(produto);
     }

     public Produto updateProduto(Produto produto){
         return produtoDAO.update(produto);

     }

     public Produto deleteProduto(long id){
         return produtoDAO.delete(id);
     }

     public List<Produto> getProdutosByPagination(int fistResult, int maxResult){
         return produtoDAO.getByPagination(fistResult,maxResult);
     }

     public List<Produto> getProdutoByName(String name){
         return produtoDAO.getByName(name);
     }


}
