package com.company;

import com.company.collections.Game.Token;
import com.company.collections.ThreeMensMorris;
import com.company.collections.Game;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static int position = 0;

    static List<ThreeMensMorris> dummyList = new ArrayList<>();
    static int whiteX = 1;
    static int whiteY = 1;
    static int blackX = 1;
    static int blackY = 1;

    public static void main(String[] args) {
        System.out.println("I am white: "+ Token.WHITE);
        System.out.println("I am black: "+ Token.BLACK);

        ThreeMensMorris original = new ThreeMensMorris();

        original.play(2, 2);
        original.play(3, 1);
        original.play(1, 2);
        original.play(3, 2);

        System.out.println(original.toString());

        System.out.println("Number of piece play: " + original.getNumberOfTokenInGame());

        int counter = 0;
        for (ThreeMensMorris game: generate(original)) {
            counter++;
            System.out.print(game.toString() + " : Winning side is: " + game.winner() + "\n");
        }
        System.out.println("Number of configuration: " + counter);
    }

    public static List<ThreeMensMorris> generate(ThreeMensMorris initial){

        List<ThreeMensMorris> configList= new ArrayList<>();
        configList.add(initial);

        configList=generateHelper(initial, configList);

        for (int i = 0; i < configList.size(); i++) {
            //Remove board with less than 6 token
            if (configList.get(i).getNumberOfTokenInGame()<6)
                configList.remove(i);
        }

        return configList;

    }


    /*
    Start with initial board, add to list, increase black file, once it reaches 3, reset and increase black rank
    once that reaches 3, reset and increase white file. Once that reaches 3, then increase white ranks until its 3
    Then its finish. This way, it goes through every possibility
     */
    private static List<ThreeMensMorris> generateHelper(ThreeMensMorris initial, List<ThreeMensMorris> acc){
        if(whiteY <= 3){
            ThreeMensMorris current = initial.copy();
            if(current.play(whiteX, whiteY) && current.play(blackX, blackY))
                acc.add(current);

            //Each  time blackX touch 3, reset it and work on the next blackY
            if(blackX >= 3){
                blackX = 1;
                blackY++;
            }
            else
                blackX++;

            //Each time blackY touch3, reset it and work on the whiteX
            if (blackY > 3){
                blackY = 1;
                whiteX++;
            }

            //Each time blackX touch 3, reset it and increment whiteY
            if(whiteX >3){
                whiteX = 1;
                whiteY++;
            }

            acc = generateHelper(initial, acc);
        }
        return acc;
    }
}
