package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;

public class Arrow extends Projectiles implements Interactor {

    private final ArrowInteractionHandler handler;

    public Arrow(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);

        handler = new ArrowInteractionHandler();
    }

    public static void createArrow(Area owner, Orientation orientation, DiscreteCoordinates coordinates, Sprite sprite){
        Arrow arrow = new Arrow(owner, orientation, coordinates);
        arrow.setSprite(sprite);
        arrow.enterArea(owner, coordinates);
    }

    // Interactor

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }
    @Override
    public boolean wantsCellInteraction() {
        return true;
    }
    @Override
    public boolean wantsViewInteraction() {
        return false;
    }
    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }


    public class ArrowInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {

            if (isCellInteraction && player.isCellInteractable() && wantsCellInteraction()){
                player.takeDamage();
                consume();
            }
        }
    }
}
