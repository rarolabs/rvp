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
 * (build: 2015-05-05 20:00:12 UTC)
 * on 2015-05-12 at 20:43:09 UTC 
 * Modify at your own risk.
 */

package br.com.rarolabs.rvp.api.rvpAPI.model;

/**
 * Model definition for GeoqueryResponderCollection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the rvpAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class GeoqueryResponderCollection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<GeoqueryResponder> items;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<GeoqueryResponder> getItems() {
    return items;
  }

  /**
   * @param items items or {@code null} for none
   */
  public GeoqueryResponderCollection setItems(java.util.List<GeoqueryResponder> items) {
    this.items = items;
    return this;
  }

  @Override
  public GeoqueryResponderCollection set(String fieldName, Object value) {
    return (GeoqueryResponderCollection) super.set(fieldName, value);
  }

  @Override
  public GeoqueryResponderCollection clone() {
    return (GeoqueryResponderCollection) super.clone();
  }

}
