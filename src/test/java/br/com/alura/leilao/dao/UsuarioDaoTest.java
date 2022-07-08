package br.com.alura.leilao.dao;

import br.com.alura.leilao.dao.util.JPAUtil;
import br.com.alura.leilao.dao.util.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDaoTest {
    private UsuarioDao usuarioDao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach(){
        this.em = JPAUtil.getEntityManager();
        this.usuarioDao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach(){
        em.getTransaction().rollback();
    }

    @Test
    void testBuscaDeUsuarioPeloUsernameExistente(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("Solala@gmail.com")
                .comSenha("12345")
                .criar();

        em.persist(usuario);

        Usuario encontrado = this.usuarioDao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(encontrado);

    }

    @Test
    void testBuscaDeUsuarioPeloUsernameNaoExistente(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("Solala@gmail.com")
                .comSenha("12345")
                .criar();

        em.persist(usuario);

        Assert.assertThrows(NoResultException.class,
                () -> this.usuarioDao.buscarPorUsername("Neto"));

    }

    @Test
    void testDeleteUsuario(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("Solala@gmail.com")
                .comSenha("12345")
                .criar();

        em.persist(usuario);

        usuarioDao.deletar(usuario);

        Assert.assertThrows(NoResultException.class,
                () -> this.usuarioDao.buscarPorUsername(usuario.getNome()));
    }
}