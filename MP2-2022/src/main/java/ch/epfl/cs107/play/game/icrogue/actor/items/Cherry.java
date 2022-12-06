package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Cherry extends Item{


    public Cherry(Area area, Orientation orientation, DiscreteCoordinates position){
        super(area, orientation, position);
        this.sprite= new Sprite("icrogue/cherry", 0.6f, 0.6f, this);

    }


    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }//should fill this shit cuz it is empty in the tuto

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


}
