package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

// copies Tuto2
public class ICRogue extends AreaGame {
    private ICRogueRoom currentRoom;
    private Level level0;


    public void setCurrentRoom(String roomKey){
        currentRoom = (ICRogueRoom)setCurrentArea(roomKey, true);
    }


    private void initLevel(String areaKey){

        level0 = new Level0(this);

        player = new ICRoguePlayer(currentRoom, Orientation.UP, new DiscreteCoordinates(2, 2));
        player.enterArea(currentRoom, new DiscreteCoordinates(2, 2));



        /*currentRoom = new Level0Room(new DiscreteCoordinates(0, 0));
        addArea(currentRoom);
        setCurrentArea(areaKey, true);
        player = new ICRoguePlayer(currentRoom, Orientation.UP, new DiscreteCoordinates(2,2));
        player.enterArea(currentRoom, new DiscreteCoordinates(2, 2));*/
    }





    public final static float CAMERA_SCALE_FACTOR = 11.f;

    private ICRoguePlayer player;
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


         super.update(deltaTime);
    }

    protected void switchRoom() {

        player.leaveArea();

        currentRoom = (ICRogueRoom)setCurrentArea(player.getCrossingConnector().getDestinationAreaName(),
                false);

        player.enterArea(currentRoom, new DiscreteCoordinates(2, 2));       // need to put this, and other like this in a constant
                                                                                  // is currently in Level0, but inaccessible

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

}
