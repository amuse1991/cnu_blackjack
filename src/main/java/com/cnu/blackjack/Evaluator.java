package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.EvaluatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Evaluator {

    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
        dealer = new Dealer();
        dealCardToPlayers();
    }

    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void start() {
        while(!isGameOver(this.playerMap)){
            for(Player player : this.getPlayerMap().values()){
                PlayerStatus status = player.getStatus();
                int score = evaluatePlayerScore(player);

                if(status != PlayerStatus.PLAYING){
                    continue;
                }else{
                    if(score <= 16){
                        player.hitCard();
                    }
                    if(score >= 17 && score < 21) {
                        player.setStatus(PlayerStatus.STAND);
                    }
                    if(score == 21){
                        player.setStatus(PlayerStatus.BLACKJACK);
                    }
                    if(score > 21){
                        player.setStatus(PlayerStatus.BUST);
                    }
                }
            }
        }
        finish(this.playerMap);
    }

    private boolean isGameOver(Map<String,Player>playerMap){
        for(Player player : playerMap.values()){
            if(player.getStatus() == PlayerStatus.PLAYING){
                return false;
            }
        }
        return true;
    }

    private void dealCardToPlayers() {
        playerMap.forEach((name, player) -> {
            player.hitCard();
            player.hitCard();
        });
    }


    public int evaluatePlayerScore(Player player) {
        int sum = 0;
        for(Card card : player.getHand().getCardList()){
            sum += card.getRank();
        }
        return sum;
    }

    public void finish(Map<String, Player> playerMap) {
        Dealer dealer = new Dealer();
        int dealerScore = dealer.getDealerScore();
        System.out.println("딜러 점수 합계 : " + dealerScore + "\r\n");

        for(Player player : playerMap.values()){
            int playerScore = evaluatePlayerScore(player);
            switch (player.getStatus()){
                case STAND:
                    if(dealerScore >= playerScore){
                        if(dealerScore > 21 && playerScore <= 21){
                            player.setBalance(player.getBalance() + (player.getCurrentBet()*2));
                            System.out.println("카드합계:" + playerScore + "\r\n" +getName(this.playerMap,player)+"승리\r\n"+"현재금액:"+ player.getBalance() + "\r\n");
                        }
                        else{
                            player.setBalance(player.getBalance());
                            System.out.println("카드합계:" + playerScore + "\r\n" +getName(this.playerMap,player)+"패배\r\n"+"현재금액:"+ player.getBalance() + "\r\n");
                        }
                    }else{ //플레이어가 이김
                        player.setBalance(player.getBalance() + (player.getCurrentBet())*2);
                        System.out.println("카드합계:" + playerScore + "\r\n" +getName(this.playerMap,player)+"승리\r\n"+"현재금액:"+ player.getBalance() + "\r\n");
                    }
                    break;
                case BUST:
                    player.setBalance(player.getBalance());
                    System.out.println("카드합계:" + playerScore + "\r\n" +getName(this.playerMap,player)+"패배\r\n"+"현재금액:"+ player.getBalance() + "\r\n");
                    //플레이어 짐
                    break;
                case BLACKJACK:
                    player.setBalance(player.getBalance() + (player.getCurrentBet()*3));
                    System.out.println("카드합계:" + playerScore + "\r\n" + getName(this.playerMap,player)+"블랙잭!\r\n"+"현재금액:"+ player.getBalance() + "\r\n");
                    //돈 두배줌
                    break;
                default:
                    throw new EvaluatorException();
            }
        }
    }
    private String getName(Map<String,Player> playerMap, Player value) {
        for (String key : playerMap.keySet()) {
            if (playerMap.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }
}
