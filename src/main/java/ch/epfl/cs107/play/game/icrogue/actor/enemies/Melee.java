package ch.epfl.cs107.play.game.icrogue.actor.enemies;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

import java.util.Collections;
import java.util.List;

public class Melee extends MovingEnemy implements Interactor{

    private static final int MOVE_DURATION = 10;
    private int wait;
    private static final int DAMAGE = 2;
    private final MeleeInteractionHandler handler;

    private updateInterface updateMethod;


    private boolean wantsCellInteraction = true;

    public Melee(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {

        super(owner, orientation, coordinates);

        handler = new MeleeInteractionHandler();

        updateMethod = this::initialOrientate;
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("icrogue/zombie_npc", 1f, 1.5f, this,
                new RegionOfInterest(0, 0, 16, 32), new Vector(0f, 0f));
    }

    @Override
    public void die() {
        updateMethod = this::deathUpdate;
        setDrawMethod(super::deathDraw);
        wantsCellInteraction = false;
    }

    // update

    @Override
    public void update(float deltaTime) {
        updateMethod.update(deltaTime);
    }

    @Override
    protected void normalUpdate(float deltaTime){
        super.update(deltaTime);

        move(MOVE_DURATION);
        updateMethod = this::moveUpdate;
    }

    private void initialOrientate(float deltaTime) {
        orientate(calculateOrientation(getPosition(), getOrientation()));
        updateMethod = this::normalUpdate;

        super.update(deltaTime);
    }

    @Override
    protected void moveUpdate(float deltaTime){
        if (isTargetReached() && !isDisplacementOccurs()){
            orientate(calculateOrientation(getPosition(), getOrientation()));
            updateMethod = this::normalUpdate;
        }
        super.update(deltaTime);
    }

    // =====                =====
    // =====  Interactable  =====
    // =====                =====


    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    @Override
    public boolean takeCellSpace(){
        return false;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    // =====              =====
    // =====  Interactor  =====
    // =====              =====

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return wantsCellInteraction;
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



    public static  Orientation calculateOrientation(Vector enemyPosition, Orientation enemyOrientation){
        Vector playerPosition = ICRogue.getPlayerPosition();
        Vector difference = playerPosition.sub(enemyPosition);

        Orientation orientationToPlayer;

        if (Math.abs(difference.x) > Math.abs(difference.y)){
            orientationToPlayer = Orientation.fromVector(new Vector(difference.x, 0));
        }
        else{
            orientationToPlayer = Orientation.fromVector(new Vector(0, difference.y));
        }

        if (orientationToPlayer != null && !orientationToPlayer.equals(enemyOrientation)){
            return orientationToPlayer;
        }
        return enemyOrientation;
    }
}
