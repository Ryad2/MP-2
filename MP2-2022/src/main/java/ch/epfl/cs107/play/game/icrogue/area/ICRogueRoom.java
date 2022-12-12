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
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICRogueRoom extends Area {

    private final DiscreteCoordinates coordinates;
    private ICRogueBehavior behavior;


    private List<Connector> connectors;
    private List<DiscreteCoordinates> connectorsCoordinates;
    private List<Orientation> orientations;
    private final String behaviorName;


    // these are temporary variables. They should stop being used when part 3 is done
    private final float keyPressCooldown = 0.5f;
    private float keyPressCooldownValue;
    private int key;

    public ICRogueRoom(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations,
                       String behaviorName, DiscreteCoordinates roomCoordinates){

        assert connectorsCoordinates.size() == orientations.size();

        this.coordinates = roomCoordinates;
        this.behaviorName = behaviorName;


        connectors = new ArrayList<>();
        // I create the Connectors here to lighten the later code
        for (int i = 0; i < connectorsCoordinates.size(); i++){
            connectors.add(new Connector(this, connectorsCoordinates.get(i), orientations.get(i).opposite()));
        }




        this.connectorsCoordinates = connectorsCoordinates;
        this.orientations = orientations;
    }
    /*enum W(west) S(south) E(est) N(north)*/


    // ====         ====
    // ==== Getters ====
    // ====         ====


    public String getBehaviorName(){
        return behaviorName;
    }

    public DiscreteCoordinates getCoordinates(){
        return coordinates;
    }

    public String getTitle() {              // WHY IS IT OVERWRITTEN !?
        return behaviorName;
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
    protected void createArea(){
        //this.connectors = new ArrayList<>();

        /*for (int i=0; i<orientations.size(); i++){
            connectors.add(new Connector(this, connectorsCoordinates.get(i), orientations.get(i).opposite()));
            registerActor(connectors.get(i));
        }*/

        for (var x : connectors){
            registerActor(x);
        }
    }

    // ====                             ====
    // ==== methods for the Level class ====
    // ====                             ====

    public void setConnectorDestination(int connectorIndex, String destination, DiscreteCoordinates targetCoordinates){
        Connector connector = connectors.get(connectorIndex);
        connector.setDestinationAreaName(destination);
        connector.setTargetCoordinates(targetCoordinates);
    }

    public void setConnector(int connectorIndex, String destination, DiscreteCoordinates targetCoordinates){
        setConnectorDestination(connectorIndex, destination, targetCoordinates);

        connectors.get(connectorIndex).lock(Connector.NO_KEY_ID);
    }

    public void setConnectorKey(int connectorIndex, int keyID){
        connectors.get(connectorIndex).lock(keyID);
    }



    // what does this method do, and where does it come from ?
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

        Keyboard keyboard = getKeyboard();
        if (keyboard.get(Keyboard.O).isDown() && keyPressCooldownValue >= keyPressCooldown){
            for (Connector connector : connectors){
                connector.open();
                keyPressCooldownValue = 0;
            }
        }

        if(keyboard.get(Keyboard.L).isDown() && keyPressCooldownValue >= keyPressCooldown){
            connectors.get(0).lock(1);              // this probably should be done with getIndex()
            keyPressCooldownValue = 0;
        }

        if (keyboard.get(Keyboard.T).isDown() && keyPressCooldownValue >= keyPressCooldown){
            for (Connector connector : connectors){
                connector.switchState();
                keyPressCooldownValue = 0;
            }
        }


        // this code is bad, but it is purely temporary. This is to facilitate testing
        if (keyPressCooldownValue < keyPressCooldown){
            keyPressCooldownValue += deltaTime;
            if (keyPressCooldownValue > keyPressCooldown) keyPressCooldownValue = keyPressCooldown;
        }
    }
}
