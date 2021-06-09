package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Controller {
    public Button startBtn;
    public TextField betAmountInput;
    public double betAmount = 0;
    public Button betHigherBtn;
    public Button betLowerBtn;
    public String betSide;
    public Button rollDiceBtn;
    public Label diceLbl;
    public int diceNum;
    public Label playersMoneyLbl;
    public Label housesMoneyLbl;
    public double playersMoney = 100;
    public double housesMoney = 100;
    final String HIGHER = "higher";
    final String LOWER = "lower";
    public Label winLoseLbl;

    public void startGame(ActionEvent actionEvent) {
        //set money balances, dice numbers and labels
        playersMoney = 100;
        housesMoney = 100;
        playersMoneyLbl.setText("100");
        housesMoneyLbl.setText("100");
        betAmount = 0;
        //make sure win/loss label is invisible
        winLoseLbl.setVisible(false);
        //make invisible/get rid of the start button
        startBtn.setVisible(false);
        //roll dice
        int random;
        random = (int) (Math.random() * 100 % 6) + 1;
        diceLbl.setText(Integer.toString(random));
        diceNum = random;
        betHigherBtn.setDisable(false);
        betLowerBtn.setDisable(false);
    }

    public void getLowerBet(ActionEvent actionEvent) {
        //disable button
        betHigherBtn.setDisable(true);
        betLowerBtn.setDisable(true);
        //get the side the player bet
        betSide = LOWER;
        betAmountInput.setDisable(false);
    }

    public void getHigherBet(ActionEvent actionEvent){
        //disable button
        betLowerBtn.setDisable(true);
        betHigherBtn.setDisable(true);
        //get the side the player bet
        betSide = HIGHER;
        betAmountInput.setDisable(false);
    }

    public void getBetAmount(ActionEvent actionEvent) {
        //get amount bet
        //make sure it's > 0
        if(Double.parseDouble(betAmountInput.getText()) > 0){
            betAmount = Double.parseDouble(betAmountInput.getText());
            rollDiceBtn.setDisable(false);
        }
        betAmountInput.setDisable(true);
        betAmountInput.setText("amount");
    }

    public void rollDice(ActionEvent actionEvent) {
        //disable button
        rollDiceBtn.setDisable(true);
        //generate random dice number
        //re-roll if the number is the same as the previous number
        int random;
        do {
            random = (int) (Math.random() * 100 % 6) + 1;
            diceLbl.setText(Integer.toString(random));
        }while(random == diceNum);
        /*if number is smaller than the previous and the player bet smaller than the player wins the bet
        if number is larger than the previous and the player but larger, the player also wins
        otherwise the house wins*/
        if((random < diceNum && betSide.equals(LOWER)) || (random > diceNum && betSide.equals(HIGHER))){
            //change player's and house's amount of money
            playersMoney += betAmount;
            housesMoney -= betAmount;
        }
        else{
            playersMoney -= betAmount;
            housesMoney += betAmount;
        }
        //update the new dice number
        diceNum = random;
        //update money balances
        playersMoneyLbl.setText(Double.toString(playersMoney));
        housesMoneyLbl.setText(Double.toString(housesMoney));
        //check if the game should end: when either the player or the house runs out of money
        //announce win/loss
        if(playersMoney <= 0){
            winLoseLbl.setText("You lose!");
            winLoseLbl.setVisible(true);
            startBtn.setVisible(true);
            return;
        }
        else if(housesMoney <= 0){
            winLoseLbl.setText("You win!");
            winLoseLbl.setVisible(true);
            startBtn.setVisible(true);
            return;
        }
        betHigherBtn.setDisable(false);
        betLowerBtn.setDisable(false);
    }
}
