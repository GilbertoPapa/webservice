package br.com.gilbertopapa.webservice.model.dao;

import br.com.gilbertopapa.webservice.exceptions.DAOExceptions;
import br.com.gilbertopapa.webservice.exceptions.ErrorCode;
import br.com.gilbertopapa.webservice.model.domain.Produto;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoDAO {


    public List<Produto> getByPagination(int fistResult, int maxResult) {

        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = null;


        try {
            produtos = em.createQuery("Select p from Produto p ", Produto.class)
                    .setFirstResult(fistResult)
                    .setMaxResults(maxResult).
                            getResultList();


        } catch (RuntimeException e) {
            throw new DAOExceptions("Erro ao buscar produtos no banco de dados" + e.getMessage(), ErrorCode.SERVER_ERROR.getCode());

        } finally {
            em.close();
        }

        if (produtos.isEmpty()) {

            throw new DAOExceptions("Página com produtos vazia", ErrorCode.NOT_FOUND.getCode());

        }


        return produtos;
    }


    public List<Produto> getByName(String name) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = null;

        try {

            produtos = em.createQuery("Select p from Produto p Where p.nome like :name", Produto.class).setParameter("name", "%" + name + "%").getResultList();


        } catch (RuntimeException e) {

            throw new DAOExceptions("Erro ao buscar produto por nome no banco de dados:" + e.getMessage(), ErrorCode.SERVER_ERROR.getCode());


        } finally {

            em.close();

        }


        if (produtos.isEmpty()) {
            throw new DAOExceptions("A consulta não retornou elementos", ErrorCode.NOT_FOUND.getCode());
        }


        return produtos;
    }


    public List<Produto> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> produtos = null;


        try {
            produtos = em.createQuery("Select p from Produto p", Produto.class).getResultList();

        } catch (RuntimeException e) {

            throw new DAOExceptions("Erro ao recuperar todos os produtos do banco: " + e.getMessage(), ErrorCode.SERVER_ERROR.getCode());

        } finally {
            em.close();

        }

        return produtos;
    }


    public Produto getById(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produto = null;


        if (id <= 0) {
            throw new DAOExceptions("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
        }


        try {
            produto = em.find(Produto.class, id);


        } catch (RuntimeException e) {

            throw new DAOExceptions("Erro ao buscar produto por id no banco de dados: ", ErrorCode.SERVER_ERROR.getCode());

        } finally {
            em.close();
        }


        if (produto == null) {
            throw new DAOExceptions("Produto de id \" + id + \" não existe.", ErrorCode.NOT_FOUND.getCode());
        }

        return produto;
    }


    public Produto save(Produto produto) {

        EntityManager em = JPAUtil.getEntityManager();

        if (!produtoIsValid(produto)) {

            throw new DAOExceptions("Produto com dados incompletos", ErrorCode.BAD_REQUEST.getCode());
        }

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


        if (produto.getId() <= 0) {
            throw new DAOExceptions("O id precisa der maior que 0", ErrorCode.BAD_REQUEST.getCode());

        }

        if (!produtoIsValid(produto)) {

            throw new DAOExceptions("Produto com dados incompletos", ErrorCode.BAD_REQUEST.getCode());
        }

        try {
            em.getTransaction().begin();
            produtoManaged = em.find(Produto.class, produto.getId());
            produtoManaged.setNome(produto.getNome());
            produtoManaged.setQuantidade(produto.getQuantidade());
            em.merge(produtoManaged);

            em.getTransaction().commit();
        } catch (NullPointerException ex) {
            em.getTransaction().rollback();
            throw new DAOExceptions("Produto informado para atualização não existe: " +
                    ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOExceptions("Erro ao atualizar produto no banco de dados: " +
                    ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
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

        } catch (NullPointerException ex) {
            em.getTransaction().rollback();
            throw new DAOExceptions("Produto informado para atualização não existe: " +
                    ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw new DAOExceptions("Erro ao atualizar produto no banco de dados: " +
                    ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
        } finally {

            em.close();
        }

        return produto;
    }

    private boolean produtoIsValid(Produto produto) {
        try {
            if ((produto.getNome().isEmpty()) || (produto.getQuantidade() < 0))
                return false;
        } catch (NullPointerException ex) {
            throw new DAOExceptions("Produto com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
        }

        return true;
    }


}
