package simple21;

import java.util.Scanner;
import java.util.Random;

/**
 * This is a simplified version of a common card game, "21".<p>
 * In this game, the dealer deals two "cards" to each player,
 * one hidden, so that only the player who gets the card knows
 * what it is, and one face up, so that everyone can see it.
 * (Actually, what the other players see is the <i>total</i>
 * of each other player's visible cards, not the individual cards.)<p>
 * <p>
 * The players then take turns requesting cards, trying to get
 * as close to 21 as possible, but not going over 21. These cards
 * will be visible to all players.  A player may pass (ask for no
 * more cards). Once a player has passed, he or she cannot later
 * ask for another card. When all players have passed, the
 * game ends.<p>
 * <p>
 * The winner is the player who has come closest to 21 without
 * exceeding it. In the case of a tie, or if everyone goes over
 * 21, no one wins.<p>
 * <p>
 * In this program, there are some number of computer players
 * and one human player. The game is only played once.
 *
 * @author David Matuszek and Keith Mannock
 * @author (Students : Edward Milman)
 * @version 0
 */
public class GameControl {

    /**
     * All the Player objects, including the Human player.
     * The number of players is set here; other places in the
     * program should use <code>players.length</code>.
     */
    Player[] players = new Player[4];

    /**
     * passed[i] == true indicates that players[i] has passed.
     */
    boolean[] passed = new boolean[]{false, false, false, false};

    /**
     * Used for getting input from the user.
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * A random number generator.
     */
    Random random = new Random();

    //top score possible for any card
    private final int TOPSCORE = 10;

    //integer to represent no winner
    private final int NO_WINNER = -1;

    /**
     * The main method just creates a GameControl object and calls
     * its <code>run</code> method.
     *
     * @param args Not used.
     */
    public static void main(String args[]) {
        GameControl game = new GameControl();
        game.run();
    }

    /**
     * Prints a welcome method, then calls methods to perform each
     * of the following actions:
     * <ol>
     * <li>Create the Players (one of them a Human),</li>
     * <li>Deal the initial two cards to each player,</li>
     * <li>Control the play of the game, and</li>
     * <li>Print the final results.</li>
     * </ol>
     */
    void run() {
        System.out.println("Welcome to 21 - try and score the closest to 21 without going over to win!");
        createPlayers();
        deal();
        controlPlay();
        printResults();
    }

    /**
     * Asks the human player for a name, and then creates a Human
     * object and all the other Player objects, saving them in the
     * <code>players</code> array.
     */
    void createPlayers() {
        System.out.print("What is your name?  ");
        players[0] = new Human(scanner.next());
        players[1] = new Player("John");
        players[2] = new Player("Sue");
        players[3] = new Player("Mark");
    }

    /**
     * Deals the initial two cards (one hidden, one not hidden)
     * to each player.
     */
    void deal() {
        for (Player p : players) {
            p.takeHiddenCard(nextCard());
            p.takeVisibleCard(nextCard());
        }
    }

    /**
     * Returns a random "card", represented by a integer between
     * 1 and 10, inclusive. The odds of returning a 10 are four
     * times as likely as any other value (because in an actual
     * deck of cards, 10, Jack, Queen, and King all count as 10).
     *
     * @return a random integer in the range 1..10.
     */
    int nextCard() {
        int number = random.nextInt(13) + 1;
        return number >= TOPSCORE ? TOPSCORE : number;
    }

    /**
     * Gives each player in turn a chance to take a card, until all
     * players have passed. Prints a message when a player passes.
     * Once a player has passed, that player is not given another
     * chance to take a card.<br />
     * <b>Note:</b> The global array <code>passed</code> is used to
     * keep track of which players have passed.
     */
    void controlPlay() {
        for (int i = 0; i < players.length; i++) {
            do {
                if (players[i].offerCard(players)) {
                    players[i].takeVisibleCard(nextCard());
                } else {
                    passed[i] = true;
                }
            } while (!passed[i]);
            System.out.println(players[i].getName() + " passes.");
        }
    }

    /**
     * Prints a summary at the end of the game, saying how many
     * points each player had, and who won.
     */
    void printResults() {
        System.out.println("\n===GAME OVER===\n");
        for (Player p : players) {
            System.out.println(p.getName() + " scored " + p.getScore() + "points");
        }
        int winner = findWinningPlayer();
        if (winner != NO_WINNER) {
            System.out.println(players[winner].getName() + " wins with " + players[winner].getScore() + " points!");
        } else System.out.println("It's a draw - nobody wins!");
    }

    /**
     * Determines who won the game, and returns it as an index into
     * the players array.
     *
     * @return The index of the winner, or -1 if nobody won.
     */
    int findWinningPlayer() {
        int max = 0;
        int index = 0;
        boolean draw = false;
        for (int i = 0; i < players.length; i++) {
            // if player not over 21 and score is higher than max, player index and score recorded
            if ((players[i].getScore() > max) && (players[i].getScore() <= 21)) {
                max = players[i].getScore();
                index = i;
                // possible that draw may have been set to true in previous turns
                draw = false;
            } else if (players[i].getScore() == max) {
                // record state if draw
                draw = true;
            }
        }
        return draw ? NO_WINNER : index;
    }
}