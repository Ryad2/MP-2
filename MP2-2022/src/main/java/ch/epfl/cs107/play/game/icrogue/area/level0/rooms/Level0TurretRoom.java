package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.WalkingTurret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0TurretRoom extends Level0EnemyRoom {

    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);

        addEnemy(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(1, 8),
                new Orientation[] {Orientation.RIGHT, Orientation.DOWN}));

        addEnemy(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(8, 1),
                new Orientation[] {Orientation.LEFT, Orientation.UP}));

        addEnemy(new WalkingTurret(this, Orientation.LEFT, new DiscreteCoordinates(8, 8),
                new Orientation[] {}));
    }
}
