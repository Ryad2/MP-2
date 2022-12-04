package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Projectiles extends ICRogueActor implements Consumable {

    //protected Sprite sprite;

    protected int moveDuration;
    protected int damage;
    protected boolean isConsumed;

    protected static final int DEFAULT_DAMAGE = 1;
    protected static final int DEFAULT_MOVE_DURATION = 8;


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
        move(MOVE_DURATION);
        super.update(deltaTime);
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void consume(){
        isConsumed = true;
    }

    public boolean isConsumed(){
        return isConsumed;
    }

    @Override
    public final boolean takeCellSpace(){
        return false;
    }
}
