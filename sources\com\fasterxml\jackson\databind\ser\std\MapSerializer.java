package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties.Value;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@JacksonStdImpl
public class MapSerializer extends ContainerSerializer<Map<?, ?>> implements ContextualSerializer {
    public static final Object MARKER_FOR_EMPTY = Include.NON_EMPTY;
    protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final Object _filterId;
    protected final Set<String> _ignoredEntries;
    protected JsonSerializer<Object> _keySerializer;
    protected final JavaType _keyType;
    protected final BeanProperty _property;
    protected final boolean _sortKeys;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final boolean _valueTypeIsStatic;
    protected final TypeSerializer _valueTypeSerializer;

    /* renamed from: com.fasterxml.jackson.databind.ser.std.MapSerializer$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include = new int[Include.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|(3:11|12|14)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[Include.NON_DEFAULT.ordinal()] = 1;
            $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[Include.NON_ABSENT.ordinal()] = 2;
            $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[Include.NON_EMPTY.ordinal()] = 3;
            $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[Include.CUSTOM.ordinal()] = 4;
            $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[Include.NON_NULL.ordinal()] = 5;
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[Include.ALWAYS.ordinal()] = 6;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    protected MapSerializer(Set<String> set, JavaType javaType, JavaType javaType2, boolean z, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2) {
        super(Map.class, false);
        if (set == null || set.isEmpty()) {
            set = null;
        }
        this._ignoredEntries = set;
        this._keyType = javaType;
        this._valueType = javaType2;
        this._valueTypeIsStatic = z;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = null;
        this._filterId = null;
        this._sortKeys = false;
        this._suppressableValue = null;
        this._suppressNulls = false;
    }

    private final JsonSerializer<Object> _findSerializer(SerializerProvider serializerProvider, Object obj) throws JsonMappingException {
        Class cls = obj.getClass();
        JsonSerializer<Object> serializerFor = this._dynamicValueSerializers.serializerFor(cls);
        if (serializerFor != null) {
            return serializerFor;
        }
        if (this._valueType.hasGenericTypes()) {
            return _findAndAddDynamic(this._dynamicValueSerializers, serializerProvider.constructSpecializedType(this._valueType, cls), serializerProvider);
        }
        return _findAndAddDynamic(this._dynamicValueSerializers, cls, serializerProvider);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    public static MapSerializer construct(Set<String> set, JavaType javaType, boolean z, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, Object obj) {
        JavaType javaType2;
        JavaType javaType3;
        boolean z2;
        if (javaType == null) {
            javaType3 = UNSPECIFIED_TYPE;
            javaType2 = javaType3;
        } else {
            JavaType keyType = javaType.getKeyType();
            javaType2 = javaType.getContentType();
            javaType3 = keyType;
        }
        if (!z) {
            z = javaType2 != null && javaType2.isFinal();
        } else if (javaType2.getRawClass() == Object.class) {
            z2 = false;
            MapSerializer mapSerializer = new MapSerializer(set, javaType3, javaType2, z2, typeSerializer, jsonSerializer, jsonSerializer2);
            return obj == null ? mapSerializer.withFilterId(obj) : mapSerializer;
        }
        z2 = z;
        MapSerializer mapSerializer2 = new MapSerializer(set, javaType3, javaType2, z2, typeSerializer, jsonSerializer, jsonSerializer2);
        if (obj == null) {
        }
    }

    /* access modifiers changed from: protected */
    public void _ensureOverride(String str) {
        ClassUtil.verifyMustOverride(MapSerializer.class, this, str);
    }

    /* access modifiers changed from: protected */
    public final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> cls, SerializerProvider serializerProvider) throws JsonMappingException {
        SerializerAndMapResult findAndAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(cls, serializerProvider, this._property);
        PropertySerializerMap propertySerializerMap2 = findAndAddSecondarySerializer.map;
        if (propertySerializerMap != propertySerializerMap2) {
            this._dynamicValueSerializers = propertySerializerMap2;
        }
        return findAndAddSecondarySerializer.serializer;
    }

    /* access modifiers changed from: protected */
    public boolean _hasNullKey(Map<?, ?> map) {
        return (map instanceof HashMap) && map.containsKey(null);
    }

    /* access modifiers changed from: protected */
    public Map<?, ?> _orderEntries(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (map instanceof SortedMap) {
            return map;
        }
        if (!_hasNullKey(map)) {
            return new TreeMap(map);
        }
        TreeMap treeMap = new TreeMap();
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            if (key == null) {
                _writeNullKeyedEntry(jsonGenerator, serializerProvider, entry.getValue());
            } else {
                treeMap.put(key, entry.getValue());
            }
        }
        return treeMap;
    }

    /* access modifiers changed from: protected */
    public void _writeNullKeyedEntry(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer findNullKeySerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
        if (obj != null) {
            jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer = _findSerializer(serializerProvider, obj);
            }
            Object obj2 = this._suppressableValue;
            if (obj2 == MARKER_FOR_EMPTY) {
                if (jsonSerializer.isEmpty(serializerProvider, obj)) {
                    return;
                }
            } else if (obj2 != null && obj2.equals(obj)) {
                return;
            }
        } else if (!this._suppressNulls) {
            jsonSerializer = serializerProvider.getDefaultNullValueSerializer();
        } else {
            return;
        }
        try {
            findNullKeySerializer.serialize(null, jsonGenerator, serializerProvider);
            jsonSerializer.serialize(obj, jsonGenerator, serializerProvider);
        } catch (Exception e) {
            wrapAndThrow(serializerProvider, (Throwable) e, obj, "");
            throw null;
        }
    }

    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Annotated annotated;
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        JsonSerializer jsonSerializer3;
        Set<String> set;
        boolean z;
        AnnotationIntrospector annotationIntrospector = serializerProvider.getAnnotationIntrospector();
        Object obj = null;
        if (beanProperty == null) {
            annotated = null;
        } else {
            annotated = beanProperty.getMember();
        }
        if (StdSerializer._neitherNull(annotated, annotationIntrospector)) {
            Object findKeySerializer = annotationIntrospector.findKeySerializer(annotated);
            jsonSerializer2 = findKeySerializer != null ? serializerProvider.serializerInstance(annotated, findKeySerializer) : null;
            Object findContentSerializer = annotationIntrospector.findContentSerializer(annotated);
            jsonSerializer = findContentSerializer != null ? serializerProvider.serializerInstance(annotated, findContentSerializer) : null;
        } else {
            jsonSerializer2 = null;
            jsonSerializer = null;
        }
        if (jsonSerializer == null) {
            jsonSerializer = this._valueSerializer;
        }
        JsonSerializer findContextualConvertingSerializer = findContextualConvertingSerializer(serializerProvider, beanProperty, jsonSerializer);
        if (findContextualConvertingSerializer == null && this._valueTypeIsStatic && !this._valueType.isJavaLangObject()) {
            findContextualConvertingSerializer = serializerProvider.findValueSerializer(this._valueType, beanProperty);
        }
        JsonSerializer jsonSerializer4 = findContextualConvertingSerializer;
        if (jsonSerializer2 == null) {
            jsonSerializer2 = this._keySerializer;
        }
        if (jsonSerializer2 == null) {
            jsonSerializer3 = serializerProvider.findKeySerializer(this._keyType, beanProperty);
        } else {
            jsonSerializer3 = serializerProvider.handleSecondaryContextualization(jsonSerializer2, beanProperty);
        }
        JsonSerializer jsonSerializer5 = jsonSerializer3;
        Set<String> set2 = this._ignoredEntries;
        if (StdSerializer._neitherNull(annotated, annotationIntrospector)) {
            Value findPropertyIgnorals = annotationIntrospector.findPropertyIgnorals(annotated);
            if (findPropertyIgnorals != null) {
                Set<String> findIgnoredForSerialization = findPropertyIgnorals.findIgnoredForSerialization();
                if (StdSerializer._nonEmpty(findIgnoredForSerialization)) {
                    set2 = set2 == null ? new HashSet<>() : new HashSet<>(set2);
                    for (String add : findIgnoredForSerialization) {
                        set2.add(add);
                    }
                }
            }
            z = Boolean.TRUE.equals(annotationIntrospector.findSerializationSortAlphabetically(annotated));
            set = set2;
        } else {
            set = set2;
            z = false;
        }
        JsonFormat.Value findFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, Map.class);
        if (findFormatOverrides != null) {
            Boolean feature = findFormatOverrides.getFeature(Feature.WRITE_SORTED_MAP_ENTRIES);
            if (feature != null) {
                z = feature.booleanValue();
            }
        }
        MapSerializer withResolved = withResolved(beanProperty, jsonSerializer5, jsonSerializer4, set, z);
        if (beanProperty == null) {
            return withResolved;
        }
        AnnotatedMember member = beanProperty.getMember();
        if (member != null) {
            Object findFilterId = annotationIntrospector.findFilterId(member);
            if (findFilterId != null) {
                withResolved = withResolved.withFilterId(findFilterId);
            }
        }
        JsonInclude.Value findPropertyInclusion = beanProperty.findPropertyInclusion(serializerProvider.getConfig(), null);
        if (findPropertyInclusion == null) {
            return withResolved;
        }
        Include contentInclusion = findPropertyInclusion.getContentInclusion();
        if (contentInclusion == Include.USE_DEFAULTS) {
            return withResolved;
        }
        int i = AnonymousClass1.$SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[contentInclusion.ordinal()];
        boolean z2 = true;
        if (i == 1) {
            obj = BeanUtil.getDefaultValue(this._valueType);
            if (obj != null && obj.getClass().isArray()) {
                obj = ArrayBuilders.getArrayComparator(obj);
            }
        } else if (i != 2) {
            if (i == 3) {
                obj = MARKER_FOR_EMPTY;
            } else if (i == 4) {
                obj = serializerProvider.includeFilterInstance(null, findPropertyInclusion.getContentFilter());
                if (obj != null) {
                    z2 = serializerProvider.includeFilterSuppressNulls(obj);
                }
            } else if (i != 5) {
                z2 = false;
            }
        } else if (this._valueType.isReferenceType()) {
            obj = MARKER_FOR_EMPTY;
        }
        return withResolved.withContentInclusion(obj, z2);
    }

    public JavaType getContentType() {
        return this._valueType;
    }

    public void serializeFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Object obj;
        if (this._valueTypeSerializer != null) {
            serializeTypedFields(map, jsonGenerator, serializerProvider, null);
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._keySerializer;
        Set<String> set = this._ignoredEntries;
        try {
            obj = null;
            for (Entry entry : map.entrySet()) {
                try {
                    Object value = entry.getValue();
                    obj = entry.getKey();
                    if (obj == null) {
                        serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
                    } else if (set == null || !set.contains(obj)) {
                        jsonSerializer.serialize(obj, jsonGenerator, serializerProvider);
                    }
                    if (value == null) {
                        serializerProvider.defaultSerializeNull(jsonGenerator);
                    } else {
                        JsonSerializer<Object> jsonSerializer2 = this._valueSerializer;
                        if (jsonSerializer2 == null) {
                            jsonSerializer2 = _findSerializer(serializerProvider, value);
                        }
                        jsonSerializer2.serialize(value, jsonGenerator, serializerProvider);
                    }
                } catch (Exception e) {
                    e = e;
                    wrapAndThrow(serializerProvider, (Throwable) e, (Object) map, String.valueOf(obj));
                    throw null;
                }
            }
        } catch (Exception e2) {
            e = e2;
            obj = null;
            wrapAndThrow(serializerProvider, (Throwable) e, (Object) map, String.valueOf(obj));
            throw null;
        }
    }

    public void serializeFieldsUsing(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException {
        JsonSerializer<Object> jsonSerializer2 = this._keySerializer;
        Set<String> set = this._ignoredEntries;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            if (set == null || !set.contains(key)) {
                if (key == null) {
                    serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer2.serialize(key, jsonGenerator, serializerProvider);
                }
                Object value = entry.getValue();
                if (value == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else if (typeSerializer == null) {
                    try {
                        jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
                    } catch (Exception e) {
                        wrapAndThrow(serializerProvider, (Throwable) e, (Object) map, String.valueOf(key));
                        throw null;
                    }
                } else {
                    jsonSerializer.serializeWithType(value, jsonGenerator, serializerProvider, typeSerializer);
                }
            }
        }
    }

    public void serializeFilteredAnyProperties(SerializerProvider serializerProvider, JsonGenerator jsonGenerator, Object obj, Map<?, ?> map, PropertyFilter propertyFilter, Object obj2) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        Set<String> set = this._ignoredEntries;
        MapProperty mapProperty = new MapProperty(this._valueTypeSerializer, this._property);
        boolean z = MARKER_FOR_EMPTY == obj2;
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            if (set == null || !set.contains(key)) {
                if (key == null) {
                    jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
                } else {
                    jsonSerializer = this._keySerializer;
                }
                Object value = entry.getValue();
                if (value != null) {
                    jsonSerializer2 = this._valueSerializer;
                    if (jsonSerializer2 == null) {
                        jsonSerializer2 = _findSerializer(serializerProvider, value);
                    }
                    if (z) {
                        if (jsonSerializer2.isEmpty(serializerProvider, value)) {
                        }
                    } else if (obj2 != null && obj2.equals(value)) {
                    }
                } else if (!this._suppressNulls) {
                    jsonSerializer2 = serializerProvider.getDefaultNullValueSerializer();
                }
                mapProperty.reset(key, value, jsonSerializer, jsonSerializer2);
                try {
                    propertyFilter.serializeAsField(obj, jsonGenerator, serializerProvider, mapProperty);
                } catch (Exception e) {
                    wrapAndThrow(serializerProvider, (Throwable) e, (Object) map, String.valueOf(key));
                    throw null;
                }
            }
        }
    }

    public void serializeFilteredFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyFilter propertyFilter, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        Set<String> set = this._ignoredEntries;
        MapProperty mapProperty = new MapProperty(this._valueTypeSerializer, this._property);
        boolean z = MARKER_FOR_EMPTY == obj;
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            if (set == null || !set.contains(key)) {
                if (key == null) {
                    jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
                } else {
                    jsonSerializer = this._keySerializer;
                }
                Object value = entry.getValue();
                if (value != null) {
                    jsonSerializer2 = this._valueSerializer;
                    if (jsonSerializer2 == null) {
                        jsonSerializer2 = _findSerializer(serializerProvider, value);
                    }
                    if (z) {
                        if (jsonSerializer2.isEmpty(serializerProvider, value)) {
                        }
                    } else if (obj != null && obj.equals(value)) {
                    }
                } else if (!this._suppressNulls) {
                    jsonSerializer2 = serializerProvider.getDefaultNullValueSerializer();
                }
                mapProperty.reset(key, value, jsonSerializer, jsonSerializer2);
                try {
                    propertyFilter.serializeAsField(map, jsonGenerator, serializerProvider, mapProperty);
                } catch (Exception e) {
                    wrapAndThrow(serializerProvider, (Throwable) e, (Object) map, String.valueOf(key));
                    throw null;
                }
            }
        }
    }

    public void serializeOptionalFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        if (this._valueTypeSerializer != null) {
            serializeTypedFields(map, jsonGenerator, serializerProvider, obj);
            return;
        }
        Set<String> set = this._ignoredEntries;
        boolean z = MARKER_FOR_EMPTY == obj;
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            if (key == null) {
                jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
            } else if (set == null || !set.contains(key)) {
                jsonSerializer = this._keySerializer;
            }
            Object value = entry.getValue();
            if (value != null) {
                jsonSerializer2 = this._valueSerializer;
                if (jsonSerializer2 == null) {
                    jsonSerializer2 = _findSerializer(serializerProvider, value);
                }
                if (z) {
                    if (jsonSerializer2.isEmpty(serializerProvider, value)) {
                    }
                } else if (obj != null && obj.equals(value)) {
                }
            } else if (!this._suppressNulls) {
                jsonSerializer2 = serializerProvider.getDefaultNullValueSerializer();
            }
            try {
                jsonSerializer.serialize(key, jsonGenerator, serializerProvider);
                jsonSerializer2.serialize(value, jsonGenerator, serializerProvider);
            } catch (Exception e) {
                wrapAndThrow(serializerProvider, (Throwable) e, (Object) map, String.valueOf(key));
                throw null;
            }
        }
    }

    public void serializeTypedFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        Set<String> set = this._ignoredEntries;
        boolean z = MARKER_FOR_EMPTY == obj;
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            if (key == null) {
                jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
            } else if (set == null || !set.contains(key)) {
                jsonSerializer = this._keySerializer;
            }
            Object value = entry.getValue();
            if (value != null) {
                jsonSerializer2 = this._valueSerializer;
                if (jsonSerializer2 == null) {
                    jsonSerializer2 = _findSerializer(serializerProvider, value);
                }
                if (z) {
                    if (jsonSerializer2.isEmpty(serializerProvider, value)) {
                    }
                } else if (obj != null && obj.equals(value)) {
                }
            } else if (!this._suppressNulls) {
                jsonSerializer2 = serializerProvider.getDefaultNullValueSerializer();
            }
            jsonSerializer.serialize(key, jsonGenerator, serializerProvider);
            try {
                jsonSerializer2.serializeWithType(value, jsonGenerator, serializerProvider, this._valueTypeSerializer);
            } catch (Exception e) {
                wrapAndThrow(serializerProvider, (Throwable) e, (Object) map, String.valueOf(key));
                throw null;
            }
        }
    }

    public MapSerializer withContentInclusion(Object obj, boolean z) {
        if (obj == this._suppressableValue && z == this._suppressNulls) {
            return this;
        }
        _ensureOverride("withContentInclusion");
        return new MapSerializer(this, this._valueTypeSerializer, obj, z);
    }

    public MapSerializer withFilterId(Object obj) {
        if (this._filterId == obj) {
            return this;
        }
        _ensureOverride("withFilterId");
        return new MapSerializer(this, obj, this._sortKeys);
    }

    public MapSerializer withResolved(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set, boolean z) {
        _ensureOverride("withResolved");
        MapSerializer mapSerializer = new MapSerializer(this, beanProperty, jsonSerializer, jsonSerializer2, set);
        return z != mapSerializer._sortKeys ? new MapSerializer(mapSerializer, this._filterId, z) : mapSerializer;
    }

    public MapSerializer _withValueTypeSerializer(TypeSerializer typeSerializer) {
        if (this._valueTypeSerializer == typeSerializer) {
            return this;
        }
        _ensureOverride("_withValueTypeSerializer");
        return new MapSerializer(this, typeSerializer, this._suppressableValue, this._suppressNulls);
    }

    public boolean isEmpty(SerializerProvider serializerProvider, Map<?, ?> map) {
        if (map.isEmpty()) {
            return true;
        }
        Object obj = this._suppressableValue;
        if (obj == null && !this._suppressNulls) {
            return false;
        }
        JsonSerializer<Object> jsonSerializer = this._valueSerializer;
        boolean z = MARKER_FOR_EMPTY == obj;
        if (jsonSerializer != null) {
            for (Object next : map.values()) {
                if (next == null) {
                    if (!this._suppressNulls) {
                        return false;
                    }
                } else if (z) {
                    if (!jsonSerializer.isEmpty(serializerProvider, next)) {
                        return false;
                    }
                } else if (obj == null || !obj.equals(map)) {
                    return false;
                }
            }
            return true;
        }
        for (Object next2 : map.values()) {
            if (next2 != null) {
                try {
                    JsonSerializer _findSerializer = _findSerializer(serializerProvider, next2);
                    if (!z) {
                        if (obj != null && obj.equals(map)) {
                        }
                        return false;
                    } else if (!_findSerializer.isEmpty(serializerProvider, next2)) {
                        return false;
                    }
                } catch (JsonMappingException unused) {
                }
            } else if (!this._suppressNulls) {
                return false;
            }
        }
        return true;
    }

    public void serialize(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject(map);
        if (!map.isEmpty()) {
            if (this._sortKeys || serializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
                map = _orderEntries(map, jsonGenerator, serializerProvider);
            }
            Map<?, ?> map2 = map;
            Object obj = this._filterId;
            if (obj != null) {
                PropertyFilter findPropertyFilter = findPropertyFilter(serializerProvider, obj, map2);
                if (findPropertyFilter != null) {
                    serializeFilteredFields(map2, jsonGenerator, serializerProvider, findPropertyFilter, this._suppressableValue);
                }
            }
            if (this._suppressableValue != null || this._suppressNulls) {
                serializeOptionalFields(map2, jsonGenerator, serializerProvider, this._suppressableValue);
            } else {
                JsonSerializer<Object> jsonSerializer = this._valueSerializer;
                if (jsonSerializer != null) {
                    serializeFieldsUsing(map2, jsonGenerator, serializerProvider, jsonSerializer);
                } else {
                    serializeFields(map2, jsonGenerator, serializerProvider);
                }
            }
        }
        jsonGenerator.writeEndObject();
    }

    public void serializeWithType(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        jsonGenerator.setCurrentValue(map);
        WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(map, JsonToken.START_OBJECT));
        if (!map.isEmpty()) {
            if (this._sortKeys || serializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
                map = _orderEntries(map, jsonGenerator, serializerProvider);
            }
            Map<?, ?> map2 = map;
            Object obj = this._filterId;
            if (obj != null) {
                PropertyFilter findPropertyFilter = findPropertyFilter(serializerProvider, obj, map2);
                if (findPropertyFilter != null) {
                    serializeFilteredFields(map2, jsonGenerator, serializerProvider, findPropertyFilter, this._suppressableValue);
                }
            }
            if (this._suppressableValue != null || this._suppressNulls) {
                serializeOptionalFields(map2, jsonGenerator, serializerProvider, this._suppressableValue);
            } else {
                JsonSerializer<Object> jsonSerializer = this._valueSerializer;
                if (jsonSerializer != null) {
                    serializeFieldsUsing(map2, jsonGenerator, serializerProvider, jsonSerializer);
                } else {
                    serializeFields(map2, jsonGenerator, serializerProvider);
                }
            }
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }

    /* access modifiers changed from: protected */
    public final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, JavaType javaType, SerializerProvider serializerProvider) throws JsonMappingException {
        SerializerAndMapResult findAndAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(javaType, serializerProvider, this._property);
        PropertySerializerMap propertySerializerMap2 = findAndAddSecondarySerializer.map;
        if (propertySerializerMap != propertySerializerMap2) {
            this._dynamicValueSerializers = propertySerializerMap2;
        }
        return findAndAddSecondarySerializer.serializer;
    }

    protected MapSerializer(MapSerializer mapSerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set) {
        super(Map.class, false);
        if (set == null || set.isEmpty()) {
            set = null;
        }
        this._ignoredEntries = set;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = beanProperty;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
        this._suppressableValue = mapSerializer._suppressableValue;
        this._suppressNulls = mapSerializer._suppressNulls;
    }

    protected MapSerializer(MapSerializer mapSerializer, TypeSerializer typeSerializer, Object obj, boolean z) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = mapSerializer._dynamicValueSerializers;
        this._property = mapSerializer._property;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
        this._suppressableValue = obj;
        this._suppressNulls = z;
    }

    protected MapSerializer(MapSerializer mapSerializer, Object obj, boolean z) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = mapSerializer._property;
        this._filterId = obj;
        this._sortKeys = z;
        this._suppressableValue = mapSerializer._suppressableValue;
        this._suppressNulls = mapSerializer._suppressNulls;
    }
}
