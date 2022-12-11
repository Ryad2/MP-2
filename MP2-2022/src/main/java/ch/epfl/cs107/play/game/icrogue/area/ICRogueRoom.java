package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.List;

public abstract class ICRogueRoom extends Area {

    public DiscreteCoordinates coordinates;// WHY THIS SHIT IS PUBLIC I LL USE IT AS PUBLIC IN CONNECTOR BUT SHOULD CHANGE IT
    private ICRogueBehavior behavior;

    private List<DiscreteCoordinates > connectorsCoordinates;
    private List<Orientation> orientations;
    public final String behaviorName; // WHY THIS SHIT IS PUBLIC


    public ICRogueRoom(String behaviorName, DiscreteCoordinates roomCoordinates){
        this.behaviorName = behaviorName;
        this.coordinates = roomCoordinates;
    }

    public ICRogueRoom(List<DiscreteCoordinates > connectorsCoordinates ,
                List<Orientation> orientations ,
                String behaviorName , DiscreteCoordinates roomCoordinates){

        this.connectorsCoordinates=connectorsCoordinates;
        this.orientations=orientations;
        this.behaviorName = behaviorName;
        this.coordinates = roomCoordinates;
    }

    /*enum W(ouest) S(south) E(est) N(north)*/




    @Override
    public final float getCameraScaleFactor() {
        return ICRogue.CAMERA_SCALE_FACTOR;
    }





    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected  void createArea(){
        for (int i=0; i<orientations.size(); i++){
            registerActor(new Connector(this, connectorsCoordinates.get(i), orientations.get(i)));
        }
    }


    public String getTitle() {              // WHY IS IT OVERWRITTEN !?
        return behaviorName;
    }


    public DiscreteCoordinates getCoordinates(){
        return coordinates;
    }



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

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
