package edu.berkeley.nlp.lm.io;

import java.io.*;
import java.nio.channels.Channels;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class IOObjUtils {

    private IOObjUtils() {} // private constructor to prevent instantiation

    private static BufferedInputStream getBufferedInputStream(final File path) throws FileNotFoundException {
        return new BufferedInputStream(Channels.newInputStream(new FileInputStream(path).getChannel()));
    }

    public static ObjectInputStream openObjIn(final String path) throws IOException {
        return openObjIn(new File(path));
    }

    public static ObjectInputStream openObjIn(final File path) throws IOException {
        final InputStream fis = getBufferedInputStream(path);
        return path.getName().endsWith(".gz")
                ? new ObjectInputStream(new GZIPInputStream(fis))
                : new ObjectInputStream(fis);
    }

    public static ObjectOutputStream openObjOut(final String path) throws IOException {
        return openObjOut(new File(path));
    }

    public static ObjectOutputStream openObjOut(final File path) throws IOException {
        final OutputStream fos = new BufferedOutputStream(new FileOutputStream(path));
        return path.getName().endsWith(".gz")
                ? new ObjectOutputStream(new GZIPOutputStream(fos))
                : new ObjectOutputStream(fos);
    }

    public static Object readObjFile(final String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = openObjIn(path)) {
            return in.readObject();
        }
    }

    public static Object readObjFile(final File path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = openObjIn(path)) {
            return in.readObject();
        }
    }

    public static void writeObjFile(final File path, final Object obj) throws IOException {
        try (ObjectOutputStream out = openObjOut(path)) {
            out.writeObject(obj);
        }
    }
}

