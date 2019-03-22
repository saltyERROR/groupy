package runtime;

import java.util.Optional;

public class Member extends Node{
    String name;
    Float value;

    private Member(final String s,final Float f){
            if(VirtualMachine.members.stream()
                                     .anyMatch(x -> x.name == s)){
                System.out.println("ERROR: \"" + s + "\" is already defined");
                System.exit(1);
            }
            name = s;
            value = f;
        }
        static void define(final String s,final float f){
            Member defined = new Member(s,f);
            VirtualMachine.members.add(defined);
        }
        static Float call(final String s) {
            Optional<Member> called =
                    VirtualMachine.members.stream()
                                          .filter(x -> x.name == s)
                                          .findFirst();
            if (called.isPresent()) {
                return called.get().value;
            } else {
                System.out.println("ERROR: \"" + s + "\" is undefined");
                System.exit(1);
            }
            return null;
        }
        static void print(final String s){
            System.out.println(call(s));
        }

}
