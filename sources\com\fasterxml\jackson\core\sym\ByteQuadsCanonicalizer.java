package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory.Feature;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class ByteQuadsCanonicalizer {
    /* access modifiers changed from: private */
    public int _count;
    private final boolean _failOnDoS;
    /* access modifiers changed from: private */
    public int[] _hashArea;
    private boolean _hashShared;
    /* access modifiers changed from: private */
    public int _hashSize;
    private boolean _intern;
    /* access modifiers changed from: private */
    public int _longNameOffset;
    /* access modifiers changed from: private */
    public String[] _names;
    private transient boolean _needRehash;
    private final ByteQuadsCanonicalizer _parent;
    private int _secondaryStart;
    private final int _seed;
    /* access modifiers changed from: private */
    public int _spilloverEnd;
    private final AtomicReference<TableInfo> _tableInfo;
    /* access modifiers changed from: private */
    public int _tertiaryShift;
    private int _tertiaryStart;

    private static final class TableInfo {
        public final int count;
        public final int longNameOffset;
        public final int[] mainHash;
        public final String[] names;
        public final int size;
        public final int spilloverEnd;
        public final int tertiaryShift;

        public TableInfo(int i, int i2, int i3, int[] iArr, String[] strArr, int i4, int i5) {
            this.size = i;
            this.count = i2;
            this.tertiaryShift = i3;
            this.mainHash = iArr;
            this.names = strArr;
            this.spilloverEnd = i4;
            this.longNameOffset = i5;
        }

        public static TableInfo createInitial(int i) {
            int i2 = i << 3;
            TableInfo tableInfo = new TableInfo(i, 0, ByteQuadsCanonicalizer._calcTertiaryShift(i), new int[i2], new String[(i << 1)], i2 - i, i2);
            return tableInfo;
        }

        public TableInfo(ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
            this.size = byteQuadsCanonicalizer._hashSize;
            this.count = byteQuadsCanonicalizer._count;
            this.tertiaryShift = byteQuadsCanonicalizer._tertiaryShift;
            this.mainHash = byteQuadsCanonicalizer._hashArea;
            this.names = byteQuadsCanonicalizer._names;
            this.spilloverEnd = byteQuadsCanonicalizer._spilloverEnd;
            this.longNameOffset = byteQuadsCanonicalizer._longNameOffset;
        }
    }

    private ByteQuadsCanonicalizer(int i, boolean z, int i2, boolean z2) {
        this._parent = null;
        this._seed = i2;
        this._intern = z;
        this._failOnDoS = z2;
        int i3 = 16;
        if (i >= 16) {
            if (((i - 1) & i) != 0) {
                while (i3 < i) {
                    i3 += i3;
                }
            } else {
                i3 = i;
            }
        }
        this._tableInfo = new AtomicReference<>(TableInfo.createInitial(i3));
    }

    private int _appendLongName(int[] iArr, int i) {
        int i2 = this._longNameOffset;
        int i3 = i2 + i;
        int[] iArr2 = this._hashArea;
        if (i3 > iArr2.length) {
            this._hashArea = Arrays.copyOf(this._hashArea, this._hashArea.length + Math.max(i3 - iArr2.length, Math.min(4096, this._hashSize)));
        }
        System.arraycopy(iArr, 0, this._hashArea, i2, i);
        this._longNameOffset += i;
        return i2;
    }

    private final int _calcOffset(int i) {
        return (i & (this._hashSize - 1)) << 2;
    }

    static int _calcTertiaryShift(int i) {
        int i2 = i >> 2;
        if (i2 < 64) {
            return 4;
        }
        if (i2 <= 256) {
            return 5;
        }
        return i2 <= 1024 ? 6 : 7;
    }

    private int _findOffsetForAdd(int i) {
        int _calcOffset = _calcOffset(i);
        int[] iArr = this._hashArea;
        if (iArr[_calcOffset + 3] == 0) {
            return _calcOffset;
        }
        int i2 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        if (iArr[i2 + 3] == 0) {
            return i2;
        }
        int i3 = this._tertiaryStart;
        int i4 = this._tertiaryShift;
        int i5 = i3 + ((_calcOffset >> (i4 + 2)) << i4);
        int i6 = (1 << i4) + i5;
        while (i5 < i6) {
            if (iArr[i5 + 3] == 0) {
                return i5;
            }
            i5 += 4;
        }
        int i7 = this._spilloverEnd;
        this._spilloverEnd = i7 + 4;
        if (this._spilloverEnd >= (this._hashSize << 3)) {
            if (this._failOnDoS) {
                _reportTooManyCollisions();
            }
            this._needRehash = true;
        }
        return i7;
    }

    private String _findSecondary(int i, int i2) {
        int i3 = this._tertiaryStart;
        int i4 = this._tertiaryShift;
        int i5 = i3 + ((i >> (i4 + 2)) << i4);
        int[] iArr = this._hashArea;
        int i6 = (1 << i4) + i5;
        while (i5 < i6) {
            int i7 = iArr[i5 + 3];
            if (i2 == iArr[i5] && 1 == i7) {
                return this._names[i5 >> 2];
            }
            if (i7 == 0) {
                return null;
            }
            i5 += 4;
        }
        for (int _spilloverStart = _spilloverStart(); _spilloverStart < this._spilloverEnd; _spilloverStart += 4) {
            if (i2 == iArr[_spilloverStart] && 1 == iArr[_spilloverStart + 3]) {
                return this._names[_spilloverStart >> 2];
            }
        }
        return null;
    }

    private final int _spilloverStart() {
        int i = this._hashSize;
        return (i << 3) - i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0021, code lost:
        if (r6[r7] == r0[r8]) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r8 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0027, code lost:
        r7 = r3 + 1;
        r4 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002f, code lost:
        if (r6[r3] == r0[r8]) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0032, code lost:
        r8 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0035, code lost:
        r3 = r7 + 1;
        r4 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003d, code lost:
        if (r6[r7] == r0[r8]) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0040, code lost:
        r8 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0043, code lost:
        r7 = r3 + 1;
        r4 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004b, code lost:
        if (r6[r3] == r0[r8]) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004d, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004e, code lost:
        r8 = r7 + 1;
        r3 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        if (r6[r7] == r0[r4]) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0058, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0059, code lost:
        r7 = r8 + 1;
        r4 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0061, code lost:
        if (r6[r8] == r0[r3]) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0063, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0068, code lost:
        if (r6[r7] == r0[r4]) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006a, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x006b, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        r3 = r7 + 1;
        r4 = r8 + 1;
     */
    private boolean _verifyLongName(int[] iArr, int i, int i2) {
        int i3;
        int[] iArr2 = this._hashArea;
        switch (i) {
            case 4:
                int i4 = 0;
                break;
            case 5:
                int i5 = 0;
                break;
            case 6:
                int i6 = 0;
                break;
            case 7:
                i3 = 0;
                break;
            case 8:
                int i7 = i2 + 1;
                if (iArr[0] == iArr2[i2]) {
                    i2 = i7;
                    i3 = 1;
                    break;
                } else {
                    return false;
                }
            default:
                return _verifyLongName2(iArr, i, i2);
        }
    }

    private boolean _verifyLongName2(int[] iArr, int i, int i2) {
        int i3 = i2;
        int i4 = 0;
        while (true) {
            int i5 = i4 + 1;
            int i6 = i3 + 1;
            if (iArr[i4] != this._hashArea[i3]) {
                return false;
            }
            if (i5 >= i) {
                return true;
            }
            i4 = i5;
            i3 = i6;
        }
    }

    private void _verifyNeedForRehash() {
        if (this._count > (this._hashSize >> 1)) {
            int _spilloverStart = (this._spilloverEnd - _spilloverStart()) >> 2;
            int i = this._count;
            if (_spilloverStart > ((i + 1) >> 7) || ((double) i) > ((double) this._hashSize) * 0.8d) {
                this._needRehash = true;
            }
        }
    }

    private void _verifySharing() {
        if (this._hashShared) {
            int[] iArr = this._hashArea;
            this._hashArea = Arrays.copyOf(iArr, iArr.length);
            String[] strArr = this._names;
            this._names = (String[]) Arrays.copyOf(strArr, strArr.length);
            this._hashShared = false;
            _verifyNeedForRehash();
        }
        if (this._needRehash) {
            rehash();
        }
    }

    public static ByteQuadsCanonicalizer createRoot() {
        long currentTimeMillis = System.currentTimeMillis();
        return createRoot((((int) currentTimeMillis) + ((int) (currentTimeMillis >>> 32))) | 1);
    }

    private void mergeChild(TableInfo tableInfo) {
        int i = tableInfo.count;
        TableInfo tableInfo2 = (TableInfo) this._tableInfo.get();
        if (i != tableInfo2.count) {
            if (i > 6000) {
                tableInfo = TableInfo.createInitial(64);
            }
            this._tableInfo.compareAndSet(tableInfo2, tableInfo);
        }
    }

    private void nukeSymbols(boolean z) {
        this._count = 0;
        this._spilloverEnd = _spilloverStart();
        this._longNameOffset = this._hashSize << 3;
        if (z) {
            Arrays.fill(this._hashArea, 0);
            Arrays.fill(this._names, null);
        }
    }

    private void rehash() {
        this._needRehash = false;
        this._hashShared = false;
        int[] iArr = this._hashArea;
        String[] strArr = this._names;
        int i = this._hashSize;
        int i2 = this._count;
        int i3 = i + i;
        int i4 = this._spilloverEnd;
        if (i3 > 65536) {
            nukeSymbols(true);
            return;
        }
        this._hashArea = new int[(iArr.length + (i << 3))];
        this._hashSize = i3;
        this._secondaryStart = i3 << 2;
        int i5 = this._secondaryStart;
        this._tertiaryStart = i5 + (i5 >> 1);
        this._tertiaryShift = _calcTertiaryShift(i3);
        this._names = new String[(strArr.length << 1)];
        nukeSymbols(false);
        int[] iArr2 = new int[16];
        int i6 = 0;
        for (int i7 = 0; i7 < i4; i7 += 4) {
            int i8 = iArr[i7 + 3];
            if (i8 != 0) {
                i6++;
                String str = strArr[i7 >> 2];
                if (i8 == 1) {
                    iArr2[0] = iArr[i7];
                    addName(str, iArr2, 1);
                } else if (i8 == 2) {
                    iArr2[0] = iArr[i7];
                    iArr2[1] = iArr[i7 + 1];
                    addName(str, iArr2, 2);
                } else if (i8 != 3) {
                    if (i8 > iArr2.length) {
                        iArr2 = new int[i8];
                    }
                    System.arraycopy(iArr, iArr[i7 + 1], iArr2, 0, i8);
                    addName(str, iArr2, i8);
                } else {
                    iArr2[0] = iArr[i7];
                    iArr2[1] = iArr[i7 + 1];
                    iArr2[2] = iArr[i7 + 2];
                    addName(str, iArr2, 3);
                }
            }
        }
        if (i6 != i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed rehash(): old count=");
            sb.append(i2);
            sb.append(", copyCount=");
            sb.append(i6);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void _reportTooManyCollisions() {
        if (this._hashSize > 1024) {
            StringBuilder sb = new StringBuilder();
            sb.append("Spill-over slots in symbol table with ");
            sb.append(this._count);
            sb.append(" entries, hash area of ");
            sb.append(this._hashSize);
            sb.append(" slots is now full (all ");
            sb.append(this._hashSize >> 3);
            sb.append(" slots -- suspect a DoS attack based on hash collisions. You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
            throw new IllegalStateException(sb.toString());
        }
    }

    public String addName(String str, int[] iArr, int i) {
        int i2;
        _verifySharing();
        if (this._intern) {
            str = InternCache.instance.intern(str);
        }
        if (i == 1) {
            i2 = _findOffsetForAdd(calcHash(iArr[0]));
            int[] iArr2 = this._hashArea;
            iArr2[i2] = iArr[0];
            iArr2[i2 + 3] = 1;
        } else if (i == 2) {
            i2 = _findOffsetForAdd(calcHash(iArr[0], iArr[1]));
            int[] iArr3 = this._hashArea;
            iArr3[i2] = iArr[0];
            iArr3[i2 + 1] = iArr[1];
            iArr3[i2 + 3] = 2;
        } else if (i != 3) {
            int calcHash = calcHash(iArr, i);
            i2 = _findOffsetForAdd(calcHash);
            this._hashArea[i2] = calcHash;
            int _appendLongName = _appendLongName(iArr, i);
            int[] iArr4 = this._hashArea;
            iArr4[i2 + 1] = _appendLongName;
            iArr4[i2 + 3] = i;
        } else {
            int _findOffsetForAdd = _findOffsetForAdd(calcHash(iArr[0], iArr[1], iArr[2]));
            int[] iArr5 = this._hashArea;
            iArr5[_findOffsetForAdd] = iArr[0];
            iArr5[_findOffsetForAdd + 1] = iArr[1];
            iArr5[_findOffsetForAdd + 2] = iArr[2];
            iArr5[_findOffsetForAdd + 3] = 3;
            i2 = _findOffsetForAdd;
        }
        this._names[i2 >> 2] = str;
        this._count++;
        _verifyNeedForRehash();
        return str;
    }

    public int calcHash(int i) {
        int i2 = i ^ this._seed;
        int i3 = i2 + (i2 >>> 16);
        int i4 = i3 ^ (i3 << 3);
        return i4 + (i4 >>> 12);
    }

    public String findName(int i) {
        int _calcOffset = _calcOffset(calcHash(i));
        int[] iArr = this._hashArea;
        int i2 = iArr[_calcOffset + 3];
        if (i2 == 1) {
            if (iArr[_calcOffset] == i) {
                return this._names[_calcOffset >> 2];
            }
        } else if (i2 == 0) {
            return null;
        }
        int i3 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        int i4 = iArr[i3 + 3];
        if (i4 == 1) {
            if (iArr[i3] == i) {
                return this._names[i3 >> 2];
            }
        } else if (i4 == 0) {
            return null;
        }
        return _findSecondary(_calcOffset, i);
    }

    public ByteQuadsCanonicalizer makeChild(int i) {
        ByteQuadsCanonicalizer byteQuadsCanonicalizer = new ByteQuadsCanonicalizer(this, Feature.INTERN_FIELD_NAMES.enabledIn(i), this._seed, Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(i), (TableInfo) this._tableInfo.get());
        return byteQuadsCanonicalizer;
    }

    public boolean maybeDirty() {
        return !this._hashShared;
    }

    public int primaryCount() {
        int i = this._secondaryStart;
        int i2 = 0;
        for (int i3 = 3; i3 < i; i3 += 4) {
            if (this._hashArea[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }

    public void release() {
        if (this._parent != null && maybeDirty()) {
            this._parent.mergeChild(new TableInfo(this));
            this._hashShared = true;
        }
    }

    public int secondaryCount() {
        int i = this._tertiaryStart;
        int i2 = 0;
        for (int i3 = this._secondaryStart + 3; i3 < i; i3 += 4) {
            if (this._hashArea[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }

    public int spilloverCount() {
        return (this._spilloverEnd - _spilloverStart()) >> 2;
    }

    public int tertiaryCount() {
        int i = this._tertiaryStart + 3;
        int i2 = this._hashSize + i;
        int i3 = 0;
        while (i < i2) {
            if (this._hashArea[i] != 0) {
                i3++;
            }
            i += 4;
        }
        return i3;
    }

    public String toString() {
        int primaryCount = primaryCount();
        int secondaryCount = secondaryCount();
        int tertiaryCount = tertiaryCount();
        int spilloverCount = spilloverCount();
        return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", new Object[]{ByteQuadsCanonicalizer.class.getName(), Integer.valueOf(this._count), Integer.valueOf(this._hashSize), Integer.valueOf(primaryCount), Integer.valueOf(secondaryCount), Integer.valueOf(tertiaryCount), Integer.valueOf(spilloverCount), Integer.valueOf(primaryCount + secondaryCount + tertiaryCount + spilloverCount), Integer.valueOf(totalCount())});
    }

    public int totalCount() {
        int i = this._hashSize << 3;
        int i2 = 0;
        for (int i3 = 3; i3 < i; i3 += 4) {
            if (this._hashArea[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }

    public int calcHash(int i, int i2) {
        int i3 = i + (i >>> 15);
        int i4 = ((i3 ^ (i3 >>> 9)) + (i2 * 33)) ^ this._seed;
        int i5 = i4 + (i4 >>> 16);
        int i6 = i5 ^ (i5 >>> 4);
        return i6 + (i6 << 3);
    }

    protected static ByteQuadsCanonicalizer createRoot(int i) {
        return new ByteQuadsCanonicalizer(64, true, i, true);
    }

    public int calcHash(int i, int i2, int i3) {
        int i4 = i ^ this._seed;
        int i5 = (((i4 + (i4 >>> 9)) * 31) + i2) * 33;
        int i6 = (i5 + (i5 >>> 15)) ^ i3;
        int i7 = i6 + (i6 >>> 4);
        int i8 = i7 + (i7 >>> 15);
        return i8 ^ (i8 << 9);
    }

    public int calcHash(int[] iArr, int i) {
        if (i >= 4) {
            int i2 = iArr[0] ^ this._seed;
            int i3 = i2 + (i2 >>> 9) + iArr[1];
            int i4 = ((i3 + (i3 >>> 15)) * 33) ^ iArr[2];
            int i5 = i4 + (i4 >>> 4);
            for (int i6 = 3; i6 < i; i6++) {
                int i7 = iArr[i6];
                i5 += i7 ^ (i7 >> 21);
            }
            int i8 = i5 * 65599;
            int i9 = i8 + (i8 >>> 19);
            return (i9 << 5) ^ i9;
        }
        throw new IllegalArgumentException();
    }

    private ByteQuadsCanonicalizer(ByteQuadsCanonicalizer byteQuadsCanonicalizer, boolean z, int i, boolean z2, TableInfo tableInfo) {
        this._parent = byteQuadsCanonicalizer;
        this._seed = i;
        this._intern = z;
        this._failOnDoS = z2;
        this._tableInfo = null;
        this._count = tableInfo.count;
        this._hashSize = tableInfo.size;
        this._secondaryStart = this._hashSize << 2;
        int i2 = this._secondaryStart;
        this._tertiaryStart = i2 + (i2 >> 1);
        this._tertiaryShift = tableInfo.tertiaryShift;
        this._hashArea = tableInfo.mainHash;
        this._names = tableInfo.names;
        this._spilloverEnd = tableInfo.spilloverEnd;
        this._longNameOffset = tableInfo.longNameOffset;
        this._needRehash = false;
        this._hashShared = true;
    }

    private String _findSecondary(int i, int i2, int i3) {
        int i4 = this._tertiaryStart;
        int i5 = this._tertiaryShift;
        int i6 = i4 + ((i >> (i5 + 2)) << i5);
        int[] iArr = this._hashArea;
        int i7 = (1 << i5) + i6;
        while (i6 < i7) {
            int i8 = iArr[i6 + 3];
            if (i2 == iArr[i6] && i3 == iArr[i6 + 1] && 2 == i8) {
                return this._names[i6 >> 2];
            }
            if (i8 == 0) {
                return null;
            }
            i6 += 4;
        }
        for (int _spilloverStart = _spilloverStart(); _spilloverStart < this._spilloverEnd; _spilloverStart += 4) {
            if (i2 == iArr[_spilloverStart] && i3 == iArr[_spilloverStart + 1] && 2 == iArr[_spilloverStart + 3]) {
                return this._names[_spilloverStart >> 2];
            }
        }
        return null;
    }

    public String findName(int i, int i2) {
        int _calcOffset = _calcOffset(calcHash(i, i2));
        int[] iArr = this._hashArea;
        int i3 = iArr[_calcOffset + 3];
        if (i3 == 2) {
            if (i == iArr[_calcOffset] && i2 == iArr[_calcOffset + 1]) {
                return this._names[_calcOffset >> 2];
            }
        } else if (i3 == 0) {
            return null;
        }
        int i4 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        int i5 = iArr[i4 + 3];
        if (i5 == 2) {
            if (i == iArr[i4] && i2 == iArr[i4 + 1]) {
                return this._names[i4 >> 2];
            }
        } else if (i5 == 0) {
            return null;
        }
        return _findSecondary(_calcOffset, i, i2);
    }

    private String _findSecondary(int i, int i2, int i3, int i4) {
        int i5 = this._tertiaryStart;
        int i6 = this._tertiaryShift;
        int i7 = i5 + ((i >> (i6 + 2)) << i6);
        int[] iArr = this._hashArea;
        int i8 = (1 << i6) + i7;
        while (i7 < i8) {
            int i9 = iArr[i7 + 3];
            if (i2 == iArr[i7] && i3 == iArr[i7 + 1] && i4 == iArr[i7 + 2] && 3 == i9) {
                return this._names[i7 >> 2];
            }
            if (i9 == 0) {
                return null;
            }
            i7 += 4;
        }
        for (int _spilloverStart = _spilloverStart(); _spilloverStart < this._spilloverEnd; _spilloverStart += 4) {
            if (i2 == iArr[_spilloverStart] && i3 == iArr[_spilloverStart + 1] && i4 == iArr[_spilloverStart + 2] && 3 == iArr[_spilloverStart + 3]) {
                return this._names[_spilloverStart >> 2];
            }
        }
        return null;
    }

    public String findName(int i, int i2, int i3) {
        int _calcOffset = _calcOffset(calcHash(i, i2, i3));
        int[] iArr = this._hashArea;
        int i4 = iArr[_calcOffset + 3];
        if (i4 == 3) {
            if (i == iArr[_calcOffset] && iArr[_calcOffset + 1] == i2 && iArr[_calcOffset + 2] == i3) {
                return this._names[_calcOffset >> 2];
            }
        } else if (i4 == 0) {
            return null;
        }
        int i5 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        int i6 = iArr[i5 + 3];
        if (i6 == 3) {
            if (i == iArr[i5] && iArr[i5 + 1] == i2 && iArr[i5 + 2] == i3) {
                return this._names[i5 >> 2];
            }
        } else if (i6 == 0) {
            return null;
        }
        return _findSecondary(_calcOffset, i, i2, i3);
    }

    private String _findSecondary(int i, int i2, int[] iArr, int i3) {
        int i4 = this._tertiaryStart;
        int i5 = this._tertiaryShift;
        int i6 = i4 + ((i >> (i5 + 2)) << i5);
        int[] iArr2 = this._hashArea;
        int i7 = (1 << i5) + i6;
        while (i6 < i7) {
            int i8 = iArr2[i6 + 3];
            if (i2 == iArr2[i6] && i3 == i8 && _verifyLongName(iArr, i3, iArr2[i6 + 1])) {
                return this._names[i6 >> 2];
            }
            if (i8 == 0) {
                return null;
            }
            i6 += 4;
        }
        for (int _spilloverStart = _spilloverStart(); _spilloverStart < this._spilloverEnd; _spilloverStart += 4) {
            if (i2 == iArr2[_spilloverStart] && i3 == iArr2[_spilloverStart + 3] && _verifyLongName(iArr, i3, iArr2[_spilloverStart + 1])) {
                return this._names[_spilloverStart >> 2];
            }
        }
        return null;
    }

    public String findName(int[] iArr, int i) {
        if (i >= 4) {
            int calcHash = calcHash(iArr, i);
            int _calcOffset = _calcOffset(calcHash);
            int[] iArr2 = this._hashArea;
            int i2 = iArr2[_calcOffset + 3];
            if (calcHash == iArr2[_calcOffset] && i2 == i && _verifyLongName(iArr, i, iArr2[_calcOffset + 1])) {
                return this._names[_calcOffset >> 2];
            }
            if (i2 == 0) {
                return null;
            }
            int i3 = this._secondaryStart + ((_calcOffset >> 3) << 2);
            int i4 = iArr2[i3 + 3];
            if (calcHash == iArr2[i3] && i4 == i && _verifyLongName(iArr, i, iArr2[i3 + 1])) {
                return this._names[i3 >> 2];
            }
            return _findSecondary(_calcOffset, calcHash, iArr, i);
        } else if (i == 1) {
            return findName(iArr[0]);
        } else {
            if (i != 2) {
                return i != 3 ? "" : findName(iArr[0], iArr[1], iArr[2]);
            }
            return findName(iArr[0], iArr[1]);
        }
    }
}
