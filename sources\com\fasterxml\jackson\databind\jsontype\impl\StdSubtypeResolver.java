package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StdSubtypeResolver extends SubtypeResolver implements Serializable {
    protected LinkedHashSet<NamedType> _registeredSubtypes;

    /* access modifiers changed from: protected */
    public void _collectAndResolve(AnnotatedClass annotatedClass, NamedType namedType, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, HashMap<NamedType, NamedType> hashMap) {
        if (!namedType.hasName()) {
            String findTypeName = annotationIntrospector.findTypeName(annotatedClass);
            if (findTypeName != null) {
                namedType = new NamedType(namedType.getType(), findTypeName);
            }
        }
        if (hashMap.containsKey(namedType)) {
            if (namedType.hasName() && !((NamedType) hashMap.get(namedType)).hasName()) {
                hashMap.put(namedType, namedType);
            }
            return;
        }
        hashMap.put(namedType, namedType);
        List<NamedType> findSubtypes = annotationIntrospector.findSubtypes(annotatedClass);
        if (findSubtypes != null && !findSubtypes.isEmpty()) {
            for (NamedType namedType2 : findSubtypes) {
                _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, annotationIntrospector, hashMap);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void _collectAndResolveByTypeId(AnnotatedClass annotatedClass, NamedType namedType, MapperConfig<?> mapperConfig, Set<Class<?>> set, Map<String, NamedType> map) {
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        if (!namedType.hasName()) {
            String findTypeName = annotationIntrospector.findTypeName(annotatedClass);
            if (findTypeName != null) {
                namedType = new NamedType(namedType.getType(), findTypeName);
            }
        }
        if (namedType.hasName()) {
            map.put(namedType.getName(), namedType);
        }
        if (set.add(namedType.getType())) {
            List<NamedType> findSubtypes = annotationIntrospector.findSubtypes(annotatedClass);
            if (findSubtypes != null && !findSubtypes.isEmpty()) {
                for (NamedType namedType2 : findSubtypes) {
                    _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, set, map);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Collection<NamedType> _combineNamedAndUnnamed(Class<?> cls, Set<Class<?>> set, Map<String, NamedType> map) {
        ArrayList arrayList = new ArrayList(map.values());
        for (NamedType type : map.values()) {
            set.remove(type.getType());
        }
        for (Class<?> cls2 : set) {
            if (cls2 != cls || !Modifier.isAbstract(cls2.getModifiers())) {
                arrayList.add(new NamedType(cls2));
            }
        }
        return arrayList;
    }

    public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class rawType = javaType == null ? annotatedMember.getRawType() : javaType.getRawClass();
        HashMap hashMap = new HashMap();
        LinkedHashSet<NamedType> linkedHashSet = this._registeredSubtypes;
        if (linkedHashSet != null) {
            Iterator it = linkedHashSet.iterator();
            while (it.hasNext()) {
                NamedType namedType = (NamedType) it.next();
                if (rawType.isAssignableFrom(namedType.getType())) {
                    _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        if (annotatedMember != null) {
            List<NamedType> findSubtypes = annotationIntrospector.findSubtypes(annotatedMember);
            if (findSubtypes != null) {
                for (NamedType namedType2 : findSubtypes) {
                    _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, rawType), new NamedType(rawType, null), mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList(hashMap.values());
    }

    public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class rawClass = javaType.getRawClass();
        HashSet hashSet = new HashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, rawClass), new NamedType(rawClass, null), mapperConfig, hashSet, linkedHashMap);
        if (annotatedMember != null) {
            List<NamedType> findSubtypes = annotationIntrospector.findSubtypes(annotatedMember);
            if (findSubtypes != null) {
                for (NamedType namedType : findSubtypes) {
                    _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, hashSet, linkedHashMap);
                }
            }
        }
        LinkedHashSet<NamedType> linkedHashSet = this._registeredSubtypes;
        if (linkedHashSet != null) {
            Iterator it = linkedHashSet.iterator();
            while (it.hasNext()) {
                NamedType namedType2 = (NamedType) it.next();
                if (rawClass.isAssignableFrom(namedType2.getType())) {
                    _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, hashSet, linkedHashMap);
                }
            }
        }
        return _combineNamedAndUnnamed(rawClass, hashSet, linkedHashMap);
    }

    public void registerSubtypes(NamedType... namedTypeArr) {
        if (this._registeredSubtypes == null) {
            this._registeredSubtypes = new LinkedHashSet<>();
        }
        for (NamedType add : namedTypeArr) {
            this._registeredSubtypes.add(add);
        }
    }

    public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass) {
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        HashMap hashMap = new HashMap();
        if (this._registeredSubtypes != null) {
            Class rawType = annotatedClass.getRawType();
            Iterator it = this._registeredSubtypes.iterator();
            while (it.hasNext()) {
                NamedType namedType = (NamedType) it.next();
                if (rawType.isAssignableFrom(namedType.getType())) {
                    _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        _collectAndResolve(annotatedClass, new NamedType(annotatedClass.getRawType(), null), mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList(hashMap.values());
    }

    public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass) {
        Class rawType = annotatedClass.getRawType();
        HashSet hashSet = new HashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        _collectAndResolveByTypeId(annotatedClass, new NamedType(rawType, null), mapperConfig, hashSet, linkedHashMap);
        LinkedHashSet<NamedType> linkedHashSet = this._registeredSubtypes;
        if (linkedHashSet != null) {
            Iterator it = linkedHashSet.iterator();
            while (it.hasNext()) {
                NamedType namedType = (NamedType) it.next();
                if (rawType.isAssignableFrom(namedType.getType())) {
                    _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, hashSet, linkedHashMap);
                }
            }
        }
        return _combineNamedAndUnnamed(rawType, hashSet, linkedHashMap);
    }
}
