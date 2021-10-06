package Main.Objects;

import java.util.Collections;
import java.util.Optional;
import java.util.Stack;

public class Deck {

    private final Stack<Card> deckCards;

    public Deck(Boolean shuffle){
        this.deckCards= initDeck(shuffle);
    }

    private Stack<Card> initDeck(Boolean shuffle){

        final Stack<Card> deckCards = new Stack<>();

        for(final Card.Suit suit : Card.Suit.values()){
            for(final Card.Rank rank : Card.Rank.values()){
                deckCards.push(Card.getCard(rank, suit));
            }
        }

        if(shuffle == true){
            Collections.shuffle(deckCards);
        }else {
            Collections.sort(deckCards);
        }
        return deckCards;
    }

    /*
    public static void main(String [] args){
        final Deck deck = new Deck(true);
        final int count = 40;
        IntStream.range(0, count).forEach( value -> System.out.println(deck.deal()));

    }
     */
    public Optional<Card> deal(){
        return this.deckCards.empty() ? Optional.empty() : Optional.of(this.deckCards.pop());
    }
}
