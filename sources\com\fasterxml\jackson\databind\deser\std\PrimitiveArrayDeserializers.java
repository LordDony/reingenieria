package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsFailProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.fasterxml.jackson.databind.util.ArrayBuilders.ByteBuilder;
import com.fasterxml.jackson.databind.util.ArrayBuilders.DoubleBuilder;
import com.fasterxml.jackson.databind.util.ArrayBuilders.FloatBuilder;
import com.fasterxml.jackson.databind.util.ArrayBuilders.IntBuilder;
import com.fasterxml.jackson.databind.util.ArrayBuilders.LongBuilder;
import com.fasterxml.jackson.databind.util.ArrayBuilders.ShortBuilder;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class PrimitiveArrayDeserializers<T> extends StdDeserializer<T> implements ContextualDeserializer {
    private transient Object _emptyValue;
    protected final NullValueProvider _nuller;
    protected final Boolean _unwrapSingle;

    @JacksonStdImpl
    static final class BooleanDeser extends PrimitiveArrayDeserializers<boolean[]> {
        public BooleanDeser() {
            super(boolean[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new BooleanDeser(this, nullValueProvider, bool);
        }

        protected BooleanDeser(BooleanDeser booleanDeser, NullValueProvider nullValueProvider, Boolean bool) {
            super(booleanDeser, nullValueProvider, bool);
        }

        /* access modifiers changed from: protected */
        public boolean[] _concat(boolean[] zArr, boolean[] zArr2) {
            int length = zArr.length;
            int length2 = zArr2.length;
            boolean[] copyOf = Arrays.copyOf(zArr, length + length2);
            System.arraycopy(zArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public boolean[] _constructEmpty() {
            return new boolean[0];
        }

        public boolean[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            boolean z;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return (boolean[]) handleNonArray(jsonParser, deserializationContext);
            }
            BooleanBuilder booleanBuilder = deserializationContext.getArrayBuilders().getBooleanBuilder();
            boolean[] zArr = (boolean[]) booleanBuilder.resetAndStart();
            int i = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return (boolean[]) booleanBuilder.completeAndClearBuffer(zArr, i);
                    }
                    if (nextToken == JsonToken.VALUE_TRUE) {
                        z = true;
                    } else {
                        if (nextToken != JsonToken.VALUE_FALSE) {
                            if (nextToken != JsonToken.VALUE_NULL) {
                                z = _parseBooleanPrimitive(jsonParser, deserializationContext);
                            } else if (this._nuller != null) {
                                this._nuller.getNullValue(deserializationContext);
                            } else {
                                _verifyNullForPrimitive(deserializationContext);
                            }
                        }
                        z = false;
                    }
                    if (i >= zArr.length) {
                        zArr = (boolean[]) booleanBuilder.appendCompletedChunk(zArr, i);
                        i = 0;
                    }
                    int i2 = i + 1;
                    try {
                        zArr[i] = z;
                        i = i2;
                    } catch (Exception e) {
                        e = e;
                        i = i2;
                        throw JsonMappingException.wrapWithPath((Throwable) e, (Object) zArr, booleanBuilder.bufferedSize() + i);
                    }
                } catch (Exception e2) {
                    e = e2;
                    throw JsonMappingException.wrapWithPath((Throwable) e, (Object) zArr, booleanBuilder.bufferedSize() + i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public boolean[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new boolean[]{_parseBooleanPrimitive(jsonParser, deserializationContext)};
        }
    }

    @JacksonStdImpl
    static final class ByteDeser extends PrimitiveArrayDeserializers<byte[]> {
        public ByteDeser() {
            super(byte[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new ByteDeser(this, nullValueProvider, bool);
        }

        protected ByteDeser(ByteDeser byteDeser, NullValueProvider nullValueProvider, Boolean bool) {
            super(byteDeser, nullValueProvider, bool);
        }

        /* access modifiers changed from: protected */
        public byte[] _concat(byte[] bArr, byte[] bArr2) {
            int length = bArr.length;
            int length2 = bArr2.length;
            byte[] copyOf = Arrays.copyOf(bArr, length + length2);
            System.arraycopy(bArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public byte[] _constructEmpty() {
            return new byte[0];
        }

        /* JADX WARNING: Removed duplicated region for block: B:43:0x008d A[Catch:{ Exception -> 0x00a5 }] */
        public byte[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            byte b;
            int i;
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_STRING) {
                try {
                    return jsonParser.getBinaryValue(deserializationContext.getBase64Variant());
                } catch (JsonParseException e) {
                    String originalMessage = e.getOriginalMessage();
                    if (originalMessage.contains("base64")) {
                        return (byte[]) deserializationContext.handleWeirdStringValue(byte[].class, jsonParser.getText(), originalMessage, new Object[0]);
                    }
                }
            }
            if (currentToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                Object embeddedObject = jsonParser.getEmbeddedObject();
                if (embeddedObject == null) {
                    return null;
                }
                if (embeddedObject instanceof byte[]) {
                    return (byte[]) embeddedObject;
                }
            }
            if (!jsonParser.isExpectedStartArrayToken()) {
                return (byte[]) handleNonArray(jsonParser, deserializationContext);
            }
            ByteBuilder byteBuilder = deserializationContext.getArrayBuilders().getByteBuilder();
            byte[] bArr = (byte[]) byteBuilder.resetAndStart();
            int i2 = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return (byte[]) byteBuilder.completeAndClearBuffer(bArr, i2);
                    }
                    if (nextToken != JsonToken.VALUE_NUMBER_INT) {
                        if (nextToken != JsonToken.VALUE_NUMBER_FLOAT) {
                            if (nextToken != JsonToken.VALUE_NULL) {
                                b = _parseBytePrimitive(jsonParser, deserializationContext);
                            } else if (this._nuller != null) {
                                this._nuller.getNullValue(deserializationContext);
                            } else {
                                _verifyNullForPrimitive(deserializationContext);
                                b = 0;
                            }
                            if (i2 >= bArr.length) {
                                bArr = (byte[]) byteBuilder.appendCompletedChunk(bArr, i2);
                                i2 = 0;
                            }
                            i = i2 + 1;
                            bArr[i2] = b;
                            i2 = i;
                        }
                    }
                    b = jsonParser.getByteValue();
                    if (i2 >= bArr.length) {
                    }
                    i = i2 + 1;
                    try {
                        bArr[i2] = b;
                        i2 = i;
                    } catch (Exception e2) {
                        e = e2;
                        i2 = i;
                        throw JsonMappingException.wrapWithPath((Throwable) e, (Object) bArr, byteBuilder.bufferedSize() + i2);
                    }
                } catch (Exception e3) {
                    e = e3;
                    throw JsonMappingException.wrapWithPath((Throwable) e, (Object) bArr, byteBuilder.bufferedSize() + i2);
                }
            }
        }

        /* access modifiers changed from: protected */
        public byte[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            byte b;
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                b = jsonParser.getByteValue();
            } else if (currentToken == JsonToken.VALUE_NULL) {
                NullValueProvider nullValueProvider = this._nuller;
                if (nullValueProvider != null) {
                    nullValueProvider.getNullValue(deserializationContext);
                    return (byte[]) getEmptyValue(deserializationContext);
                }
                _verifyNullForPrimitive(deserializationContext);
                return null;
            } else {
                b = ((Number) deserializationContext.handleUnexpectedToken(this._valueClass.getComponentType(), jsonParser)).byteValue();
            }
            return new byte[]{b};
        }
    }

    @JacksonStdImpl
    static final class CharDeser extends PrimitiveArrayDeserializers<char[]> {
        public CharDeser() {
            super(char[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return this;
        }

        /* access modifiers changed from: protected */
        public char[] _concat(char[] cArr, char[] cArr2) {
            int length = cArr.length;
            int length2 = cArr2.length;
            char[] copyOf = Arrays.copyOf(cArr, length + length2);
            System.arraycopy(cArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public char[] _constructEmpty() {
            return new char[0];
        }

        public char[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String str;
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_STRING) {
                char[] textCharacters = jsonParser.getTextCharacters();
                int textOffset = jsonParser.getTextOffset();
                int textLength = jsonParser.getTextLength();
                char[] cArr = new char[textLength];
                System.arraycopy(textCharacters, textOffset, cArr, 0, textLength);
                return cArr;
            } else if (jsonParser.isExpectedStartArrayToken()) {
                StringBuilder sb = new StringBuilder(64);
                while (true) {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return sb.toString().toCharArray();
                    }
                    if (nextToken == JsonToken.VALUE_STRING) {
                        str = jsonParser.getText();
                    } else if (nextToken == JsonToken.VALUE_NULL) {
                        NullValueProvider nullValueProvider = this._nuller;
                        if (nullValueProvider != null) {
                            nullValueProvider.getNullValue(deserializationContext);
                        } else {
                            _verifyNullForPrimitive(deserializationContext);
                            str = "\u0000";
                        }
                    } else {
                        str = ((CharSequence) deserializationContext.handleUnexpectedToken(Character.TYPE, jsonParser)).toString();
                    }
                    if (str.length() == 1) {
                        sb.append(str.charAt(0));
                    } else {
                        deserializationContext.reportInputMismatch((JsonDeserializer<?>) this, "Cannot convert a JSON String of length %d into a char element of char array", Integer.valueOf(str.length()));
                        throw null;
                    }
                }
            } else {
                if (currentToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                    Object embeddedObject = jsonParser.getEmbeddedObject();
                    if (embeddedObject == null) {
                        return null;
                    }
                    if (embeddedObject instanceof char[]) {
                        return (char[]) embeddedObject;
                    }
                    if (embeddedObject instanceof String) {
                        return ((String) embeddedObject).toCharArray();
                    }
                    if (embeddedObject instanceof byte[]) {
                        return Base64Variants.getDefaultVariant().encode((byte[]) embeddedObject, false).toCharArray();
                    }
                }
                return (char[]) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }

        /* access modifiers changed from: protected */
        public char[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return (char[]) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
        }
    }

    @JacksonStdImpl
    static final class DoubleDeser extends PrimitiveArrayDeserializers<double[]> {
        public DoubleDeser() {
            super(double[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new DoubleDeser(this, nullValueProvider, bool);
        }

        protected DoubleDeser(DoubleDeser doubleDeser, NullValueProvider nullValueProvider, Boolean bool) {
            super(doubleDeser, nullValueProvider, bool);
        }

        /* access modifiers changed from: protected */
        public double[] _concat(double[] dArr, double[] dArr2) {
            int length = dArr.length;
            int length2 = dArr2.length;
            double[] copyOf = Arrays.copyOf(dArr, length + length2);
            System.arraycopy(dArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public double[] _constructEmpty() {
            return new double[0];
        }

        public double[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return (double[]) handleNonArray(jsonParser, deserializationContext);
            }
            DoubleBuilder doubleBuilder = deserializationContext.getArrayBuilders().getDoubleBuilder();
            double[] dArr = (double[]) doubleBuilder.resetAndStart();
            int i = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return (double[]) doubleBuilder.completeAndClearBuffer(dArr, i);
                    }
                    if (nextToken != JsonToken.VALUE_NULL || this._nuller == null) {
                        double _parseDoublePrimitive = _parseDoublePrimitive(jsonParser, deserializationContext);
                        if (i >= dArr.length) {
                            dArr = (double[]) doubleBuilder.appendCompletedChunk(dArr, i);
                            i = 0;
                        }
                        int i2 = i + 1;
                        try {
                            dArr[i] = _parseDoublePrimitive;
                            i = i2;
                        } catch (Exception e) {
                            e = e;
                            i = i2;
                            throw JsonMappingException.wrapWithPath((Throwable) e, (Object) dArr, doubleBuilder.bufferedSize() + i);
                        }
                    } else {
                        this._nuller.getNullValue(deserializationContext);
                    }
                } catch (Exception e2) {
                    e = e2;
                    throw JsonMappingException.wrapWithPath((Throwable) e, (Object) dArr, doubleBuilder.bufferedSize() + i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public double[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new double[]{_parseDoublePrimitive(jsonParser, deserializationContext)};
        }
    }

    @JacksonStdImpl
    static final class FloatDeser extends PrimitiveArrayDeserializers<float[]> {
        public FloatDeser() {
            super(float[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new FloatDeser(this, nullValueProvider, bool);
        }

        protected FloatDeser(FloatDeser floatDeser, NullValueProvider nullValueProvider, Boolean bool) {
            super(floatDeser, nullValueProvider, bool);
        }

        /* access modifiers changed from: protected */
        public float[] _concat(float[] fArr, float[] fArr2) {
            int length = fArr.length;
            int length2 = fArr2.length;
            float[] copyOf = Arrays.copyOf(fArr, length + length2);
            System.arraycopy(fArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public float[] _constructEmpty() {
            return new float[0];
        }

        public float[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return (float[]) handleNonArray(jsonParser, deserializationContext);
            }
            FloatBuilder floatBuilder = deserializationContext.getArrayBuilders().getFloatBuilder();
            float[] fArr = (float[]) floatBuilder.resetAndStart();
            int i = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return (float[]) floatBuilder.completeAndClearBuffer(fArr, i);
                    }
                    if (nextToken != JsonToken.VALUE_NULL || this._nuller == null) {
                        float _parseFloatPrimitive = _parseFloatPrimitive(jsonParser, deserializationContext);
                        if (i >= fArr.length) {
                            fArr = (float[]) floatBuilder.appendCompletedChunk(fArr, i);
                            i = 0;
                        }
                        int i2 = i + 1;
                        try {
                            fArr[i] = _parseFloatPrimitive;
                            i = i2;
                        } catch (Exception e) {
                            e = e;
                            i = i2;
                            throw JsonMappingException.wrapWithPath((Throwable) e, (Object) fArr, floatBuilder.bufferedSize() + i);
                        }
                    } else {
                        this._nuller.getNullValue(deserializationContext);
                    }
                } catch (Exception e2) {
                    e = e2;
                    throw JsonMappingException.wrapWithPath((Throwable) e, (Object) fArr, floatBuilder.bufferedSize() + i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public float[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new float[]{_parseFloatPrimitive(jsonParser, deserializationContext)};
        }
    }

    @JacksonStdImpl
    static final class IntDeser extends PrimitiveArrayDeserializers<int[]> {
        public static final IntDeser instance = new IntDeser();

        public IntDeser() {
            super(int[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new IntDeser(this, nullValueProvider, bool);
        }

        protected IntDeser(IntDeser intDeser, NullValueProvider nullValueProvider, Boolean bool) {
            super(intDeser, nullValueProvider, bool);
        }

        /* access modifiers changed from: protected */
        public int[] _concat(int[] iArr, int[] iArr2) {
            int length = iArr.length;
            int length2 = iArr2.length;
            int[] copyOf = Arrays.copyOf(iArr, length + length2);
            System.arraycopy(iArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public int[] _constructEmpty() {
            return new int[0];
        }

        public int[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            int i;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return (int[]) handleNonArray(jsonParser, deserializationContext);
            }
            IntBuilder intBuilder = deserializationContext.getArrayBuilders().getIntBuilder();
            int[] iArr = (int[]) intBuilder.resetAndStart();
            int i2 = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return (int[]) intBuilder.completeAndClearBuffer(iArr, i2);
                    }
                    if (nextToken == JsonToken.VALUE_NUMBER_INT) {
                        i = jsonParser.getIntValue();
                    } else if (nextToken != JsonToken.VALUE_NULL) {
                        i = _parseIntPrimitive(jsonParser, deserializationContext);
                    } else if (this._nuller != null) {
                        this._nuller.getNullValue(deserializationContext);
                    } else {
                        _verifyNullForPrimitive(deserializationContext);
                        i = 0;
                    }
                    if (i2 >= iArr.length) {
                        iArr = (int[]) intBuilder.appendCompletedChunk(iArr, i2);
                        i2 = 0;
                    }
                    int i3 = i2 + 1;
                    try {
                        iArr[i2] = i;
                        i2 = i3;
                    } catch (Exception e) {
                        e = e;
                        i2 = i3;
                        throw JsonMappingException.wrapWithPath((Throwable) e, (Object) iArr, intBuilder.bufferedSize() + i2);
                    }
                } catch (Exception e2) {
                    e = e2;
                    throw JsonMappingException.wrapWithPath((Throwable) e, (Object) iArr, intBuilder.bufferedSize() + i2);
                }
            }
        }

        /* access modifiers changed from: protected */
        public int[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new int[]{_parseIntPrimitive(jsonParser, deserializationContext)};
        }
    }

    @JacksonStdImpl
    static final class LongDeser extends PrimitiveArrayDeserializers<long[]> {
        public static final LongDeser instance = new LongDeser();

        public LongDeser() {
            super(long[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new LongDeser(this, nullValueProvider, bool);
        }

        protected LongDeser(LongDeser longDeser, NullValueProvider nullValueProvider, Boolean bool) {
            super(longDeser, nullValueProvider, bool);
        }

        /* access modifiers changed from: protected */
        public long[] _concat(long[] jArr, long[] jArr2) {
            int length = jArr.length;
            int length2 = jArr2.length;
            long[] copyOf = Arrays.copyOf(jArr, length + length2);
            System.arraycopy(jArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public long[] _constructEmpty() {
            return new long[0];
        }

        public long[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            long j;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return (long[]) handleNonArray(jsonParser, deserializationContext);
            }
            LongBuilder longBuilder = deserializationContext.getArrayBuilders().getLongBuilder();
            long[] jArr = (long[]) longBuilder.resetAndStart();
            int i = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return (long[]) longBuilder.completeAndClearBuffer(jArr, i);
                    }
                    if (nextToken == JsonToken.VALUE_NUMBER_INT) {
                        j = jsonParser.getLongValue();
                    } else if (nextToken != JsonToken.VALUE_NULL) {
                        j = _parseLongPrimitive(jsonParser, deserializationContext);
                    } else if (this._nuller != null) {
                        this._nuller.getNullValue(deserializationContext);
                    } else {
                        _verifyNullForPrimitive(deserializationContext);
                        j = 0;
                    }
                    if (i >= jArr.length) {
                        jArr = (long[]) longBuilder.appendCompletedChunk(jArr, i);
                        i = 0;
                    }
                    int i2 = i + 1;
                    try {
                        jArr[i] = j;
                        i = i2;
                    } catch (Exception e) {
                        e = e;
                        i = i2;
                        throw JsonMappingException.wrapWithPath((Throwable) e, (Object) jArr, longBuilder.bufferedSize() + i);
                    }
                } catch (Exception e2) {
                    e = e2;
                    throw JsonMappingException.wrapWithPath((Throwable) e, (Object) jArr, longBuilder.bufferedSize() + i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public long[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new long[]{_parseLongPrimitive(jsonParser, deserializationContext)};
        }
    }

    @JacksonStdImpl
    static final class ShortDeser extends PrimitiveArrayDeserializers<short[]> {
        public ShortDeser() {
            super(short[].class);
        }

        /* access modifiers changed from: protected */
        public PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new ShortDeser(this, nullValueProvider, bool);
        }

        protected ShortDeser(ShortDeser shortDeser, NullValueProvider nullValueProvider, Boolean bool) {
            super(shortDeser, nullValueProvider, bool);
        }

        /* access modifiers changed from: protected */
        public short[] _concat(short[] sArr, short[] sArr2) {
            int length = sArr.length;
            int length2 = sArr2.length;
            short[] copyOf = Arrays.copyOf(sArr, length + length2);
            System.arraycopy(sArr2, 0, copyOf, length, length2);
            return copyOf;
        }

        /* access modifiers changed from: protected */
        public short[] _constructEmpty() {
            return new short[0];
        }

        public short[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            short s;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return (short[]) handleNonArray(jsonParser, deserializationContext);
            }
            ShortBuilder shortBuilder = deserializationContext.getArrayBuilders().getShortBuilder();
            short[] sArr = (short[]) shortBuilder.resetAndStart();
            int i = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return (short[]) shortBuilder.completeAndClearBuffer(sArr, i);
                    }
                    if (nextToken != JsonToken.VALUE_NULL) {
                        s = _parseShortPrimitive(jsonParser, deserializationContext);
                    } else if (this._nuller != null) {
                        this._nuller.getNullValue(deserializationContext);
                    } else {
                        _verifyNullForPrimitive(deserializationContext);
                        s = 0;
                    }
                    if (i >= sArr.length) {
                        sArr = (short[]) shortBuilder.appendCompletedChunk(sArr, i);
                        i = 0;
                    }
                    int i2 = i + 1;
                    try {
                        sArr[i] = s;
                        i = i2;
                    } catch (Exception e) {
                        e = e;
                        i = i2;
                        throw JsonMappingException.wrapWithPath((Throwable) e, (Object) sArr, shortBuilder.bufferedSize() + i);
                    }
                } catch (Exception e2) {
                    e = e2;
                    throw JsonMappingException.wrapWithPath((Throwable) e, (Object) sArr, shortBuilder.bufferedSize() + i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public short[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new short[]{_parseShortPrimitive(jsonParser, deserializationContext)};
        }
    }

    protected PrimitiveArrayDeserializers(Class<T> cls) {
        super(cls);
        this._unwrapSingle = null;
        this._nuller = null;
    }

    public static JsonDeserializer<?> forType(Class<?> cls) {
        if (cls == Integer.TYPE) {
            return IntDeser.instance;
        }
        if (cls == Long.TYPE) {
            return LongDeser.instance;
        }
        if (cls == Byte.TYPE) {
            return new ByteDeser();
        }
        if (cls == Short.TYPE) {
            return new ShortDeser();
        }
        if (cls == Float.TYPE) {
            return new FloatDeser();
        }
        if (cls == Double.TYPE) {
            return new DoubleDeser();
        }
        if (cls == Boolean.TYPE) {
            return new BooleanDeser();
        }
        if (cls == Character.TYPE) {
            return new CharDeser();
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public abstract T _concat(T t, T t2);

    /* access modifiers changed from: protected */
    public abstract T _constructEmpty();

    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        Boolean findFormatFeature = findFormatFeature(deserializationContext, beanProperty, this._valueClass, Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        Nulls findContentNullStyle = findContentNullStyle(deserializationContext, beanProperty);
        NullValueProvider nullValueProvider = findContentNullStyle == Nulls.SKIP ? NullsConstantProvider.skipper() : findContentNullStyle == Nulls.FAIL ? beanProperty == null ? NullsFailProvider.constructForRootValue(deserializationContext.constructType(this._valueClass)) : NullsFailProvider.constructForProperty(beanProperty) : null;
        if (findFormatFeature == this._unwrapSingle && nullValueProvider == this._nuller) {
            return this;
        }
        return withResolved(nullValueProvider, findFormatFeature);
    }

    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, T t) throws IOException {
        T deserialize = deserialize(jsonParser, deserializationContext);
        if (t == null || Array.getLength(t) == 0) {
            return deserialize;
        }
        return _concat(t, deserialize);
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.CONSTANT;
    }

    public Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
        Object obj = this._emptyValue;
        if (obj != null) {
            return obj;
        }
        Object _constructEmpty = _constructEmpty();
        this._emptyValue = _constructEmpty;
        return _constructEmpty;
    }

    /* access modifiers changed from: protected */
    public T handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING) && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
            return null;
        }
        Boolean bool = this._unwrapSingle;
        if (bool == Boolean.TRUE || (bool == null && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))) {
            return handleSingleElementUnwrapped(jsonParser, deserializationContext);
        }
        return deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
    }

    /* access modifiers changed from: protected */
    public abstract T handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

    public Boolean supportsUpdate(DeserializationConfig deserializationConfig) {
        return Boolean.TRUE;
    }

    /* access modifiers changed from: protected */
    public abstract PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool);

    protected PrimitiveArrayDeserializers(PrimitiveArrayDeserializers<?> primitiveArrayDeserializers, NullValueProvider nullValueProvider, Boolean bool) {
        super(primitiveArrayDeserializers._valueClass);
        this._unwrapSingle = bool;
        this._nuller = nullValueProvider;
    }
}
