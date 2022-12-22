package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.*;

public class Level0Room extends ICRogueRoom {

    private List<AreaEntity> entities;

    protected final DiscreteCoordinates ITEM_COORDINATES = new DiscreteCoordinates(5, 5);

    //WE CAN ALSO USE THE getTitle() OF ICROGUE ROOM
    public String getTitle() { return "icrogue/level0" + getCoordinates().x + getCoordinates().y;}

    public Level0Room(DiscreteCoordinates roomCoordinates){
        super(Level0Connectors.getAllConnectorsPosition(), Level0Connectors.getAllConnectorsOrientation(),
                "icrogue/level0Room", roomCoordinates);

        entities = new ArrayList<>();
    }

    public void addEntity(AreaEntity entity){
        entities.add(entity);
    }

    public void createEntity(AreaEntity entity){
        entities.add(entity);
        registerActor(entity);
    }

    public void removeEntity(AreaEntity entity){
        entities.remove(entity);
        unregisterActor(entity);
        if (entities.isEmpty()){
            beatRoom();
        }
    }

    protected void beatRoom(){
        for (Connector connector : connectors){
            connector.open();
        }
        System.out.println("Room is beaten");
        ICRogue.beatBossRoom(getTitle());
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5,15);
    }

    protected void createArea() {
        super.createArea();
        registerActor(new Background(this, getBehaviorName()));

        /*if (entities.isEmpty()){
            beatRoom();
        }*/

        for (AreaEntity entity : entities){
            registerActor(entity);
        }
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
            return ordinal();
        }

        @Override
        public DiscreteCoordinates getDestination() {
            return this.destination;
        }

        public Orientation getOrientation(){
            return orientation;
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