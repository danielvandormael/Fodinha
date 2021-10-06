package Main;

import Main.GUI.Menu;
import Main.GUI.UI;
import Main.Logic.Game;
import Main.Objects.Player;

import javax.swing.*;
import javax.swing.plaf.MenuBarUI;
import java.util.ArrayList;

public class Main {
    public static void main(String args []){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run () {
                new Menu();
            }
        });

    }
}
