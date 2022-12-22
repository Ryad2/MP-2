package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.List;

public abstract class Level0ItemRoom extends Level0Room {
    private List<Item> items;

    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    public void addItem(Item newItem){
        items.add(items.size(), newItem);
    }

    @Override
    protected void createArea() {
        super.createArea();

        for (Item item : items){
            registerActor(item);
        }
    }
}
