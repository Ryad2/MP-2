package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.io.Serializable;
import java.util.*;

public class level0Room extends ICRogueRoom {

    // =====    WARNING    ==== end of page 6 says something about background in this file

    //WE CAN ALSO USE THE getTitle() OF ICROGUE ROOM
    public String getTitle() { return "icrogue/level0" + getCoordinates().x + getCoordinates().y;}

    public level0Room(DiscreteCoordinates roomCoordinates){
        super(Level0Connectors.getAllConnectorsPosition(), Level0Connectors.getAllConnectorsOrientation(),
                "icrogue/level0Room", roomCoordinates);
    }


    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5,15);
    }

    protected void

    createArea() {

        super.createArea();

        registerActor(new Background(this, getBehaviorName()));
        registerActor(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(6,3) ));
        registerActor(new Staff(this, Orientation.DOWN, new DiscreteCoordinates(4,3) ));
        registerActor(new Key(this, Orientation.DOWN, new DiscreteCoordinates(5, 4), 0));



        /*for (var x : Level0Connectors.values()){
            registerActor(new Connector(this, Level0Connectors.getAllConnectorsPosition().get(x.getIndex()),
                    Level0Connectors.getAllConnectorsOrientation().get(x.getIndex()).opposite()));
        }*/


        // what follows is for enemy testing only. Comment when working on step 2

        /*Orientation[] turretOrientations =
                new Orientation[] {Orientation.DOWN, Orientation.UP, Orientation.LEFT, Orientation.RIGHT};

        registerActor(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(5, 5), turretOrientations));*/
    }


    public enum Level0Connectors implements ConnectorInRoom {
        W(new DiscreteCoordinates(0,4), new DiscreteCoordinates(8, 5), Orientation.LEFT),
        S(new DiscreteCoordinates(4, 0), new DiscreteCoordinates(5, 8), Orientation.DOWN),
        E(new DiscreteCoordinates(9, 4), new DiscreteCoordinates(1, 5), Orientation.RIGHT),
        N(new DiscreteCoordinates(4, 9), new DiscreteCoordinates(5, 1), Orientation.UP);
        private DiscreteCoordinates position;
        private DiscreteCoordinates destination;
        private Orientation orientation;


        Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation) {
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
        }

        @Override
        public int getIndex() {
            return orientation.ordinal();
        }

        @Override
        public DiscreteCoordinates getDestination() {
            return this.destination;
        }



        public static List<Orientation> getAllConnectorsOrientation(){//I M SURE THAT THERE IS A DIRECT WAY TO PUT ENUM IN A LIST

            List<Orientation> connectorsOrientations = new ArrayList<>();
            for (Level0Connectors ori : Level0Connectors.values()) connectorsOrientations.add(ori.orientation);

            return connectorsOrientations;
        }//should be sure about the order of the elements


        public static List <DiscreteCoordinates > getAllConnectorsPosition(){//I M SURE THAT THERE IS A DIRECT WAY TO PUT ENUM IN A LIST

            List<DiscreteCoordinates> connectorsPosition = new ArrayList<>();
            for (Level0Connectors pos : Level0Connectors.values()) connectorsPosition.add(pos.position);

            return connectorsPosition;
        }//should be sure about the order of the elements
    }
}