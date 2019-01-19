package io;

import java.util.*;
import java.io.*;

public class EAM{
    private final FileReader fr;

    private EAM(final String s) throws IOException{
        fr = new FileReader(s);
    }
    public static char[] use(final String s,final Use<EAM,char[],IOException> u) throws IOException{
        final EAM eam = new EAM(s);
        char[] c;
        try{
            c = u.apply(eam);
        }finally{
            eam.close();
        }
        return c;
    }
    private void close() throws IOException{
        fr.close();
    }
    public char[] read() throws IOException {
        System.out.println("\n--read()");
        char[] chars = new char[100];
        int length = fr.read(chars);
        return Arrays.copyOfRange(chars, 0, length);
    }
}