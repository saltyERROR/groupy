package deprecated;

public class MemberCall extends Instruction {
    String called;
    private MemberCall(final String s){
        type = Type.MEMBER_CALL;
        called = s;
    }
    public static MemberCall init(final String s){
        return new MemberCall(s);
    }
}
