package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;

public interface ArrowFire {

    void launch();

    void launch(Orientation removedOrientation);

    void launchCopy();
}
