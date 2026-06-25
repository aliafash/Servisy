package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMutableSet;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: ScatterMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00005\n\u0000\n\u0002\u0010#\n\u0002\u0010'\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010)\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\u0001J\u001c\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0002H\u0016J\"\u0010\n\u001a\u00020\b2\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\u001d\u0010\u000f\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0002H\u0096\u0002J\"\u0010\u0010\u001a\u00020\b2\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\fH\u0016J\b\u0010\u0011\u001a\u00020\bH\u0016J\u001b\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\u0013H\u0096\u0002J\u001c\u0010\u0014\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0002H\u0016J\"\u0010\u0015\u001a\u00020\b2\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\fH\u0016J\"\u0010\u0016\u001a\u00020\b2\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0017"}, d2 = {"androidx/collection/MutableScatterMap$MutableMapWrapper$entries$1", "", "", "size", "", "getSize", "()I", "add", "", "element", "addAll", "elements", "", "clear", "", "contains", "containsAll", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "collection"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class MutableScatterMap$MutableMapWrapper$entries$1<K, V> implements Set<Map.Entry<K, V>>, KMutableSet {
    final /* synthetic */ MutableScatterMap<K, V> this$0;

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) CollectionToArray.toArray(this, array);
    }

    MutableScatterMap$MutableMapWrapper$entries$1(MutableScatterMap<K, V> mutableScatterMap) {
        this.this$0 = mutableScatterMap;
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ boolean contains(Object element) {
        if (TypeIntrinsics.isMutableMapEntry(element)) {
            return contains((Map.Entry) element);
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ boolean remove(Object element) {
        if (TypeIntrinsics.isMutableMapEntry(element)) {
            return remove((Map.Entry) element);
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    public int getSize() {
        return this.this$0._size;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.this$0.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return new MutableScatterMap$MutableMapWrapper$entries$1$iterator$1(this.this$0);
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.this$0.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection<? extends Object> collection = elements;
        MutableScatterMap<K, V> mutableScatterMap = this.this$0;
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!Intrinsics.areEqual(mutableScatterMap.get((K) entry.getKey()), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(Map.Entry<K, V> element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return Intrinsics.areEqual(this.this$0.get(element.getKey()), element.getValue());
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends Map.Entry<K, V>> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(Map.Entry<K, V> element) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00a2 A[PHI: r1
      0x00a2: PHI (r1v4 'changed' boolean) = (r1v3 'changed' boolean), (r1v5 'changed' boolean) binds: [B:5:0x002d, B:28:0x00a0] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // java.util.Set, java.util.Collection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean retainAll(java.util.Collection<? extends java.lang.Object> r21) {
        /*
            r20 = this;
            r0 = r20
            java.lang.String r1 = "elements"
            r2 = r21
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r1)
            r1 = 0
            androidx.collection.MutableScatterMap<K, V> r3 = r0.this$0
            androidx.collection.ScatterMap r3 = (androidx.collection.ScatterMap) r3
            androidx.collection.MutableScatterMap<K, V> r4 = r0.this$0
            r5 = 0
            long[] r6 = r3.metadata
            int r7 = r6.length
            int r7 = r7 + (-2)
            r8 = 0
            if (r8 > r7) goto Laa
        L1a:
            r9 = r6[r8]
            r11 = r9
            r13 = 0
            long r14 = ~r11
            r16 = 7
            long r14 = r14 << r16
            long r14 = r14 & r11
            r16 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r11 = r14 & r16
            int r11 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
            if (r11 == 0) goto La2
            int r11 = r8 - r7
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = 0
        L39:
            if (r13 >= r11) goto L9f
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 0
            r17 = 128(0x80, double:6.3E-322)
            int r17 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r17 >= 0) goto L49
            r17 = 1
            goto L4b
        L49:
            r17 = 0
        L4b:
            if (r17 == 0) goto L96
            int r14 = r8 << 3
            int r14 = r14 + r13
            r15 = r14
            r16 = 0
            r17 = 0
            java.util.Iterator r18 = r21.iterator()
        L59:
            boolean r19 = r18.hasNext()
            if (r19 == 0) goto L8e
            java.lang.Object r19 = r18.next()
            java.util.Map$Entry r19 = (java.util.Map.Entry) r19
            java.lang.Object r12 = r19.getKey()
            java.lang.Object[] r0 = r4.keys
            r0 = r0[r15]
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r0)
            if (r0 == 0) goto L89
            java.lang.Object r0 = r19.getValue()
            java.lang.Object[] r12 = r4.values
            r12 = r12[r15]
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r12)
            if (r0 == 0) goto L84
            r17 = 1
            goto L8e
        L84:
            r12 = 8
            r0 = r20
            goto L59
        L89:
            r12 = 8
            r0 = r20
            goto L59
        L8e:
            if (r17 != 0) goto L94
            r4.removeValueAt(r15)
            r1 = 1
        L94:
        L96:
            r0 = 8
            long r9 = r9 >> r0
            int r13 = r13 + 1
            r12 = r0
            r0 = r20
            goto L39
        L9f:
            r0 = r12
            if (r11 != r0) goto Lab
        La2:
            if (r8 == r7) goto Laa
            int r8 = r8 + 1
            r0 = r20
            goto L1a
        Laa:
        Lab:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableScatterMap$MutableMapWrapper$entries$1.retainAll(java.util.Collection):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x009c A[PHI: r1
      0x009c: PHI (r1v4 'changed' boolean) = (r1v3 'changed' boolean), (r1v5 'changed' boolean) binds: [B:5:0x002d, B:26:0x009a] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // java.util.Set, java.util.Collection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean removeAll(java.util.Collection<? extends java.lang.Object> r20) {
        /*
            r19 = this;
            r0 = r19
            java.lang.String r1 = "elements"
            r2 = r20
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r1)
            r1 = 0
            androidx.collection.MutableScatterMap<K, V> r3 = r0.this$0
            androidx.collection.ScatterMap r3 = (androidx.collection.ScatterMap) r3
            androidx.collection.MutableScatterMap<K, V> r4 = r0.this$0
            r5 = 0
            long[] r6 = r3.metadata
            int r7 = r6.length
            int r7 = r7 + (-2)
            r8 = 0
            if (r8 > r7) goto La4
        L1a:
            r9 = r6[r8]
            r11 = r9
            r13 = 0
            long r14 = ~r11
            r16 = 7
            long r14 = r14 << r16
            long r14 = r14 & r11
            r16 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r11 = r14 & r16
            int r11 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
            if (r11 == 0) goto L9c
            int r11 = r8 - r7
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = 0
        L39:
            if (r13 >= r11) goto L99
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 0
            r17 = 128(0x80, double:6.3E-322)
            int r17 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r17 >= 0) goto L49
            r17 = 1
            goto L4b
        L49:
            r17 = 0
        L4b:
            if (r17 == 0) goto L90
            int r14 = r8 << 3
            int r14 = r14 + r13
            r15 = r14
            r16 = 0
            java.util.Iterator r17 = r20.iterator()
        L57:
            boolean r18 = r17.hasNext()
            if (r18 == 0) goto L8e
            java.lang.Object r18 = r17.next()
            java.util.Map$Entry r18 = (java.util.Map.Entry) r18
            java.lang.Object r12 = r18.getKey()
            java.lang.Object[] r0 = r4.keys
            r0 = r0[r15]
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r0)
            if (r0 == 0) goto L89
            java.lang.Object r0 = r18.getValue()
            java.lang.Object[] r12 = r4.values
            r12 = r12[r15]
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r12)
            if (r0 == 0) goto L84
            r4.removeValueAt(r15)
            r1 = 1
            goto L8e
        L84:
            r12 = 8
            r0 = r19
            goto L57
        L89:
            r12 = 8
            r0 = r19
            goto L57
        L8e:
        L90:
            r0 = 8
            long r9 = r9 >> r0
            int r13 = r13 + 1
            r12 = r0
            r0 = r19
            goto L39
        L99:
            r0 = r12
            if (r11 != r0) goto La5
        L9c:
            if (r8 == r7) goto La4
            int r8 = r8 + 1
            r0 = r19
            goto L1a
        La4:
        La5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableScatterMap$MutableMapWrapper$entries$1.removeAll(java.util.Collection):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a7, code lost:
    
        r22 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00b5, code lost:
    
        if (((((~r6) << 6) & r6) & (-9187201950435737472L)) == 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00b8, code lost:
    
        r11 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean remove(java.util.Map.Entry<K, V> r26) {
        /*
            Method dump skipped, instruction units count: 230
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableScatterMap$MutableMapWrapper$entries$1.remove(java.util.Map$Entry):boolean");
    }
}
