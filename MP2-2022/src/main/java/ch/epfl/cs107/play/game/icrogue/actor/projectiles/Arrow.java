package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.WalkingTurret;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

import java.util.Collections;
import java.util.List;

public class Arrow extends Projectiles implements Interactor {

    private static final int DAMAGE = 1;
    private static final int MOVE_DURATION = 5;
    private final ArrowInteractionHandler handler;

    private final boolean isCopy;

    private Arrow(Area owner, Orientation orientation, DiscreteCoordinates coordinates, boolean isCopy) {
        super(owner, orientation, coordinates, DAMAGE, MOVE_DURATION);

        setSprite(new Sprite("zelda/arrow", 1f, 1f, this,
                new RegionOfInterest(32* orientation.ordinal() , 0, 32, 32) , new Vector(0, 0)));

        enterArea(owner, coordinates);

        handler = new ArrowInteractionHandler();
        this.isCopy = isCopy;
    }

    public static void createArrow(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        createArrow(owner, orientation, coordinates, false);
    }

    public static void createArrow(Area owner, Orientation orientation, DiscreteCoordinates coordinates, boolean isCopy){
        new Arrow(owner, orientation, coordinates, isCopy);
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


    public class ArrowInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {

            if (isCellInteraction && player.isCellInteractable()){
                player.takeDamage(DAMAGE);
                consume();
            }
        }

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {

            ICRogueBehavior.ICRogueCellType wall = ICRogueBehavior.ICRogueCellType.WALL;
            ICRogueBehavior.ICRogueCellType hole = ICRogueBehavior.ICRogueCellType.HOLE;

            if (!isCellInteraction && (cell.getType() == wall || cell.getType() == hole)){
                consume();
            }
        }

        @Override
        public void interactWith(WalkingTurret turret, boolean isCellInteraction) {
            if (turret.isCellInteractable() && isCellInteraction){
                if (!isCopy){
                    turret.redirect(getOrientation());
                    consume();
                }
            }
        }
    }
}
