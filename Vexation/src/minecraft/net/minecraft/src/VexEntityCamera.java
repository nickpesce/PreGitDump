package net.minecraft.src;

public class VexEntityCamera extends EntityLiving
{
    public VexEntityCamera(World world)
    {
        super(world);
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    /**
     * Gets called every tick from main Entity class
     */
    public void onEntityUpdate()
    {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    }

    public void onDeath(Entity entity)
    {
    }

    /**
     * Checks whether target entity is alive.
     */
    public boolean isEntityAlive()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 0;
    }

    public void setCamera(double d, double d1, double d2, float f, float f1)
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        posX += d;
        posY += d1;
        posZ += d2;
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
        rotationYaw = f;
        rotationPitch = f1;
    }
}
