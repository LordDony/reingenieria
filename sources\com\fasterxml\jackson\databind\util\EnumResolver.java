package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class EnumResolver implements Serializable {
    protected final Enum<?> _defaultValue;
    protected final Class<Enum<?>> _enumClass;
    protected final Enum<?>[] _enums;
    protected final HashMap<String, Enum<?>> _enumsById;

    protected EnumResolver(Class<Enum<?>> cls, Enum<?>[] enumArr, HashMap<String, Enum<?>> hashMap, Enum<?> enumR) {
        this._enumClass = cls;
        this._enums = enumArr;
        this._enumsById = hashMap;
        this._defaultValue = enumR;
    }

    public static EnumResolver constructFor(Class<Enum<?>> cls, AnnotationIntrospector annotationIntrospector) {
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        if (enumArr != null) {
            String[] findEnumValues = annotationIntrospector.findEnumValues(cls, enumArr, new String[enumArr.length]);
            HashMap hashMap = new HashMap();
            int length = enumArr.length;
            for (int i = 0; i < length; i++) {
                String str = findEnumValues[i];
                if (str == null) {
                    str = enumArr[i].name();
                }
                hashMap.put(str, enumArr[i]);
            }
            return new EnumResolver(cls, enumArr, hashMap, annotationIntrospector.findDefaultEnumValue(cls));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No enum constants for class ");
        sb.append(cls.getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static EnumResolver constructUnsafe(Class<?> cls, AnnotationIntrospector annotationIntrospector) {
        return constructFor(cls, annotationIntrospector);
    }

    public static EnumResolver constructUnsafeUsingMethod(Class<?> cls, AnnotatedMember annotatedMember, AnnotationIntrospector annotationIntrospector) {
        return constructUsingMethod(cls, annotatedMember, annotationIntrospector);
    }

    public static EnumResolver constructUnsafeUsingToString(Class<?> cls, AnnotationIntrospector annotationIntrospector) {
        return constructUsingToString(cls, annotationIntrospector);
    }

    public static EnumResolver constructUsingMethod(Class<Enum<?>> cls, AnnotatedMember annotatedMember, AnnotationIntrospector annotationIntrospector) {
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        HashMap hashMap = new HashMap();
        int length = enumArr.length;
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            Enum enumR = enumArr[length];
            try {
                Object value = annotatedMember.getValue(enumR);
                if (value != null) {
                    hashMap.put(value.toString(), enumR);
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to access @JsonValue of Enum value ");
                sb.append(enumR);
                sb.append(": ");
                sb.append(e.getMessage());
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return new EnumResolver(cls, enumArr, hashMap, annotationIntrospector != null ? annotationIntrospector.findDefaultEnumValue(cls) : null);
    }

    public static EnumResolver constructUsingToString(Class<Enum<?>> cls, AnnotationIntrospector annotationIntrospector) {
        Enum enumR;
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        HashMap hashMap = new HashMap();
        int length = enumArr.length;
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            Enum enumR2 = enumArr[length];
            hashMap.put(enumR2.toString(), enumR2);
        }
        if (annotationIntrospector == null) {
            enumR = null;
        } else {
            enumR = annotationIntrospector.findDefaultEnumValue(cls);
        }
        return new EnumResolver(cls, enumArr, hashMap, enumR);
    }

    public CompactStringObjectMap constructLookup() {
        return CompactStringObjectMap.construct(this._enumsById);
    }

    public Enum<?> findEnum(String str) {
        return (Enum) this._enumsById.get(str);
    }

    public Enum<?> getDefaultValue() {
        return this._defaultValue;
    }

    public Class<Enum<?>> getEnumClass() {
        return this._enumClass;
    }

    public Collection<String> getEnumIds() {
        return this._enumsById.keySet();
    }

    public Enum<?>[] getRawEnums() {
        return this._enums;
    }
}
