package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connector extends AreaEntity {

    enum Condition{
        OPEN,
        CLOSED,
        LOCKED,
        INVISIBLE
    }

    private Condition condition;
    private String destinationAreaName;
    private DiscreteCoordinates futureCoordinates;
    private int NO_KEY_ID;// have to make a setter for this attribute

    public Connector(ICRogueRoom ownerArea, DiscreteCoordinates currentMainCellCoordinates, Orientation orientation) {
        super(ownerArea, orientation, currentMainCellCoordinates);//maybe owner Area is just a area and not an ICRogue room

        this.condition=Condition.INVISIBLE;//we suppose that the defaulte condition is invisible

        this.destinationAreaName=ownerArea.getTitle(ownerArea.behaviorName,futureRoomCoordinatesFinder(ownerArea.coordinates,orientation));


        this.futureCoordinates=futureCoordinatesFinder(currentMainCellCoordinates,orientation);

        createSpriteMAP();

    }


    //THIS SHIT CALLCULATE THE COORDINATE OF THE FUTURE ROOM OF IN OUR LEVEL
    //can use methodes Left() Right() and jump .... of DiscreteCoordinates
    public DiscreteCoordinates futureRoomCoordinatesFinder(DiscreteCoordinates ownerAreaCoordinates, Orientation orientation){
        return new DiscreteCoordinates((int)(ownerAreaCoordinates.x+orientation.toVector().x), (int)(ownerAreaCoordinates.y+orientation.toVector().y));
    }




    //HAVE TO MAKE IT CLEAN IT IS TOO DISCUSTING
    public DiscreteCoordinates futureCoordinatesFinder(DiscreteCoordinates currentMainCellCoordinates,Orientation orientation){
return new DiscreteCoordinates((int)(currentMainCellCoordinates.x-orientation.toVector().x* this.getOwnerArea().getWidth()),   (int)(currentMainCellCoordinates.y-orientation.toVector().y* this.getOwnerArea().getHeight()));
    }




//sure that we will need it one day to put our player in the next room
    public DiscreteCoordinates getFutureCoordinates() {
        return futureCoordinates;
    }




    Map<Condition, Sprite> sprites = new HashMap<>();

    public void createSpriteMAP() {

        Sprite CLOSED = new Sprite("icrogue/door_"+ this.getOrientation().ordinal(),
                (this.getOrientation().ordinal()+1)%2+1, this.getOrientation().ordinal()%2+1, this);


        Sprite INVISIBLE = new Sprite("icrogue/invisibleDoor_"+this.getOrientation().ordinal(),
                (this.getOrientation().ordinal()+1)%2+1, this.getOrientation().ordinal()%2+1, this);


        Sprite LOCKED =new Sprite("icrogue/lockedDoor_"+this.getOrientation().ordinal(),
                (this.getOrientation().ordinal()+1)%2+1, this.getOrientation().ordinal()%2+1, this);

        sprites.put(Condition.CLOSED, CLOSED);
        sprites.put(Condition.INVISIBLE, INVISIBLE);
        sprites.put(Condition.LOCKED, LOCKED);

    }
//I'M NOT SURE FOR THAT BUT SEEMS NICE XD
    public boolean isCellInteractable() {
        if(condition==Condition.OPEN) return true;
        else return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord , coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));

    }


    public void draw(Canvas canvas) {
        sprites.get(condition).draw(canvas);
    }



    public boolean takeCellSpace() {
        if(condition==Condition.OPEN) return true;
        else return false;
    }

//IDK BUT I THINK THAT I HAVE TO LET IT EMPTY FOR NOW
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
    }




}
