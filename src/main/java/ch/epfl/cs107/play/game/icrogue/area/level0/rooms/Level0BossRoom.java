package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Melee;
import ch.epfl.cs107.play.game.icrogue.actor.items.Orb;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0BossRoom extends Level0EnemyRoom{

    public Level0BossRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);

        /*addEnemy(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(1, 8),
                new Orientation[] {Orientation.RIGHT, Orientation.DOWN}));

        addEnemy(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(8, 1),
                new Orientation[] {Orientation.LEFT, Orientation.UP}));

        addEnemy(new WalkingTurret(this, Orientation.LEFT, new DiscreteCoordinates(8, 8),
                new Orientation[] {Orientation.LEFT, Orientation.RIGHT, Orientation.DOWN}));*/

        /*addEntity(new Melee(this, Orientation.LEFT, new DiscreteCoordinates(8, 8)));
        addEntity(new Melee(this, Orientation.LEFT, new DiscreteCoordinates(1, 8)));
        addEntity(new Melee(this, Orientation.LEFT, new DiscreteCoordinates(8, 1)));*/
        addEntity(new Melee(this, Orientation.LEFT, new DiscreteCoordinates(1, 1)));
    }

    @Override
    protected void beatRoom(){
        createEntity(new Orb(this, Orientation.DOWN, new DiscreteCoordinates(4, 4)));

        super.beatRoom();
    }
}
