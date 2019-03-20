package runtime;

import library.Standerd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

public class VirtualMachine {
    //no static
    static ArrayList<Member> members;
    static ArrayList<Function> functions;

    static VirtualMachine init(){
        return new VirtualMachine();
    }
    private VirtualMachine(){
        members = new ArrayList<>();
        functions = new ArrayList<>();
        Standerd.use();
    }
    void loop(final LinkedList<Node> l){
        while(!l.isEmpty()) {
            Node pointer = l.pop();
            switch (pointer.type) {
                case MEMBER_DEFINE:
                    break;
                case MEMBER_CALL:
                    MemberCall m = (MemberCall)pointer;
                    Optional<Member> calledMember =
                            VirtualMachine.members.stream()
                                    .filter(x -> x.name == (m.called))
                                    .findFirst();
                    if (calledMember.isPresent()) {
                        Literal.init(calledMember.get().value);
                    } else {
                        System.out.println("ERROR: \"" + m.called + "\" is undefined");
                        System.exit(1);
                    }
                    break;
                case FUNCTION_DEFINE:
                    break;
                case FUNCTION_CALL:
                    FunctionCall f = (FunctionCall)pointer;
                    Optional<Function> calledFunction =
                            VirtualMachine.functions.stream()
                                    .filter(x -> x.name == (f.called))
                                    .findFirst();
                    if (calledFunction.isPresent()) {
                        if (f.argument.type != Type.LITERAL) {
                            System.out.println("ERROR: illegal node");
                            System.exit(1);
                        }
                        calledFunction.get().argument.accept(((Literal) f.argument).value);
                    } else {
                        System.out.println("ERROR: \"" + f.called + "\" is undefined");
                        System.exit(1);
                    }
                    break;
                case LITERAL:
                    break;
                default:
                    System.out.println("ERROR: VM can't interpret");
                    System.exit(1);
            }
        }
    }
}