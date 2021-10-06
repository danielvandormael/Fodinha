package Main.GUI;

import Main.Objects.Player;
import Main.Objects.State;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UI {
    private JPanel root;
    private JPanel details;
    private Display display;


    JFrame frame = new JFrame();

    public UI(int players, int lives){
        createGUI(players, lives);
    }

    private void createGUI(int players, int lives){
        root = new JPanel(new BorderLayout());
        details= new JPanel(new GridBagLayout());

        createStats(players,lives);
        display = new Display();
        details.setOpaque(false);
        root.add(details,  BorderLayout.NORTH);
        root.add(display, BorderLayout.CENTER);
        root.setBackground(new Color(53, 101, 77));

        ImageIcon icon = new ImageIcon("src/Main/Images/logo.png");
        frame.setIconImage(icon.getImage());

        JPanel theroot = getRootPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(theroot);
        frame.pack();
        frame.setTitle("FODINHA");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createStats(int players, int lives){
        details.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 6,5,6);
        JLabel temp = new JLabel();

        String live = "";
        for(int i = 0; i<lives; i++){
            live += "I ";
        }

        for(int x = 0; x<players; x++){
            for(int y = 0; y<3; y++){
                constraints.gridx=x;
                constraints.gridy=y;
                if(y==0){
                    temp= new JLabel("Player " + (x+1));
                    temp.setForeground(Color.WHITE);
                }else if(y==1){
                    temp= new JLabel("Lives: " + live);
                    temp.setForeground(Color.WHITE);
                }else if(y==2){
                    temp= new JLabel("Makes:0   Made:0");
                    temp.setForeground(Color.WHITE);
                }
                details.add(temp, constraints);
            }
        }
    }

    public void repaintStats(ArrayList<Player> players){
        details.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 6,5,6);
        JLabel temp = new JLabel();


        for(int x = 0; x<players.size(); x++){
            for(int y = 0; y<3; y++){
                constraints.gridx=x;
                constraints.gridy=y;
                if(y==0){
                    temp= new JLabel("Player " + (players.get(x).getId()+1));
                }else if(y==1){
                    int lives = players.get(x).getLives();
                    String live = "";
                    for(int i = 0; i<lives; i++){
                        live += "I ";
                    }
                    temp= new JLabel("Lives: " + live);
                }else if(y==2){
                    int make = players.get(x).getToMake();
                    int made = players.get(x).getMade();
                    temp= new JLabel("Makes:"+make+"   Made:"+made);
                }
                temp.setForeground(Color.WHITE);
                details.add(temp, constraints);
                details.repaint();
                details.revalidate();
            }
        }
    }

    public void updateGraphics(State state){
        display.setState(state);
        display.letsGo();
    }

    public JPanel getRootPanel(){
        return root;
    }

}
