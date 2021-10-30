package itransitioncourse.game;

import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;

public class Table {

    public void displayMenu(ArrayList<String> moves) {
        System.out.println("Available moves:");
        for (int i = 1; i <= moves.size(); i++) {
            System.out.println(i + " - " + moves.get(i - 1));
        }
        System.out.println("0 - exit");
        System.out.println("? - help");
    }

    public void displayHelpTable(ArrayList<String> moves) {
        String[][] tableContent = getHelpTableContent(moves);
        AsciiTable at = new AsciiTable();
        at.addRule();
        for (int i = 0; i < tableContent.length; i++) {
            at.addRow(tableContent[i]);
            at.addRule();
        }
        at.getContext().setWidth(60);
        String rend = at.render();
        System.out.println(rend);
    }

    private String[][] getHelpTableContent(ArrayList<String> moves) {
        Winner winner = new Winner();
        String[][] tableContent = new String[moves.size() + 1][moves.size() + 1];
        tableContent[0][0] = "PC \\ User";
        for (int k = 0; k < moves.size(); k++) {
            tableContent[0][k + 1] = moves.get(k);
        }
        for (int i = 0; i < moves.size(); i++) {
            tableContent[i + 1][0] = moves.get(i);
            for (int j = 0; j < moves.size(); j++) {
                tableContent[i + 1][j + 1] = winner.getWinner(Integer.toString(i + 1), Integer.toString(j + 1), moves);
            }
        }
        return tableContent;
    }
}

