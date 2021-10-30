package itransitioncourse.game;

import org.apache.commons.lang3.Range;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static ArrayList<String> gameMoves;
    private static String userMove;
    private static String compMove;
    boolean inputNotValid = true;
    private Table table = new Table();
    final static int SECURE_KEY_LENGTH = 128;

    Game(ArrayList<String> moves) {
        this.gameMoves = moves;
    }

    private boolean isNumeric(String inputString) {
        if ((inputString.isEmpty()) || (inputString.length() == 0)) {
            return false;
        } else {
            try {
                int inputInt = Integer.parseInt(inputString);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    }

    private void scanUserMove() {
        Scanner inputScanner = new Scanner(System.in);
        this.userMove = inputScanner.nextLine();
    }

    private void validateUserMove() {
        Range range = Range.between(1, gameMoves.size());
        if ((userMove.isEmpty())||((!isNumeric(userMove)) & (!"?".equals(userMove)))) {
            System.out.println("Your input is incorrect. Please try again");
            return;
        } else if ("0".equals(userMove)) {
            System.exit(0);
        } else if ("?".equals(userMove)) {
            table.displayHelpTable(gameMoves);
        } else if (range.contains(Integer.parseInt(userMove))) {
            inputNotValid = false;
        } else {
            System.out.println("Your input is incorrect. Please try again");
        }
    }

    private void setUserMove() {
        while (inputNotValid) {
            table.displayMenu(gameMoves);
            System.out.print("Enter your move (number): ");
            scanUserMove();
            validateUserMove();
        }
    }

    private void setCompMove() {
        Random random = new Random();
        compMove = String.valueOf(random.nextInt(gameMoves.size()) + 1);
    }

    private void printPlayersMoves() {
        System.out.println("Your move: " + gameMoves.get(Integer.parseInt(userMove) - 1));
        System.out.println("Computer move: " + gameMoves.get(Integer.parseInt(compMove) - 1));
    }

    private void playGame() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Winner winner = new Winner();
        MoveHash moveHash = new MoveHash();
        setCompMove();
        byte[] secureKey = moveHash.getSecureKey(SECURE_KEY_LENGTH / 8);
        byte[] hmac = moveHash.getHMAC(compMove, secureKey);
        System.out.println("HMAC: " + moveHash.bytesToHex(hmac));
        setUserMove();
        printPlayersMoves();
        String win = winner.getWinner(userMove, compMove, gameMoves);
        winner.printWinner(win);
        System.out.println("HMAC key:" + moveHash.bytesToHex(secureKey));
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        ArrayList<String> moves = new ArrayList<>();
        for (String str : args) {
            if (moves.contains(str)) {
                throw new IllegalArgumentException("Parameters should be unique values.");
            } else {
                moves.add(str.toLowerCase());
            }
        }
        if ((moves.size() % 2 == 0) || (moves.size() < 3)) {
            throw new IllegalArgumentException("The number of parameters should be odd and at least 3.");
        }
        Game game = new Game(moves);
        game.playGame();
    }
}