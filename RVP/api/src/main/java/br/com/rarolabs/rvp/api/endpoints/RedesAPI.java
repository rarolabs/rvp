package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;


import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.responders.GeoqueryResponder;
import br.com.rarolabs.rvp.api.service.SearchService;


@Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class RedesAPI {
    /**
     * Cria uma nova rede de vizinhos protegidos
     * @param nome Nome da Rede
     * @param usuarioId ID usuário criador da rede
     * @param endereco Endereco do usuário criador da rede
     * @return A rede criada
     * @throws ConflictException Em caso de já existir uma rede com o mesmo nome
     */
    @ApiMethod(name ="novaRede")
    public Rede novaRede(
            @Named("nome") String nome,
            @Named("usuario_id") Long usuarioId,
            Endereco endereco) throws ConflictException {

        return Rede.novaRede(nome,usuarioId,endereco);
    }

    /**
     * Busca uma rede pelo seu ID
     * @param id ID da Rede
     * @return Rede encontrada
     * @throws ConflictException
     * @throws NotFoundException Caso a rede não seja encontrada
     */
    @ApiMethod(name ="buscarRede")
    public  Rede buscarRede(@Named("id") Long id) throws ConflictException, NotFoundException {
        return Rede.buscar(id);
    }

    /**
     * Apaga uma rede
     * @param id ID da rede a ser apagada
     */
    @ApiMethod(name ="apagarRede")
    public void apagarRede(@Named("id") Long id){
        Rede.apagar(id);
    }

    /**
     * Busca redes próximas do usuário
     * @param latitude Latitude de origem da busca
     * @param longitude Longitude de origim da busca
     * @param distancia Raio de busca em metros
     * @return Lista de redes encontradas
     */
    @ApiMethod(name = "buscarRedesProximas")
    public List<GeoqueryResponder> buscarRedesProximas(@Named("latitude") Double latitude,
                                                       @Named("longitude") Double longitude,
                                                       @Named("distancia") Double distancia){

        return SearchService.searchByPosition(latitude, longitude, distancia);

    }

    /**
     * Busca todas as rede que o usuário faz parte
     * @param usuarioId ID do Usuário
     * @return Coleção das redes do usuário
     */
    @ApiMethod(name = "minhasRedes")
    public Collection<Membro> minhasRedes(@Named("usuario_id") Long usuarioId){
        return Usuario.minhasRedes(usuarioId);
    }

    /**
     * Retorna as solicitações de adesão de novos usuário que estão pendentes de
     * ação dos administradores
     * @param redeId ID da Rede
     * @return Retorna os membros que estão pendentes
     */
    @ApiMethod(name = "solicitacoesPendentes")
    public Collection<Membro> solicitacoesPendentes(@Named("rede_id") Long redeId){
        return Rede.solicitacoesPendentes(redeId);
    }

    /**
     * busca o criado da rede
     * @param redeID ID da Rede
     * @return Criado da rede
     * @throws NotFoundException Caso a rede não seja encontrada
     */
    @ApiMethod(name = "buscarDono")
    public Membro buscarDono(@Named("rede_id") Long redeID) throws NotFoundException {
        return Rede.buscar(redeID).getDono();
    }

    /**
     * busca todos os membros da rede
     * @param redeID ID da Rede
     * @return Membros
     * @throws NotFoundException Caso a rede não seja encontrada
     */
    @ApiMethod(name = "buscarMembros")
    public Collection<Membro> buscarMembros(@Named("rede_id") Long redeID) throws NotFoundException {
        return Rede.buscar(redeID).getMembros();
    }

    /**
     * busca todos os membros ativos da rede
     * @param redeID ID da Rede
     * @return Membros
     * @throws NotFoundException Caso a rede não seja encontrada
     */
    @ApiMethod(name = "buscarMembrosAtivos")
    public Collection<Membro> buscarMembrosAtios(@Named("rede_id") Long redeID) throws NotFoundException {
        return Rede.buscar(redeID).membrosAtivos();
    }

}
