package epsi.blackjack;

import groovyjarjarpicocli.CommandLine.Help;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Test creation of a card
     */
    public void testToStringCard() {
        Value value = Value.TROIS;
        Suit suite = Suit.PIQUE;
        Card card = new Card(suite, value);
        assertTrue(card.toString().equals("PIQUE-TROIS"));
    }

    /**
     * Test creation of a card
     */
    public void testGetValueCard() {
        Value value = Value.TROIS;
        Suit suite = Suit.PIQUE;
        Card card = new Card(suite, value);
        assertTrue(card.getValue() == value);
    }

    /**
     * Test creation of a deck by counting number of card
     */
    public void testDeckNumberOfCard() {
        Deck deck = new Deck();
        deck.createFullDeck();
        assertTrue(deck.deckSize() == 52);
    }

    /**
     * Test count card in deck after removing
     */
    public void testDeckRemoveCard() {
        Deck deck = new Deck();
        deck.createFullDeck();
        deck.removeCard(5);
        assertTrue(deck.deckSize() == 51);
    }

    /**
     * Test count card in deck after adding
     */
    public void testDeckAddCard() {
        Deck deck = new Deck();
        deck.createFullDeck();
        deck.addCard(HelpTest.createCard());
        assertTrue(deck.deckSize() == 53);
    }

    /**
     * Test getting first card of a deck
     */
    public void testDeckGetFirstCard() {
        // First value in enum classes, so first card to be created
        Value value = Value.DEUX;
        Suit suite = Suit.COEUR;
        Card card = new Card(suite, value);
        Deck deck = new Deck();
        deck.createFullDeck();
        deck.addCard(HelpTest.createCard());
        assertTrue(deck.getCard(0).toString().equals(card.toString()));
    }

    /**
     * Test drawing card in a empty deck
     */
    public void testDeckDrawingCardInEmpty() {
        Deck deckFull = new Deck();
        deckFull.createFullDeck();
        Deck deckEmpty = new Deck();
        deckEmpty.draw(deckFull);
        assertTrue(deckEmpty.deckSize() == 1 && deckFull.deckSize() == 51);
    }

    /**
     * Test drawing card in a full deck
     */
    public void testDeckDrawingCardInFull() {
        Deck deckFull = new Deck();
        deckFull.createFullDeck();
        Deck deckFull2 = new Deck();
        deckFull2.createFullDeck();
        deckFull2.draw(deckFull);
        assertTrue(deckFull2.deckSize() == 53 && deckFull.deckSize() == 51);
    }

    /**
     * Test moving all cards in a empty deck
     */
    public void testDeckMoveAllCardInEmpty() {
        Deck deckFull = new Deck();
        deckFull.createFullDeck();
        Deck deckEmpty = new Deck();
        deckFull.moveAllToDeck(deckEmpty);
        assertTrue(deckEmpty.deckSize() == 52 && deckFull.deckSize() == 0);
    }

    /**
     * Test moving all cards in a full deck
     */
    public void testDeckMoveAllCardInFull() {
        Deck deckFull = new Deck();
        deckFull.createFullDeck();
        Deck deckFull2 = new Deck();
        deckFull2.createFullDeck();
        deckFull.moveAllToDeck(deckFull2);
        assertTrue(deckFull2.deckSize() == 104 && deckFull.deckSize() == 0);
    }
}
