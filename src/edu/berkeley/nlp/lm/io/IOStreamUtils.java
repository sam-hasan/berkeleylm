package edu.berkeley.nlp.lm.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.IOException;


public class IOStreamUtils {

    public static int copy(final InputStream in, final OutputStream out) throws IOException {
        final byte[] buf = new byte[16384];
        int total = 0, n;
        while ((n = in.read(buf)) != -1) {
            total += n;
            out.write(buf, 0, n);
        }
        out.flush();
        return total;
    }

    public static int copy(final Reader in, final Writer out) throws IOException {
        final char[] buf = new char[16384];
        int total = 0, n;
        while ((n = in.read(buf)) != -1) {
            total += n;
            out.write(buf, 0, n);
        }
        out.flush();
        return total;
    }
}
