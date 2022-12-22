package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Key extends Item{

    private int ID;


    public Key(Area area, Orientation orientation, DiscreteCoordinates position, int ID){
        super(area, orientation, position);
        this.ID=ID;
        this.sprite= new Sprite("icrogue/key", 0.6f, 0.6f, this);

    }



    public int getID(){
        return ID;
    }


    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    @Override
    public boolean isViewInteractable() {// BE CAREFUL TO DO A PROJECTILE
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas);
    }


}// should make a method to memorise the ID in player



