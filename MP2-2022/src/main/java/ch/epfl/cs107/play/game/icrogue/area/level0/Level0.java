package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0 extends Level {

    private final static DiscreteCoordinates DEFAULT_STARTING_ROOM_POSITION = new DiscreteCoordinates(0, 0);
    private final static DiscreteCoordinates startingRoomPosition = new DiscreteCoordinates(2, 2);
    private final static DiscreteCoordinates DEFAULT_START_POSITION = new DiscreteCoordinates(2, 2);
    private final static DiscreteCoordinates DEFAULT_LEVEL_SIZE = new DiscreteCoordinates(5, 3);

    public Level0(ICRogue game){
        super(DEFAULT_START_POSITION, DEFAULT_LEVEL_SIZE, game, startingRoomPosition);
    }

    // this is given code
    private void generateMap1() {

        int keyID = 1;

        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, 1));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        lockRoomConnector(room00, Level0Room.Level0Connectors.E, keyID);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
    }


    // this is given code
    private void generateMap2() {

        int BOSS_KEY_ID = 2;

        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0Room(room00));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

        lockRoomConnector(room10, Level0Room.Level0Connectors.W, BOSS_KEY_ID);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2, 0);
        setRoom(room20, new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3, 0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom(room11, new Level0Room(room11));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);
    }

    private void generateFinalMap() {

        int BOSS_KEY_ID = 2;

        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0BossRoom(room00));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0TurretRoom(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

        lockRoomConnector(room10, Level0Room.Level0Connectors.W, BOSS_KEY_ID);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2, 0);
        setRoom(room20, new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3, 0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom(room11, new Level0TurretRoom(room11));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);
    }

    private void generateTestGame(){
        int BOSS_KEY_ID = 2;

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);

        DiscreteCoordinates room01 = new DiscreteCoordinates(0, 1);
        setRoom(room01, new Level0Room(room01));
        setRoomConnector(room01, "icrogue/level011", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom(room11, new Level0BossRoom(room11));
        setRoomConnector(room11, "icrogue/level012", Level0Room.Level0Connectors.E);
        setRoomConnector(room11, "icrogue/level001", Level0Room.Level0Connectors.W);
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);

        DiscreteCoordinates room21 = new DiscreteCoordinates(2, 1);
        setRoom(room21, new Level0TurretRoom(room21));
        setRoomConnector(room21, "icrogue/level031", Level0Room.Level0Connectors.E);

        lockRoomConnector(room21, Level0Room.Level0Connectors.W, BOSS_KEY_ID);
        setRoomConnectorDestination(room21, "icrogue/level011", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room31 = new DiscreteCoordinates(3, 1);
        setRoom(room31, new Level0StaffRoom(room31));
        setRoomConnector(room31, "icrogue/level041", Level0Room.Level0Connectors.E);
        setRoomConnector(room31, "icrogue/level021", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room41 = new DiscreteCoordinates(4, 1);
        setRoom(room41, new Level0KeyRoom(room41, BOSS_KEY_ID));
        setRoomConnector(room41, "icrogue/level031", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room22 = new DiscreteCoordinates(2, 2);
        setRoom(room22, new Level0TurretRoom(room22));
        setRoomConnector(room22, "icrogue/level021", Level0Room.Level0Connectors.N);
    }

    @Override
    protected void generateFixedMap() {
        //generateMap1();

        //generateMap2();

        //generateFinalMap();

        generateTestGame();
    }

    // comment to test
}
