package Main.GUI;

import Main.Objects.Card;
import Main.Objects.CardInPlay;
import Main.Objects.State;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class Display extends JPanel {

    private Image table;
    Image card;
    private State state;

    private Card inPlay[][] =new Card[4][6];


    Display(){
        setPreferredSize(new Dimension(1000, 700));
        setBackground(Color.WHITE);
        table =new ImageIcon("src/Main/Images/table.png").getImage();
        card =new ImageIcon("src/Main/Images/cards.png").getImage();
    }

    public void paint(Graphics g){
        super.paintComponents(g);
        g.drawImage(table,0,0,1000,700, new Color(53, 101, 77), null);


        int cardX = 79;
        int cardY = 123;
        int spaceBetweenCards = 20;

        //figure out how to get player without storing it in class
        if(this.state != null){
            int handSize = 0;
            if(state.getPlayers().get(0).getCards().size() == 1){
                handSize = 123/2;
            }else if(state.getPlayers().get(0).getCards().size() > 1) {
                handSize = 123/2 + (state.getPlayers().get(0).getCards().size() - 1)*spaceBetweenCards;
            }

            int centerStartX;
            int centerStartY;
            for(int x = 0; x < state.getPlayers().size(); x++){
                if(state.getPlayers().get(x).getId() == 0){
                    centerStartX= 515;
                    centerStartY= 440;
                }else if(state.getPlayers().get(x).getId() == 1){
                    centerStartX= 150;
                    centerStartY= 420;
                }else if(state.getPlayers().get(x).getId() == 2){
                    centerStartX= 150;
                    centerStartY= 70;
                }else if(state.getPlayers().get(x).getId() == 3){
                    centerStartX= 515;
                    centerStartY= 50;
                }else if(state.getPlayers().get(x).getId() == 4){
                    centerStartX= 890;
                    centerStartY= 70;
                }else{
                    centerStartX= 890;
                    centerStartY= 420;
                }
                for(int y = 0; y< this.state.getPlayers().get(x).getCards().size(); y++){
                    int l = this.state.getPlayers().get(x).getCards().get(y).getSuit() -1;
                    int k = this.state.getPlayers().get(x).getCards().get(y).getRank() -2;
                    g.drawImage(card, (y)*spaceBetweenCards+centerStartX-handSize, (0)*cardY+centerStartY, (y)*spaceBetweenCards+centerStartX+cardX-handSize, (0+1)*cardY+centerStartY, k*79, l*123, (k+1)*79, (l+1)*123, null);
                }
            }

            if(this.state.getCardsInPlay().size() > 0){
                sortCards();
                centerStartX= 320;
                centerStartY= 250;
                spaceBetweenCards = 85;
                int seperation = 35;
                for(int x= 0; x< this.inPlay[0].length; x++){
                    int counter = 0;
                    for(int i = 0; i < this.inPlay.length; i++){
                        if(this.inPlay[i][x] != null){
                            counter++;
                        }
                    }
                    handSize = ((counter-1)*seperation)/2;


                    for(int y = 0; y< this.inPlay.length; y++){
                        if(this.inPlay[y][x] != null){
                            int l = this.inPlay[y][x].getSuit()-1;
                            int k = this.inPlay[y][x].getRank()-2;
                            g.drawImage(card, x*spaceBetweenCards+centerStartX, centerStartY+handSize-((counter-1)*seperation), x*spaceBetweenCards+centerStartX+cardX, cardY+centerStartY+handSize-((counter-1)*seperation), k*79, l*123, (k+1)*79, (l+1)*123, null);
                            counter--;
                        }
                    }
                }
            }
            centerStartX= 200;
            centerStartY= 250;
            int l = this.state.getManilha().getSuit() -1;
            int k = this.state.getManilha().getRank() -2;
            g.setColor(Color.RED);
            g.drawRoundRect(centerStartX-5 , centerStartY-5 , 89, 133, 10,10);
            g.drawImage(card, centerStartX, (0)*cardY+centerStartY, centerStartX+cardX, (0+1)*cardY+centerStartY, k*79, l*123, (k+1)*79, (l+1)*123, null);
        }
    }

    public void sortCards(){

        Collections.sort(this.state.getCardsInPlay(), new Comparator<CardInPlay>() {
            @Override
            public int compare(CardInPlay o1, CardInPlay o2) {
                return Integer.valueOf(o1.getCard().getRank()).compareTo(o2.getCard().getRank());
            }
        });

        this.inPlay = new Card[4][6];
        this.inPlay[0][0] = this.state.getCardsInPlay().get(0).getCard();


        int rank = 0;
        int suit = 0;
        for(int x = 1; x< this.state.getCardsInPlay().size(); x++){
            if(this.inPlay[suit][rank].getRank() == this.state.getCardsInPlay().get(x).getCard().getRank()){
                suit++;
            }else{
                rank++;
                suit= 0;
            }
            this.inPlay[suit][rank] = this.state.getCardsInPlay().get(x).getCard();
        }
    }

    public void letsGo(){
        repaint();
    }
    public void setState(State state) {
        this.state = state;
    }
}
