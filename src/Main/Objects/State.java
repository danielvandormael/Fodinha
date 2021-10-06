package Main.Objects;

import java.util.ArrayList;

public class State {
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<CardInPlay> cardsInPlay = new ArrayList<CardInPlay>();
    private Card manilha;
    private Player turn;
    private int roundWorth;


    public State(int players, int lives, int roundWorth) { //initialize state
        for(int x= 0; x<players; x++){
            this.players.add(new Player(lives, x));
        }
        this.roundWorth = roundWorth;
    }

    public State(State previousState, Move move, Player turn){ //during the round
        this.players = previousState.players;
        this.manilha = previousState.manilha;
        this.turn = turn;
        this.cardsInPlay = previousState.getCardsInPlay();
        this.cardsInPlay.add(new CardInPlay(move.getCard(), move.getPlayer().getId()));
        this.roundWorth = previousState.roundWorth;
        //delete from player hand
    }

    public State(State previousState, Player turn, Card manilha, int roundWorth){ //new round or set
        this.players = previousState.players;
        this.manilha = manilha;
        this.turn = turn;
        this.cardsInPlay.clear();
        this.roundWorth = roundWorth;
    }

    public Player getTurn() {
        return turn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<CardInPlay> getCardsInPlay() {
        return cardsInPlay;
    }

    public Card getManilha() {
        return manilha;
    }

    public int getRoundWorth() {
        return roundWorth;
    }

    public void setManilha(Card manilha) {
        this.manilha = manilha;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

    public int cardsInHand(){
        return players.get(0).getCardCount();
    }
}
