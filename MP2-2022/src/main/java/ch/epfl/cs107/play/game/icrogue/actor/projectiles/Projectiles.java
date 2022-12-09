package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;

public class Projectiles extends ICRogueActor implements Consumable {

    //protected Sprite sprite;

    private int moveDuration;
    private int damage;
    private boolean isConsumed;

    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_MOVE_DURATION = 8;


    public Projectiles(Area owner, Orientation orientation, DiscreteCoordinates coordinates, int damage, int moveDuration){
        super(owner, orientation, coordinates);
        this.damage = damage;
        this.moveDuration = moveDuration;
    }

    public Projectiles(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        this(owner, orientation, coordinates, DEFAULT_DAMAGE, DEFAULT_MOVE_DURATION);
    }


    @Override
    public void update(float deltaTime) {
        boolean x = move(moveDuration);
        //System.out.println(x);
        super.update(deltaTime);
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void consume(){
        if (!isConsumed){
            isConsumed = true;
            leaveArea();
        }
    }

    public boolean isConsumed(){
        return isConsumed;
    }

    @Override
    public final boolean takeCellSpace(){
        return false;
    }


    // Interactor

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }


    // we are told to have interactor here:!!! >:(
}
