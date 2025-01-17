package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import java.io.Serializable;

public class DeserializerFactoryConfig implements Serializable {
    protected static final KeyDeserializers[] DEFAULT_KEY_DESERIALIZERS = {new StdKeyDeserializers()};
    protected static final AbstractTypeResolver[] NO_ABSTRACT_TYPE_RESOLVERS = new AbstractTypeResolver[0];
    protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];
    protected static final BeanDeserializerModifier[] NO_MODIFIERS = new BeanDeserializerModifier[0];
    protected static final ValueInstantiators[] NO_VALUE_INSTANTIATORS = new ValueInstantiators[0];
    protected final AbstractTypeResolver[] _abstractTypeResolvers;
    protected final Deserializers[] _additionalDeserializers;
    protected final KeyDeserializers[] _additionalKeyDeserializers;
    protected final BeanDeserializerModifier[] _modifiers;
    protected final ValueInstantiators[] _valueInstantiators;

    public DeserializerFactoryConfig() {
        this(null, null, null, null, null);
    }

    public Iterable<AbstractTypeResolver> abstractTypeResolvers() {
        return new ArrayIterator(this._abstractTypeResolvers);
    }

    public Iterable<BeanDeserializerModifier> deserializerModifiers() {
        return new ArrayIterator(this._modifiers);
    }

    public Iterable<Deserializers> deserializers() {
        return new ArrayIterator(this._additionalDeserializers);
    }

    public boolean hasAbstractTypeResolvers() {
        return this._abstractTypeResolvers.length > 0;
    }

    public boolean hasDeserializerModifiers() {
        return this._modifiers.length > 0;
    }

    public boolean hasKeyDeserializers() {
        return this._additionalKeyDeserializers.length > 0;
    }

    public boolean hasValueInstantiators() {
        return this._valueInstantiators.length > 0;
    }

    public Iterable<KeyDeserializers> keyDeserializers() {
        return new ArrayIterator(this._additionalKeyDeserializers);
    }

    public Iterable<ValueInstantiators> valueInstantiators() {
        return new ArrayIterator(this._valueInstantiators);
    }

    public DeserializerFactoryConfig withAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver) {
        if (abstractTypeResolver != null) {
            DeserializerFactoryConfig deserializerFactoryConfig = new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, (AbstractTypeResolver[]) ArrayBuilders.insertInListNoDup(this._abstractTypeResolvers, abstractTypeResolver), this._valueInstantiators);
            return deserializerFactoryConfig;
        }
        throw new IllegalArgumentException("Cannot pass null resolver");
    }

    public DeserializerFactoryConfig withAdditionalDeserializers(Deserializers deserializers) {
        if (deserializers != null) {
            DeserializerFactoryConfig deserializerFactoryConfig = new DeserializerFactoryConfig((Deserializers[]) ArrayBuilders.insertInListNoDup(this._additionalDeserializers, deserializers), this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
            return deserializerFactoryConfig;
        }
        throw new IllegalArgumentException("Cannot pass null Deserializers");
    }

    public DeserializerFactoryConfig withAdditionalKeyDeserializers(KeyDeserializers keyDeserializers) {
        if (keyDeserializers != null) {
            DeserializerFactoryConfig deserializerFactoryConfig = new DeserializerFactoryConfig(this._additionalDeserializers, (KeyDeserializers[]) ArrayBuilders.insertInListNoDup(this._additionalKeyDeserializers, keyDeserializers), this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
            return deserializerFactoryConfig;
        }
        throw new IllegalArgumentException("Cannot pass null KeyDeserializers");
    }

    public DeserializerFactoryConfig withDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
        if (beanDeserializerModifier != null) {
            DeserializerFactoryConfig deserializerFactoryConfig = new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, (BeanDeserializerModifier[]) ArrayBuilders.insertInListNoDup(this._modifiers, beanDeserializerModifier), this._abstractTypeResolvers, this._valueInstantiators);
            return deserializerFactoryConfig;
        }
        throw new IllegalArgumentException("Cannot pass null modifier");
    }

    public DeserializerFactoryConfig withValueInstantiators(ValueInstantiators valueInstantiators) {
        if (valueInstantiators != null) {
            DeserializerFactoryConfig deserializerFactoryConfig = new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, (ValueInstantiators[]) ArrayBuilders.insertInListNoDup(this._valueInstantiators, valueInstantiators));
            return deserializerFactoryConfig;
        }
        throw new IllegalArgumentException("Cannot pass null resolver");
    }

    protected DeserializerFactoryConfig(Deserializers[] deserializersArr, KeyDeserializers[] keyDeserializersArr, BeanDeserializerModifier[] beanDeserializerModifierArr, AbstractTypeResolver[] abstractTypeResolverArr, ValueInstantiators[] valueInstantiatorsArr) {
        if (deserializersArr == null) {
            deserializersArr = NO_DESERIALIZERS;
        }
        this._additionalDeserializers = deserializersArr;
        if (keyDeserializersArr == null) {
            keyDeserializersArr = DEFAULT_KEY_DESERIALIZERS;
        }
        this._additionalKeyDeserializers = keyDeserializersArr;
        if (beanDeserializerModifierArr == null) {
            beanDeserializerModifierArr = NO_MODIFIERS;
        }
        this._modifiers = beanDeserializerModifierArr;
        if (abstractTypeResolverArr == null) {
            abstractTypeResolverArr = NO_ABSTRACT_TYPE_RESOLVERS;
        }
        this._abstractTypeResolvers = abstractTypeResolverArr;
        if (valueInstantiatorsArr == null) {
            valueInstantiatorsArr = NO_VALUE_INSTANTIATORS;
        }
        this._valueInstantiators = valueInstantiatorsArr;
    }
}
