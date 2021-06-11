import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoDaoTeste {

    private LeilaoDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach(){
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();

    }

    @AfterEach
    public void afterEach(){
        em.getTransaction().rollback(); //desfaz apos o final de cada teste
    }

   @Test
   void deveriaCadastrarUmLeilao(){
        Usuario usuario = criarUsuario();
       Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);

       leilao = dao.salvar(leilao);

       Leilao salvo = dao.buscarPorId(leilao.getId());
       Assert.assertNotNull(salvo);
   }

    @Test
    void deveriaAtualizarUmLeilao(){
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);
        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("400"));

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());
        Assert.assertEquals("Celular", salvo.getNome());
        Assert.assertEquals(new BigDecimal("400"), salvo.getValorInicial());
    }



    private Usuario criarUsuario(){
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "123456");
        em.persist(usuario);
        return  usuario;
    }

}
