package net.minecraft.src;

import java.util.Calendar;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
    /** The normal small chest model. */
    private ModelChest chestModel = new ModelChest();

    /** The large double chest model. */
    private ModelChest largeChestModel = new ModelLargeChest();
    private boolean field_92061_d;

    public TileEntityChestRenderer()
    {
        Calendar var1 = Calendar.getInstance();

        if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26)
        {
            this.field_92061_d = true;
        }
    }

    /**
     * Renders the TileEntity for the chest at a position.
     */
    public void renderTileEntityChestAt(TileEntityChest par1TileEntityChest, double par2, double par4, double par6, float par8)
    {
        int var9;

        if (!par1TileEntityChest.func_70309_m())
        {
            var9 = 0;
        }
        else
        {
            Block var10 = par1TileEntityChest.getBlockType();
            var9 = par1TileEntityChest.getBlockMetadata();

            if (var10 != null && var9 == 0)
            {
                ((BlockChest)var10).unifyAdjacentChests(par1TileEntityChest.getWorldObj(), par1TileEntityChest.xCoord, par1TileEntityChest.yCoord, par1TileEntityChest.zCoord);
                var9 = par1TileEntityChest.getBlockMetadata();
            }

            par1TileEntityChest.checkForAdjacentChests();
        }

        if (par1TileEntityChest.adjacentChestZNeg == null && par1TileEntityChest.adjacentChestXNeg == null)
        {
            ModelChest var14;

            if (par1TileEntityChest.adjacentChestXPos == null && par1TileEntityChest.adjacentChestZPosition == null)
            {
                var14 = this.chestModel;

                if (this.field_92061_d)
                {
                    this.bindTextureByName("/item/xmaschest.png");
                }
                else
                {
                    this.bindTextureByName("/item/chest.png");
                }
            }
            else
            {
                var14 = this.largeChestModel;

                if (this.field_92061_d)
                {
                    this.bindTextureByName("/item/largexmaschest.png");
                }
                else
                {
                    this.bindTextureByName("/item/largechest.png");
                }
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short var11 = 0;

            if (var9 == 2)
            {
                var11 = 180;
            }

            if (var9 == 3)
            {
                var11 = 0;
            }

            if (var9 == 4)
            {
                var11 = 90;
            }

            if (var9 == 5)
            {
                var11 = -90;
            }

            if (var9 == 2 && par1TileEntityChest.adjacentChestXPos != null)
            {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (var9 == 5 && par1TileEntityChest.adjacentChestZPosition != null)
            {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float)var11, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float var12 = par1TileEntityChest.prevLidAngle + (par1TileEntityChest.lidAngle - par1TileEntityChest.prevLidAngle) * par8;
            float var13;

            if (par1TileEntityChest.adjacentChestZNeg != null)
            {
                var13 = par1TileEntityChest.adjacentChestZNeg.prevLidAngle + (par1TileEntityChest.adjacentChestZNeg.lidAngle - par1TileEntityChest.adjacentChestZNeg.prevLidAngle) * par8;

                if (var13 > var12)
                {
                    var12 = var13;
                }
            }

            if (par1TileEntityChest.adjacentChestXNeg != null)
            {
                var13 = par1TileEntityChest.adjacentChestXNeg.prevLidAngle + (par1TileEntityChest.adjacentChestXNeg.lidAngle - par1TileEntityChest.adjacentChestXNeg.prevLidAngle) * par8;

                if (var13 > var12)
                {
                    var12 = var13;
                }
            }

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;
            var14.chestLid.rotateAngleX = -(var12 * (float)Math.PI / 2.0F);
            var14.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityChestAt((TileEntityChest)par1TileEntity, par2, par4, par6, par8);
        //VEX
        if (Variables.chest && (par1TileEntity instanceof TileEntityChest))
        {
            if (!((TileEntityChest)par1TileEntity).checkForCloseMoss())
            {
                Minecraft.theMinecraft.entityRenderer.disableLightmap(par8);
                RenderGlobal.chestRadar(par2, par4, par6, par8, false);
                Minecraft.theMinecraft.entityRenderer.enableLightmap(par8);
            }
            else if (Variables.dungeonChest)
            {
                Minecraft.theMinecraft.entityRenderer.disableLightmap(par8);
                RenderGlobal.chestRadar(par2, par4, par6, par8, true);
                Minecraft.theMinecraft.entityRenderer.enableLightmap(par8);
            }
        }
    }
}
