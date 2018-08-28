package simple21;

/**
 * Represents a player in this simplified version of the
 * "21" card game.
 *
 * @author David Matuszek and Keith Mannock
 * @author (Students : Edward Milman)
 * @version 0
 */
public class Player {

    /**
     * The name of the player (used for printing purposes).
     */
    String name;

    /**
     * The player's one hidden card (a value 1..10).
     */
    private int hiddenCard = 0;

    /**
     * The sum of the player's cards, not counting the hidden card.
     */
    public int sumOfVisibleCards = 0;

    // score boundary for taking another card
    private final int TWIST = 16;

    /**
     * Constructs a player with the given name.
     *
     * @param name the name to be given to this player.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Decides whether to take another card. Information available
     * in making this decision includes the sum of all this Player's
     * cards, and the sum of each other Player's visible cards, but
     * not the value of other Player's hidden cards.
     *
     * @param players An array of all Players, including this one.
     * @return <code>true</code> if the decision is to take another card.
     */
    boolean offerCard(Player[] players) {
        int max = 0;
        for(Player name: players){
            if(name.sumOfVisibleCards > max){
                max = name.sumOfVisibleCards;
            }
        }
        // return true if players hidden card + visible card is scoring less than 16 (TWIST) or less than
        // TWIST - largest visible card score (whichever is larger)
        return this.hiddenCard + this.sumOfVisibleCards < Math.max(TWIST, TWIST - max);
    }

    /**
     * Adds the given card to the sum of the visible cards for this
     * Player, and prints a message saying that the card is being taken.
     *
     * @param card The card (1..10) being taken.
     */
    void takeVisibleCard(int card) {
        System.out.println(this.name + " takes a " + card);
        sumOfVisibleCards += card;
    }

    /**
     * Puts the specified card in this player's hand, and prints a
     * message saying that the card is being taken, but does not
     * print the value of the card.
     *
     * @param card the card received from the dealer
     */
    void takeHiddenCard(int card) {
        System.out.println(this.name + " takes a hidden card.");
        hiddenCard += card;
    }

    /**
     * Return this player's score (the total of all this player's cards).<br />
     * <b>Note:</b> The way this program is organized, there is nothing to
     * prevent another player from calling this routine to "cheat." Please
     * don't do so. There are various ways to prevent cheating, but they all
     * add complexity that we don't need in a program this small.
     *
     * @return The total of the cards (both visible and hidden) in this player's hand.
     */
    int getScore() {
        return this.hiddenCard + this.sumOfVisibleCards;
    }


    /**
     * getter for player name
     * @return String of player name
     */
    public String getName(){return this.name;}
}
