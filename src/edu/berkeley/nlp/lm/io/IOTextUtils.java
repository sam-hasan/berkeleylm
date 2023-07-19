package edu.berkeley.nlp.lm.io;

import edu.berkeley.nlp.lm.util.StrUtils;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class IOTextUtils {

    private static String charEncoding = "UTF-8";

    public static String getCharEncoding() {
        return charEncoding;
    }

    public static void setCharEncoding(final String charEncoding) {
        if (StrUtils.isEmpty(charEncoding)) return;
        IOTextUtils.charEncoding = charEncoding;
    }

    public static BufferedReader getReader(final InputStream in) throws IOException {
        return new BufferedReader(new InputStreamReader(in, getCharEncoding()));
    }

    public static PrintWriter getWriter(final OutputStream out) throws IOException {
        return new PrintWriter(new OutputStreamWriter(out, getCharEncoding()), true);
    }

    public static List<String> readLines(final BufferedReader in) throws IOException {
        String line;
        final List<String> lines = new ArrayList<String>();
        while ((line = in.readLine()) != null)
            lines.add(line);
        return lines;
    }

    public static List<String> readLines(final String path) throws IOException {
        try (final BufferedReader in = IOFileUtils.openIn(path)) {
            return readLines(in);
        }
    }

    public static String readLine(final String path) throws IOException {
        try (final BufferedReader in = IOFileUtils.openIn(path)) {
            return in.readLine();
        }
    }

    public static Iterator<String> lineIterator(final String path) throws IOException {
        final BufferedReader reader = IOFileUtils.openIn(path);
        return lineIterator(reader);
    }

    public static Iterator<String> lineIterator(final BufferedReader reader) {
        return new Iterator<String>() {
            private String line;

            @Override
            public boolean hasNext() {
                try {
                    return nextLine();
                } catch (final Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            private boolean nextLine() throws IOException {
                if (line != null) { return true; }
                line = reader.readLine();
                if (line == null) reader.close();
                return line != null;
            }

            @Override
            public String next() {
                try {
                    nextLine();
                    final String retLine = line;
                    line = null;
                    return retLine;
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() not supported");
            }
        };
    }
}


