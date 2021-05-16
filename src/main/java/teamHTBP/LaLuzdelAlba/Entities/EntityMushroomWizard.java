package teamHTBP.LaLuzdelAlba.Entities;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import teamHTBP.LaLuzdelAlba.Entities.Goals.DemoGoal;
import teamHTBP.LaLuzdelAlba.Entities.Goals.RandomSittingGoal;
import teamHTBP.LaLuzdelAlba.Entities.Interface.IMobSit;

import javax.annotation.Nullable;

public class EntityMushroomWizard extends AnimalEntity implements IMobSit {
    private static DataParameter<Boolean> isSit = EntityDataManager.defineId(EntityMushroomWizard.class, DataSerializers.BOOLEAN);

    protected EntityMushroomWizard(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0,new DemoGoal());
        this.goalSelector.addGoal(1,new LookAtGoal(this, PlayerEntity.class,7.0f));
        this.goalSelector.addGoal(2,new LookRandomlyGoal(this));
        this.goalSelector.addGoal(3,new RandomSittingGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(isSit,false);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }

    @Override
    public void tick() {
        super.tick();
    }


    @Override
    public boolean isSit() {
        return this.entityData.get(isSit);
    }

    @Override
    public void setSit(boolean sit) {
        this.entityData.set(isSit,sit);
    }

}
