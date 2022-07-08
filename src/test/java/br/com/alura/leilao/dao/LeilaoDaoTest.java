package br.com.alura.leilao.dao;

import br.com.alura.leilao.dao.util.JPAUtil;
import br.com.alura.leilao.dao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.dao.util.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LeilaoDaoTest {

    private LeilaoDao leilaoDao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach(){
        this.em = JPAUtil.getEntityManager();
        this.leilaoDao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach(){
        em.getTransaction().rollback();
    }

    @Test
    void testInsereUmLeilao(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("Solala@gmail.com")
                .comSenha("12345")
                .criar();

        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Mochila")
                .comValorInicial("500")
                .comUsuario(usuario)
                .criar();

        leilao = leilaoDao.salvar(leilao);

        Leilao salvo = leilaoDao.buscarPorId(leilao.getId());

        Assert.assertNotNull(salvo);

    }

    @Test
    void testDeveriaAtualizarUsuario(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("Solala@gmail.com")
                .comSenha("12345")
                .criar();

        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Mochila")
                .comValorInicial("500")
                .comUsuario(usuario)
                .criar();

        leilao = leilaoDao.salvar(leilao);

        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("400"));

        Leilao salvo = leilaoDao.buscarPorId(leilao.getId());

        Assert.assertEquals("Celular", salvo.getNome());
        Assert.assertEquals(new BigDecimal("400"), salvo.getValorInicial());

    }

}