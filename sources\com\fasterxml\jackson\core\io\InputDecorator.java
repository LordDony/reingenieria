package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

public abstract class InputDecorator implements Serializable {
    public abstract InputStream decorate(IOContext iOContext, byte[] bArr, int i, int i2) throws IOException;

    public abstract Reader decorate(IOContext iOContext, Reader reader) throws IOException;
}
