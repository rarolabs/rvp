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
 * on 2014-12-31 at 20:50:33 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI.model;

/**
 * Model definition for Usuario.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Usuario extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dddTelefoneCelular;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dddTelefoneFixo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String telefoneCelular;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String telefoneFixo;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDddTelefoneCelular() {
    return dddTelefoneCelular;
  }

  /**
   * @param dddTelefoneCelular dddTelefoneCelular or {@code null} for none
   */
  public Usuario setDddTelefoneCelular(java.lang.String dddTelefoneCelular) {
    this.dddTelefoneCelular = dddTelefoneCelular;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDddTelefoneFixo() {
    return dddTelefoneFixo;
  }

  /**
   * @param dddTelefoneFixo dddTelefoneFixo or {@code null} for none
   */
  public Usuario setDddTelefoneFixo(java.lang.String dddTelefoneFixo) {
    this.dddTelefoneFixo = dddTelefoneFixo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public Usuario setEmail(java.lang.String email) {
    this.email = email;
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
  public Usuario setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNome() {
    return nome;
  }

  /**
   * @param nome nome or {@code null} for none
   */
  public Usuario setNome(java.lang.String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTelefoneCelular() {
    return telefoneCelular;
  }

  /**
   * @param telefoneCelular telefoneCelular or {@code null} for none
   */
  public Usuario setTelefoneCelular(java.lang.String telefoneCelular) {
    this.telefoneCelular = telefoneCelular;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTelefoneFixo() {
    return telefoneFixo;
  }

  /**
   * @param telefoneFixo telefoneFixo or {@code null} for none
   */
  public Usuario setTelefoneFixo(java.lang.String telefoneFixo) {
    this.telefoneFixo = telefoneFixo;
    return this;
  }

  @Override
  public Usuario set(String fieldName, Object value) {
    return (Usuario) super.set(fieldName, value);
  }

  @Override
  public Usuario clone() {
    return (Usuario) super.clone();
  }

}
