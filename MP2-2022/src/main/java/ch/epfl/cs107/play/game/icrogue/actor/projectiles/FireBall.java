package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

// to corrector: boule de feu = FireBall and not Fire. hence the name change
public class FireBall extends Projectiles{

    private static final int DAMAGE = 1;
    private static final int MOVE_DURATION = 5;

    public FireBall(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super(owner, orientation, coordinates, DAMAGE, MOVE_DURATION);


        Sprite sprite = new Sprite ("zelda/fire", 1f, 1f, this,
                new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0));

        setSprite(new Sprite ("zelda/fire", 1f, 1f, this,
                new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0)));

        name = "FireBall";

        enterArea(owner, coordinates);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (isConsumed()) leaveArea();
    }
}
