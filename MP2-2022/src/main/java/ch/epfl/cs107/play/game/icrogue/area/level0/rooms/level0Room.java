package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class level0Room extends ICRogueRoom {

    // =====    WARNING    ==== end of page 6 says something about background in this file

    //WE CAN ALSO USE THE getTitle() OF ICROGUE ROOM
    public String getTitle() { return "icrogue/level0" + this.coordinates.x + this.coordinates.y;}

    public level0Room (DiscreteCoordinates roomCoordinates){
        super("icrogue/level0Room", roomCoordinates);       // make it the same as the behaviorName + coordinates
    }




    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5,15);
    }

    protected void

    createArea() {
        registerActor(new Background(this, this.behaviorName));
        registerActor(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(6,3) ));
        registerActor(new Staff(this, Orientation.DOWN, new DiscreteCoordinates(4,3) ));

    }


    public enum Level0Connectors implements ConnectorInRoom {
        // ordre des attributs: position , destination , orientation
        W(new DiscreteCoordinates(0, 4),
                new DiscreteCoordinates(8, 5), Orientation.LEFT),
        S(new DiscreteCoordinates(4, 0),
                new DiscreteCoordinates(5, 8), Orientation.DOWN),
        E(new DiscreteCoordinates(9, 4),
                new DiscreteCoordinates(1, 5), Orientation.RIGHT),
        N(new DiscreteCoordinates(4, 9),
                new DiscreteCoordinates(5, 1), Orientation.UP);

    }

 int getIndex(){
        int x=0;
       return x;
 }









}