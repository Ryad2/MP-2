package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;

public class WalkingTurret extends Turret implements Interactor { // for now, it extends a non-abstract class. Will fix it later

    private static final int MOVE_DURATION = 8;
    private final WalkingTurretInteractionHandler handler;

    private updateInterface updateMethod;

    public WalkingTurret(Area owner, Orientation orientation, DiscreteCoordinates coordinates,
                         Orientation[] targetOrientations) {
        super(owner, orientation, coordinates, targetOrientations);

        handler = new WalkingTurretInteractionHandler();

        updateMethod = this::normalUpdate;
    }

    public void redirect(Orientation orientation){
        launch(orientation);
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

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    // =====              =====
    // =====  Interactor  =====
    // =====              =====

    @Override
    public boolean takeCellSpace(){
        return false;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }
    @Override
    public boolean wantsViewInteraction() {
        return true;
    }
    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    private class WalkingTurretInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(Item item, boolean isCellInteraction) {
            if (item.isCellInteractable() && isCellInteraction){
                item.collect();
            }
        }

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            ICRogueBehavior.ICRogueCellType wall = ICRogueBehavior.ICRogueCellType.WALL;
            ICRogueBehavior.ICRogueCellType hole = ICRogueBehavior.ICRogueCellType.HOLE;

            if (!isCellInteraction && (cell.getType() == wall || cell.getType() == hole)){
                orientate(getOrientation().opposite());
            }
        }


        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            if (turret.isViewInteractable() && !isCellInteraction && !turret.equals(WalkingTurret.this)){
                orientate(getOrientation().opposite());
            }
        }
    }
}
