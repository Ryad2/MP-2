package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room {

    private List<ICRogueActor> enemies;

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);

        enemies = new ArrayList<>();
    }

    protected void addEnemy(ICRogueActor enemy){
        enemies.add(enemy);
    }

    public void removeEnemy(ICRogueActor enemy){
        enemies.remove(enemy);
        enemy.leaveArea();
        if (enemies.size() == 0){
            beatRoom();
        }
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

        for (ICRogueActor enemy : enemies) {
            registerActor(enemy);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
