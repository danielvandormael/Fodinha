package Main.Objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card implements Comparable<Card>{
    private final Suit suit;
    private final Rank rank;

    private final static Map<String, Card> CARD_CACHE= initCache();

    private static Map<String,Card> initCache() {
        final Map <String, Card> cache = new HashMap<>();

        for(final Suit suit: Suit.values()){
            for (final Rank rank: Rank.values()){
                cache.put(CardKey(rank, suit), new Card(rank, suit));
            }
        }
        return Collections.unmodifiableMap(cache);
    }

    private static String CardKey(final Rank rank, final Suit suit){
        return rank + " of " + suit;
    }

    private Card(final Rank rank, final Suit suit){
        this.rank=rank;
        this.suit=suit;
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(this.rank.getRankValue(), o.rank.getRankValue());
    }

    public int compareSuit(Card o) {
        return Integer.compare(this.suit.getSuitValue(), o.suit.getSuitValue());
    }

    public int getRank() {
        return this.rank.getRankValue();
    }

    public int getSuit() { return this.suit.getSuitValue(); }

    enum Suit{
        DIAMONDS(1),
        SPADES(2),
        HEARTS(3),
        CLUBS(4);

        private final int suitValue;

        public int getSuitValue() {
            return suitValue;
        }

        Suit(final int suitValue){
            this.suitValue=suitValue;
        }
    }

    enum Rank{

        FOUR(2),
        FIVE(3),
        SIX(4),
        SEVEN(5),
        QUEEN(6),
        JACK(7),
        KING(8),
        ACE(9),
        TWO(10),
        THREE(11);

        private final int rankValue;

        public int getRankValue() {
            return rankValue;
        }

        Rank(int i) {
            this.rankValue= i;
        }
    }

    @Override
    public String toString(){
        return String.format("%s of %s", this.rank,  this.suit);
    }

    public static Card getCard( final Rank rank, final Suit suit){
        return CARD_CACHE.get(CardKey(rank, suit));
    }

}
