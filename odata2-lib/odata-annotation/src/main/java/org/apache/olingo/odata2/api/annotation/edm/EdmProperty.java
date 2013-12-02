/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.olingo.odata2.api.annotation.edm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

/**
 * <p>Annotation for definition of an EdmProperty for an EdmEntityType or EdmComplexType 
 * which contains the EdmProperty as a field.</p>
 * The EdmProperty annotation has to be used on a field within a EdmEntityType or EdmComplexType annotated class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EdmProperty {
  /**
   * Define the EdmSimpleType which is used for the Property in the EDM.
   * If not set a default value has to be generated by the EDM provider (which should be compatible to the 
   * java type of the annotated field).
   * If the property is a EdmComplexType (and no EdmSimpleType) the default value has to be used.
   * 
   * @return type for the Property as used in the EDM
   */
  EdmSimpleTypeKind type() default EdmSimpleTypeKind.Null;
  /**
   * Define the name for the Property.
   * If not set a default value has to be generated by the EDM provider.
   * 
   * @return name for the Property
   */
  String name() default "";
  /**
   * Define the EdmFacets for the Property in the EDM.
   * If not set the default EdmFacet values are used (see {@link EdmFacets}).
   * 
   * @return facets for the Property as used in the EDM
   */
  EdmFacets facets() default @EdmFacets;
}