package Main.Logic;

import Main.GUI.UI;
import Main.Objects.*;

import javax.swing.*;
import java.util.*;

public class Game {

    private State currentState;
    private Player startsSet;
    private Player startsPlaying;
    private boolean resetCardCount;
    private int cardsToGive;

    UI table;

    public Game(int players, int lives){
        this.currentState = new State(players, lives, 1);
        table = new UI(players,lives);
        whoStarts();
        table.updateGraphics(this.currentState);
        this.currentState = new State(this.currentState, this.startsSet, null, 1);

        this.cardsToGive=1;
        while(currentState.getPlayers().size()>=2){
            playSet();
        }
    }

    public void playSet(){
        if(this.currentState.getPlayers().size()*cardsToGive+1 > 40){ //checks to see if there are enough cards to play
            this.resetCardCount = true;
        }
        if(this.resetCardCount == true){
            cardsToGive = 1;
            this.resetCardCount = false;
        }

        dealCards(this.cardsToGive, this.currentState.getPlayers().indexOf(this.startsSet));
        table.updateGraphics(this.currentState);

        for(int x = this.currentState.getPlayers().indexOf(this.startsSet); x < this.currentState.getPlayers().size(); x++){
            playerMakes(x);
            table.repaintStats(this.currentState.getPlayers());
        }
        for(int x = 0; x < this.currentState.getPlayers().indexOf(this.startsSet); x++){
            playerMakes(x);
            table.repaintStats(this.currentState.getPlayers());
        }

        for(int x = this.cardsToGive; x > 0; x--){
            int whoWon= playRound();

            if(x == 1){
                updateStateRoundOrSet(whoWon, true);
            }else{
                updateStateRoundOrSet(whoWon,false);
            }
        }
        this.cardsToGive++;
        JOptionPane.showMessageDialog(null,"End of set");
    }

    public int playRound(){
        if(this.currentState.cardsInHand() == 1){
            playPane();
            for(int x = this.currentState.getPlayers().indexOf(this.startsPlaying); x < this.currentState.getPlayers().size(); x++){
                Move move = new Move(this.currentState.getPlayers().get(x), this.currentState.getPlayers().get(x).getCard(0));
                this.currentState.getPlayers().get(x).removeCard(0);
                updateState(move);
                table.updateGraphics(this.currentState);
            }
            for(int x = 0; x < this.currentState.getPlayers().indexOf(this.startsPlaying); x++){
                Move move = new Move(this.currentState.getPlayers().get(x), this.currentState.getPlayers().get(x).getCard(0));
                this.currentState.getPlayers().get(x).removeCard(0);
                updateState(move);
                table.updateGraphics(this.currentState);
            }
        }else{
            for(int x = this.currentState.getPlayers().indexOf(this.startsPlaying); x < this.currentState.getPlayers().size(); x++){
                int choice = playerPlays(x);
                Move move = new Move(this.currentState.getPlayers().get(x), this.currentState.getTurn().getCard(choice));
                this.currentState.getPlayers().get(x).removeCard(choice);
                updateState(move);
                table.updateGraphics(this.currentState);
            }
            for(int x = 0; x < this.currentState.getPlayers().indexOf(this.startsPlaying); x++){
                int choice = playerPlays(x);
                Move move = new Move(this.currentState.getPlayers().get(x), this.currentState.getTurn().getCard(choice));
                this.currentState.getPlayers().get(x).removeCard(choice);
                updateState(move);
                table.updateGraphics(this.currentState);
            }
        }
        int temp = whoWins();
        endOfRoundPane(temp);
        return temp;
    }

    public void dealCards(int cardsToDeal, int startIndex){
        final Deck deck = new Deck(true);
        this.currentState.setManilha(deck.deal().get());
        for(int y = 0; y < cardsToDeal; y++){
            for(int x = startIndex; x < currentState.getPlayers().size(); x++){
                currentState.getPlayers().get(x).addCard(deck.deal());
            }
            for(int x = 0; x < startIndex; x++){
                currentState.getPlayers().get(x).addCard(deck.deal());
            }
        }
        table.updateGraphics(this.currentState);
    }

    public void fakeDeck(int cardsToDeal, int startIndex){
        final Deck deck = new Deck(false);
        currentState.getPlayers().get(0).addCard(deck.deal());
        deck.deal();
        deck.deal();
        deck.deal();
        currentState.getPlayers().get(1).addCard(deck.deal());
        this.currentState.setManilha(deck.deal().get());
        deck.deal();
        deck.deal();
        currentState.getPlayers().get(2).addCard(deck.deal());
        currentState.getPlayers().get(3).addCard(deck.deal());

        /*
        for(int y = 0; y < cardsToDeal; y++){
            for(int x = startIndex; x < currentState.getPlayers().size(); x++){
                currentState.getPlayers().get(x).addCard(deck.deal());
            }
            for(int x = 0; x < startIndex; x++){
                currentState.getPlayers().get(x).addCard(deck.deal());
            }
        }

         */
        table.updateGraphics(this.currentState);
    }

    public void whoStarts(){
        int whoWon= -1;
        while(whoWon == -1){
            this.startsSet = this.currentState.getPlayers().get(0);
            this.startsPlaying = this.currentState.getPlayers().get(0);
            this.currentState = new State(this.currentState, this.startsSet, null, 0);
            dealCards(1,0);
            JOptionPane.showMessageDialog(null,"who starts");
            whoWon= playRound();
        }
        this.startsPlaying = this.currentState.getPlayers().get(whoWon);
        this.startsSet = this.startsPlaying;
        this.currentState.setTurn(this.startsPlaying);
    }

    public int whoWins(){
        ArrayList<CardInPlay> inRound = new ArrayList<>(this.currentState.getCardsInPlay());
        while(1 <= inRound.size()){
            inRound= CardRating(inRound);
            for(int x = inRound.size()-1; x>=0; x--){
                if(inRound.get(x).getWinning() == 1){
                    return inRound.get(x).getPlayerID();
                }else if(inRound.get(x).getWinning() == 0){
                    inRound.remove(inRound.get(x));
                }
            }
        }
        return -1;
    }

    public ArrayList<CardInPlay> CardRating(ArrayList<CardInPlay> inRound){
        if(inRound.size() > 1){
            System.out.println(inRound);
            for(int i = 0; i< inRound.size(); i++){ //reset all winnings to 1 of cards
                inRound.get(i).setWinning(1);
            }
            for(int x = 0; x< inRound.size(); x++){
                for(int y = 0; y < inRound.size(); y++){
                    if(x != y){
                        if(inRound.get(x).getCard().getRank() == this.currentState.getManilha().getRank()){ //compares if both are manilhas and which is higher
                            if(inRound.get(y).getCard().getRank() == this.currentState.getManilha().getRank()){
                                if(inRound.get(x).getCard().compareSuit(inRound.get(y).getCard())< 0){
                                    inRound.get(x).setWinning(-1);
                                }
                            }
                        }else if(inRound.get(y).getCard().getRank() == this.currentState.getManilha().getRank()){
                            inRound.get(x).setWinning(-1);
                        }
                        else if(inRound.get(x).getCard().getRank() == inRound.get(y).getCard().getRank()){
                            inRound.get(x).setWinning(0);
                        }else if(inRound.get(x).getCard().compareTo(inRound.get(y).getCard()) < 0 && inRound.get(y).getCard().getRank() != this.currentState.getManilha().getRank() && inRound.get(x).getWinning() != 0){
                            inRound.get(x).setWinning(-1);
                        }
                    }
                }
            }
            System.out.println(inRound);
        }else{
            inRound.get(0).setWinning(1);
        }
        return inRound;
    }

    public int playerPlays(int player){
        JComboBox cards = new JComboBox();
        for(int x = 0; x< this.currentState.getPlayers().get(player).getCards().size(); x++){
            cards.addItem(this.currentState.getPlayers().get(player).getCards().get(x));
        }
        JOptionPane.showConfirmDialog(null, cards, "Player "+ (this.currentState.getPlayers().get(player).getId()+1) +" select a card to play", JOptionPane.DEFAULT_OPTION);
        int card = cards.getSelectedIndex();
        return card;
    }

    public void playerMakes(int player){
        JComboBox makes = new JComboBox();
        for(int x = 0; x<= cardsToGive; x++){
           makes.addItem(x);
        }
        JOptionPane.showConfirmDialog(null, makes, "Player "+ (this.currentState.getPlayers().get(player).getId()+1) +" select how many will you make", JOptionPane.DEFAULT_OPTION);
        int make = makes.getSelectedIndex();
        this.currentState.getPlayers().get(player).setToMake(make);
    }

    public void endOfRoundPane(int id){
        if(id == -1){
            JOptionPane.showMessageDialog(null,"No player won. Next round is double");
        }else {
            JOptionPane.showMessageDialog(null,"player "+ (id+1) +" won the round");
        }

    }

    public void playPane(){
        JOptionPane.showMessageDialog(null,"Play round of last cards");
    }

    public void removeDeadPlayers(){
        for(int x = this.currentState.getPlayers().size()-1; x>=0; x--){
            if(this.currentState.getPlayers().get(x).getLives() <= 0){
                this.currentState.getPlayers().remove(x);
                this.resetCardCount = true;
            }
        }
    }

    public void takeLives(){
        for(int x = this.currentState.getPlayers().size()-1; x>=0; x--){
            this.currentState.getPlayers().get(x).changeLives();
        }
    }

    public void updateStateRoundOrSet(int whoWon, boolean newSet){
        Player next = null;
        int counter =0;
        int worth = 1;

        if(newSet == true){ //new set
            if (whoWon != -1){
                this.currentState.getPlayers().get(whoWon).addMade(this.currentState.getRoundWorth());
            }
            if(this.currentState.getPlayers().indexOf(this.startsSet)+1 < this.currentState.getPlayers().size() && this.currentState.getPlayers().indexOf(this.startsSet) != -1){
                whoWon = this.currentState.getPlayers().indexOf(this.startsSet)+1;
                next = this.currentState.getPlayers().get(whoWon);
                this.startsSet=next;
                this.startsPlaying=next;
            }else if(this.currentState.getPlayers().indexOf(this.startsSet) == -1){
                for(int x = 0; x< this.currentState.getPlayers().size(); x++){
                    if(this.currentState.getPlayers().get(x).getId() > this.startsSet.getId() && counter == 0){
                        next = this.currentState.getPlayers().get(x);
                        this.startsSet=next;
                        this.startsPlaying=next;
                        counter++;
                    }else if(counter == 0){
                        next = this.currentState.getPlayers().get(0);
                        this.startsSet=next;
                        this.startsPlaying=next;
                    }
                }
            }else{
                next = this.currentState.getPlayers().get(0);
                this.startsSet=next;
                this.startsPlaying=next;
            }
            takeLives();
            removeDeadPlayers();
            table.repaintStats(this.currentState.getPlayers());
            this.currentState = new State(this.currentState, next, null, worth);
        }else{ //new round
            if(whoWon == -1){
                next = this.startsPlaying;
                worth = this.currentState.getRoundWorth()+1;
            }else{
                this.currentState.getPlayers().get(whoWon).addMade(this.currentState.getRoundWorth());
                table.repaintStats(this.currentState.getPlayers());
                if(this.currentState.getPlayers().indexOf(this.startsPlaying)+1 < this.currentState.getPlayers().size()){
                    whoWon = this.currentState.getPlayers().indexOf(this.startsPlaying)+1;
                    next = this.currentState.getPlayers().get(whoWon);
                }else{
                    next = this.currentState.getPlayers().get(0);
                }
                this.startsPlaying = next;
            }
            this.currentState = new State(this.currentState, next, this.currentState.getManilha(), worth);
        }
    }

    public void updateState(Move move){

        Player next;
        if(this.currentState.getPlayers().indexOf(this.currentState.getTurn())+1 == this.currentState.getPlayers().size()){
            next = this.currentState.getPlayers().get(0);
        }else{
            next = this.currentState.getPlayers().get(this.currentState.getPlayers().indexOf(this.currentState.getTurn())+1);
        }
        this.currentState = new State(this.currentState, move, next);
    }

}
