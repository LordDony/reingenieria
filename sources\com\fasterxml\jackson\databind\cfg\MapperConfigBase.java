package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.Serializable;
import java.text.DateFormat;

public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>> extends MapperConfig<T> implements Serializable {
    private static final int AUTO_DETECT_MASK = ((((MapperFeature.AUTO_DETECT_FIELDS.getMask() | MapperFeature.AUTO_DETECT_GETTERS.getMask()) | MapperFeature.AUTO_DETECT_IS_GETTERS.getMask()) | MapperFeature.AUTO_DETECT_SETTERS.getMask()) | MapperFeature.AUTO_DETECT_CREATORS.getMask());
    private static final int DEFAULT_MAPPER_FEATURES = MapperConfig.collectFeatureDefaults(MapperFeature.class);
    protected static final ConfigOverride EMPTY_OVERRIDE = ConfigOverride.empty();
    protected final ContextAttributes _attributes;
    protected final ConfigOverrides _configOverrides;
    protected final SimpleMixInResolver _mixIns;
    protected final PropertyName _rootName;
    protected final RootNameLookup _rootNames;
    protected final SubtypeResolver _subtypeResolver;
    protected final Class<?> _view;

    protected MapperConfigBase(BaseSettings baseSettings, SubtypeResolver subtypeResolver, SimpleMixInResolver simpleMixInResolver, RootNameLookup rootNameLookup, ConfigOverrides configOverrides) {
        super(baseSettings, DEFAULT_MAPPER_FEATURES);
        this._mixIns = simpleMixInResolver;
        this._subtypeResolver = subtypeResolver;
        this._rootNames = rootNameLookup;
        this._rootName = null;
        this._view = null;
        this._attributes = ContextAttributes.getEmpty();
        this._configOverrides = configOverrides;
    }

    /* access modifiers changed from: protected */
    public abstract T _withBase(BaseSettings baseSettings);

    /* access modifiers changed from: protected */
    public abstract T _withMapperFeatures(int i);

    public final Class<?> findMixInClassFor(Class<?> cls) {
        return this._mixIns.findMixInClassFor(cls);
    }

    public PropertyName findRootName(JavaType javaType) {
        PropertyName propertyName = this._rootName;
        if (propertyName != null) {
            return propertyName;
        }
        return this._rootNames.findRootName(javaType, (MapperConfig<?>) this);
    }

    public final Class<?> getActiveView() {
        return this._view;
    }

    public final ContextAttributes getAttributes() {
        return this._attributes;
    }

    public final ConfigOverride getConfigOverride(Class<?> cls) {
        ConfigOverride findOverride = this._configOverrides.findOverride(cls);
        return findOverride == null ? EMPTY_OVERRIDE : findOverride;
    }

    public final Value getDefaultInclusion(Class<?> cls, Class<?> cls2) {
        Value includeAsProperty = getConfigOverride(cls2).getIncludeAsProperty();
        Value defaultPropertyInclusion = getDefaultPropertyInclusion(cls);
        if (defaultPropertyInclusion == null) {
            return includeAsProperty;
        }
        return defaultPropertyInclusion.withOverrides(includeAsProperty);
    }

    public Boolean getDefaultMergeable() {
        return this._configOverrides.getDefaultMergeable();
    }

    public final JsonFormat.Value getDefaultPropertyFormat(Class<?> cls) {
        ConfigOverride findOverride = this._configOverrides.findOverride(cls);
        if (findOverride != null) {
            JsonFormat.Value format = findOverride.getFormat();
            if (format != null) {
                return format;
            }
        }
        return MapperConfig.EMPTY_FORMAT;
    }

    public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> cls) {
        ConfigOverride findOverride = this._configOverrides.findOverride(cls);
        if (findOverride != null) {
            JsonIgnoreProperties.Value ignorals = findOverride.getIgnorals();
            if (ignorals != null) {
                return ignorals;
            }
        }
        return null;
    }

    public final Value getDefaultPropertyInclusion() {
        return this._configOverrides.getDefaultInclusion();
    }

    public final JsonSetter.Value getDefaultSetterInfo() {
        return this._configOverrides.getDefaultSetterInfo();
    }

    public final VisibilityChecker<?> getDefaultVisibilityChecker() {
        VisibilityChecker<?> defaultVisibility = this._configOverrides.getDefaultVisibility();
        int i = this._mapperFeatures;
        int i2 = AUTO_DETECT_MASK;
        if ((i & i2) == i2) {
            return defaultVisibility;
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
            defaultVisibility = defaultVisibility.withFieldVisibility(Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
            defaultVisibility = defaultVisibility.withGetterVisibility(Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
            defaultVisibility = defaultVisibility.withIsGetterVisibility(Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
            defaultVisibility = defaultVisibility.withSetterVisibility(Visibility.NONE);
        }
        return !isEnabled(MapperFeature.AUTO_DETECT_CREATORS) ? defaultVisibility.withCreatorVisibility(Visibility.NONE) : defaultVisibility;
    }

    public final PropertyName getFullRootName() {
        return this._rootName;
    }

    public final SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }

    public final T with(MapperFeature... mapperFeatureArr) {
        int i = this._mapperFeatures;
        for (MapperFeature mask : mapperFeatureArr) {
            i |= mask.getMask();
        }
        if (i == this._mapperFeatures) {
            return this;
        }
        return _withMapperFeatures(i);
    }

    public final T withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return _withBase(this._base.withAppendedAnnotationIntrospector(annotationIntrospector));
    }

    public final T withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return _withBase(this._base.withInsertedAnnotationIntrospector(annotationIntrospector));
    }

    public final T without(MapperFeature... mapperFeatureArr) {
        int i = this._mapperFeatures;
        for (MapperFeature mask : mapperFeatureArr) {
            i &= ~mask.getMask();
        }
        if (i == this._mapperFeatures) {
            return this;
        }
        return _withMapperFeatures(i);
    }

    public Boolean getDefaultMergeable(Class<?> cls) {
        ConfigOverride findOverride = this._configOverrides.findOverride(cls);
        if (findOverride != null) {
            Boolean mergeable = findOverride.getMergeable();
            if (mergeable != null) {
                return mergeable;
            }
        }
        return this._configOverrides.getDefaultMergeable();
    }

    public final Value getDefaultPropertyInclusion(Class<?> cls) {
        Value include = getConfigOverride(cls).getInclude();
        Value defaultPropertyInclusion = getDefaultPropertyInclusion();
        if (defaultPropertyInclusion == null) {
            return include;
        }
        return defaultPropertyInclusion.withOverrides(include);
    }

    public PropertyName findRootName(Class<?> cls) {
        PropertyName propertyName = this._rootName;
        if (propertyName != null) {
            return propertyName;
        }
        return this._rootNames.findRootName(cls, (MapperConfig<?>) this);
    }

    public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> cls, AnnotatedClass annotatedClass) {
        JsonIgnoreProperties.Value value;
        AnnotationIntrospector annotationIntrospector = getAnnotationIntrospector();
        if (annotationIntrospector == null) {
            value = null;
        } else {
            value = annotationIntrospector.findPropertyIgnorals(annotatedClass);
        }
        return JsonIgnoreProperties.Value.merge(value, getDefaultPropertyIgnorals(cls));
    }

    public final T with(PropertyNamingStrategy propertyNamingStrategy) {
        return _withBase(this._base.withPropertyNamingStrategy(propertyNamingStrategy));
    }

    public T with(DateFormat dateFormat) {
        return _withBase(this._base.withDateFormat(dateFormat));
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, BaseSettings baseSettings) {
        super((MapperConfig<T>) mapperConfigBase, baseSettings);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }

    public final VisibilityChecker<?> getDefaultVisibilityChecker(Class<?> cls, AnnotatedClass annotatedClass) {
        VisibilityChecker<?> defaultVisibilityChecker = getDefaultVisibilityChecker();
        AnnotationIntrospector annotationIntrospector = getAnnotationIntrospector();
        if (annotationIntrospector != null) {
            defaultVisibilityChecker = annotationIntrospector.findAutoDetectVisibility(annotatedClass, defaultVisibilityChecker);
        }
        ConfigOverride findOverride = this._configOverrides.findOverride(cls);
        return findOverride != null ? defaultVisibilityChecker.withOverrides(findOverride.getVisibility()) : defaultVisibilityChecker;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, int i) {
        super((MapperConfig<T>) mapperConfigBase, i);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
    }
}
