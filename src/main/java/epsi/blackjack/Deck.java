package epsi.blackjack;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

	private ArrayList<Card> cards;

	/**
	 * 
	 */
	public Deck() {
		this.cards = new ArrayList<Card>();
	}

	/**
	 * Génération des cartes
	 */
	public void createFullDeck() {
		for (Suit cardSuit : Suit.values()) {
			for (Value cardValue : Value.values()) {
				this.cards.add(new Card(cardSuit, cardValue));
			}
		}
	}

	/**
	 * Mélanger le jeu de carte
	 */
	public void shuffle() {
		ArrayList<Card> tmpDeck = new ArrayList<Card>();
		Random random = new Random();
		int randomCardIndex = 0;
		int originalSize = this.cards.size();
		for (int i = 0; i < originalSize; i++) {
			randomCardIndex = random.nextInt((this.cards.size() - 1) + 1);
			// randomCardIndex = random.nextInt((this.cards.size() - 1 - 0) + 1) + 0;
			// Jeter une carte au hasard dans le nouveau deck
			tmpDeck.add(this.cards.get(randomCardIndex));
			// Supprimer carte de l'ancien deck
			this.cards.remove(randomCardIndex);
		}
		// Set le nouveau paquet mélangé
		this.cards = tmpDeck;
	}

	/**
	 * Supprime la carte du paquet
	 * @param i
	 */
	public void removeCard(int i) {
		this.cards.remove(i);
	}

	/**
	 * Récupère la carte du paquet
	 * @param i
	 * @return card
	 */
	public Card getCard(int i) {
		return this.cards.get(i);
	}

	/**
	 * Ajoute carte au paquet
	 * @param addCard
	 */
	public void addCard(Card addCard) {
		this.cards.add(addCard);
	}

	/**
	 * Tire la première carte du paquet
	 * @param comingFrom
	 */
	public void draw(Deck comingFrom) {
		// Ajouter la carte au paquet
		this.cards.add(comingFrom.getCard(0));
		// Supprime la carte dans le paquet
		comingFrom.removeCard(0);
	}

	/**
	 * 
	 */
	public String toString() {
		String cardListOutput = "";
		int i = 0;
		for (Card aCard : this.cards) {
			cardListOutput += "\n" + aCard.toString();
			i++;
		}
		return cardListOutput;
	}

	/**
	 * Déplaces toutes les cartes dans le paquet
	 * @param moveTo
	 */
	public void moveAllToDeck(Deck moveTo) {
		int thisDeckSize = this.cards.size();

		for (int i = 0; i < thisDeckSize; i++) {
			moveTo.addCard(this.getCard(i));
		}

		for (int i = 0; i < thisDeckSize; i++) {
			this.removeCard(0);
		}
	}

	/**
	 * Taille du paquet
	 * @return taille paquet
	 */
	public int deckSize() {
		return this.cards.size();
	}

	/**
	 * Valeur de la carte
	 * @return
	 */
	public int cardsValue() {
		int totalValue = 0;
		int aces = 0;

		for (Card aCard : this.cards) {
			switch (aCard.getValue()) {
			case DEUX:
				totalValue += 2;
				break;
			case TROIS:
				totalValue += 3;
				break;
			case QUATRE:
				totalValue += 4;
				break;
			case CINQ:
				totalValue += 5;
				break;
			case SIX:
				totalValue += 6;
				break;
			case SEPT:
				totalValue += 7;
				break;
			case HUIT:
				totalValue += 8;
				break;
			case NEUF:
				totalValue += 9;
				break;
			case DIX:
				totalValue += 10;
				break;
			case VALET:
				totalValue += 10;
				break;
			case REINE:
				totalValue += 10;
				break;
			case ROI:
				totalValue += 10;
				break;
			case AS:
				aces += 1;
				break;
			}
		}

		// Determine valeur totale avec les as
		// As valant 11 ou 1 - si 11 dépassait 21, ça vaut 1
		for (int i = 0; i < aces; i++) {

			// S'ils sont déjà à plus de 10, obtenir un as évalué à 11 
			// mettrait la valeur jusqu'à 22
			if (totalValue > 10) {
				totalValue += 1;
			} else {
				totalValue += 11;
			}
		}

		return totalValue;
	}

}
