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
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-05-27 16:00:31 UTC)
 * on 2016-07-01 at 11:58:26 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI.model;

/**
 * Model definition for Dispositivo.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Dispositivo extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dispositivoId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sistemaOperacional;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String usuarioId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String versao;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDispositivoId() {
    return dispositivoId;
  }

  /**
   * @param dispositivoId dispositivoId or {@code null} for none
   */
  public Dispositivo setDispositivoId(java.lang.String dispositivoId) {
    this.dispositivoId = dispositivoId;
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
  public Dispositivo setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSistemaOperacional() {
    return sistemaOperacional;
  }

  /**
   * @param sistemaOperacional sistemaOperacional or {@code null} for none
   */
  public Dispositivo setSistemaOperacional(java.lang.String sistemaOperacional) {
    this.sistemaOperacional = sistemaOperacional;
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
  public Dispositivo setUsuarioId(java.lang.String usuarioId) {
    this.usuarioId = usuarioId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getVersao() {
    return versao;
  }

  /**
   * @param versao versao or {@code null} for none
   */
  public Dispositivo setVersao(java.lang.String versao) {
    this.versao = versao;
    return this;
  }

  @Override
  public Dispositivo set(String fieldName, Object value) {
    return (Dispositivo) super.set(fieldName, value);
  }

  @Override
  public Dispositivo clone() {
    return (Dispositivo) super.clone();
  }

}
