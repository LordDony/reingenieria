package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.Annotations;

public class AttributePropertyWriter extends VirtualBeanPropertyWriter {
    protected final String _attrName;

    protected AttributePropertyWriter(String str, BeanPropertyDefinition beanPropertyDefinition, Annotations annotations, JavaType javaType) {
        this(str, beanPropertyDefinition, annotations, javaType, beanPropertyDefinition.findInclusion());
    }

    public static AttributePropertyWriter construct(String str, BeanPropertyDefinition beanPropertyDefinition, Annotations annotations, JavaType javaType) {
        return new AttributePropertyWriter(str, beanPropertyDefinition, annotations, javaType);
    }

    /* access modifiers changed from: protected */
    public Object value(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        return serializerProvider.getAttribute(this._attrName);
    }

    public VirtualBeanPropertyWriter withConfig(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, BeanPropertyDefinition beanPropertyDefinition, JavaType javaType) {
        throw new IllegalStateException("Should not be called on this type");
    }

    protected AttributePropertyWriter(String str, BeanPropertyDefinition beanPropertyDefinition, Annotations annotations, JavaType javaType, Value value) {
        super(beanPropertyDefinition, annotations, javaType, null, null, null, value, null);
        this._attrName = str;
    }
}
