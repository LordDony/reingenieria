package com.fasterxml.jackson.core.io;

import com.google.common.base.Ascii;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class UTF32Reader extends Reader {
    protected final boolean _bigEndian;
    protected byte[] _buffer;
    protected int _byteCount;
    protected int _charCount;
    protected final IOContext _context;
    protected InputStream _in;
    protected int _length;
    protected final boolean _managedBuffers;
    protected int _ptr;
    protected char _surrogate = 0;
    protected char[] _tmpBuf;

    public UTF32Reader(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2, boolean z) {
        boolean z2 = false;
        this._context = iOContext;
        this._in = inputStream;
        this._buffer = bArr;
        this._ptr = i;
        this._length = i2;
        this._bigEndian = z;
        if (inputStream != null) {
            z2 = true;
        }
        this._managedBuffers = z2;
    }

    private void freeBuffers() {
        byte[] bArr = this._buffer;
        if (bArr != null) {
            this._buffer = null;
            this._context.releaseReadIOBuffer(bArr);
        }
    }

    private boolean loadMore(int i) throws IOException {
        int i2;
        this._byteCount += this._length - i;
        if (i > 0) {
            int i3 = this._ptr;
            if (i3 > 0) {
                byte[] bArr = this._buffer;
                System.arraycopy(bArr, i3, bArr, 0, i);
                this._ptr = 0;
            }
            this._length = i;
        } else {
            this._ptr = 0;
            InputStream inputStream = this._in;
            int read = inputStream == null ? -1 : inputStream.read(this._buffer);
            if (read < 1) {
                this._length = 0;
                if (read < 0) {
                    if (this._managedBuffers) {
                        freeBuffers();
                    }
                    return false;
                }
                reportStrangeStream();
                throw null;
            }
            this._length = read;
        }
        while (true) {
            int i4 = this._length;
            if (i4 >= 4) {
                return true;
            }
            InputStream inputStream2 = this._in;
            if (inputStream2 == null) {
                i2 = -1;
            } else {
                byte[] bArr2 = this._buffer;
                i2 = inputStream2.read(bArr2, i4, bArr2.length - i4);
            }
            if (i2 >= 1) {
                this._length += i2;
            } else if (i2 < 0) {
                if (this._managedBuffers) {
                    freeBuffers();
                }
                reportUnexpectedEOF(this._length, 4);
                throw null;
            } else {
                reportStrangeStream();
                throw null;
            }
        }
    }

    private void reportBounds(char[] cArr, int i, int i2) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("read(buf,");
        sb.append(i);
        sb.append(",");
        sb.append(i2);
        sb.append("), cbuf[");
        sb.append(cArr.length);
        sb.append("]");
        throw new ArrayIndexOutOfBoundsException(sb.toString());
    }

    private void reportInvalid(int i, int i2, String str) throws IOException {
        int i3 = (this._byteCount + this._ptr) - 1;
        int i4 = this._charCount + i2;
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid UTF-32 character 0x");
        sb.append(Integer.toHexString(i));
        sb.append(str);
        sb.append(" at char #");
        sb.append(i4);
        sb.append(", byte #");
        sb.append(i3);
        sb.append(")");
        throw new CharConversionException(sb.toString());
    }

    private void reportStrangeStream() throws IOException {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }

    private void reportUnexpectedEOF(int i, int i2) throws IOException {
        int i3 = this._byteCount + i;
        int i4 = this._charCount;
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected EOF in the middle of a 4-byte UTF-32 char: got ");
        sb.append(i);
        sb.append(", needed ");
        sb.append(i2);
        sb.append(", at char #");
        sb.append(i4);
        sb.append(", byte #");
        sb.append(i3);
        sb.append(")");
        throw new CharConversionException(sb.toString());
    }

    public void close() throws IOException {
        InputStream inputStream = this._in;
        if (inputStream != null) {
            this._in = null;
            freeBuffers();
            inputStream.close();
        }
    }

    public int read() throws IOException {
        if (this._tmpBuf == null) {
            this._tmpBuf = new char[1];
        }
        if (read(this._tmpBuf, 0, 1) < 1) {
            return -1;
        }
        return this._tmpBuf[0];
    }

    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        byte b;
        byte b2;
        if (this._buffer == null) {
            return -1;
        }
        if (i2 < 1) {
            return i2;
        }
        if (i < 0 || i + i2 > cArr.length) {
            reportBounds(cArr, i, i2);
            throw null;
        }
        int i5 = i2 + i;
        char c = this._surrogate;
        if (c != 0) {
            i3 = i + 1;
            cArr[i] = c;
            this._surrogate = 0;
        } else {
            int i6 = this._length - this._ptr;
            if (i6 >= 4 || loadMore(i6)) {
                i3 = i;
            } else if (i6 == 0) {
                return -1;
            } else {
                reportUnexpectedEOF(this._length - this._ptr, 4);
                throw null;
            }
        }
        int i7 = this._length - 4;
        while (true) {
            if (i3 >= i5) {
                i4 = i3;
                break;
            }
            int i8 = this._ptr;
            if (this._bigEndian) {
                byte[] bArr = this._buffer;
                byte b3 = (bArr[i8] << 8) | (bArr[i8 + 1] & 255);
                b = (bArr[i8 + 3] & 255) | ((bArr[i8 + 2] & 255) << 8);
                b2 = b3;
            } else {
                byte[] bArr2 = this._buffer;
                b = (bArr2[i8] & 255) | ((bArr2[i8 + 1] & 255) << 8);
                b2 = (bArr2[i8 + 3] << 8) | (bArr2[i8 + 2] & 255);
            }
            this._ptr += 4;
            if (b2 != 0) {
                byte b4 = b2 & 65535;
                byte b5 = ((b4 - 1) << 16) | b;
                if (b4 <= 16) {
                    i4 = i3 + 1;
                    cArr[i3] = (char) ((b5 >> 10) + 55296);
                    b = (b5 & 1023) | Ascii.NUL;
                    if (i4 >= i5) {
                        this._surrogate = (char) b5;
                        break;
                    }
                    i3 = i4;
                } else {
                    reportInvalid(b5, i3 - i, String.format(" (above 0x%08x)", new Object[]{Integer.valueOf(1114111)}));
                    throw null;
                }
            }
            i4 = i3 + 1;
            cArr[i3] = (char) b;
            if (this._ptr > i7) {
                break;
            }
            i3 = i4;
        }
        int i9 = i4 - i;
        this._charCount += i9;
        return i9;
    }
}
