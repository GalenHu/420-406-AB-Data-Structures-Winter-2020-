/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.Queue;
import ca.qc.johnabbott.cs406.collections.SparseArray;
import ca.qc.johnabbott.cs406.collections.Stack;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Generator;
import ca.qc.johnabbott.cs406.terrain.Location;
import ca.qc.johnabbott.cs406.terrain.Terrain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BFS2 implements Search {

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
    public BFS2() {
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

        // make an array of direction
        Direction[] directionDonut = Direction.getClockwise();
        int directionCounter;
        Queue locationQueue = new Queue<>(terrain.getHeight()*terrain.getWidth());
        Queue directionQueue = new Queue(terrain.getHeight()*terrain.getWidth());
        memory.get(current).setColor(Color.BLACK);
        //locationQueue.enqueue(current);


        while(!current.equals(terrain.getGoal())) {

            directionCounter = 0;
            // find the next available direction
            Direction direction = directionDonut[directionCounter++%4];
            /*
            Location next = current.get(direction);

            // change direction if we can't go in the next direction... ex: if cant go up, try right
            if((!terrain.inTerrain(next) || terrain.isWall(next)) || memory.get(next).getColor() != Color.WHITE) {
                directionCounter = 0;
                // keep track of what we've seen in a set of directions


             */
            Set<Direction> checked = new HashSet<>();
            directionCounter = 0;
            while (checked.size() < 4) {
                // check the next direction
                Direction tmp = directionDonut[directionCounter++ % 4];
                checked.add(tmp);

                // see if stepping in that direction is possible if possible, enqueue it to the queue
                Location next = current.get(tmp);
                if (terrain.inTerrain(next) && !terrain.isWall(next) && memory.get(next).getColor() == Color.WHITE) {
                    locationQueue.enqueue(current.get(tmp));
                    memory.get(current).setColor(Color.GREY);
                    memory.get(current).setTo(tmp);
                    directionQueue.enqueue(tmp);
                    direction = tmp;
                    //break;
                }

                if (checked.size() >= 4) {
                    if (locationQueue.isEmpty()){
                        foundSolution= false;
                        return;
                    }
                    //current = (Location) locationQueue.dequeue();
                }


            }
            checked.clear();
            // record the step we've taken to memory to recreate the solution in the later traversal.
            //memory.get(current).setTo((Direction) directionQueue.dequeue());
            memory.get(current).setTo(direction);

            // step
            current = (Location) locationQueue.dequeue();

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
