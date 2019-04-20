package system;

import io.Eam;
import io.Reader;
import io.Writer;
import runtime.VirtualMachine;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException{
        String file_name = "HelloWorld";
        compiler(file_name);
        runtime(file_name);
    }
    private static void compiler(final String file_name) throws IOException{
        String source_code = Eam.use(file_name + ".f",(Reader<Eam, char[], IOException>) eam -> eam.read());
        String object_code = new Compiler("LLParser").use(source_code);
        Eam.use(file_name + ".g",(Writer<Eam, IOException>) eam -> eam.write(object_code));
    }
    private static void runtime(final String file_name) throws IOException{
        String object_code = Eam.use(file_name + ".g",(Reader<Eam, char[], IOException>) eam -> eam.read());
        String debug_code = new VirtualMachine(0).use(object_code);
        Eam.use(file_name + ".d",(Writer<Eam, IOException>) eam -> eam.write(debug_code));
    }
}
