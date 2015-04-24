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
 * on 2015-04-24 at 14:03:35 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI.model;

/**
 * Model definition for Alerta.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Alerta extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime ate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime data;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime de;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String descricao;

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
  private java.lang.Double logitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long membroId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long redeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipo;

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getAte() {
    return ate;
  }

  /**
   * @param ate ate or {@code null} for none
   */
  public Alerta setAte(com.google.api.client.util.DateTime ate) {
    this.ate = ate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getData() {
    return data;
  }

  /**
   * @param data data or {@code null} for none
   */
  public Alerta setData(com.google.api.client.util.DateTime data) {
    this.data = data;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getDe() {
    return de;
  }

  /**
   * @param de de or {@code null} for none
   */
  public Alerta setDe(com.google.api.client.util.DateTime de) {
    this.de = de;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescricao() {
    return descricao;
  }

  /**
   * @param descricao descricao or {@code null} for none
   */
  public Alerta setDescricao(java.lang.String descricao) {
    this.descricao = descricao;
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
  public Alerta setId(java.lang.Long id) {
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
  public Alerta setLatitude(java.lang.Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLogitude() {
    return logitude;
  }

  /**
   * @param logitude logitude or {@code null} for none
   */
  public Alerta setLogitude(java.lang.Double logitude) {
    this.logitude = logitude;
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
  public Alerta setMembroId(java.lang.Long membroId) {
    this.membroId = membroId;
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
  public Alerta setRedeId(java.lang.Long redeId) {
    this.redeId = redeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTipo() {
    return tipo;
  }

  /**
   * @param tipo tipo or {@code null} for none
   */
  public Alerta setTipo(java.lang.String tipo) {
    this.tipo = tipo;
    return this;
  }

  @Override
  public Alerta set(String fieldName, Object value) {
    return (Alerta) super.set(fieldName, value);
  }

  @Override
  public Alerta clone() {
    return (Alerta) super.clone();
  }

}
