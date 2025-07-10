package devguilherme7.hms.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import devguilherme7.hms.ui.laf.LightLookAndFeel;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new LightLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            // TODO: log
        }
    }
}
