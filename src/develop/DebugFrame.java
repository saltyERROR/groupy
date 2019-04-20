package develop;

import system.Error;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EmptyStackException;

public class DebugFrame extends JFrame{
    public DebugFrame(final String input) {
        super("debug");
        setSize(600, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowClosing());
        final JPanel pane = (JPanel)getContentPane();
        String html = "<html>"
                + "<font size=5><i>instruction</i> [<i>stack</i>] "
                + "{<i>namespace</i>}</font><br>";
        try {
            //html = new VirtualMachine(1).debug(html,input,0);
        } catch (EmptyStackException e) {
            Error.exit(Error.VM, "stack is empty");
        }
        pane.add(new JLabel(html));
    }
    private class WindowClosing extends WindowAdapter{
        @Override
        public void windowClosing(final WindowEvent e){
            int answer = JOptionPane.showConfirmDialog(DebugFrame.this,
                    "Are you sure you want to exit debug?");
            if (answer == JOptionPane.YES_OPTION) System.exit(0);
        }
    }
}
