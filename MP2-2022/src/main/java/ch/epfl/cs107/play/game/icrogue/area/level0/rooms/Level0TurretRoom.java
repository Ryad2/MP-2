package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Melee;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.WalkingTurret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.HashMap;
import java.util.Map;

public class Level0TurretRoom extends Level0EnemyRoom {

    private static final Map<Integer, createEnemies> enemyConfigurations = new HashMap<>();

    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);

        addEntity(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(1, 8),
                new Orientation[] {Orientation.RIGHT, Orientation.DOWN}));

        addEntity(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(8, 1),
                new Orientation[] {Orientation.LEFT, Orientation.UP}));

        addEntity(new WalkingTurret(this, Orientation.LEFT, new DiscreteCoordinates(8, 4),
                new Orientation[] {Orientation.LEFT, Orientation.RIGHT, Orientation.DOWN}));
    }


    // this is a delegate. We were not taught to do this
    @FunctionalInterface
    private interface createEnemies{
        void creteEnemies();
    }

    private void cornerTurrets(){
        addEntity(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(1, 8),
                new Orientation[] {Orientation.RIGHT, Orientation.DOWN}));

        addEntity(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(8, 1),
                new Orientation[] {Orientation.LEFT, Orientation.UP}));

    }
    private void walkingTurret(){
        addEntity(new WalkingTurret(this, Orientation.LEFT, new DiscreteCoordinates(8, 8),
                new Orientation[] {Orientation.LEFT, Orientation.RIGHT, Orientation.DOWN}));
    }
}
