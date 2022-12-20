package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0EnemyRoom extends Level0Room {

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    protected void beatRoom(){
        for (Connector connector : connectors){
            connector.open();
        }
        System.out.println("Room is beaten");
        ICRogue.beatBossRoom(getTitle());
    }

    @Override
    protected void createArea() {
        super.createArea();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
