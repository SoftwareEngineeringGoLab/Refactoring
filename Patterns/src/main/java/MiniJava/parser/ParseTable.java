package MiniJava.parser;

import MiniJava.errorHandler.ErrorHandler;
import MiniJava.parser.action.Accept;
import MiniJava.parser.action.Action;
import MiniJava.parser.action.Reduce;
import MiniJava.parser.action.Shift;
import MiniJava.scanner.token.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohammad hosein on 6/25/2015.
 */

public class ParseTable {
    private ArrayList<Map<Token, Action>> actionTable;
    private ArrayList<Map<NonTerminal, Integer>> gotoTable;

    private ParseTable(ArrayList<Map<Token, Action>> actionTable, ArrayList<Map<NonTerminal, Integer>> gotoTable) {
        this.actionTable = actionTable;
        this.gotoTable = gotoTable;
    }

    public static ParseTable createParseTable(String jsonTable) throws Exception {
        String[] rows = ParseTable.getRows(jsonTable);
        String[] cols = ParseTable.getFirstCols(rows);

        Map<Integer, Token> terminals = new HashMap<Integer, Token>();
        Map<Integer, NonTerminal> nonTerminals = new HashMap<Integer, NonTerminal>();
        ParseTable.updateTerminalsAndNonTerminals(cols, terminals, nonTerminals);

        ArrayList<Map<Token, Action>> actionTable = new ArrayList<Map<Token, Action>>();
        ArrayList<Map<NonTerminal, Integer>>  gotoTable = new ArrayList<Map<NonTerminal, Integer>>();
        getSymbolsFromRows(rows, terminals, nonTerminals, actionTable, gotoTable);

        return new ParseTable(actionTable, gotoTable);
    }

    private static void getSymbolsFromRows(String[] rows, Map<Integer, Token> terminals, Map<Integer, NonTerminal> nonTerminals, ArrayList<Map<Token, Action>> actionTable, ArrayList<Map<NonTerminal, Integer>> gotoTable) throws Exception {
        String[] cols;
        for (int i = 1; i < rows.length; i++) {
            rows[i] = rows[i].substring(1, rows[i].length() - 1);
            cols = rows[i].split("\",\"");
            actionTable.add(new HashMap<Token, Action>());
            gotoTable.add(new HashMap<>());
            getRowSymbolsFromColumns(cols, terminals, nonTerminals, actionTable, gotoTable);
        }
    }

    private static void getRowSymbolsFromColumns(String[] cols, Map<Integer, Token> terminals, Map<Integer, NonTerminal> nonTerminals, ArrayList<Map<Token, Action>> actionTable, ArrayList<Map<NonTerminal, Integer>> gotoTable) throws Exception {
        for (int j = 1; j < cols.length; j++) {
            if (!cols[j].equals("")) {
                if (cols[j].equals("acc")) {
                    actionTable.get(actionTable.size() - 1).put(terminals.get(j), new Accept());
                } else if (terminals.containsKey(j)) {
                    Token t = terminals.get(j);
                    int actionNumber = Integer.parseInt(cols[j].substring(1));
                    Action a;
                    if (cols[j].charAt(0) == 'r') a = new Reduce(actionNumber);
                    else a = new Shift(actionNumber);
                    actionTable.get(actionTable.size() - 1).put(t, a);
                } else if (nonTerminals.containsKey(j)) {
                    gotoTable.get(gotoTable.size() - 1).put(nonTerminals.get(j), Integer.parseInt(cols[j]));
                } else {
                    throw new Exception();
                }
            }
        }
    }

    private static String[] getRows(String jsonTable) {
        jsonTable = jsonTable.substring(2, jsonTable.length() - 2);
        String[] rows = jsonTable.split("\\],\\[");
        rows[0] = rows[0].substring(1, rows[0].length() - 1);
        return rows;
    }

    private static String[] getFirstCols(String[] rows) {
        String[] cols = rows[0].split("\",\"");
        for (int i = 0; i < cols.length; i++) {
            cols[i] = cols[i].replaceAll("\"", "");
        }
        return cols;
    }

    private static void updateTerminalsAndNonTerminals(String[] cols, Map<Integer, Token> terminals, Map<Integer, NonTerminal> nonTerminals) {
        for (int i = 1; i < cols.length; i++) {
            if (cols[i].startsWith("Goto")) {
                String temp = cols[i].substring(5);
                try {
                    nonTerminals.put(i, NonTerminal.valueOf(temp));
                } catch (Exception e) {
                    ErrorHandler.printError(e.getMessage());
                }
            } else {
                terminals.put(i, new Token(Token.getTyepFormString(cols[i]), cols[i]));
            }
        }
    }

    public int getGotoTable(int currentState, NonTerminal variable) {
//        try {
        return gotoTable.get(currentState).get(variable);
//        }catch (NullPointerException dd)
//        {
//            dd.printStackTrace();
//        }
//        return 0;
    }

    public Action getActionTable(int currentState, Token terminal) {
        return actionTable.get(currentState).get(terminal);
    }
}
