package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.WalkingTurret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0BossRoom extends Level0EnemyRoom{

    public Level0BossRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);

        addEnemy(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(1, 8),
                new Orientation[] {Orientation.RIGHT, Orientation.DOWN}));

        addEnemy(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(8, 1),
                new Orientation[] {Orientation.LEFT, Orientation.UP}));

        addEnemy(new WalkingTurret(this, Orientation.DOWN, new DiscreteCoordinates(8, 3),
                new Orientation[] {Orientation.LEFT, Orientation.UP}));

        addEnemy(new WalkingTurret(this, Orientation.RIGHT, new DiscreteCoordinates(1, 3),
                new Orientation[] {Orientation.UP, Orientation.DOWN}));

        /*addEnemy(new WalkingTurret(this, Orientation.DOWN, new DiscreteCoordinates(8, 3),
                new Orientation[] {}));*/
    }
}
