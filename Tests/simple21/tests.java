package simple21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class tests {
    static GameControl game;

    @BeforeAll
    public static void setUp(){
        game = new GameControl();
        game.players[0] = new Player("a");
        game.players[0].takeVisibleCard(1);
        game.players[1] = new Player("b");
        game.players[1].takeVisibleCard(15);
        game.players[2] = new Player("c");
        game.players[2].sumOfVisibleCards = 16;
        game.players[3] = new Player("d");
        game.players[3].sumOfVisibleCards = 20;
    }

    @Test
    public void testNextCard() {
        for (int i = 0; i < 1000; i++) {
            assert game.nextCard() <= 10;
            assert game.nextCard() > 0;
        }
    }

    @Test
    public void testTakeVisibleCard(){
        // player 0 already has 1
        game.players[0].takeVisibleCard(10);
        assertEquals(game.players[0].sumOfVisibleCards, 11);
        game.players[0].takeVisibleCard(5);
        assertEquals(game.players[0].sumOfVisibleCards, 16);
    }


    @Test
    public void testOfferCard(){
        assertEquals(game.players[0].offerCard(game.players), true);
        assertEquals(game.players[1].offerCard(game.players), true);
        assertEquals(game.players[2].offerCard(game.players), false);
        assertEquals(game.players[3].offerCard(game.players), false);
    }

    @Test
    public void testGetScore(){
        Player a = new Player("a");
        assertEquals(a.getScore(), 0);
        a.takeVisibleCard(10);
        assertEquals(a.getScore(), 10);
        a.takeVisibleCard(5);
        assertEquals(a.getScore(), 15);
    }

    @Test
    public void testHiddenCard(){
        Player a = new Player("a");
        assertEquals(a.getScore(), 0);
        a.takeHiddenCard(10);
        assertEquals(a.getScore(), 10);
        a.takeHiddenCard(10);
        assertEquals(a.getScore(), 20);
    }

    @Test
    public void testFindWinner(){
        assertEquals(game.findWinningPlayer(), 3);
        game.players[3].sumOfVisibleCards = 22; // test player over limit
        assertEquals(game.findWinningPlayer(), 2);
        game.players[1].sumOfVisibleCards = 16; // test draw
        assertEquals(game.findWinningPlayer(), -1);
    }

    @Test
    public void testControlPlay(){
        game.players[0].sumOfVisibleCards = 20;
        game.players[1].sumOfVisibleCards = 20;
        game.players[2].sumOfVisibleCards = 20;
        game.players[3].sumOfVisibleCards = 20;
        game.controlPlay();
        for(boolean bool: game.passed){
            assertEquals(bool, true);
        }
    }
}
