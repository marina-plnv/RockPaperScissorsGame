package itransitioncourse.game;

import java.util.ArrayList;

public class Winner {

    public String getWinner(String userMove, String compMove, ArrayList<String> gameMoves) {
        int userMoveInt = Integer.parseInt(userMove);
        int computerMoveInt = Integer.parseInt(compMove);
        if (userMoveInt == computerMoveInt) {
            return "DRAW";
        } else if (((userMoveInt <= (gameMoves.size() + 1) / 2)
                && ((computerMoveInt > userMoveInt) && (computerMoveInt < userMoveInt + (gameMoves.size() + 1) / 2)))
                || ((userMoveInt > (gameMoves.size() + 1) / 2)
                && ((computerMoveInt > userMoveInt) || (computerMoveInt < (userMoveInt - (gameMoves.size() - 1) / 2))))) {
            return "LOSE";
        } else {
            return "WIN";
        }
    }

    public void printWinner(String winner) {
        String result = switch (winner) {
            case "WIN" -> "You win!";
            case "LOSE" -> "You lose!";
            case "DRAW" -> "It's a draw!";
            default -> "Unpredicted value.";
        };
        System.out.println(result);
    }
}
