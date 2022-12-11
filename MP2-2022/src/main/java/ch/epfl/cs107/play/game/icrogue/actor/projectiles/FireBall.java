package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

import java.util.Collections;
import java.util.List;

// to corrector: boule de feu = FireBall and not Fire. hence the name change
public class FireBall extends Projectiles implements Interactor {


    // WARNING
    //
    //          fireBall does not go to the end of the doorway
    //
    // WARNING

    private static final int DAMAGE = 1;
    private static final int MOVE_DURATION = 5;
    private final FireBallInteractionHandler handler;

    public FireBall(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super(owner, orientation, coordinates, DAMAGE, MOVE_DURATION);

        setSprite(new Sprite ("zelda/fire", 1f, 1f, this,
                new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0)));

        enterArea(owner, coordinates);

        handler = new FireBallInteractionHandler();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    // Interactor

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }
    @Override
    public boolean wantsCellInteraction() {
        return true;
    }
    @Override
    public boolean wantsViewInteraction() {
        return true;
    }
    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }


    public class FireBallInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {

            ICRogueBehavior.ICRogueCellType wall = ICRogueBehavior.ICRogueCellType.WALL;
            ICRogueBehavior.ICRogueCellType hole = ICRogueBehavior.ICRogueCellType.HOLE;

            if (!isCellInteraction && (cell.getType() == wall || cell.getType() == hole)){
                consume();
            }
        }

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {

            if (isCellInteraction && turret.isCellInteractable() && wantsCellInteraction()){
                turret.die();
                consume();
            }
        }
    }
}
