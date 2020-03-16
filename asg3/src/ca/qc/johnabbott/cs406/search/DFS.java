/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.SparseArray;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Generator;
import ca.qc.johnabbott.cs406.terrain.Location;
import ca.qc.johnabbott.cs406.terrain.Terrain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class DFS implements Search{
    // records where we've been and what steps we've taken.
    private SparseArray<Cell> memory;

    // for tracking the "traversable" solution.
    private Location solution;
    private boolean foundSolution;

    // the terrain we're searching in.
    private Terrain terrain;

    /**
     * Create a new Depth-first Search.
     */
    public DFS() {
    }

    @Override
    public void solve(Terrain terrain) {

        this.terrain = terrain;

        // track locations we've been to using our terrain "memory"... Looks useful
        Cell defaultCell = new Cell();
        defaultCell.setColor(Color.WHITE);
        memory = new SparseArray<>(defaultCell);

        //Create stack of direction
        Stack directionStack = new Stack();
        directionStack.push(Direction.NONE);

        // setup random direction generator. ... dont need random direction
        /*Random random = new Random(); //This is use to look for either going to next thing or not
        Generator<Direction> generator = Direction.generator();*/

        // track the current search location, starting at the terrain start location.
        Location current = terrain.getStart();

        // start in up direction. We will adjust this accordingly.
        Direction[] directions = {Direction.UP,Direction.RIGHT,Direction.DOWN,Direction.LEFT};
        Direction previous = Direction.UP;
        Location next = current.get(previous);
        int directionCounter = 0;
        Direction direction;

        boolean noAvailableRoute = false;

        while(!current.equals(terrain.getGoal())) { //If current location is not goal

            // change direction if we can't go in the next location ex:if cant go up, try right
            if((!terrain.inTerrain(next) || terrain.isWall(next)) || memory.get(next).getColor() != Color.WHITE) {


                // find the next direction
                direction = directions[++directionCounter%4];

                // keep track of what we've seen in a set of directions
                Set<Direction> checked = new HashSet<>();
                //Add stack here?


                // check in all directions randomly
                int tmpDir = directionCounter%4;
                while (checked.size() < 4) {
                    // get a random direction
                    //Direction enteredDirection = directions[directionCounter];
                    Direction tmp = directions[tmpDir++%4];
                    System.out.println("check : "+ directions[tmpDir%4]);
                    checked.add(tmp);

                    // see if stepping in that direction is possible and do it!
                    next = current.get(tmp);
                    if (terrain.inTerrain(next) && !terrain.isWall(next) && memory.get(next).getColor() == Color.WHITE) {
                        previous = direction = tmp;
                        directionStack.push(tmp);
                        break;
                    }
                }

                // if no direction was found, we are stuck and leave without solution
                // see below on how foundSolution would normally be used.
                //
                if(checked.size() > 3) {
                    //foundSolution = false;
                    //return;
                    Direction initialGoBack = (Direction) directionStack.peek();
                    Direction goBack;
                    noAvailableRoute = true;
                    while (directionStack.peek() == initialGoBack && directionStack.size() != 1){
                        goBack = (Direction) directionStack.pop();
                        switch (goBack) {
                            case UP:
                                current = current.get(Direction.DOWN);
                                //memory.get(current).setTo(Direction.DOWN);
                                break;
                            case RIGHT:
                                current = current.get(Direction.LEFT);
                                //memory.get(current).setTo(Direction.LEFT);
                                break;
                            case DOWN:
                                current = current.get(Direction.UP);
                                //memory.get(current).setTo(Direction.UP);
                                break;
                            case LEFT:
                                current = current.get(Direction.RIGHT);
                                //memory.get(current).setTo(Direction.RIGHT);
                                break;
                        }
                    }
                }

            }
            else {
                direction = previous;
                directionStack.push(direction);
            }

            // record the step we've taken to memory to recreate the solution in the later traversal.
            memory.get(current).setTo(direction);

            // step
            if(!noAvailableRoute){
                current = current.get(direction);
            }
            noAvailableRoute = false;

            // record that we've been here
            memory.get(current).setColor(Color.BLACK);

            System.out.println(memory);

            next = current.get(direction);
        }

        // we reached the goal and have a solution.
        // see below on how foundSolution would normally be used.
        foundSolution = true;
    }

    @Override
    public void reset() {
        // start the traversal of our path at the terrain's start.
        solution = terrain.getStart();
    }

    @Override
    public Direction next() {
        // recall the direction at this location, move to the corresponding location and return it.
        Direction direction = memory.get(solution).getTo();
        solution = solution.get(direction);
        return direction;
    }

    @Override
    public boolean hasNext() {
        // we're only done when we get to the terrain goal.
        return !solution.equals(terrain.getGoal());
    }
}
