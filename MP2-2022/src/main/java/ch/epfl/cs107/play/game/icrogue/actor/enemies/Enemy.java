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

public class Enemy extends ICRogueActor {

    private boolean isDead;

    public boolean getLivingStatus(){       // change the getter name
        return isDead;
    }

    public void die(){
        if (!isDead){
            isDead = true;
            Level0EnemyRoom room = (Level0EnemyRoom)getOwnerArea();
            room.removeEnemy(this);
        }
    }

    public Enemy(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super(owner, orientation, coordinates);

        sprite = getSprite();
    }


    protected Sprite getSprite(){
        return new Sprite("icrogue/static_npc", 1f, 1.5f, this,
                new RegionOfInterest(0, 0, 16, 32), new Vector(0, 0));
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
    }


    // Interactable


    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }
}
