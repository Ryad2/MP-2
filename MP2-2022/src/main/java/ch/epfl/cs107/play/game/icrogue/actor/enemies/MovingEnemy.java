package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class MovingEnemy extends Enemy {
    private final static int MOVE_DURATION = 8;
    private updateInterface updateMethod;


    public MovingEnemy(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);

        updateMethod = this::normalUpdate;
    }

    // update

    protected void normalUpdate(float deltaTime){
        super.update(deltaTime);

        move(MOVE_DURATION);
        updateMethod = this::moveUpdate;
    }

    protected void deathUpdate(float deltaTime){
        if (!isDisplacementOccurs() && deathAnimation.isCompleted()){
            super.die();
        }

        deathAnimation.update(deltaTime);
        super.update(deltaTime);
    }

    protected void moveUpdate(float deltaTime){
        if (isTargetReached() && !isDisplacementOccurs()){
            updateMethod = this::normalUpdate;
        }
        super.update(deltaTime);
    }
}
