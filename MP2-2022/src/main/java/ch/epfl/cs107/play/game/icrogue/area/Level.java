package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Level {            // no getters are to appear in this class

    private final ICRogueRoom[][] map;
    private DiscreteCoordinates bossRoomCoordinates;

    private DiscreteCoordinates startRoomCoordinates;
    private String startingRoomTitle;

    private final static DiscreteCoordinates DEFAULT_BOSS_ROOM_POSITION = new DiscreteCoordinates(0, 0);

    public Level (boolean randomMap, DiscreteCoordinates startPosition, int[] roomsDistribution, int width, int height){

        if (randomMap){
            int roomNumber = IntStream.of(roomsDistribution).sum();
            map = new ICRogueRoom[roomNumber][roomNumber];
            generateRandomMap(roomNumber, roomsDistribution);
        }
        else{
            map = new ICRogueRoom[width][height];
            generateFixedMap();

            bossRoomCoordinates = DEFAULT_BOSS_ROOM_POSITION;
            setStartingRoomTitle(startPosition);
            startRoomCoordinates = startPosition;
        }
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
     * add a desitination to the connector in the argument, and closes the connector
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

    protected void generateFixedMap(){                              // IMPLEMENT PLS

    }

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

    protected void generateRandomMap(int roomNumber, int[] roomsDistribution){
        MapState[][] roomsPlacement = generateRandomRoomPlacement(roomNumber);
        
        Map<Integer, DiscreteCoordinates> placeableRooms = new HashMap<>();
        Map<Integer, DiscreteCoordinates> placedRooms = new HashMap<>();
        DiscreteCoordinates createdBossRoomCoordinates = DEFAULT_BOSS_ROOM_POSITION;        // it will always be initialized. This is to shut up intellij
        int room = roomNumber;

        // gets the coordinates where rooms can be created
        for (int i = 0; i < roomsPlacement.length; i++){
            for (int j = 0; j < roomsPlacement.length; j++){
                if ((roomsPlacement[i][j].equals(MapState.PLACED) || roomsPlacement[i][j].equals(MapState.EXPLORED))
                        && !roomsPlacement[i][j].equals(MapState.BOSS_ROOM)) {

                    placeableRooms.put(room, new DiscreteCoordinates(i, j));
                    room++;
                }
                // sets the boss room
                else if (roomsPlacement[i][j].equals(MapState.BOSS_ROOM)){
                    createdBossRoomCoordinates = new DiscreteCoordinates(i, j);
                }
            }
        }

        // creates the rooms
        for (int i = 0; i < roomsDistribution.length; i++){
            int roomTypeNumber = roomsDistribution[i];
            List<Integer> keys = RandomHelper.chooseKInList(roomTypeNumber, placeableRooms.keySet().stream().toList());

            for (Integer key : keys){
                DiscreteCoordinates roomCoordinates = placeableRooms.get(key);

                Level0Room newRoom = createRoom(i, placeableRooms.get(key));
                setRoom(roomCoordinates, newRoom);

                // rather bad code, but we are out of time
                if (Level0.RoomType.values()[i].equals(Level0.RoomType.SPAWN)){
                    startRoomCoordinates = roomCoordinates;
                    setStartingRoomTitle(startRoomCoordinates);
                }

                placedRooms.put(key, placeableRooms.get(key));               // bad
                placeableRooms.remove(key);
                roomsPlacement[roomCoordinates.x][roomCoordinates.y] = MapState.CREATED;
            }
        }

        // create boss room
        setRoom(createdBossRoomCoordinates, new Level0BossRoom(createdBossRoomCoordinates));
        bossRoomCoordinates = createdBossRoomCoordinates;


        for (DiscreteCoordinates placedRoom : placedRooms.values()){
            setUpConnector(roomsPlacement, map[placedRoom.x][placedRoom.y]);
        }
    }

    private Level0Room createRoom(int number, DiscreteCoordinates roomCoordinates){
        Level0.RoomType type = Level0.RoomType.values()[number];

        Level0Room createdRoom;

        switch(type){
            case TURRET_ROOM -> createdRoom = new Level0TurretRoom(roomCoordinates);
            case STAFF_ROOM -> createdRoom =new Level0StaffRoom(roomCoordinates);
            case BOSS_KEY -> createdRoom =new Level0KeyRoom(roomCoordinates, 2);
            default -> createdRoom = new Level0Room(roomCoordinates);
            // takes into account NORMAL, SPAWN, And DEFAULT. default is to satisfy compiler
        }

        return createdRoom;
    }

    protected void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room){

        DiscreteCoordinates roomCoordinates = room.getCoordinates();
        List<DiscreteCoordinates> neighbours = roomCoordinates.getNeighbours();

        List<ConnectorInRoom> connectors = new ArrayList<>();

        for (DiscreteCoordinates neighbour : neighbours){

            if (roomsPlacement[neighbour.x][neighbour.y] != MapState.NULL){
                Level0Room.Level0Connectors connector = chooseConnector(roomCoordinates, neighbour);

                DiscreteCoordinates destination = roomCoordinates.jump(connector.getOrientation().toVector());
                setRoomConnector(roomCoordinates, map[neighbour.x][neighbour.y].getTitle(), connector);

                connectors.add(connector);

                if (roomsPlacement[neighbour.x][neighbour.y] == MapState.BOSS_ROOM){
                    lockRoomConnector(roomCoordinates, connector, 2);                           // increment key id
                }
            }
        }

        if (roomCoordinates.equals(bossRoomCoordinates)){
            for (ConnectorInRoom connector : connectors){
                lockRoomConnector(roomCoordinates, connector, 2);       // replace the keyID
            }
        }
    }

    private Level0Room.Level0Connectors chooseConnector(DiscreteCoordinates roomCoordinates, DiscreteCoordinates arrivalCoordinates){
        Vector direction = arrivalCoordinates.toVector().sub(roomCoordinates.toVector());

        if (direction.equals(new Vector(1, 0))) {
            return Level0Room.Level0Connectors.E;
        }
        if (direction.equals(new Vector(0, 1))){
            return Level0Room.Level0Connectors.N;
        }
        if (direction.equals(new Vector(0, -1))){
            return Level0Room.Level0Connectors.S;
        }
        if (direction.equals(new Vector(-1, 0))){
            return Level0Room.Level0Connectors.W;
        }
        else{
            throw new IllegalArgumentException("the rooms are not neighbours");
        }
    }


    // random room generation

    protected MapState[][] generateRandomRoomPlacement(int roomNumber) {
        MapState[][] roomsPlacement = new MapState[roomNumber][roomNumber];
        int roomsToPlace = roomNumber;
        List<Room> rooms = new ArrayList<>();

        // step 1
        for (MapState[] mapRow : roomsPlacement){
            Arrays.fill(mapRow, MapState.NULL);
        }

        // step 2
        int centerRoomCoordinate = roomNumber / 2;
        roomsPlacement[centerRoomCoordinate][centerRoomCoordinate] = MapState.PLACED;
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
                        roomsPlacement[neighbour.x][neighbour.y].equals(MapState.NULL)){
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

                roomsPlacement[roomCoordinates.x][roomCoordinates.y] = MapState.PLACED;
                rooms.add(new Room(MapState.PLACED, new DiscreteCoordinates(roomCoordinates.x, roomCoordinates.y)));
                roomsToPlace--;
            }

            // step 3.d)
            rooms.remove(room);
            roomsPlacement[room.coordinates.x][room.coordinates.y] = MapState.EXPLORED;

            printMap(roomsPlacement);
            System.out.println("_____");
        }

        // step 4
        Map<Integer, DiscreteCoordinates> potentialBossRooms = new HashMap<>();
        Integer potentialBossRoomNumber = 0;

        for (int i = 0; i < roomNumber; i++){
            for (int j = 0; j < roomNumber; j++){
                if (roomsPlacement[i][j] == MapState.NULL && hasNeighbours(new DiscreteCoordinates(i, j), roomsPlacement, roomNumber)){
                    potentialBossRooms.put(potentialBossRoomNumber, new DiscreteCoordinates(i, j));
                    potentialBossRoomNumber++;
                }
            }
        }

        Integer bossRoomIndex = RandomHelper.chooseKInList(1, potentialBossRooms.keySet().stream().toList()).get(0);
        DiscreteCoordinates bossRoomCoordinates = potentialBossRooms.get(bossRoomIndex);
        roomsPlacement[bossRoomCoordinates.x][bossRoomCoordinates.y] = MapState.BOSS_ROOM;


        System.out.println(roomsToPlace);
        return roomsPlacement;
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
