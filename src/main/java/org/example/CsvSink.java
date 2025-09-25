package org.example;

import java.io.*;

public class CsvSink implements Closeable {
    private final PrintWriter out;

    public CsvSink(File file) throws IOException {
        boolean exists = file.exists();
        this.out = new PrintWriter(new FileWriter(file, true));
        if (!exists) {
            out.println("algorithmName;runTime;counter;depth;allocation");
        }
    }

    public void write(String algorithm, Metrics m, int depth) {
        out.printf("%s;%d;%d;%d;%d%n",
                algorithm,
                m.runtimeNanos,
                m.comparisons,
                depth,
                m.allocations);
        out.flush();
    }

    @Override
    public void close() {
        out.close();
    }
}