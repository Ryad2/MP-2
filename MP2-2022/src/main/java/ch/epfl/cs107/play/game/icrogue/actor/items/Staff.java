package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Staff extends Item{

    Animation staffAnimation;

    public Staff(Area area, Orientation orientation, DiscreteCoordinates position){
        super(area, orientation, position);

        this.sprite=new Sprite("zelda/staff_water.icon", .5f, .5f, this);

        staffAnimation = new Animation(8, Sprite.extractSprites("zelda/staff",
                8, 1f, 1f, this, 32, 32), true);
    }

    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }


    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {// BE CAREFUL TO DO A PROJECTILE
        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        staffAnimation.update(deltaTime);
    }

    public void draw(Canvas canvas){
        //sprite.draw(canvas);
        staffAnimation.draw(canvas);
    }
}
