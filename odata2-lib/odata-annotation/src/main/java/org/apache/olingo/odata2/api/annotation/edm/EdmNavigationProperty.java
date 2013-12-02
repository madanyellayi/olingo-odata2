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

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;

/**
 * <p>Annotation for definition of an EdmNavigationProperty for an EdmEntityType
 * which contains the navigation property as a field.</p>
 * The EdmNavigationProperty annotation has to be used on a field within a EdmEntityType annotated class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EdmNavigationProperty {
  /**
   * Define the name for the Navigation Property.
   * If not set a default value has to be generated by the EDM provider.
   * 
   * @return name for the Navigation Property
   */
  String name() default "";
  /**
   * Define the target entity in form of a java class for the Navigation Property.
   * The referenced java class must be annotated with {@link EdmEntityType}.
   * If not set a EDM provider should be able to determine the corresponding type or
   * to handle the default setting.
   * 
   * @return referenced java class which must be annotated with {@link EdmEntityType}.
   */
  Class<?> toType() default Object.class;
  /**
   * Define the name for the related Edm Association Set (of the Navigation Property).
   * If not set a default value has to be generated by the EDM provider.
   * 
   * @return name for the related Edm Association Set (of the Navigation Property).
   */
  String association() default "";
  /**
   * Define the name for the related Edm Role (of the Navigation Property).
   * If not set a default value has to be generated by the EDM provider.
   * 
   * @return name for the related Edm Role (of the Navigation Property).
   */
  String toRole() default "";
  /**
   * Define the multiplicity to the target EdmEntity of the Navigation Property.
   * If not set a default multiplicity of <code>ONE</code> is assumed.
   * This could be overwritten by the EDM provider if the correct multiplicity can be determined.
   * As example: The default multiplicity of <code>ONE</code> on a field (EdmProperty) with
   * a java type <code>Collection</code> can be overwritten by a multiplicity of <code>MANY</code>.
   * 
   * @return multiplicity to the target EdmEntity of the Navigation Property.
   */
  EdmMultiplicity toMultiplicity() default EdmMultiplicity.ONE;
}