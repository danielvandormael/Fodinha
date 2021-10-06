package Main.Objects;


public class CardInPlay {
    private Card card;
    private int playerID;
    private int winning;

    public CardInPlay(Card card, int playerID) {
        this.card = card;
        this.playerID = playerID;
        this.winning = 1;
    }

    public Card getCard() {
        return this.card;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public int getWinning() {
        return this.winning;
    }

    public void setWinning(int winning) {
        this.winning = winning;
    }

    @Override
    public String toString() {
        return "CardInPlay{" +
                "Card=" + card +
                ", playerID=" + playerID +
                ", winning=" + winning +
                '}';
    }
}
