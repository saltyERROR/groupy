package system;

import java.util.*;
import java.io.*;

public class Eam{
    private final File file;
    private final FileReader file_reader;
    private final FileWriter file_writer;


    private Eam(final String file_name) throws IOException{
        file = new File(file_name);
        file_reader = new FileReader(file);
        file_writer = new FileWriter(file,true);
    }
    public static String use(final String file_name,
                             final Reader<Eam,char[],IOException> reader) throws IOException{
        final Eam eam = new Eam(file_name);
        char[] output;
        try{
            output = reader.apply(eam);
        }finally{
            eam.close();
        }
        return new String(output);
    }
    public static void use(final String file_name,
                             final Writer<Eam,IOException> writer) throws IOException{
        final Eam eam = new Eam(file_name);
        try{
            writer.consume(eam);
        }finally{
            eam.close();
        }
    }
    private void close() throws IOException{
        file_reader.close();
    }
    public char[] read() throws IOException {
        char[] chars = new char[(int)file.length()];
        int length = file_reader.read(chars);
        return Arrays.copyOfRange(chars, 0, length);
    }
    public void write(final String input) throws IOException {
        file_writer.write(input);
    }
}