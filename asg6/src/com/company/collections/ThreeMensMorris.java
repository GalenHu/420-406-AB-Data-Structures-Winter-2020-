/*
 * Copyright (c) 2020 Galen Hu. All rights reserved.
 */

package com.company.collections;

import java.util.Arrays;

public class ThreeMensMorris implements Game, Copyable{
    //private field
    private Game.Token token;
    private int tokenId = 0;
    private Game.Token[][] board = new Game.Token[3][3];
    private int side = -1;       //even is white (first to go) odd is black (second to go)
    private Object Copyable;


    //Constructor
    public ThreeMensMorris() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = token.NONE;
            }
        }
    }

    //Method

    //get number of Token on the current Board
    //return the number of token
    public int getNumberOfTokenInGame(){
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != Token.NONE)
                    count++;
            }
        }
        return count;
    }


     // Play the current piece at the specified position
     // @param file The file of
     // @param rank
     // @return True if the move was performed, false otherwise.
    @Override
    public boolean play(int x, int y){
        //If the board has a token, return false
        if(board[x-1][y-1]!=token.NONE)
            return false;
        //If the board has more than 6 Token, return false
        if (getNumberOfTokenInGame()>=6)
            return false;
        side++; //if side is even, its white. if side is odd, its black
        if(side%2==0) {
            token = token.WHITE;
            tokenId++;
        }

        else if (side%2==1) {
            token = token.BLACK;
            tokenId++;
        }

        board[x-1][y-1] = token;
        return true;
    }

    //tell which side is the winner
    //Return the winning Token
    @Override
    public Token winner() {
        Game.Token winningToken = token.NONE;
        //Check Row
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0]; //Return first token of the row as winning token

            //Check Column
            for (int j = 0; j < board[i].length; j++) {
                if(board[0][j] == board[1][j] && board[1][j] == board[2][j])
                    return board[0][j]; //Return the first token of the column as winning token
            }
        }

        //Check Diagonal
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0];     //Return bottom left token as winning token/side
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2];     //Return top left token as winning token/side

            //No Winner
        else
            return winningToken;
    }

    //Make a copy of the board. Copy the value inside instead of the pointer
    //return a copy
    @Override
    public ThreeMensMorris copy() {
        ThreeMensMorris duplicate = new ThreeMensMorris();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                duplicate.board[i][j] = board[i][j];
            }
        }
        return duplicate;
    }

    //make a hashcode
    //return the hashcode
    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    //compare 2 object if they are similar
    //return true if they are similar, return false if one of the token is not the same
    @Override
    public boolean equals(Object o) {
        ThreeMensMorris rhs = (ThreeMensMorris) o;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != rhs.board[i][j])
                    return false;
            }
        }
        return true;
    }

    //return the board as String
    @Override
    public String toString() {
        String tmp = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                tmp += board[i][j];
            }
        }
        return tmp;
    }
}
