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
 * Model definition for Profile.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Profile extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String avatar;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String avatarBlur;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String endereco;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long membroId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String papel;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String papelDoVisualizado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String redeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

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
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String usuarioId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAvatar() {
    return avatar;
  }

  /**
   * @param avatar avatar or {@code null} for none
   */
  public Profile setAvatar(java.lang.String avatar) {
    this.avatar = avatar;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAvatarBlur() {
    return avatarBlur;
  }

  /**
   * @param avatarBlur avatarBlur or {@code null} for none
   */
  public Profile setAvatarBlur(java.lang.String avatarBlur) {
    this.avatarBlur = avatarBlur;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEndereco() {
    return endereco;
  }

  /**
   * @param endereco endereco or {@code null} for none
   */
  public Profile setEndereco(java.lang.String endereco) {
    this.endereco = endereco;
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
  public Profile setMembroId(java.lang.Long membroId) {
    this.membroId = membroId;
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
  public Profile setNome(java.lang.String nome) {
    this.nome = nome;
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
  public Profile setPapel(java.lang.String papel) {
    this.papel = papel;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPapelDoVisualizado() {
    return papelDoVisualizado;
  }

  /**
   * @param papelDoVisualizado papelDoVisualizado or {@code null} for none
   */
  public Profile setPapelDoVisualizado(java.lang.String papelDoVisualizado) {
    this.papelDoVisualizado = papelDoVisualizado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRedeId() {
    return redeId;
  }

  /**
   * @param redeId redeId or {@code null} for none
   */
  public Profile setRedeId(java.lang.String redeId) {
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
  public Profile setStatus(java.lang.String status) {
    this.status = status;
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
  public Profile setTelefoneCelular(java.lang.String telefoneCelular) {
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
  public Profile setTelefoneFixo(java.lang.String telefoneFixo) {
    this.telefoneFixo = telefoneFixo;
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
  public Profile setUsuarioId(java.lang.String usuarioId) {
    this.usuarioId = usuarioId;
    return this;
  }

  @Override
  public Profile set(String fieldName, Object value) {
    return (Profile) super.set(fieldName, value);
  }

  @Override
  public Profile clone() {
    return (Profile) super.clone();
  }

}
