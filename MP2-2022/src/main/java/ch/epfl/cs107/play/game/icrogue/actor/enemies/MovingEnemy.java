package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;

public class MovingEnemy extends Enemy {
    private final static int MOVE_DURATION = 8;
    protected updateInterface updateMethod;


    public MovingEnemy(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);

        updateMethod = this::normalUpdate;
    }

    @Override
    public void die() {
        updateMethod = this::deathUpdate;
    }

    private void normalUpdate(float deltaTime){
        super.update(deltaTime);

        move(MOVE_DURATION);
    }

    private void deathUpdate(float deltaTime){
        if (!isDisplacementOccurs()){
            super.die();
        }

        super.update(deltaTime);
    }

    @Override
    public void update(float deltaTime) {
        updateMethod.update(deltaTime);
    }

    // this is a delegate. We were not taught to do this
    @FunctionalInterface
    private interface updateInterface{
        void update(float deltaTime);
    }
}
