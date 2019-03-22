package runtime;

public class MemberDefine extends Node{
    String defined;
    Float value;
    private MemberDefine(final String s,final Float f){
        type = Type.MEMBER_DEFINE;
        defined = s;
        value = f;
    }
    public static MemberDefine init(final String s,final String t){
        return new MemberDefine(s,Float.valueOf(t));
    }
}