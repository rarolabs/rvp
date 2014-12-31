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
 * on 2014-12-31 at 19:04:06 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI.model;

/**
 * Model definition for Visibilidade.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Visibilidade extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String endereco;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

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
  public java.lang.String getEndereco() {
    return endereco;
  }

  /**
   * @param endereco endereco or {@code null} for none
   */
  public Visibilidade setEndereco(java.lang.String endereco) {
    this.endereco = endereco;
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
  public Visibilidade setId(java.lang.Long id) {
    this.id = id;
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
  public Visibilidade setTelefoneCelular(java.lang.String telefoneCelular) {
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
  public Visibilidade setTelefoneFixo(java.lang.String telefoneFixo) {
    this.telefoneFixo = telefoneFixo;
    return this;
  }

  @Override
  public Visibilidade set(String fieldName, Object value) {
    return (Visibilidade) super.set(fieldName, value);
  }

  @Override
  public Visibilidade clone() {
    return (Visibilidade) super.clone();
  }

}
