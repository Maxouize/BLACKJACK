# BLACKJACK

## Learning Goals

- Utilize conditional logic and looping
- Gain an introduction to the command line interface

## Background

In [Blackjack](http://en.wikipedia.org/wiki/Blackjack), the goal is to have a
hand that is closer to 21 than the dealer's hand without ever exceeding a card
total of 21.

However, in this simplified version of Blackjack, we'll cut out that "compare
with the dealer's hand" part and pretend that the goal of the game is to have a
card total of, or very close to, _but never exceeding_, 21.

To start, a player gets dealt two cards, each of which has values between 1-11.
Then, the player is asked if they want to "hit" (get another card dealt to
them), or "stay".

If they hit, they get dealt another card. If the sum of their three cards
exceeds 21, they've lost. If it does not, they can decide to hit or stay again
FOREVER.