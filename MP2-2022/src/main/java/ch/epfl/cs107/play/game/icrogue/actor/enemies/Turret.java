package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

public class Turret extends ICRogueActor {

    private boolean isDead;
    private final Orientation[] targetOrientations;       // might want to change the type later

    private final float COOLDOWN = 2.f;
    private float cooldown = 0;

    public boolean getLivingStatus(){       // change the getter name
        return isDead;
    }

    public void die(){
        isDead = true;
        leaveArea();
    }

    public Turret(Area owner, Orientation orientation, DiscreteCoordinates coordinates, Orientation[] targetOrientations){
        super(owner, orientation, coordinates);
        this.targetOrientations = targetOrientations.clone();

        Sprite sprite = new Sprite("icrogue/static_npc", 1f, 1f, this,
                new RegionOfInterest(32* orientation.ordinal() , 0, 32, 32) , new Vector(0, 0));
        this.sprite = sprite;
    }

    @Override
    public void update(float deltaTime) {

        cooldown += deltaTime;
        if (cooldown > COOLDOWN){
            cooldown = 0;
            launch();
        }

        super.update(deltaTime);
    }


    private void launch(){

        for (Orientation orientation : targetOrientations){

            Sprite sprite = new Sprite("zelda/arrow", 1f, 1f, this,
                    new RegionOfInterest(32* orientation.ordinal() , 0, 32, 32) , new Vector(0, 0));

            new Arrow(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates(), sprite);
        }
    }
}
