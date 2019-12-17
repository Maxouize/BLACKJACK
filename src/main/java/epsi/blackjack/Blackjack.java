package epsi.blackjack;

import java.util.Scanner;

public class Blackjack {
	
    public static void main(String[] args) {
    	System.out.println("Bienvenue au Blackjack!");

		// playingDeck : jeu que détient le croupier
		Deck playingDeck = new Deck();
		playingDeck.createFullDeck();
		playingDeck.shuffle();

		// playerCards : cartes que détient le joueur
		Deck playerCards = new Deck();
		
		double playerMoney = 100.0;
		
		// dealerCards : cartes que détient le dealer
		Deck dealerCards = new Deck();

		Scanner userInput = new Scanner(System.in);

		// Game loop
		while (playerMoney > 0) {
			
			// Prendre le pari
			System.out.println("Vous avez " + playerMoney + "€, combien voulez-vous miser ?");
			double playerBet = userInput.nextDouble();
			boolean endRound = false;
			if (playerBet > playerMoney) {
				System.out.println("Vous ne pouvez pas parier plus que ce que vous avez.");
				break;
			}

			System.out.println("Pari...");

			playerCards.draw(playingDeck);
			playerCards.draw(playingDeck);

			dealerCards.draw(playingDeck);
			dealerCards.draw(playingDeck);

			// While loop tirage nouvelles cartes
			while (true) {
				// Afficher cartes joueur
				System.out.println("Votre main : " + playerCards.toString());

				// Aficher valeur
				System.out.println("Your main est évaluée à : " + playerCards.cardsValue());

				// Afficher cartes dealer
				System.out.println("Main Dealer : " + dealerCards.getCard(0).toString()); // + " and [hidden]"

				System.out.println("Voulez-vous (1)Tirer ou (2)Passer");
				int response = userInput.nextInt();
				// Tirage
				if (response == 1) {
					playerCards.draw(playingDeck);
					System.out.println("Vous avez tiré : " + playerCards.getCard(playerCards.deckSize() - 1).toString());
					
					if (playerCards.cardsValue() > 21) {
						System.out.println("Bust. Valeur : " + playerCards.cardsValue());
						playerMoney -= playerBet;
						endRound = true;
						break;
					}
				}

				// Passer
				if (response == 2) {
					break;
				}
			}

			// Révéler cartes dealer
			System.out.println("Cartes dealer:" + dealerCards.toString());
			
			if ((dealerCards.cardsValue() > playerCards.cardsValue()) && endRound == false) {
				System.out.println("Dealer beats you " + dealerCards.cardsValue() + " to " + playerCards.cardsValue());
				playerMoney -= playerBet;
				endRound = true;
			}
			
			// Dealer tire à 16, reste à 17
			while ((dealerCards.cardsValue() < 17) && endRound == false) {
				dealerCards.draw(playingDeck);
				System.out.println("Dealer tire : " + dealerCards.getCard(dealerCards.deckSize() - 1).toString());
			}

			System.out.println("Main dealer évalué à : " + dealerCards.cardsValue());
			if ((dealerCards.cardsValue() > 21) && endRound == false) {
				System.out.println("Dealer Busts. Vous gagnez!");
				playerMoney += playerBet;
				endRound = true;
			}

			if ((dealerCards.cardsValue() == playerCards.cardsValue()) && endRound == false) {
				System.out.println("Push.");
				endRound = true;
			}
			
			if ((playerCards.cardsValue() > dealerCards.cardsValue()) && endRound == false) {
				System.out.println("Vous gagnez la main.");
				playerMoney += playerBet;
				endRound = true;
			} else if (endRound == false) {
				System.out.println("Dealer gagne.");
				playerMoney -= playerBet;
			}

			playerCards.moveAllToDeck(playingDeck);
			dealerCards.moveAllToDeck(playingDeck);
			System.out.println("Fin de la main.");

		}

		System.out.println("Game over! Vous avez perdu tout votre argent. :(");

		userInput.close();
    }
}
