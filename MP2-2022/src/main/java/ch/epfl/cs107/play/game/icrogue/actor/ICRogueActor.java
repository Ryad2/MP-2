package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.Collections;
import java.util.List;


public class ICRogueActor extends MovableAreaEntity {

    public ICRogueActor(Area owner, Orientation orientation, DiscreteCoordinates coordinates ){
        super(owner, orientation, coordinates);
    }


    protected Sprite sprite;
    /// Animation duration in frame number

    protected final static int MOVE_DURATION = 8;






    /*public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }*/




    /**
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        //area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }


    ///Ghost implements Interactable

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
    }
}




