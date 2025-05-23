package net.minecraft.src;

import java.util.Iterator;
import net.minecraft.src.MEDMEX.Modules.Player.Freecam;
import org.lwjgl.opengl.GL11;

public class GuiChest extends GuiContainer {
   private IInventory upperChestInventory;
   private IInventory lowerChestInventory;
   private int inventoryRows = 0;

   public GuiChest(IInventory var1, IInventory var2) {
      super(new ContainerChest(var1, var2));
      this.upperChestInventory = var1;
      this.lowerChestInventory = var2;
      this.allowUserInput = false;
      short var3 = 222;
      int var4 = var3 - 108;
      this.inventoryRows = var2.getSizeInventory() / 9;
      this.ySize = var4 + this.inventoryRows * 18;
   }

   public void initGui() {
      super.initGui();
      int posY = (this.height - this.ySize) / 2 - 20;
      this.controlList.add(new GuiButton(1, this.width / 2 - 88, posY, 60, 20, "Mount"));
      this.controlList.add(new GuiButton(2, this.width / 2 + 28, posY, 60, 20, "Freecam"));
   }

   protected void drawGuiContainerForegroundLayer() {
      this.fontRenderer.drawString(this.lowerChestInventory.getInvName(), 8.0D, 6.0D, 4210752);
      this.fontRenderer.drawString(this.upperChestInventory.getInvName(), 8.0D, (double)(this.ySize - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float var1) {
      int var2 = this.mc.renderEngine.getTexture("/gui/container.png");
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.renderEngine.bindTexture(var2);
      int var3 = (this.width - this.xSize) / 2;
      int var4 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
      this.drawTexturedModalRect(var3, var4 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
   }

   protected void actionPerformed(GuiButton par1) {
      if (par1.id == 1) {
         try {
            Iterator var3 = this.mc.theWorld.loadedEntityList.iterator();

            while(var3.hasNext()) {
               Entity e = (Entity)var3.next();
               if (e instanceof EntityBoat && this.mc.thePlayer.getDistanceSqToEntity(e) < 6.0D) {
                  this.mc.playerController.interactWithEntity(this.mc.thePlayer, e);
               }
            }
         } catch (Exception var5) {
         }
      }

      if (par1.id == 2) {
         try {
            Freecam.instance.toggle();
         } catch (Exception var4) {
         }
      }

   }
}
