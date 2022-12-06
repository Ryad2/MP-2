package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Item extends CollectableAreaEntity {

    protected Sprite sprite;


    Item(Area area, Orientation orientation, DiscreteCoordinates position){
        super(area, orientation, position);
    }
    public final boolean takeCellSpace(){
        return false;
    }



}
