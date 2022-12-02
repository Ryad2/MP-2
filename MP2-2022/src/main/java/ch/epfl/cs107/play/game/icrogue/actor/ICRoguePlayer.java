package ch.epfl.cs107.play.game.icrogue.actor;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;


public class ICRoguePlayer extends ICRogueActor {


    private static final int MOVE_DURATION = 8;

    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
    }



    //bas
    Sprite bottom = new Sprite("zelda/player",.75f,1.5f,this, new RegionOfInterest(0, 0,16,32), new Vector(.15f,-.15f));

    // droite
   Sprite right = new Sprite("zelda/player",.75f,1.5f,this, new RegionOfInterest(0, 32,16,32), new Vector(.15f, -.15f));

   // haut
Sprite top = new Sprite("zelda/player",.75f,1.5f,this, new RegionOfInterest(0, 64,16,32), new Vector(.15f, -.15f));

   // gauche
Sprite left =  new Sprite("zelda/player",.75f,1.5f,this, new RegionOfInterest(0, 96,16,32), new Vector(.15f,-.15f));









    public void update(float deltaTime) {

        Keyboard keyboard= getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        super.update(deltaTime);

    }






    private void moveIfPressed(Orientation orientation, Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }

    }


    private void launchFireBall(){
        ICRogueActor fireBall= new ICRogueActor(getOwnerArea(), getOrientation(),getCurrentMainCellCoordinates());
    }//create fire ball when press X; maybe create a class PROJECTILLE


}







