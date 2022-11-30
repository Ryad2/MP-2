package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.actor.ICRouguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.level0Room;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

// copies Tuto2
public class ICRogue extends AreaGame {
    private ICRouguePlayer player;
    private ICRogueRoom currentRoom;

    public String getTitle(){ return ("ICRogue"); }


    private void init(String areaKey){
        currentRoom = new level0Room(new DiscreteCoordinates(0, 0));
        addArea(currentRoom);
        setCurrentArea(areaKey, true);
        //player = new ICRouguePlayer();
        //player.enterArea(area, coords);
    }
}
