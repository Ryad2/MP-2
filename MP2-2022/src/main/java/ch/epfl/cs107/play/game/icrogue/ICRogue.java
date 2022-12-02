package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.level0Room;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

// copies Tuto2
public class ICRogue extends AreaGame {
    private ICRogueRoom currentRoom;


    private void initLevel(String areaKey){
        currentRoom = new level0Room(new DiscreteCoordinates(0, 0));
        addArea(currentRoom);
        setCurrentArea(areaKey, true);
        //player = new ICRouguePlayer();
        //player.enterArea(area, coords);
    }




    public final static float CAMERA_SCALE_FACTOR = 11.f;

    private ICRoguePlayer player;
    private final String[] areas = {};

    private int areaIndex;
    /**
     * Add all the areas
     */
    private void createAreas(){

    }

    /*@Override
    public boolean begin(Window window, FileSystem fileSystem) {

        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            return true;
        }
        return false;
    }*/

    /*private void initArea(String areaKey) {

        Tuto2Area area = (Tuto2Area)setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICRoguePlayer(area, Orientation.DOWN, coords,"ghost.1");
        player.enterArea(area, coords);
        player.centerCamera();

    }*/
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void end() {
    }

    @Override
    public String getTitle(){ return ("ICRogue"); }

}
