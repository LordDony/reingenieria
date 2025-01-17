package defpackage;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/* renamed from: jD reason: default package and case insensitive filesystem */
/* compiled from: Collections2 */
public final class C3063jD {

    /* renamed from: jD$a */
    /* compiled from: Collections2 */
    static class a<E> extends AbstractCollection<E> {
        final Collection<E> a;
        final Predicate<? super E> b;

        a(Collection<E> collection, Predicate<? super E> predicate) {
            this.a = collection;
            this.b = predicate;
        }

        /* access modifiers changed from: 0000 */
        public a<E> a(Predicate<? super E> predicate) {
            return new a<>(this.a, Predicates.and(this.b, predicate));
        }

        public boolean add(E e) {
            Preconditions.checkArgument(this.b.apply(e));
            return this.a.add(e);
        }

        public boolean addAll(Collection<? extends E> collection) {
            for (Object apply : collection) {
                Preconditions.checkArgument(this.b.apply(apply));
            }
            return this.a.addAll(collection);
        }

        public void clear() {
            C1943BD.e(this.a, this.b);
        }

        public boolean contains(Object obj) {
            if (C3063jD.a(this.a, obj)) {
                return this.b.apply(obj);
            }
            return false;
        }

        public boolean containsAll(Collection<?> collection) {
            return C3063jD.a((Collection<?>) this, collection);
        }

        public boolean isEmpty() {
            return !C1943BD.a((Iterable<T>) this.a, this.b);
        }

        public Iterator<E> iterator() {
            return C2023FD.b(this.a.iterator(), this.b);
        }

        public boolean remove(Object obj) {
            return contains(obj) && this.a.remove(obj);
        }

        public boolean removeAll(Collection<?> collection) {
            Iterator it = this.a.iterator();
            boolean z = false;
            while (it.hasNext()) {
                Object next = it.next();
                if (this.b.apply(next) && collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            Iterator it = this.a.iterator();
            boolean z = false;
            while (it.hasNext()) {
                Object next = it.next();
                if (this.b.apply(next) && !collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public int size() {
            int i = 0;
            for (E apply : this.a) {
                if (this.b.apply(apply)) {
                    i++;
                }
            }
            return i;
        }

        public Object[] toArray() {
            return C2063HD.a(iterator()).toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return C2063HD.a(iterator()).toArray(tArr);
        }
    }

    /* renamed from: jD$b */
    /* compiled from: Collections2 */
    static class b<F, T> extends AbstractCollection<T> {
        final Collection<F> a;
        final Function<? super F, ? extends T> b;

        b(Collection<F> collection, Function<? super F, ? extends T> function) {
            Preconditions.checkNotNull(collection);
            this.a = collection;
            Preconditions.checkNotNull(function);
            this.b = function;
        }

        public void clear() {
            this.a.clear();
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        public Iterator<T> iterator() {
            return C2023FD.a(this.a.iterator(), this.b);
        }

        public int size() {
            return this.a.size();
        }
    }

    public static <E> Collection<E> a(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof a) {
            return ((a) collection).a(predicate);
        }
        Preconditions.checkNotNull(collection);
        Collection collection2 = collection;
        Preconditions.checkNotNull(predicate);
        return new a(collection2, predicate);
    }

    static boolean a(Collection<?> collection, Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    public static <F, T> Collection<T> a(Collection<F> collection, Function<? super F, T> function) {
        return new b(collection, function);
    }

    static boolean a(Collection<?> collection, Collection<?> collection2) {
        for (Object contains : collection2) {
            if (!collection.contains(contains)) {
                return false;
            }
        }
        return true;
    }

    static StringBuilder a(int i) {
        C3044iD.a(i, "size");
        return new StringBuilder((int) Math.min(((long) i) * 8, 1073741824));
    }

    static <T> Collection<T> a(Iterable<T> iterable) {
        return (Collection) iterable;
    }
}
