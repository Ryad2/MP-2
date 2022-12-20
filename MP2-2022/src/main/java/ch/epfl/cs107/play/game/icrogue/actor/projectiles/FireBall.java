package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Melee;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.WalkingTurret;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

// to corrector: boule de feu = FireBall and not Fire. hence the name change
public class FireBall extends Projectiles implements Interactor {


    // WARNING
    //
    //          fireBall does not go to the end of the doorway
    //
    // WARNING

    private static final int DAMAGE = 1;
    private static final int MOVE_DURATION = 5;
    private final FireBallInteractionHandler handler;

    private final Animation animation;

    public FireBall(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super(owner, orientation, coordinates, DAMAGE, MOVE_DURATION);

        setSprite(new Sprite ("zelda/fire", 1f, 1f, this,
                new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0)));

        animation = new Animation(2, Sprite.extractSprites("zelda/MagicWaterProjectile", 4, 1f, 1f, this, 32, 32), true);

        enterArea(owner, coordinates);

        handler = new FireBallInteractionHandler();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        animation.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
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
        return true;
    }
    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }


    public class FireBallInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {

            ICRogueBehavior.ICRogueCellType wall = ICRogueBehavior.ICRogueCellType.WALL;
            ICRogueBehavior.ICRogueCellType hole = ICRogueBehavior.ICRogueCellType.HOLE;

            if (!isCellInteraction && (cell.getType() == wall || cell.getType() == hole)){
                consume();
            }
        }

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {

            if (isCellInteraction && turret.isCellInteractable() && wantsCellInteraction()){
                turret.die();
                consume();
            }
        }

        @Override
        public void interactWith(WalkingTurret turret, boolean isCellInteraction) {
            interactWith((Turret)turret, isCellInteraction);
        }

        @Override
        public void interactWith(Melee melee, boolean isCellInteraction) {
            if (isCellInteraction && melee.isCellInteractable() && wantsCellInteraction()){
                if (!isConsumed()){
                    melee.die();
                    consume();
                }
            }
        }
    }
}
