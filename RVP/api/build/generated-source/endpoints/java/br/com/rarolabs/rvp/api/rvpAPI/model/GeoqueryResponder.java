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
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-03-05 at 00:14:09 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI.model;

/**
 * Model definition for GeoqueryResponder.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class GeoqueryResponder extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String avatarAdministrador;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Coordinator> coordinators;

  static {
    // hack to force ProGuard to consider Coordinator used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Coordinator.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double distance;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long idRede;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double latitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String localizacao;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double longitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nomeAdministrador;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nomeRede;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer quantidadeMembros;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer resultSize;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime ultimaAtividade;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAvatarAdministrador() {
    return avatarAdministrador;
  }

  /**
   * @param avatarAdministrador avatarAdministrador or {@code null} for none
   */
  public GeoqueryResponder setAvatarAdministrador(java.lang.String avatarAdministrador) {
    this.avatarAdministrador = avatarAdministrador;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Coordinator> getCoordinators() {
    return coordinators;
  }

  /**
   * @param coordinators coordinators or {@code null} for none
   */
  public GeoqueryResponder setCoordinators(java.util.List<Coordinator> coordinators) {
    this.coordinators = coordinators;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getDistance() {
    return distance;
  }

  /**
   * @param distance distance or {@code null} for none
   */
  public GeoqueryResponder setDistance(java.lang.Double distance) {
    this.distance = distance;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getIdRede() {
    return idRede;
  }

  /**
   * @param idRede idRede or {@code null} for none
   */
  public GeoqueryResponder setIdRede(java.lang.Long idRede) {
    this.idRede = idRede;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLatitude() {
    return latitude;
  }

  /**
   * @param latitude latitude or {@code null} for none
   */
  public GeoqueryResponder setLatitude(java.lang.Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLocalizacao() {
    return localizacao;
  }

  /**
   * @param localizacao localizacao or {@code null} for none
   */
  public GeoqueryResponder setLocalizacao(java.lang.String localizacao) {
    this.localizacao = localizacao;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLongitude() {
    return longitude;
  }

  /**
   * @param longitude longitude or {@code null} for none
   */
  public GeoqueryResponder setLongitude(java.lang.Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNomeAdministrador() {
    return nomeAdministrador;
  }

  /**
   * @param nomeAdministrador nomeAdministrador or {@code null} for none
   */
  public GeoqueryResponder setNomeAdministrador(java.lang.String nomeAdministrador) {
    this.nomeAdministrador = nomeAdministrador;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNomeRede() {
    return nomeRede;
  }

  /**
   * @param nomeRede nomeRede or {@code null} for none
   */
  public GeoqueryResponder setNomeRede(java.lang.String nomeRede) {
    this.nomeRede = nomeRede;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getQuantidadeMembros() {
    return quantidadeMembros;
  }

  /**
   * @param quantidadeMembros quantidadeMembros or {@code null} for none
   */
  public GeoqueryResponder setQuantidadeMembros(java.lang.Integer quantidadeMembros) {
    this.quantidadeMembros = quantidadeMembros;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getResultSize() {
    return resultSize;
  }

  /**
   * @param resultSize resultSize or {@code null} for none
   */
  public GeoqueryResponder setResultSize(java.lang.Integer resultSize) {
    this.resultSize = resultSize;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getUltimaAtividade() {
    return ultimaAtividade;
  }

  /**
   * @param ultimaAtividade ultimaAtividade or {@code null} for none
   */
  public GeoqueryResponder setUltimaAtividade(com.google.api.client.util.DateTime ultimaAtividade) {
    this.ultimaAtividade = ultimaAtividade;
    return this;
  }

  @Override
  public GeoqueryResponder set(String fieldName, Object value) {
    return (GeoqueryResponder) super.set(fieldName, value);
  }

  @Override
  public GeoqueryResponder clone() {
    return (GeoqueryResponder) super.clone();
  }

}
