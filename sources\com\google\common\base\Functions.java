package com.google.common.base;

import java.io.Serializable;
import java.util.Map;

public final class Functions {

    private static class ConstantFunction<E> implements Function<Object, E>, Serializable {
        private static final long serialVersionUID = 0;
        private final E value;

        public ConstantFunction(E e) {
            this.value = e;
        }

        public E apply(Object obj) {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ConstantFunction)) {
                return false;
            }
            return Objects.equal(this.value, ((ConstantFunction) obj).value);
        }

        public int hashCode() {
            E e = this.value;
            if (e == null) {
                return 0;
            }
            return e.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.constant(");
            sb.append(this.value);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final V defaultValue;
        final Map<K, ? extends V> map;

        ForMapWithDefault(Map<K, ? extends V> map2, V v) {
            Preconditions.checkNotNull(map2);
            this.map = map2;
            this.defaultValue = v;
        }

        public V apply(K k) {
            V v = this.map.get(k);
            return (v != null || this.map.containsKey(k)) ? v : this.defaultValue;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ForMapWithDefault)) {
                return false;
            }
            ForMapWithDefault forMapWithDefault = (ForMapWithDefault) obj;
            if (!this.map.equals(forMapWithDefault.map) || !Objects.equal(this.defaultValue, forMapWithDefault.defaultValue)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forMap(");
            sb.append(this.map);
            sb.append(", defaultValue=");
            sb.append(this.defaultValue);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
        private static final long serialVersionUID = 0;
        private final Function<A, ? extends B> f;
        private final Function<B, C> g;

        public FunctionComposition(Function<B, C> function, Function<A, ? extends B> function2) {
            Preconditions.checkNotNull(function);
            this.g = function;
            Preconditions.checkNotNull(function2);
            this.f = function2;
        }

        public C apply(A a) {
            return this.g.apply(this.f.apply(a));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FunctionComposition)) {
                return false;
            }
            FunctionComposition functionComposition = (FunctionComposition) obj;
            if (!this.f.equals(functionComposition.f) || !this.g.equals(functionComposition.g)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.g.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.g);
            sb.append("(");
            sb.append(this.f);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, V> map;

        FunctionForMapNoDefault(Map<K, V> map2) {
            Preconditions.checkNotNull(map2);
            this.map = map2;
        }

        public V apply(K k) {
            V v = this.map.get(k);
            Preconditions.checkArgument(v != null || this.map.containsKey(k), "Key '%s' not present in map", (Object) k);
            return v;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FunctionForMapNoDefault)) {
                return false;
            }
            return this.map.equals(((FunctionForMapNoDefault) obj).map);
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forMap(");
            sb.append(this.map);
            sb.append(")");
            return sb.toString();
        }
    }

    private enum IdentityFunction implements Function<Object, Object> {
        INSTANCE;

        public Object apply(Object obj) {
            return obj;
        }

        public String toString() {
            return "Functions.identity()";
        }
    }

    private static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
        private static final long serialVersionUID = 0;
        private final Predicate<T> predicate;

        public boolean equals(Object obj) {
            if (!(obj instanceof PredicateFunction)) {
                return false;
            }
            return this.predicate.equals(((PredicateFunction) obj).predicate);
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forPredicate(");
            sb.append(this.predicate);
            sb.append(")");
            return sb.toString();
        }

        private PredicateFunction(Predicate<T> predicate2) {
            Preconditions.checkNotNull(predicate2);
            this.predicate = predicate2;
        }

        public Boolean apply(T t) {
            return Boolean.valueOf(this.predicate.apply(t));
        }
    }

    private static class SupplierFunction<T> implements Function<Object, T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Supplier<T> supplier;

        public T apply(Object obj) {
            return this.supplier.get();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SupplierFunction)) {
                return false;
            }
            return this.supplier.equals(((SupplierFunction) obj).supplier);
        }

        public int hashCode() {
            return this.supplier.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forSupplier(");
            sb.append(this.supplier);
            sb.append(")");
            return sb.toString();
        }

        private SupplierFunction(Supplier<T> supplier2) {
            Preconditions.checkNotNull(supplier2);
            this.supplier = supplier2;
        }
    }

    private enum ToStringFunction implements Function<Object, String> {
        INSTANCE;

        public String toString() {
            return "Functions.toStringFunction()";
        }

        public String apply(Object obj) {
            Preconditions.checkNotNull(obj);
            return obj.toString();
        }
    }

    private Functions() {
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> function, Function<A, ? extends B> function2) {
        return new FunctionComposition(function, function2);
    }

    public static <E> Function<Object, E> constant(E e) {
        return new ConstantFunction(e);
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault(map);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate);
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier);
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, V v) {
        return new ForMapWithDefault(map, v);
    }
}
