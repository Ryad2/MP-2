package ch.epfl.cs107.play.game.icrogue.actor.enemies;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

import java.util.Collections;
import java.util.List;

public class Melee extends MovingEnemy implements Interactor{

    private static final int MOVE_DURATION = 8;
    private static final int DAMAGE = 2;
    private final MeleeInteractionHandler handler;

    private updateInterface updateMethod;

    public Melee(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);

        handler = new MeleeInteractionHandler();

        updateMethod = this::normalUpdate;
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("icrogue/zombie_npc", 1f, 1.5f, this,
                new RegionOfInterest(0, 0, 16, 32), new Vector(0f, 0f));
    }

    @Override
    public void die() {
        updateMethod = this::deathUpdate;
    }

    private void normalUpdate(float deltaTime){
        super.update(deltaTime);

        //move(MOVE_DURATION);
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

    private class MeleeInteractionHandler implements ICRogueInteractionHandler {

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            ICRogueBehavior.ICRogueCellType wall = ICRogueBehavior.ICRogueCellType.WALL;
            ICRogueBehavior.ICRogueCellType hole = ICRogueBehavior.ICRogueCellType.HOLE;

            if (!isCellInteraction && (cell.getType() == wall || cell.getType() == hole)){
                orientate(getOrientation().opposite());
            }
        }

        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction){
            if (isCellInteraction && player.isCellInteractable()){
                player.takeDamage(DAMAGE);
                die();
            }
        }
    }
}
