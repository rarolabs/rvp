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
 * Model definition for RedeDetalhada.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class RedeDetalhada extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String avatarAdministrador;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String localizacao;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long membroId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Membro> membros;

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
  public RedeDetalhada setAvatarAdministrador(java.lang.String avatarAdministrador) {
    this.avatarAdministrador = avatarAdministrador;
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
  public RedeDetalhada setLocalizacao(java.lang.String localizacao) {
    this.localizacao = localizacao;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMembroId() {
    return membroId;
  }

  /**
   * @param membroId membroId or {@code null} for none
   */
  public RedeDetalhada setMembroId(java.lang.Long membroId) {
    this.membroId = membroId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Membro> getMembros() {
    return membros;
  }

  /**
   * @param membros membros or {@code null} for none
   */
  public RedeDetalhada setMembros(java.util.List<Membro> membros) {
    this.membros = membros;
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
  public RedeDetalhada setNomeAdministrador(java.lang.String nomeAdministrador) {
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
  public RedeDetalhada setNomeRede(java.lang.String nomeRede) {
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
  public RedeDetalhada setQuantidadeMembros(java.lang.Integer quantidadeMembros) {
    this.quantidadeMembros = quantidadeMembros;
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
  public RedeDetalhada setRedeId(java.lang.Long redeId) {
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
  public RedeDetalhada setStatus(java.lang.String status) {
    this.status = status;
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
  public RedeDetalhada setUltimaAtividade(com.google.api.client.util.DateTime ultimaAtividade) {
    this.ultimaAtividade = ultimaAtividade;
    return this;
  }

  @Override
  public RedeDetalhada set(String fieldName, Object value) {
    return (RedeDetalhada) super.set(fieldName, value);
  }

  @Override
  public RedeDetalhada clone() {
    return (RedeDetalhada) super.clone();
  }

}
