package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Level {            // not getters are to appear in this class

    private final ICRogueRoom[][] map;
    private final DiscreteCoordinates arrivalCoordinates;
    private final DiscreteCoordinates bossRoomCoordinates;
    private final DiscreteCoordinates startingRoomCoordinates;
    private String startingRoomTitle;

    /*protected Level (boolean randomMap, DiscreteCoordinates startPosition, int[] roomsDistribution, int width, int height){
        if (randomMap){
            generateRandomMap();
        }
        else{
            generateFixedMap();
        }
    }*/


    protected Level(DiscreteCoordinates startingPosition, DiscreteCoordinates mapDimensions, ICRogue game,
                    DiscreteCoordinates startingRoomCoordinates){

        arrivalCoordinates = startingPosition;                      // probably incorrect. Check the pdf
        this.startingRoomCoordinates = startingRoomCoordinates;
        map = new ICRogueRoom[mapDimensions.x][mapDimensions.y];

        bossRoomCoordinates = new DiscreteCoordinates(1, 1);
        generateFixedMap();

        setStartingRoomTitle(startingRoomCoordinates);

        addAreas(game);


        printMap(generateRandomRoomPlacement(5));
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


    // random room generation

    protected MapState[][] generateRandomRoomPlacement(int roomNumber) {
        MapState[][] mapState = new MapState[roomNumber][roomNumber];
        int roomsToPlace = roomNumber;
        List<Room> rooms = new ArrayList<>();

        // step 1
        for (int i = 0; i < roomNumber; i++){
            for (int j = 0; j < roomNumber; j++){
                mapState[i][j] = MapState.NULL;
            }
        }

        // step 2
        int centerRoomCoordinate = roomNumber / 2;
        mapState[centerRoomCoordinate][centerRoomCoordinate] = MapState.PLACED;
        rooms.add(new Room(MapState.PLACED, new DiscreteCoordinates(centerRoomCoordinate, centerRoomCoordinate)));
        roomsToPlace--;

        // step 3
        while (roomsToPlace > 0 && !rooms.isEmpty()){
            Room room = rooms.get(0);

            int freeSlots = 0;
            Map<Integer, DiscreteCoordinates> targetForRoom = new HashMap<>();
            List<DiscreteCoordinates> neighbours = room.coordinates.getNeighbours();

            // step 3.a)
            for (DiscreteCoordinates neighbour : neighbours){
                if (neighbour.x >= 0 && neighbour.x < roomNumber && neighbour.y >= 0 && neighbour.y < roomNumber &&
                        mapState[neighbour.x][neighbour.y].equals(MapState.NULL)){
                    freeSlots++;
                    targetForRoom.put(freeSlots, neighbour);
                }
            }

            // step 3.b)
            int maxCreatedRooms = RandomHelper.roomGenerator.nextInt(Math.min(freeSlots, 1), Math.min(freeSlots, roomsToPlace)+1);

            // step 3.c)
            List<Integer> keys = RandomHelper.chooseKInList(maxCreatedRooms, targetForRoom.keySet().stream().toList());
            for (Integer key : keys){
                DiscreteCoordinates roomCoordinates = targetForRoom.get(key);

                mapState[roomCoordinates.x][roomCoordinates.y] = MapState.PLACED;
                rooms.add(new Room(MapState.PLACED, new DiscreteCoordinates(roomCoordinates.x, roomCoordinates.y)));
                roomsToPlace--;
            }

            // step 3.d)
            rooms.remove(room);
            mapState[room.coordinates.x][room.coordinates.y] = MapState.EXPLORED;

            printMap(mapState);
            System.out.println("_____");
        }

        // step 4
        Map<Integer, DiscreteCoordinates> potentialBossRooms = new HashMap<>();
        Integer potentialBossRoomNumber = 0;

        for (int i = 0; i < roomNumber; i++){
            for (int j = 0; j < roomNumber; j++){
                if (mapState[i][j] == MapState.NULL && hasNeighbours(new DiscreteCoordinates(i, j), mapState, roomNumber)){
                    potentialBossRooms.put(potentialBossRoomNumber, new DiscreteCoordinates(i, j));
                    potentialBossRoomNumber++;
                }
            }
        }

        Integer bossRoomIndex = RandomHelper.chooseKInList(1, potentialBossRooms.keySet().stream().toList()).get(0);
        DiscreteCoordinates bossRoomCoordinates = potentialBossRooms.get(bossRoomIndex);
        mapState[bossRoomCoordinates.x][bossRoomCoordinates.y] = MapState.BOSS_ROOM;


        System.out.println(roomsToPlace);
        return mapState;
    }

    private boolean hasNeighbours(DiscreteCoordinates coordinates, MapState[][] mapState, int roomNumber){
        List<DiscreteCoordinates> neighbours = coordinates.getNeighbours();
        for (DiscreteCoordinates neighbour : neighbours){
            if (neighbour.x >= 0 && neighbour.x < roomNumber && neighbour.y >= 0 && neighbour.y < roomNumber &&
                    mapState[neighbour.x][neighbour.y] != MapState.NULL){
                return true;
            }
        }
        return false;
    }

    private static class Room{
        private MapState state;
        private final DiscreteCoordinates coordinates;

        Room(MapState state, DiscreteCoordinates coordinates){
            this.state = state;
            this.coordinates = coordinates;
        }

        private void changeState(MapState newState){
            state = newState;
        }
    }


    protected enum MapState {
        NULL,                   // Empty space
        PLACED,                 // The room has been placed but not yet explored by the room placement algorithm
        EXPLORED,               // The room has been placed and explored by the algorithm
        BOSS_ROOM,              // The room is a boss room
        CREATED;                // The room has been instantiated in the room map

        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }
    }

    private void printMap(MapState[][] map) {
        System.out.println("Generated map:");
        System.out.print(" | ");
        for (int j = 0; j < map[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        System.out.print(" --|-");
        for (int j = 0; j < map[0].length; j++) {
            System.out.print("--");
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
