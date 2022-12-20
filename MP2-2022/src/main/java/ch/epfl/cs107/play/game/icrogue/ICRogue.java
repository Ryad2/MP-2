package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICRogue extends AreaGame {
    private ICRogueRoom currentRoom;
    private static Level level0;            // is unknown if this should be static, but have found no other way
    private static ICRoguePlayer player;
    private static Vector playerPosition;


    public void setCurrentRoom(String roomKey){
        currentRoom = (ICRogueRoom)setCurrentArea(roomKey, true);
    }


    private void initLevel(String areaKey){

        level0 = new Level0(this);

        player = new ICRoguePlayer(currentRoom, Orientation.UP, new DiscreteCoordinates(2, 2));
        player.enterArea(currentRoom, new DiscreteCoordinates(2, 2));
    }


    public final static float CAMERA_SCALE_FACTOR = 11.f;


    private final String[] areas = {"icrogue/level000"};

    private int areaIndex;
    /**
     * Add all the areas
     */
    public void createAreas(){
        //addArea(new level0Room(new DiscreteCoordinates(0, 0)));
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {        // this code is outdated

        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initLevel(areas[areaIndex]);
            return true;
        }
        return false;

    }

    @Override
    public void update(float deltaTime) {
        if(currentRoom.getKeyboard().get(Keyboard.R).isDown()){
            restartArea();
        }

        if (player.getIsCrossing()){
            switchRoom();
        }

        playerPosition = player.getPlayerPosition();

         super.update(deltaTime);
    }

    protected void switchRoom() {

        player.leaveArea();

        currentRoom = (ICRogueRoom)setCurrentArea(player.getCrossingConnector().getDestinationAreaName(),
                false);

        player.enterArea(currentRoom, player.getCrossingConnector().getTargetCoordinates());
        player.moveToNewRoom();

        player.resetCrossing();
    }


    private void restartArea(){

        setCurrentArea(areas[areaIndex], true);
        player.enterArea(currentRoom, new DiscreteCoordinates(2, 2));

        player.resetPlayer();
    }

    @Override
    public void end() {
    }

    @Override
    public String getTitle(){ return ("ICRogue"); }


    public static void beatBossRoom(String bossRoom){
        level0.beatRoom(bossRoom);
    }

    public static Vector getPlayerPosition(){
        return playerPosition;
    }
}
