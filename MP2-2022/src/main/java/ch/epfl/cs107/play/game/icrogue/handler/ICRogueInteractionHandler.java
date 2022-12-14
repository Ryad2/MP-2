package ch.epfl.cs107.play.game.icrogue.handler;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.WalkingTurret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.FireBall;

public interface ICRogueInteractionHandler extends AreaInteractionVisitor {
    default void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {

    }
    default void interactWith(ICRoguePlayer player, boolean isCellInteraction) {

    }
    default void interactWith(Item item, boolean isCellInteraction) {

    }
    default void interactWith(Staff staff, boolean isCellInteraction) {

    }
    default void interactWith(Turret turret, boolean isCellInteraction) {

    }
    default void interactWith(Key key, boolean isCellInteraction){

    }
    default void interactWith(Connector connector, boolean isCellInteraction){

    }
    default void interactWith(WalkingTurret turret, boolean isCellInteraction){

    }
}
