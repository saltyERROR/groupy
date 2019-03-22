import java.util.ArrayList;

public class VM {
    static ArrayList<Member> members;
    static ArrayList<Func> funcs;

    static VM init(){
        return new VM();
    }
    private VM(){
        members = new ArrayList<>();
        funcs = new ArrayList<>();
    }
    void loop(Operation[] o){
        for(int i=0;i<o.length;i++){
            execute(o[i]);
        }
    }
    private void execute(final Operation o) {
        switch (o.code) {
            case MEMBER_DEFINE:
                final Define define = (Define)o;
                Member.define(define.name,define.value);
                break;
            case MEMBER_CALL:
                final Call call = (Call)o;
                Member.call(call.name);
                break;
            case MEMBER_OUT:
                final Out out = (Out)o;
                Member.out(out.name);
            case FUNC_DEFINE:
                //Func.define();
                break;
            case FUNC_CALL:
                //Func.call();
                break;
            default:
                System.out.println("ERROR: VM can't interpret");
                System.exit(1);
        }
    }
    enum Code {
        MEMBER_DEFINE,
        MEMBER_CALL,
        MEMBER_OUT,
        FUNC_DEFINE,
        FUNC_CALL
    }
    static class Operation{
        Code code;
    }
    static class Define extends Operation{
        String name;
        Float value;

        Define(final String s,final Float f){
            code = Code.MEMBER_DEFINE;
            name = s;
            value = f;
        }
    }
    static class Call extends Operation{
        String name;

        Call(final String s){
            code = Code.MEMBER_CALL;
            name = s;
        }
    }
    static class Out extends Operation{
        String name;

        Out(final String s){
            code = Code.MEMBER_OUT;
            name = s;
        }
    }
}
