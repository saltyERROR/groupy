package deprecated;

import system.Error;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;

public class Traverser {
    //namespace
    private ArrayList<Member> members;
    private ArrayList<Function> functions;

    /*
    Node pointer
     */

    public Traverser() {
        members = new ArrayList<>();
        functions = new ArrayList<>();
        functions.add(new Function("print",System.out::println));
    }
    public void loop(LinkedList<Instruction> l) {
        while(!l.isEmpty()) {
            Instruction pointer = l.pop();
            switch (pointer.type) {
                case MEMBER_DEFINE:
                    MemberDefine md = (MemberDefine)pointer;
                    if(this.members.stream()
                                   .anyMatch(x -> x.name.equals(md.defined))){
                        Error.exit(Error.INSTRUCTION,"member, \"" + md.defined + "\" is already defined");
                    }
                    Member definedMember = new Member(md.defined,md.value);
                    this.members.add(definedMember);
                    break;
                case MEMBER_CALL:
                    MemberCall mc = (MemberCall)pointer;
                    Optional<Member> called =
                            this.members.stream()
                                    .filter(x -> x.name.equals(mc.called))
                                    .findFirst();
                    if (called.isPresent()) {
                        //
                        Literal.init(called.get().value);
                    } else {
                        Error.exit(Error.INSTRUCTION,"member, \"" + mc.called + "\" is undefined");
                    }
                    break;
                case FUNCTION_DEFINE:
                    FunctionDefine fd = (FunctionDefine)pointer;
                    if(this.functions.stream()
                                     .anyMatch(x -> x.name == fd.defined)){
                        Error.exit(Error.INSTRUCTION,"function, \"" + fd.defined + "\" is already defined");
                    }
                    Function definedFunction = new Function(fd.defined,fd.value);
                    this.functions.add(definedFunction);
                    break;
                case FUNCTION_CALL:
                    FunctionCall fc = (FunctionCall)pointer;
                    Optional<Function> calledFunction =
                            this.functions.stream()
                                          .filter(x -> x.name.equals(fc.called))
                                          .findFirst();
                    if (calledFunction.isPresent()) {
                        if (fc.argument.type != Type.LITERAL) {
                            Error.exit(Error.INSTRUCTION,"illegal node");
                        }
                        calledFunction.get().argument.accept(((Literal)fc.argument).value);
                    } else {
                        Error.exit(Error.INSTRUCTION,"function, \"" + fc.called + "\" is undefined");
                    }
                    break;
                case LITERAL:
                    break;
                default:
                    Error.exit(Error.INSTRUCTION,"VM can't interpret");
            }
        }
    }
    class Member {
        String name;
        Float value;
        Member(final String s,final Float f){
            name = s;
            value = f;
        }
        @Override
        public String toString(){
            return  "Member( name: " + name + ", value: " + value + ")";
        }
    }
    class Function {
        String name;
        Consumer argument;
        Function(final String s, final Consumer c) {
            name = s;
            argument = c;
        }
        @Override
        public String toString(){
            return "Function( name: " + name + ", argument: " + argument + ")";
        }
    }
}