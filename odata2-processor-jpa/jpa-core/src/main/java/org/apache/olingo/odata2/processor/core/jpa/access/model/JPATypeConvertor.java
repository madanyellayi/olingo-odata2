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
package org.apache.olingo.odata2.processor.core.jpa.access.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.metamodel.Attribute;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.processor.api.jpa.exception.ODataJPAModelException;

/**
 * This class holds utility methods for Type conversions between JPA and OData Types.
 * 
 * 
 * 
 */
public class JPATypeConvertor {

  /**
   * This utility method converts a given jpa Type to equivalent
   * EdmSimpleTypeKind for maintaining compatibility between Java and OData
   * Types.
   * 
   * @param jpaType
   * The JPA Type input.
   * @return The corresponding EdmSimpleTypeKind.
   * @throws ODataJPAModelException
   * @throws org.apache.olingo.odata2.processor.api.jpa.exception.ODataJPARuntimeException
   * 
   * @see EdmSimpleTypeKind
   */

  private static final String OBJECT_TYPE = "java.lang.Object";

  public static EdmSimpleTypeKind
      convertToEdmSimpleType(final Class<?> jpaType, final Attribute<?, ?> currentAttribute)
          throws ODataJPAModelException {
    if (jpaType.equals(String.class) || jpaType.equals(Character.class) || jpaType.equals(char.class)
        || jpaType.equals(char[].class) ||
        jpaType.equals(Character[].class)) {
      return EdmSimpleTypeKind.String;
    } else if (jpaType.equals(Long.class) || jpaType.equals(long.class)) {
      return EdmSimpleTypeKind.Int64;
    } else if (jpaType.equals(Short.class) || jpaType.equals(short.class)) {
      return EdmSimpleTypeKind.Int16;
    } else if (jpaType.equals(Integer.class) || jpaType.equals(int.class)) {
      return EdmSimpleTypeKind.Int32;
    } else if (jpaType.equals(Double.class) || jpaType.equals(double.class)) {
      return EdmSimpleTypeKind.Double;
    } else if (jpaType.equals(Float.class) || jpaType.equals(float.class)) {
      return EdmSimpleTypeKind.Single;
    } else if (jpaType.equals(BigDecimal.class)) {
      return EdmSimpleTypeKind.Decimal;
    } else if (jpaType.equals(byte[].class)) {
      return EdmSimpleTypeKind.Binary;
    } else if (jpaType.equals(Byte.class) || jpaType.equals(byte.class)) {
      return EdmSimpleTypeKind.Byte;
    } else if (jpaType.equals(Byte[].class)) {
      return EdmSimpleTypeKind.Binary;
    } else if (jpaType.equals(Boolean.class) || jpaType.equals(boolean.class)) {
      return EdmSimpleTypeKind.Boolean;
    } else if ((jpaType.equals(Date.class)) || (jpaType.equals(Calendar.class))) {
      try {
        if ((currentAttribute != null)
            && (determineTemporalType(currentAttribute.getDeclaringType().getJavaType(), currentAttribute.getName())
              == TemporalType.TIME)) {
          return EdmSimpleTypeKind.Time;
        } else {
          return EdmSimpleTypeKind.DateTime;
        }
      } catch (SecurityException e) {
        throw ODataJPAModelException.throwException(ODataJPAModelException.GENERAL.addContent(e.getMessage()), e);
      }
    } else if (jpaType.equals(UUID.class)) {
      return EdmSimpleTypeKind.Guid;
    }
    throw ODataJPAModelException.throwException(ODataJPAModelException.TYPE_NOT_SUPPORTED
        .addContent(jpaType.toString()), null);
  }

  private static TemporalType determineTemporalType(final Class<?> type, final String fieldName)
      throws ODataJPAModelException {
    if (type != null && !type.getName().equals(OBJECT_TYPE)) {
      try {
        Field field = type.getField(fieldName);
        return field.getAnnotation(Temporal.class).value();
      } catch (NoSuchFieldException e) {
        determineTemporalType(type.getSuperclass(), fieldName);
      } catch (SecurityException e) {
        throw ODataJPAModelException.throwException(ODataJPAModelException.GENERAL.addContent(e.getMessage()), e);
      }
    }
    return null;

  }
}
