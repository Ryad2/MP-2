package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level {            // not getters are to appear in this class

    private ICRogueRoom[][] map;
    private DiscreteCoordinates arrivalCoordinates;
    private DiscreteCoordinates bossRoomCoordinates;
    private String startingRoomTitle;


    protected Level(DiscreteCoordinates startingPosition, int x, int y){

        arrivalCoordinates = startingPosition;                      // probably incorrect. Check the pdf
        map = new ICRogueRoom[x][y];

        bossRoomCoordinates = new DiscreteCoordinates(0, 0);
        //generateFixedMap();                                       // will define later in the pdf
    }

    protected void setRoom(DiscreteCoordinates coordinates, ICRogueRoom room){
        map[coordinates.x][coordinates.y] = room;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coordinates, String destination,
                                               ConnectorInRoom connector){

        ICRogueRoom room = map[coordinates.x][coordinates.y];

        room.setConnectorDestination(connector.getIndex(), destination, coordinates);
    }

    protected void setRoomConnector(DiscreteCoordinates coordinates, String destination, ConnectorInRoom connector){
        ICRogueRoom room = map[coordinates.x][coordinates.y];

        room.setConnector(connector.getIndex(), destination, coordinates);
    }

    protected void lockRoomConnector(DiscreteCoordinates coordinates, ConnectorInRoom connector, int keyID){
        ICRogueRoom room = map[coordinates.x][coordinates.y];

        room.setConnectorKey(connector.getIndex(), keyID);
    }

    protected void setStartingRoomTitle(DiscreteCoordinates coordinates){
        startingRoomTitle = map[coordinates.x][coordinates.y].getTitle();
    }
}
