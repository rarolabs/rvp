/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-11-17 18:43:33 UTC)
 * on 2014-12-29 at 17:00:53 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI;

/**
 * Service definition for RvpAPI (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link RvpAPIRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class RvpAPI extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.19.0 of the rvpAPI library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "rvpAPI/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public RvpAPI(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  RvpAPI(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "apagarRede".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link ApagarRede#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public ApagarRede apagarRede(java.lang.Long id) throws java.io.IOException {
    ApagarRede result = new ApagarRede(id);
    initialize(result);
    return result;
  }

  public class ApagarRede extends RvpAPIRequest<Void> {

    private static final String REST_PATH = "apagarRede/{id}";

    /**
     * Create a request for the method "apagarRede".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link ApagarRede#execute()} method to invoke the remote operation. <p>
     * {@link
     * ApagarRede#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected ApagarRede(java.lang.Long id) {
      super(RvpAPI.this, "POST", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public ApagarRede setAlt(java.lang.String alt) {
      return (ApagarRede) super.setAlt(alt);
    }

    @Override
    public ApagarRede setFields(java.lang.String fields) {
      return (ApagarRede) super.setFields(fields);
    }

    @Override
    public ApagarRede setKey(java.lang.String key) {
      return (ApagarRede) super.setKey(key);
    }

    @Override
    public ApagarRede setOauthToken(java.lang.String oauthToken) {
      return (ApagarRede) super.setOauthToken(oauthToken);
    }

    @Override
    public ApagarRede setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ApagarRede) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ApagarRede setQuotaUser(java.lang.String quotaUser) {
      return (ApagarRede) super.setQuotaUser(quotaUser);
    }

    @Override
    public ApagarRede setUserIp(java.lang.String userIp) {
      return (ApagarRede) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public ApagarRede setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public ApagarRede set(String parameterName, Object value) {
      return (ApagarRede) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "buscarRede".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link BuscarRede#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public BuscarRede buscarRede(java.lang.Long id) throws java.io.IOException {
    BuscarRede result = new BuscarRede(id);
    initialize(result);
    return result;
  }

  public class BuscarRede extends RvpAPIRequest<br.com.rarolabs.rvp.api.rvpAPI.model.Rede> {

    private static final String REST_PATH = "buscarRede/{id}";

    /**
     * Create a request for the method "buscarRede".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link BuscarRede#execute()} method to invoke the remote operation. <p>
     * {@link
     * BuscarRede#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected BuscarRede(java.lang.Long id) {
      super(RvpAPI.this, "POST", REST_PATH, null, br.com.rarolabs.rvp.api.rvpAPI.model.Rede.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public BuscarRede setAlt(java.lang.String alt) {
      return (BuscarRede) super.setAlt(alt);
    }

    @Override
    public BuscarRede setFields(java.lang.String fields) {
      return (BuscarRede) super.setFields(fields);
    }

    @Override
    public BuscarRede setKey(java.lang.String key) {
      return (BuscarRede) super.setKey(key);
    }

    @Override
    public BuscarRede setOauthToken(java.lang.String oauthToken) {
      return (BuscarRede) super.setOauthToken(oauthToken);
    }

    @Override
    public BuscarRede setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (BuscarRede) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public BuscarRede setQuotaUser(java.lang.String quotaUser) {
      return (BuscarRede) super.setQuotaUser(quotaUser);
    }

    @Override
    public BuscarRede setUserIp(java.lang.String userIp) {
      return (BuscarRede) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public BuscarRede setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public BuscarRede set(String parameterName, Object value) {
      return (BuscarRede) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "buscarRedesProximas".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link BuscarRedesProximas#execute()} method to invoke the remote operation.
   *
   * @param latitude
   * @param longitude
   * @param distancia
   * @return the request
   */
  public BuscarRedesProximas buscarRedesProximas(java.lang.Double latitude, java.lang.Double longitude, java.lang.Double distancia) throws java.io.IOException {
    BuscarRedesProximas result = new BuscarRedesProximas(latitude, longitude, distancia);
    initialize(result);
    return result;
  }

  public class BuscarRedesProximas extends RvpAPIRequest<br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection> {

    private static final String REST_PATH = "buscarRedesProximas/{latitude}/{longitude}/{distancia}";

    /**
     * Create a request for the method "buscarRedesProximas".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link BuscarRedesProximas#execute()} method to invoke the remote
     * operation. <p> {@link BuscarRedesProximas#initialize(com.google.api.client.googleapis.services.
     * AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param latitude
     * @param longitude
     * @param distancia
     * @since 1.13
     */
    protected BuscarRedesProximas(java.lang.Double latitude, java.lang.Double longitude, java.lang.Double distancia) {
      super(RvpAPI.this, "POST", REST_PATH, null, br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection.class);
      this.latitude = com.google.api.client.util.Preconditions.checkNotNull(latitude, "Required parameter latitude must be specified.");
      this.longitude = com.google.api.client.util.Preconditions.checkNotNull(longitude, "Required parameter longitude must be specified.");
      this.distancia = com.google.api.client.util.Preconditions.checkNotNull(distancia, "Required parameter distancia must be specified.");
    }

    @Override
    public BuscarRedesProximas setAlt(java.lang.String alt) {
      return (BuscarRedesProximas) super.setAlt(alt);
    }

    @Override
    public BuscarRedesProximas setFields(java.lang.String fields) {
      return (BuscarRedesProximas) super.setFields(fields);
    }

    @Override
    public BuscarRedesProximas setKey(java.lang.String key) {
      return (BuscarRedesProximas) super.setKey(key);
    }

    @Override
    public BuscarRedesProximas setOauthToken(java.lang.String oauthToken) {
      return (BuscarRedesProximas) super.setOauthToken(oauthToken);
    }

    @Override
    public BuscarRedesProximas setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (BuscarRedesProximas) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public BuscarRedesProximas setQuotaUser(java.lang.String quotaUser) {
      return (BuscarRedesProximas) super.setQuotaUser(quotaUser);
    }

    @Override
    public BuscarRedesProximas setUserIp(java.lang.String userIp) {
      return (BuscarRedesProximas) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Double latitude;

    /**

     */
    public java.lang.Double getLatitude() {
      return latitude;
    }

    public BuscarRedesProximas setLatitude(java.lang.Double latitude) {
      this.latitude = latitude;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double longitude;

    /**

     */
    public java.lang.Double getLongitude() {
      return longitude;
    }

    public BuscarRedesProximas setLongitude(java.lang.Double longitude) {
      this.longitude = longitude;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double distancia;

    /**

     */
    public java.lang.Double getDistancia() {
      return distancia;
    }

    public BuscarRedesProximas setDistancia(java.lang.Double distancia) {
      this.distancia = distancia;
      return this;
    }

    @Override
    public BuscarRedesProximas set(String parameterName, Object value) {
      return (BuscarRedesProximas) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "buscarUsuario".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link BuscarUsuario#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public BuscarUsuario buscarUsuario(java.lang.Long id) throws java.io.IOException {
    BuscarUsuario result = new BuscarUsuario(id);
    initialize(result);
    return result;
  }

  public class BuscarUsuario extends RvpAPIRequest<br.com.rarolabs.rvp.api.rvpAPI.model.Usuario> {

    private static final String REST_PATH = "buscarUsuario/{id}";

    /**
     * Create a request for the method "buscarUsuario".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link BuscarUsuario#execute()} method to invoke the remote operation. <p>
     * {@link BuscarUsuario#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientR
     * equest)} must be called to initialize this instance immediately after invoking the constructor.
     * </p>
     *
     * @param id
     * @since 1.13
     */
    protected BuscarUsuario(java.lang.Long id) {
      super(RvpAPI.this, "POST", REST_PATH, null, br.com.rarolabs.rvp.api.rvpAPI.model.Usuario.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public BuscarUsuario setAlt(java.lang.String alt) {
      return (BuscarUsuario) super.setAlt(alt);
    }

    @Override
    public BuscarUsuario setFields(java.lang.String fields) {
      return (BuscarUsuario) super.setFields(fields);
    }

    @Override
    public BuscarUsuario setKey(java.lang.String key) {
      return (BuscarUsuario) super.setKey(key);
    }

    @Override
    public BuscarUsuario setOauthToken(java.lang.String oauthToken) {
      return (BuscarUsuario) super.setOauthToken(oauthToken);
    }

    @Override
    public BuscarUsuario setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (BuscarUsuario) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public BuscarUsuario setQuotaUser(java.lang.String quotaUser) {
      return (BuscarUsuario) super.setQuotaUser(quotaUser);
    }

    @Override
    public BuscarUsuario setUserIp(java.lang.String userIp) {
      return (BuscarUsuario) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public BuscarUsuario setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public BuscarUsuario set(String parameterName, Object value) {
      return (BuscarUsuario) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "cleanDataBaseForTesting".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link CleanDataBaseForTesting#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public CleanDataBaseForTesting cleanDataBaseForTesting() throws java.io.IOException {
    CleanDataBaseForTesting result = new CleanDataBaseForTesting();
    initialize(result);
    return result;
  }

  public class CleanDataBaseForTesting extends RvpAPIRequest<Void> {

    private static final String REST_PATH = "cleanDataBaseForTesting";

    /**
     * Create a request for the method "cleanDataBaseForTesting".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link CleanDataBaseForTesting#execute()} method to invoke the remote
     * operation. <p> {@link CleanDataBaseForTesting#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected CleanDataBaseForTesting() {
      super(RvpAPI.this, "POST", REST_PATH, null, Void.class);
    }

    @Override
    public CleanDataBaseForTesting setAlt(java.lang.String alt) {
      return (CleanDataBaseForTesting) super.setAlt(alt);
    }

    @Override
    public CleanDataBaseForTesting setFields(java.lang.String fields) {
      return (CleanDataBaseForTesting) super.setFields(fields);
    }

    @Override
    public CleanDataBaseForTesting setKey(java.lang.String key) {
      return (CleanDataBaseForTesting) super.setKey(key);
    }

    @Override
    public CleanDataBaseForTesting setOauthToken(java.lang.String oauthToken) {
      return (CleanDataBaseForTesting) super.setOauthToken(oauthToken);
    }

    @Override
    public CleanDataBaseForTesting setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CleanDataBaseForTesting) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CleanDataBaseForTesting setQuotaUser(java.lang.String quotaUser) {
      return (CleanDataBaseForTesting) super.setQuotaUser(quotaUser);
    }

    @Override
    public CleanDataBaseForTesting setUserIp(java.lang.String userIp) {
      return (CleanDataBaseForTesting) super.setUserIp(userIp);
    }

    @Override
    public CleanDataBaseForTesting set(String parameterName, Object value) {
      return (CleanDataBaseForTesting) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "novaRede".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link NovaRede#execute()} method to invoke the remote operation.
   *
   * @param content the {@link br.com.rarolabs.rvp.api.rvpAPI.model.Rede}
   * @return the request
   */
  public NovaRede novaRede(br.com.rarolabs.rvp.api.rvpAPI.model.Rede content) throws java.io.IOException {
    NovaRede result = new NovaRede(content);
    initialize(result);
    return result;
  }

  public class NovaRede extends RvpAPIRequest<br.com.rarolabs.rvp.api.rvpAPI.model.Rede> {

    private static final String REST_PATH = "novaRede";

    /**
     * Create a request for the method "novaRede".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link NovaRede#execute()} method to invoke the remote operation. <p>
     * {@link
     * NovaRede#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link br.com.rarolabs.rvp.api.rvpAPI.model.Rede}
     * @since 1.13
     */
    protected NovaRede(br.com.rarolabs.rvp.api.rvpAPI.model.Rede content) {
      super(RvpAPI.this, "POST", REST_PATH, content, br.com.rarolabs.rvp.api.rvpAPI.model.Rede.class);
    }

    @Override
    public NovaRede setAlt(java.lang.String alt) {
      return (NovaRede) super.setAlt(alt);
    }

    @Override
    public NovaRede setFields(java.lang.String fields) {
      return (NovaRede) super.setFields(fields);
    }

    @Override
    public NovaRede setKey(java.lang.String key) {
      return (NovaRede) super.setKey(key);
    }

    @Override
    public NovaRede setOauthToken(java.lang.String oauthToken) {
      return (NovaRede) super.setOauthToken(oauthToken);
    }

    @Override
    public NovaRede setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (NovaRede) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public NovaRede setQuotaUser(java.lang.String quotaUser) {
      return (NovaRede) super.setQuotaUser(quotaUser);
    }

    @Override
    public NovaRede setUserIp(java.lang.String userIp) {
      return (NovaRede) super.setUserIp(userIp);
    }

    @Override
    public NovaRede set(String parameterName, Object value) {
      return (NovaRede) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "novoUsuario".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link NovoUsuario#execute()} method to invoke the remote operation.
   *
   * @param content the {@link br.com.rarolabs.rvp.api.rvpAPI.model.Usuario}
   * @return the request
   */
  public NovoUsuario novoUsuario(br.com.rarolabs.rvp.api.rvpAPI.model.Usuario content) throws java.io.IOException {
    NovoUsuario result = new NovoUsuario(content);
    initialize(result);
    return result;
  }

  public class NovoUsuario extends RvpAPIRequest<br.com.rarolabs.rvp.api.rvpAPI.model.Usuario> {

    private static final String REST_PATH = "novoUsuario";

    /**
     * Create a request for the method "novoUsuario".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link NovoUsuario#execute()} method to invoke the remote operation. <p>
     * {@link
     * NovoUsuario#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link br.com.rarolabs.rvp.api.rvpAPI.model.Usuario}
     * @since 1.13
     */
    protected NovoUsuario(br.com.rarolabs.rvp.api.rvpAPI.model.Usuario content) {
      super(RvpAPI.this, "POST", REST_PATH, content, br.com.rarolabs.rvp.api.rvpAPI.model.Usuario.class);
    }

    @Override
    public NovoUsuario setAlt(java.lang.String alt) {
      return (NovoUsuario) super.setAlt(alt);
    }

    @Override
    public NovoUsuario setFields(java.lang.String fields) {
      return (NovoUsuario) super.setFields(fields);
    }

    @Override
    public NovoUsuario setKey(java.lang.String key) {
      return (NovoUsuario) super.setKey(key);
    }

    @Override
    public NovoUsuario setOauthToken(java.lang.String oauthToken) {
      return (NovoUsuario) super.setOauthToken(oauthToken);
    }

    @Override
    public NovoUsuario setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (NovoUsuario) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public NovoUsuario setQuotaUser(java.lang.String quotaUser) {
      return (NovoUsuario) super.setQuotaUser(quotaUser);
    }

    @Override
    public NovoUsuario setUserIp(java.lang.String userIp) {
      return (NovoUsuario) super.setUserIp(userIp);
    }

    @Override
    public NovoUsuario set(String parameterName, Object value) {
      return (NovoUsuario) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removerUsuario".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link RemoverUsuario#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoverUsuario removerUsuario(java.lang.Long id) throws java.io.IOException {
    RemoverUsuario result = new RemoverUsuario(id);
    initialize(result);
    return result;
  }

  public class RemoverUsuario extends RvpAPIRequest<Void> {

    private static final String REST_PATH = "rusuario/{id}";

    /**
     * Create a request for the method "removerUsuario".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link RemoverUsuario#execute()} method to invoke the remote operation.
     * <p> {@link RemoverUsuario#initialize(com.google.api.client.googleapis.services.AbstractGoogleCl
     * ientRequest)} must be called to initialize this instance immediately after invoking the
     * constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoverUsuario(java.lang.Long id) {
      super(RvpAPI.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoverUsuario setAlt(java.lang.String alt) {
      return (RemoverUsuario) super.setAlt(alt);
    }

    @Override
    public RemoverUsuario setFields(java.lang.String fields) {
      return (RemoverUsuario) super.setFields(fields);
    }

    @Override
    public RemoverUsuario setKey(java.lang.String key) {
      return (RemoverUsuario) super.setKey(key);
    }

    @Override
    public RemoverUsuario setOauthToken(java.lang.String oauthToken) {
      return (RemoverUsuario) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoverUsuario setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoverUsuario) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoverUsuario setQuotaUser(java.lang.String quotaUser) {
      return (RemoverUsuario) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoverUsuario setUserIp(java.lang.String userIp) {
      return (RemoverUsuario) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoverUsuario setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoverUsuario set(String parameterName, Object value) {
      return (RemoverUsuario) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "solicitarAssociacao".
   *
   * This request holds the parameters needed by the rvpAPI server.  After setting any optional
   * parameters, call the {@link SolicitarAssociacao#execute()} method to invoke the remote operation.
   *
   * @param redeId
   * @param usuarioId
   * @param content the {@link br.com.rarolabs.rvp.api.rvpAPI.model.Endereco}
   * @return the request
   */
  public SolicitarAssociacao solicitarAssociacao(java.lang.Long redeId, java.lang.Long usuarioId, br.com.rarolabs.rvp.api.rvpAPI.model.Endereco content) throws java.io.IOException {
    SolicitarAssociacao result = new SolicitarAssociacao(redeId, usuarioId, content);
    initialize(result);
    return result;
  }

  public class SolicitarAssociacao extends RvpAPIRequest<Void> {

    private static final String REST_PATH = "solicitarAssociacao/{rede_id}/{usuario_id}";

    /**
     * Create a request for the method "solicitarAssociacao".
     *
     * This request holds the parameters needed by the the rvpAPI server.  After setting any optional
     * parameters, call the {@link SolicitarAssociacao#execute()} method to invoke the remote
     * operation. <p> {@link SolicitarAssociacao#initialize(com.google.api.client.googleapis.services.
     * AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param redeId
     * @param usuarioId
     * @param content the {@link br.com.rarolabs.rvp.api.rvpAPI.model.Endereco}
     * @since 1.13
     */
    protected SolicitarAssociacao(java.lang.Long redeId, java.lang.Long usuarioId, br.com.rarolabs.rvp.api.rvpAPI.model.Endereco content) {
      super(RvpAPI.this, "POST", REST_PATH, content, Void.class);
      this.redeId = com.google.api.client.util.Preconditions.checkNotNull(redeId, "Required parameter redeId must be specified.");
      this.usuarioId = com.google.api.client.util.Preconditions.checkNotNull(usuarioId, "Required parameter usuarioId must be specified.");
    }

    @Override
    public SolicitarAssociacao setAlt(java.lang.String alt) {
      return (SolicitarAssociacao) super.setAlt(alt);
    }

    @Override
    public SolicitarAssociacao setFields(java.lang.String fields) {
      return (SolicitarAssociacao) super.setFields(fields);
    }

    @Override
    public SolicitarAssociacao setKey(java.lang.String key) {
      return (SolicitarAssociacao) super.setKey(key);
    }

    @Override
    public SolicitarAssociacao setOauthToken(java.lang.String oauthToken) {
      return (SolicitarAssociacao) super.setOauthToken(oauthToken);
    }

    @Override
    public SolicitarAssociacao setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SolicitarAssociacao) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SolicitarAssociacao setQuotaUser(java.lang.String quotaUser) {
      return (SolicitarAssociacao) super.setQuotaUser(quotaUser);
    }

    @Override
    public SolicitarAssociacao setUserIp(java.lang.String userIp) {
      return (SolicitarAssociacao) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key("rede_id")
    private java.lang.Long redeId;

    /**

     */
    public java.lang.Long getRedeId() {
      return redeId;
    }

    public SolicitarAssociacao setRedeId(java.lang.Long redeId) {
      this.redeId = redeId;
      return this;
    }

    @com.google.api.client.util.Key("usuario_id")
    private java.lang.Long usuarioId;

    /**

     */
    public java.lang.Long getUsuarioId() {
      return usuarioId;
    }

    public SolicitarAssociacao setUsuarioId(java.lang.Long usuarioId) {
      this.usuarioId = usuarioId;
      return this;
    }

    @Override
    public SolicitarAssociacao set(String parameterName, Object value) {
      return (SolicitarAssociacao) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link RvpAPI}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link RvpAPI}. */
    @Override
    public RvpAPI build() {
      return new RvpAPI(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link RvpAPIRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setRvpAPIRequestInitializer(
        RvpAPIRequestInitializer rvpapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(rvpapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}