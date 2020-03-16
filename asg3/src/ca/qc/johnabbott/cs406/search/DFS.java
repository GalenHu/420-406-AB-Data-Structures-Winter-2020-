/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.SparseArray;
import ca.qc.johnabbott.cs406.collections.Stack;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Generator;
import ca.qc.johnabbott.cs406.terrain.Location;
import ca.qc.johnabbott.cs406.terrain.Terrain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DFS implements Search {

    // records where we've been and what steps we've taken.
    private SparseArray<Cell> memory;

    // for tracking the "traversable" solution.
    private Location solution;
    private boolean foundSolution;

    // the terrain we're searching in.
    private Terrain terrain;

    /**
     * Create a new Random search.
     */
    public DFS() {
    }

    @Override
    public void solve(Terrain terrain) {

        this.terrain = terrain;

        // track locations we've been to using our terrain "memory"
        Cell defaultCell = new Cell();
        defaultCell.setColor(Color.WHITE);
        memory = new SparseArray<>(defaultCell);

        // track the current search location, starting at the terrain start location.
        Location current = terrain.getStart();

        // start in a random direction. We will adjust this accordingly.
        Direction[] directionDonut = Direction.getClockwise();
        int directionCounter = 0;
        Stack locationStack = new Stack(terrain.getHeight()*terrain.getWidth());
        locationStack.push(current);


        while(!current.equals(terrain.getGoal())) {

            //directionCounter = 0;
            // find the next direction
            Direction direction = directionDonut[0];
            Location next = current.get(direction);

            // change direction if we can't go in the next direction... ex: if cant go up, try right
            if((!terrain.inTerrain(next) || terrain.isWall(next)) || memory.get(next).getColor() != Color.WHITE) {
                directionCounter = 0;
                // keep track of what we've seen in a set of directions
                Set<Direction> checked = new HashSet<>();

                // check in all directions randomly
                while (true) {

                    if (checked.size() < 4) {
                        // get a random direction
                        Direction tmp = directionDonut[directionCounter++ % 4];
                        checked.add(tmp);

                        // see if stepping in that direction is possible and do it!
                        next = current.get(tmp);
                        if (terrain.inTerrain(next) && !terrain.isWall(next) && memory.get(next).getColor() == Color.WHITE) {
                            direction = tmp;
                            break;
                        }
                    }
                    if (checked.size() >= 4) {
                        if (locationStack.isEmpty()){
                            foundSolution= false;
                            return;
                        }
                        current = (Location) locationStack.pop();
                        checked.clear();
                    }
                }
            }

            // record the step we've taken to memory to recreate the solution in the later traversal.
            memory.get(current).setTo(direction);

            // step
            current = current.get(direction);
            if (!locationStack.isFull())
            locationStack.push(current);

            // record that we've been here
            memory.get(current).setColor(Color.BLACK);

            System.out.println(memory);
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
