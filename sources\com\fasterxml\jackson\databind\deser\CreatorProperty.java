package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;

public class CreatorProperty extends SettableBeanProperty {
    protected final AnnotatedParameter _annotated;
    protected final int _creatorIndex;
    protected SettableBeanProperty _fallbackSetter;
    protected boolean _ignorable;
    protected final Object _injectableValueId;

    public CreatorProperty(PropertyName propertyName, JavaType javaType, PropertyName propertyName2, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedParameter annotatedParameter, int i, Object obj, PropertyMetadata propertyMetadata) {
        super(propertyName, javaType, propertyName2, typeDeserializer, annotations, propertyMetadata);
        this._annotated = annotatedParameter;
        this._creatorIndex = i;
        this._injectableValueId = obj;
        this._fallbackSetter = null;
    }

    private void _reportMissingSetter(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("No fallback setter/field defined for creator property '");
        sb.append(getName());
        sb.append("'");
        String sb2 = sb.toString();
        if (deserializationContext != null) {
            deserializationContext.reportBadDefinition(getType(), sb2);
            throw null;
        }
        throw InvalidDefinitionException.from(jsonParser, sb2, getType());
    }

    private final void _verifySetter() throws IOException {
        if (this._fallbackSetter == null) {
            _reportMissingSetter(null, null);
            throw null;
        }
    }

    public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        _verifySetter();
        this._fallbackSetter.set(obj, deserialize(jsonParser, deserializationContext));
    }

    public Object deserializeSetAndReturn(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        _verifySetter();
        return this._fallbackSetter.setAndReturn(obj, deserialize(jsonParser, deserializationContext));
    }

    public void fixAccess(DeserializationConfig deserializationConfig) {
        SettableBeanProperty settableBeanProperty = this._fallbackSetter;
        if (settableBeanProperty != null) {
            settableBeanProperty.fixAccess(deserializationConfig);
        }
    }

    public int getCreatorIndex() {
        return this._creatorIndex;
    }

    public Object getInjectableValueId() {
        return this._injectableValueId;
    }

    public AnnotatedMember getMember() {
        return this._annotated;
    }

    public boolean isIgnorable() {
        return this._ignorable;
    }

    public void markAsIgnorable() {
        this._ignorable = true;
    }

    public void set(Object obj, Object obj2) throws IOException {
        _verifySetter();
        this._fallbackSetter.set(obj, obj2);
    }

    public Object setAndReturn(Object obj, Object obj2) throws IOException {
        _verifySetter();
        return this._fallbackSetter.setAndReturn(obj, obj2);
    }

    public void setFallbackSetter(SettableBeanProperty settableBeanProperty) {
        this._fallbackSetter = settableBeanProperty;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[creator property, name '");
        sb.append(getName());
        sb.append("'; inject id '");
        sb.append(this._injectableValueId);
        sb.append("']");
        return sb.toString();
    }

    public SettableBeanProperty withName(PropertyName propertyName) {
        return new CreatorProperty(this, propertyName);
    }

    public SettableBeanProperty withNullProvider(NullValueProvider nullValueProvider) {
        return new CreatorProperty(this, this._valueDeserializer, nullValueProvider);
    }

    public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        JsonDeserializer<Object> jsonDeserializer2 = this._valueDeserializer;
        if (jsonDeserializer2 == jsonDeserializer) {
            return this;
        }
        NullValueProvider nullValueProvider = this._nullProvider;
        if (jsonDeserializer2 == nullValueProvider) {
            nullValueProvider = jsonDeserializer;
        }
        return new CreatorProperty(this, jsonDeserializer, nullValueProvider);
    }

    protected CreatorProperty(CreatorProperty creatorProperty, PropertyName propertyName) {
        super(creatorProperty, propertyName);
        this._annotated = creatorProperty._annotated;
        this._injectableValueId = creatorProperty._injectableValueId;
        this._fallbackSetter = creatorProperty._fallbackSetter;
        this._creatorIndex = creatorProperty._creatorIndex;
        this._ignorable = creatorProperty._ignorable;
    }

    protected CreatorProperty(CreatorProperty creatorProperty, JsonDeserializer<?> jsonDeserializer, NullValueProvider nullValueProvider) {
        super(creatorProperty, jsonDeserializer, nullValueProvider);
        this._annotated = creatorProperty._annotated;
        this._injectableValueId = creatorProperty._injectableValueId;
        this._fallbackSetter = creatorProperty._fallbackSetter;
        this._creatorIndex = creatorProperty._creatorIndex;
        this._ignorable = creatorProperty._ignorable;
    }
}
