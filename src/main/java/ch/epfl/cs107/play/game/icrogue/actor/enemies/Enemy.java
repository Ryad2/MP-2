package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0EnemyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Enemy extends ICRogueActor {

    private boolean isDead;
    protected Animation deathAnimation;
    private drawInterface drawMethod;
    //private updateInterface updateMethod;

    public boolean getLivingStatus(){       // change the getter name
        return isDead;
    }

    public Enemy(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super(owner, orientation, coordinates);

        sprite = getSprite();

        deathAnimation = new Animation(4, Sprite.extractSprites("zelda/explosion",
                4, 1f, 1f, this, 32, 32), false);

        drawMethod = this::normalDraw;
        //updateMethod = this::normalUpdate;
    }


    public void die(){
        if (!isDead){
            isDead = true;
            drop();
            Level0EnemyRoom room = (Level0EnemyRoom)getOwnerArea();
            room.removeEntity(this);
        }
    }

    private void drop(){
        if (Math.random() < .5f){
            ((Level0Room) getOwnerArea()).createEntity(new Cherry(getOwnerArea(), Orientation.DOWN,
                    getCurrentMainCellCoordinates()));
        }
    }

    protected Sprite getSprite(){
        return new Sprite("icrogue/static_npc", 1f, 1.5f, this,
                new RegionOfInterest(0, 0, 16, 32), new Vector(0, 0));
    }

    // update

    /*@Override
    public void update(float deltaTime) {
        updateMethod.update(deltaTime);
    }

    protected void normalUpdate(float deltaTime){
        super.update(deltaTime);
    }*/

    // draw methods

    @Override
    public void draw(Canvas canvas) {
        drawMethod.draw(canvas);
    }

    protected void normalDraw(Canvas canvas){
        super.draw(canvas);
    }

    protected void deathDraw(Canvas canvas){
        deathAnimation.draw(canvas);
    }

    protected void setDrawMethod(drawInterface drawMethod){
        this.drawMethod = drawMethod;
    }

    // Interactable

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }
}
