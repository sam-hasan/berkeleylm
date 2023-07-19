package edu.berkeley.nlp.lm.io;

import java.io.*;
import java.nio.channels.Channels;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IOFileUtils {

    public static BufferedReader openIn(final String path) throws IOException {
        return openIn(new File(path));
    }

    public static BufferedReader openIn(final File path) throws IOException {
        InputStream is = getBufferedInputStream(path);
        if (path.getName().endsWith(".gz")) is = new GZIPInputStream(is);
        return new BufferedReader(IOTextUtils.getReader(is));
    }

    public static PrintWriter openOut(final String path) throws IOException {
        return openOut(new File(path));
    }

    public static PrintWriter openOut(final File path) throws IOException {
        OutputStream os = new FileOutputStream(path);
        if (path.getName().endsWith(".gz")) os = new GZIPOutputStream(os);
        return IOTextUtils.getWriter(os);
    }

    private static BufferedInputStream getBufferedInputStream(final File path) throws FileNotFoundException {
        return new BufferedInputStream(Channels.newInputStream(new FileInputStream(path).getChannel()));
    }

}
