public class Main {
    public static void main(String[] args) {
        VM vm = VM.init();

        //test
        VM.Operation[] operations = {
            new VM.Define("hoge",2F),
            new VM.Out("hoge")
        };
        vm.loop(operations);
    }
}