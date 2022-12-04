package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.FireBall;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import java.util.HashMap;
import java.util.Map;



public class ICRoguePlayer extends ICRogueActor {
    private static final int MOVE_DURATION = 8;
    private Orientation DEFAULT_ORIENTATION;

    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {

        super(owner, orientation, coordinates);

        DEFAULT_ORIENTATION = orientation;
        createSpriteMAP();

        name = "player";
    }


    Map<Orientation, Sprite> sprites = new HashMap<>();
    public void createSpriteMAP() {


        Sprite down = new Sprite("zelda/player", .75f, 1.5f, this,
                new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
        Sprite right = new Sprite("zelda/player", .75f, 1.5f, this,
                new RegionOfInterest(0, 32, 16, 32), new Vector(.15f, -.15f));
        Sprite top = new Sprite("zelda/player", .75f, 1.5f, this,
                new RegionOfInterest(0, 64, 16, 32), new Vector(.15f, -.15f));
        Sprite left = new Sprite("zelda/player", .75f, 1.5f, this,
                new RegionOfInterest(0, 96, 16, 32), new Vector(.15f, -.15f));


        sprites.put(Orientation.UP, top);
        sprites.put(Orientation.DOWN, down);
        sprites.put(Orientation.RIGHT, right);
        sprites.put(Orientation.LEFT, left);

}

    public Sprite getSprites(Orientation orientation){
        return sprites.get(orientation);
    }

    public void resetPlayer(){
        orientate(DEFAULT_ORIENTATION);
    }


    public void update(float deltaTime) {

        Keyboard keyboard= getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        launchFireBallIfPressed(keyboard.get(Keyboard.X));

        sprite = getSprites(getOrientation());

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

    private void launchFireBallIfPressed(Button button){
        if (button.isDown()){
            launchFireBall();
        }
    }

    private void launchFireBall(){
        ICRogueActor fireBall = new FireBall(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates());
    }


    public boolean takeCellSpace(){
        return true;
    }
}







