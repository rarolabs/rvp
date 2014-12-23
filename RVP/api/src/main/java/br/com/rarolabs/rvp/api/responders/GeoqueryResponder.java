package br.com.rarolabs.rvp.api.responders;

import java.util.Collection;
import java.util.List;

import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;

/**
 * Created by rodrigosol on 12/23/14.
 */
public class GeoqueryResponder {

    private Long idRede;
    private Long nomeRede;
    private Double distance;
    private List<Coordinator> coordinators;
    private Integer resultSize;
    private Double latitude;
    private Double longitude;


    public Long getIdRede() {
        return idRede;
    }

    public void setIdRede(Long idRede) {
        this.idRede = idRede;
    }

    public Long getNomeRede() {
        return nomeRede;
    }

    public void setNomeRede(Long nomeRede) {
        this.nomeRede = nomeRede;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<Coordinator> getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(Collection<Membro> membros) {
        for(Membro m : membros){
            coordinators.add(new Coordinator(m.getEndereco().getLatitude(),m.getEndereco().getLongitude()));
        }
    }

    public Integer getResultSize() {
        return resultSize;
    }

    public void setResultSize(Integer resultSize) {
        this.resultSize = resultSize;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
