package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0KeyRoom extends Level0Room {
    private final Key key;

    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyID){
        super(roomCoordinates);
        key = new Key(this, Orientation.DOWN, ITEM_COORDINATES, keyID);
    }

    @Override
    protected void createArea() {
        registerActor(key);
        super.createArea();
    }
}
