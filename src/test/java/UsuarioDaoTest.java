import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach(){
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();

    }

    @AfterEach
    public void afterEach(){
        em.getTransaction().rollback(); //desfaz apos o final de cada teste
    }

    @Test
    void deveriaEncontrarUsuarioCadastrado(){
            Usuario usuario = criarUsuario();

        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(usuario);
    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastrado(){
        criarUsuario();
        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));

    }

    private Usuario criarUsuario(){
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "123456");
        em.persist(usuario);
        return  usuario;
    }

}
