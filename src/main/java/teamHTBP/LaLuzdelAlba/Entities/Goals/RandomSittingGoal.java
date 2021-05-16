package teamHTBP.LaLuzdelAlba.Entities.Goals;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import teamHTBP.LaLuzdelAlba.Entities.Interface.IMobSit;

import java.util.Random;

public class RandomSittingGoal extends Goal {
    protected final IMobSit entity;
    protected double probability = 0;
    private int sitTime = 0;
    private final int maxSitTime;

    public RandomSittingGoal(IMobSit entity) {
        this(entity,0.01);
    }

    public RandomSittingGoal(IMobSit entity,double probability) {
        this.entity = entity;
        this.probability = probability;
        maxSitTime = 50 + new Random().nextInt(1000);
        sitTime = 0;
    }

    @Override
    public void start() {
        entity.setSit(true);
        sitTime = 0;
    }

    @Override
    public boolean canUse() {
        return sitTime < maxSitTime && new Random().nextFloat() < this.probability;
    }

    @Override
    public boolean canContinueToUse() {
        return entity.isSit() && sitTime < maxSitTime;
    }

    @Override
    public void stop() {
        entity.setSit(false);
        sitTime = 0;
    }

    @Override
    public void tick() {
        if(entity.isSit())
            sitTime++;
    }
}
