package br.com.rarolabs.rvp.api.test;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.test.fixtures.EnderecoFixture;
import br.com.rarolabs.rvp.api.test.fixtures.RedeFixture;
import br.com.rarolabs.rvp.api.test.fixtures.SolicitacaoFixture;
import br.com.rarolabs.rvp.api.test.fixtures.UsuarioFixture;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class ManterMembroTest {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void aprovarSolicitacao() throws ConflictException, NotFoundException, ForbiddenException {
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        Membro.aprovarAssociacao(m.getId());
        assertEquals(m.getStatus(), Membro.Status.ATIVO);
        assertEquals(m.getPapel(), Membro.Papel.VIVIZINHO);

    }

    @Test
    public void tornarAdministrador() throws ConflictException, NotFoundException, ForbiddenException {
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        Membro.tornarAdministrador(m.getId());
        assertEquals(m.getStatus(), Membro.Status.ATIVO);
        assertEquals(m.getPapel(), Membro.Papel.ADMIN);

    }

    @Test
    public void retirarPermissaoAdministrador() throws ConflictException, NotFoundException, ForbiddenException {
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        Membro.retirarPermissaoAdministrador(m.getId());
        assertEquals(m.getStatus(), Membro.Status.ATIVO);
        assertEquals(m.getPapel(), Membro.Papel.VIVIZINHO);

    }

    @Test
    public void tornarAutoriadade() throws ConflictException, NotFoundException, ForbiddenException {
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        Membro.tornarAutoridade(m.getId());
        assertEquals(m.getStatus(), Membro.Status.ATIVO);
        assertEquals(m.getPapel(), Membro.Papel.AUTORIDADE);

    }

    @Test
    public void retirarPermissaoAutoriadade() throws ConflictException, NotFoundException, ForbiddenException {
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        Membro.retirarPermissaoAutoridade(m.getId());
        assertEquals(m.getStatus(), Membro.Status.ATIVO);
        assertEquals(m.getPapel(), Membro.Papel.VIVIZINHO);

    }

    @Test
    public void inativarVizinho() throws ConflictException, NotFoundException, ForbiddenException {
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        Membro.inativarVizinho(m.getId());
        assertEquals(m.getStatus(), Membro.Status.INATIVO);
        assertEquals(m.getPapel(), Membro.Papel.VIVIZINHO);

    }

    @Test
    public void ativarVizinho() throws ConflictException, NotFoundException, ForbiddenException {
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        Membro.ativarVizinho(m.getId());
        assertEquals(m.getStatus(), Membro.Status.ATIVO);
        assertEquals(m.getPapel(), Membro.Papel.VIVIZINHO);

    }

}
