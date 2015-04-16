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
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-04-15 at 20:39:13 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI.model;

/**
 * Model definition for Membro.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Membro extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime dataAssociacao;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long enderecoId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double latitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double longitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nomeRede;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String papel;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long redeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String usuarioId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String visibilidadeEmail;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String visibilidadeEndereco;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String visibilidadeTelefoneCelular;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String visibilidadeTelefoneFixo;

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getDataAssociacao() {
    return dataAssociacao;
  }

  /**
   * @param dataAssociacao dataAssociacao or {@code null} for none
   */
  public Membro setDataAssociacao(com.google.api.client.util.DateTime dataAssociacao) {
    this.dataAssociacao = dataAssociacao;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getEnderecoId() {
    return enderecoId;
  }

  /**
   * @param enderecoId enderecoId or {@code null} for none
   */
  public Membro setEnderecoId(java.lang.Long enderecoId) {
    this.enderecoId = enderecoId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Membro setId(java.lang.Long id) {
    this.id = id;
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
  public Membro setLatitude(java.lang.Double latitude) {
    this.latitude = latitude;
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
  public Membro setLongitude(java.lang.Double longitude) {
    this.longitude = longitude;
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
  public Membro setNomeRede(java.lang.String nomeRede) {
    this.nomeRede = nomeRede;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPapel() {
    return papel;
  }

  /**
   * @param papel papel or {@code null} for none
   */
  public Membro setPapel(java.lang.String papel) {
    this.papel = papel;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getRedeId() {
    return redeId;
  }

  /**
   * @param redeId redeId or {@code null} for none
   */
  public Membro setRedeId(java.lang.Long redeId) {
    this.redeId = redeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStatus() {
    return status;
  }

  /**
   * @param status status or {@code null} for none
   */
  public Membro setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUsuarioId() {
    return usuarioId;
  }

  /**
   * @param usuarioId usuarioId or {@code null} for none
   */
  public Membro setUsuarioId(java.lang.String usuarioId) {
    this.usuarioId = usuarioId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getVisibilidadeEmail() {
    return visibilidadeEmail;
  }

  /**
   * @param visibilidadeEmail visibilidadeEmail or {@code null} for none
   */
  public Membro setVisibilidadeEmail(java.lang.String visibilidadeEmail) {
    this.visibilidadeEmail = visibilidadeEmail;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getVisibilidadeEndereco() {
    return visibilidadeEndereco;
  }

  /**
   * @param visibilidadeEndereco visibilidadeEndereco or {@code null} for none
   */
  public Membro setVisibilidadeEndereco(java.lang.String visibilidadeEndereco) {
    this.visibilidadeEndereco = visibilidadeEndereco;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getVisibilidadeTelefoneCelular() {
    return visibilidadeTelefoneCelular;
  }

  /**
   * @param visibilidadeTelefoneCelular visibilidadeTelefoneCelular or {@code null} for none
   */
  public Membro setVisibilidadeTelefoneCelular(java.lang.String visibilidadeTelefoneCelular) {
    this.visibilidadeTelefoneCelular = visibilidadeTelefoneCelular;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getVisibilidadeTelefoneFixo() {
    return visibilidadeTelefoneFixo;
  }

  /**
   * @param visibilidadeTelefoneFixo visibilidadeTelefoneFixo or {@code null} for none
   */
  public Membro setVisibilidadeTelefoneFixo(java.lang.String visibilidadeTelefoneFixo) {
    this.visibilidadeTelefoneFixo = visibilidadeTelefoneFixo;
    return this;
  }

  @Override
  public Membro set(String fieldName, Object value) {
    return (Membro) super.set(fieldName, value);
  }

  @Override
  public Membro clone() {
    return (Membro) super.clone();
  }

}
