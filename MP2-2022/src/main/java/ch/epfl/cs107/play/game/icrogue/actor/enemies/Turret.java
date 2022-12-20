package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0EnemyRoom;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

public class Turret extends Enemy {

    private boolean isDead;
    private final Orientation[] targetOrientations;       // might want to change the type later

    private final float COOLDOWN = 2.f;
    private float cooldown = 0;

    public boolean getLivingStatus(){       // change the getter name
        return isDead;
    }

    public Turret(Area owner, Orientation orientation, DiscreteCoordinates coordinates, Orientation[] targetOrientations){
        super(owner, orientation, coordinates);
        this.targetOrientations = targetOrientations.clone();

        sprite = getSprite();
    }

    protected Sprite getSprite(){
        return new Sprite("icrogue/static_npc", 1f, 1.5f, this,
                new RegionOfInterest(0, 0, 16, 32), new Vector(0, 0));
    }

    @Override
    public void update(float deltaTime) {

        cooldown += deltaTime;
        if (cooldown > COOLDOWN){
            cooldown = 0;
            launch();
        }

        super.update(deltaTime);
    }


    protected void launch(){
        for (Orientation orientation : targetOrientations){
            Arrow.createArrow(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        }
    }

    protected void launch(Orientation removedOrientation){
        for (Orientation orientation : targetOrientations){
            if (!orientation.equals(removedOrientation)){
                Arrow.createArrow(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
            }
        }
    }


    // Interactable


    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }
}
