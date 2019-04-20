package io;

import java.util.*;
import java.io.*;

public class Eam{
    private final File file;
    private final FileWriter file_writer;
    private final FileReader file_reader;

    private Eam(final String file_name,final ReadOrWrite read_write) throws IOException{
        file = new File(file_name);
        switch (read_write) {
            case WRITE:
                file_writer = new FileWriter(file);
                file_reader = null;
                break;
            case READ:
                file_writer = null;
                file_reader = new FileReader(file);
                break;
            default:
                System.exit(1);
                file_writer = null;
                file_reader = null;
        }
    }
    public static void use(final String file_name,Writer<Eam,IOException> writer) throws IOException{
        final Eam eam = new Eam(file_name,ReadOrWrite.WRITE);
        try {
            writer.consume(eam);
        } finally {
            eam.close(ReadOrWrite.WRITE);
        }
    }
    public static String use(final String file_name,
                             final Reader<Eam,char[],IOException> reader) throws IOException{
        final Eam eam = new Eam(file_name,ReadOrWrite.READ);
        char[] output;
        try {
            output = reader.apply(eam);
        } finally {
            eam.close(ReadOrWrite.READ);
        }
        return new String(output);
    }
    private void close(final ReadOrWrite read_write) throws IOException{
        switch (read_write) {
            case WRITE:
                file_writer.close();
                break;
            case READ:
                file_reader.close();
                break;
            default:
                System.exit(1);
        }
    }
    public void write(final String input) throws IOException {
        file_writer.write(input);
    }
    public char[] read() throws IOException {
        char[] chars = new char[(int)file.length()];
        int length = file_reader.read(chars);
        return Arrays.copyOfRange(chars, 0, length);
    }
}