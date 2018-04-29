package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.NotEnoughBalanceException;
import lombok.Data;

@Data
public class Player {

    private int balance;
    private int currentBet;
    private Hand hand;
    private PlayerStatus status;

    public Player(int seedMoney, Hand hand) {
        this.balance = seedMoney;
        this.hand = hand;
        this.status = PlayerStatus.NONE;
    }


    public int getCurrentBet() {
        return currentBet;
    }

    public int getBalance() {
        return balance;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void placeBet(int bet) {
        if(balance < bet) {
            throw new NotEnoughBalanceException();
        }
        balance -= bet;
        currentBet = bet;
    }

    public Card hitCard() {
        return hand.drawCard();
    }

    public void evaluateCards(Player player) {

    }
}
