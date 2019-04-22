package br.com.gilbertopapa.webservice.model.dao;

import br.com.gilbertopapa.webservice.model.domain.Produto;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoDAO {
    public List<Produto> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = null;


        try {
            produtos = em.createQuery("Select p from Produto p", Produto.class).getResultList();

        } finally {
            em.close();

        }

        return produtos;
    }


    public Produto getById(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produto = null;


        try {
            produto = em.find(Produto.class, id);


        } finally {
            em.close();
        }

        return produto;
    }


    public Produto save(Produto produto) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();

        } finally {
            em.close();
        }

        return produto;
    }


    public Produto update(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produtoManaged = null;

        try {
            em.getTransaction().begin();
            produtoManaged = em.find(Produto.class, produto.getId());
            produtoManaged.setNome(produto.getNome());
            produtoManaged.setQuantidade(produto.getQuantidade());
            em.merge(produtoManaged);

            em.getTransaction().commit();
        } finally {


            em.close();

        }


        return produtoManaged;
    }


    public Produto delete(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produto = null;


        try {

            em.getTransaction().begin();
            produto = em.find(Produto.class, id);
            em.remove(produto);
            em.getTransaction().commit();

        } finally {

            em.close();
        }


        return produto;
    }
}
