package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICRogueRoom extends Area {

    public DiscreteCoordinates coordinates;// WHY THIS SHIT IS PUBLIC I LL USE IT AS PUBLIC IN CONNECTOR BUT SHOULD CHANGE IT
    private ICRogueBehavior behavior;
    public final String behaviorName;

    public ICRogueRoom(String behaviorName, DiscreteCoordinates roomCoordinates){
        this.behaviorName = behaviorName;
        this.coordinates = roomCoordinates;
    }


    @Override
    public final float getCameraScaleFactor() {
        return ICRogue.CAMERA_SCALE_FACTOR;
    }

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();

    public String getTitle() {              // WHY IS IT OVERWRITTEN !?
        return behaviorName;
    }

    //CREATED FOR CONNECTOR
    public String getTitle(String behaviorName, DiscreteCoordinates coordinates) { return behaviorName + coordinates.x + coordinates.y; }

    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, behaviorName);
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }
}
