package Main.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Player {
    private ArrayList<Card> cards = new ArrayList<>();
    private int lives;
    private int made;
    private int toMake;
    private int id;


    public Player(int lives, int id) {
        this.lives = lives;
        this.id = id;
        made = 0;
    }

    public void removeCard(int index){
        cards.remove(index);
    }

    public void addCard(Optional<Card> card){
        this.cards.add(card.get());
    }

    public Card getCard (int index) {
        return this.cards.get(index);
    }

    public void sortPlayerCards(){
        Collections.sort(this.cards);
    }

    @Override
    public String toString() {
        return "{" +
                "cards=" + cards +
                '}';
    }

   public void changeLives(){
        if(this.toMake-this.made < 0){
            this.lives -= (this.toMake-this.made)*(-1);
        }else{
            this.lives -= this.toMake-this.made;
        }
        this.toMake = 0;
        this.made = 0;
   }


    public void addMade(int made){
        this.made += made;
    }

    public void resetMade(){
        this.made = 0;
    }

    public void setToMake(int toMake){
        this.toMake = toMake;
    }

    public int getId() {
        return id;
    }

    public int getCardCount(){
        return this.cards.size();
    }

    public int getLives() {
        return lives;
    }

    public int getMade() {
        return made;
    }

    public int getToMake() {
        return toMake;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
