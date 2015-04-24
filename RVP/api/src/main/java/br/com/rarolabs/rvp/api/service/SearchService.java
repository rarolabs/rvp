package br.com.rarolabs.rvp.api.service;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.GetIndexesRequest;
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import com.google.appengine.api.search.StatusCode;
import com.google.appengine.api.search.GetResponse;
import com.google.apphosting.api.DatastorePb;
import com.googlecode.objectify.Objectify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.responders.GeoqueryResponder;
import br.com.rarolabs.rvp.api.util.Utils;

/**
 * Created by rodrigosol on 12/23/14.
 */
public class SearchService {

    private static final String indexName = "RVP_ALPHA_1_2";

    public static void createDocument(Membro membro) {
        Rede r = membro.getRede();
        GeoPoint redePosition = new GeoPoint(r.getLatitude(),r.getLongitude());

        Endereco e = membro.getEndereco();
        GeoPoint enderecoPosition = new GeoPoint(e.getLatitude(),e.getLongitude());


        Document doc = Document.newBuilder()
                .addField(Field.newBuilder().setName("memberId").setText(membro.getId().toString()))
                .addField(Field.newBuilder().setName("memberPosition").setGeoPoint(enderecoPosition))
                .addField(Field.newBuilder().setName("idRede").setText(r.getId().toString()))
                .build();

        indexDocument(doc);
    }
    public static void removeDocument(Membro m) {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        Results<ScoredDocument> result = index.search("memberId = " + m.getId().toString());
        for(ScoredDocument doc : result){
            index.delete(doc.getId());
        }

    }

    public static void indexDocument(Document document) {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        try {
            index.put(document);
        } catch (PutException e) {
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<GeoqueryResponder> searchByPosition(Double latitude, Double logitude, Double distance){

        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        // Build the SortOptions with 2 sort keys
        SortOptions sortOptions = SortOptions.newBuilder()
                .addSortExpression(SortExpression.newBuilder()
                        .setExpression("memberPosition")
                        .setDirection(SortExpression.SortDirection.ASCENDING)
                        .setDefaultValueNumeric(0))
                .setLimit(1000)
                .build();

        // Build the QueryOptions
        QueryOptions options = QueryOptions.newBuilder()
                .setLimit(10)
                .setSortOptions(sortOptions)
                .build();
        System.out.println("Buscando por:" + latitude + "," + logitude);

        String queryString = "distance(memberPosition, geopoint("+ latitude +", "+ logitude +")) < " + distance;
        Results<ScoredDocument> results = index.search(queryString);
        Map<Long,Double> redes = new HashMap<Long,Double>();

        System.out.println("Documentos Encontrados:" + results.getNumberFound());

        for(ScoredDocument doc : results){

            GeoqueryResponder geo = new GeoqueryResponder();
            Long id = Long.valueOf(doc.getOnlyField("idRede").getText());

            Double dist = Utils.distance(latitude,logitude,
                    doc.getOnlyField("memberPosition").getGeoPoint(),0.0,0.0);


            if(!redes.containsKey(id)){
                redes.put(id,dist);
            }else {
                if(redes.get(id) > dist) {
                    redes.put(id,dist);
                }
            }
        }


        return parseToGeoqueryResponders(redes);

    }

    private static List<GeoqueryResponder> parseToGeoqueryResponders(Map<Long, Double> redes) {
        List<GeoqueryResponder> results = new ArrayList<GeoqueryResponder>(redes.size());
        for(Long id : redes.keySet()){

            GeoqueryResponder geo = new GeoqueryResponder();

            Objectify ofy = OfyService.ofy();
            Rede rede = ofy.load().type(Rede.class).id(id).now();
            if(rede!=null) {

                System.out.println(rede);
                geo.setIdRede(id);
                geo.setDistance(redes.get(id));
                System.out.println("REDE:" + rede.getDono());
                geo.setNomeRede(rede.getNome());
                geo.setLocalizacao(rede.getLocalizacao());
                geo.setLatitude(rede.getLatitude());
                geo.setLongitude(rede.getLongitude());
                geo.setCoordinators(rede.getMembros());
                geo.setNomeAdministrador(rede.getDono().getUsuario().getNome());
                geo.setAvatarAdministrador(rede.getDono().getUsuario().getAvatar());
                geo.setQuantidadeMembros(rede.getMembros().size());
                geo.setUltimaAtividade(new Date());
                //geo.setMembroId(Long.valueOf(100));
                geo.setMembroId(rede.getDono().getId());

                results.add(geo);
            }

        }
        return results;
    }

    public static void cleanIndex(){
        try {
            // looping because getRange by default returns up to 100 documents at a time
            while (true) {
                List<String> docIds = new ArrayList<String>();
                // Return a set of doc_ids.

                GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();

                IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
                Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

                GetResponse<Document> response = index.getRange(request);
                if (response.getResults().isEmpty()) {
                    break;
                }
                for (Document doc : response) {
                    docIds.add(doc.getId());
                }
                index.delete(docIds);
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

}
