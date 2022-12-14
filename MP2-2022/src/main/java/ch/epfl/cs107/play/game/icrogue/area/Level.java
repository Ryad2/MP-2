package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Level {            // not getters are to appear in this class

    private final ICRogueRoom[][] map;
    private final DiscreteCoordinates arrivalCoordinates;
    private final DiscreteCoordinates bossRoomCoordinates;
    private final DiscreteCoordinates startingRoomCoordinates;
    private String startingRoomTitle;


    protected Level(DiscreteCoordinates startingPosition, DiscreteCoordinates mapDimensions, ICRogue game,
                    DiscreteCoordinates startingRoomCoordinates){

        arrivalCoordinates = startingPosition;                      // probably incorrect. Check the pdf
        this.startingRoomCoordinates = startingRoomCoordinates;
        map = new ICRogueRoom[mapDimensions.x][mapDimensions.y];

        bossRoomCoordinates = new DiscreteCoordinates(0, 0);
        generateFixedMap();

        setStartingRoomTitle(startingRoomCoordinates);

        addAreas(game);
    }

    /**
     * adds the room to the room map
     * @param coordinates DiscreteCoordinates. The coordinates of the room in the level
     * @param room ICRogueRoom. The room that will be set to the given coordinates
     */
    protected void setRoom(DiscreteCoordinates coordinates, ICRogueRoom room){
        map[coordinates.x][coordinates.y] = room;
    }

    /**
     * add a destination to the connector in the argument
     * @param coordinates
     * @param destination
     * @param connector
     */
    protected void setRoomConnectorDestination(DiscreteCoordinates coordinates, String destination,
                                               ConnectorInRoom connector){

        ICRogueRoom room = map[coordinates.x][coordinates.y];

        room.setConnectorDestination(connector.getIndex(), destination, connector.getDestination());
    }

    /**
     * add a desitination to the connector in the argument, and lock the connector
     * @param coordinates
     * @param destination
     * @param connector
     */
    protected void setRoomConnector(DiscreteCoordinates coordinates, String destination, ConnectorInRoom connector){
        ICRogueRoom room = map[coordinates.x][coordinates.y];

        room.setConnector(connector.getIndex(), destination, connector.getDestination());
    }

    /**
     * locks the room connector, giving it a keyID
     * @param coordinates
     * @param connector
     * @param keyID
     */
    protected void lockRoomConnector(DiscreteCoordinates coordinates, ConnectorInRoom connector, int keyID){
        ICRogueRoom room = map[coordinates.x][coordinates.y];

        room.setConnectorKey(connector.getIndex(), keyID);
    }

    protected void setStartingRoomTitle(DiscreteCoordinates coordinates){
        startingRoomTitle = map[coordinates.x][coordinates.y].getTitle();
    }

    protected abstract void generateFixedMap();

    // ====         ====
    // ==== helpers ====
    // ====         ====

    public void addAreas(ICRogue game){         // potentially incorrect to use ICRogue. Use AreaGame
        for (ICRogueRoom[] line : map){
            for (ICRogueRoom room : line){
                if (room != null){
                    game.addArea(room);
                }
            }
        }

        game.setCurrentRoom(startingRoomTitle);
    }

    public void beatRoom(String bossRoom){
        if (map[bossRoomCoordinates.x][bossRoomCoordinates.y].getTitle().equals(bossRoom)){
            System.out.println("Win");
        }
    }
}
