package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;

public abstract class Module implements Versioned {

    public interface SetupContext {
        void addAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver);

        void addBeanDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier);

        void addBeanSerializerModifier(BeanSerializerModifier beanSerializerModifier);

        void addDeserializers(Deserializers deserializers);

        void addKeyDeserializers(KeyDeserializers keyDeserializers);

        void addKeySerializers(Serializers serializers);

        void addSerializers(Serializers serializers);

        void addValueInstantiators(ValueInstantiators valueInstantiators);

        void appendAnnotationIntrospector(AnnotationIntrospector annotationIntrospector);

        void insertAnnotationIntrospector(AnnotationIntrospector annotationIntrospector);

        boolean isEnabled(DeserializationFeature deserializationFeature);

        void registerSubtypes(NamedType... namedTypeArr);

        void setMixInAnnotations(Class<?> cls, Class<?> cls2);

        void setNamingStrategy(PropertyNamingStrategy propertyNamingStrategy);
    }

    public abstract String getModuleName();

    public Object getTypeId() {
        return getClass().getName();
    }

    public abstract void setupModule(SetupContext setupContext);

    public abstract Version version();
}
