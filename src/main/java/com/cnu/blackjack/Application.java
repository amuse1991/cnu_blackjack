package com.cnu.blackjack;

public class Application {
    public static void main(String[] args) {
        Game game = new Game(new Deck(1));
        game.addPlayer("Player01",5000);
        game.addPlayer("Player02",5000);
        game.placeBet("Player01",100);
        game.placeBet("Player02",200);
        game.start();
    }
}
