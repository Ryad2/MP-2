package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class ICRogueRoom extends Area {

    public DiscreteCoordinates coordinates;


    private ICRogueBehavior behavior;
    private final String behaviorName;

    public ICRogueRoom(String behaviorName, DiscreteCoordinates roomCoordinates){
        this.behaviorName = behaviorName;
        this.coordinates = roomCoordinates;
    }



    public final float getCameraScaleFactor() {
        return Tuto2.CAMERA_SCALE_FACTOR;
    }

    public String getTitle() { return behaviorName; }


    // ======   WARNING   ==== end of page 6 has more info. CreateArea() has been commented
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, getTitle());
            setBehavior(behavior);
            //createArea();
            return true;
        }
        return false;
    }
}
