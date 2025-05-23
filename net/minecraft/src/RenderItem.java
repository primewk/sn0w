package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Render.NoRender;
import org.lwjgl.opengl.GL11;

public class RenderItem extends Render {
   private RenderBlocks renderBlocks = new RenderBlocks();
   private Random random = new Random();
   public boolean field_27004_a = true;

   public RenderItem() {
      this.shadowSize = 0.15F;
      this.field_194_c = 0.75F;
   }

   public void doRenderItem(EntityItem var1, double var2, double var4, double var6, float var8, float var9) {
      if (!(var1 instanceof EntityItem) || !NoRender.instance.isEnabled() || !Client.settingsmanager.getSettingByName("NoRender Items").getValBoolean()) {
         this.random.setSeed(187L);
         ItemStack var10 = var1.item;
         GL11.glPushMatrix();
         float var11 = MathHelper.sin(((float)var1.age + var9) / 10.0F + var1.hoverStart) * 0.1F + 0.1F;
         float var12 = (((float)var1.age + var9) / 20.0F + var1.hoverStart) * 57.295776F;
         byte var13 = 1;
         if (var1.item.stackSize > 1) {
            var13 = 2;
         }

         if (var1.item.stackSize > 5) {
            var13 = 3;
         }

         if (var1.item.stackSize > 20) {
            var13 = 4;
         }

         GL11.glTranslatef((float)var2, (float)var4 + var11, (float)var6);
         GL11.glEnable(32826);
         float var16;
         float var17;
         float var18;
         if (var10.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var10.itemID].getRenderType())) {
            GL11.glRotatef(var12, 0.0F, 1.0F, 0.0F);
            this.loadTexture("/terrain.png");
            float var28 = 0.25F;
            if (!Block.blocksList[var10.itemID].renderAsNormalBlock() && var10.itemID != Block.stairSingle.blockID && Block.blocksList[var10.itemID].getRenderType() != 16) {
               var28 = 0.5F;
            }

            GL11.glScalef(var28, var28, var28);

            for(int var29 = 0; var29 < var13; ++var29) {
               GL11.glPushMatrix();
               if (var29 > 0) {
                  var16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var28;
                  var17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var28;
                  var18 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var28;
                  GL11.glTranslatef(var16, var17, var18);
               }

               this.renderBlocks.renderBlockOnInventory(Block.blocksList[var10.itemID], var10.getItemDamage(), var1.getEntityBrightness(var9));
               GL11.glPopMatrix();
            }
         } else {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            int var14 = var10.getIconIndex();
            if (var10.itemID < 256) {
               this.loadTexture("/terrain.png");
            } else {
               this.loadTexture("/gui/items.png");
            }

            Tessellator var15 = Tessellator.instance;
            var16 = (float)(var14 % 16 * 16 + 0) / 256.0F;
            var17 = (float)(var14 % 16 * 16 + 16) / 256.0F;
            var18 = (float)(var14 / 16 * 16 + 0) / 256.0F;
            float var19 = (float)(var14 / 16 * 16 + 16) / 256.0F;
            float var20 = 1.0F;
            float var21 = 0.5F;
            float var22 = 0.25F;
            int var23;
            float var24;
            float var25;
            float var26;
            if (this.field_27004_a) {
               var23 = Item.itemsList[var10.itemID].getColorFromDamage(var10.getItemDamage());
               var24 = (float)(var23 >> 16 & 255) / 255.0F;
               var25 = (float)(var23 >> 8 & 255) / 255.0F;
               var26 = (float)(var23 & 255) / 255.0F;
               float var27 = var1.getEntityBrightness(var9);
               GL11.glColor4f(var24 * var27, var25 * var27, var26 * var27, 1.0F);
            }

            for(var23 = 0; var23 < var13; ++var23) {
               GL11.glPushMatrix();
               if (var23 > 0) {
                  var24 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                  var25 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                  var26 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                  GL11.glTranslatef(var24, var25, var26);
               }

               GL11.glRotatef(180.0F - RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
               var15.startDrawingQuads();
               var15.setNormal(0.0F, 1.0F, 0.0F);
               var15.addVertexWithUV((double)(0.0F - var21), (double)(0.0F - var22), 0.0D, (double)var16, (double)var19);
               var15.addVertexWithUV((double)(var20 - var21), (double)(0.0F - var22), 0.0D, (double)var17, (double)var19);
               var15.addVertexWithUV((double)(var20 - var21), (double)(1.0F - var22), 0.0D, (double)var17, (double)var18);
               var15.addVertexWithUV((double)(0.0F - var21), (double)(1.0F - var22), 0.0D, (double)var16, (double)var18);
               var15.draw();
               GL11.glPopMatrix();
            }
         }

         GL11.glDisable(32826);
         GL11.glPopMatrix();
      }
   }

   public void drawItemIntoGui(FontRenderer var1, RenderEngine var2, int var3, int var4, int var5, int var6, int var7) {
      float var11;
      float var10;
      if (var3 < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var3].getRenderType())) {
         var2.bindTexture(var2.getTexture("/terrain.png"));
         Block var14 = Block.blocksList[var3];
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(var6 - 2), (float)(var7 + 3), -3.0F);
         GL11.glScalef(10.0F, 10.0F, 10.0F);
         GL11.glTranslatef(1.0F, 0.5F, 1.0F);
         GL11.glScalef(1.0F, 1.0F, -1.0F);
         GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         int var15 = Item.itemsList[var3].getColorFromDamage(var4);
         var11 = (float)(var15 >> 16 & 255) / 255.0F;
         var10 = (float)(var15 >> 8 & 255) / 255.0F;
         float var13 = (float)(var15 & 255) / 255.0F;
         if (this.field_27004_a) {
            GL11.glColor4f(var11, var10, var13, 1.0F);
         }

         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         this.renderBlocks.field_31088_b = this.field_27004_a;
         this.renderBlocks.renderBlockOnInventory(var14, var4, 1.0F);
         this.renderBlocks.field_31088_b = true;
         GL11.glPopMatrix();
      } else if (var5 >= 0) {
         GL11.glDisable(2896);
         if (var3 < 256) {
            var2.bindTexture(var2.getTexture("/terrain.png"));
         } else {
            var2.bindTexture(var2.getTexture("/gui/items.png"));
         }

         int var8 = Item.itemsList[var3].getColorFromDamage(var4);
         float var9 = (float)(var8 >> 16 & 255) / 255.0F;
         var10 = (float)(var8 >> 8 & 255) / 255.0F;
         var11 = (float)(var8 & 255) / 255.0F;
         if (this.field_27004_a) {
            GL11.glColor4f(var9, var10, var11, 1.0F);
         }

         this.renderTexturedQuad(var6, var7, var5 % 16 * 16, var5 / 16 * 16, 16, 16);
         GL11.glEnable(2896);
      }

      GL11.glEnable(2884);
   }

   public void renderItemIntoGUI(FontRenderer var1, RenderEngine var2, ItemStack var3, int var4, int var5) {
      if (var3 != null) {
         this.drawItemIntoGui(var1, var2, var3.itemID, var3.getItemDamage(), var3.getIconIndex(), var4, var5);
      }

   }

   public void renderItemOverlayIntoGUI(FontRenderer var1, RenderEngine var2, ItemStack var3, int var4, int var5) {
      if (var3 != null) {
         String var6;
         if (var3.stackSize > 1) {
            var6 = "" + var3.stackSize;
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            var1.drawStringWithShadow(var6, (double)(var4 + 19 - 2 - var1.getStringWidth(var6)), (double)(var5 + 6 + 3), 16777215);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
         }

         if (var3.stackSize < 1) {
            var6 = "" + var3.stackSize;
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            var1.drawStringWithShadow("§c" + var6, (double)(var4 + 19 - 2 - var1.getStringWidth(var6)), (double)(var5 + 6 + 3), 16777215);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
         }

         if (var3.isItemDamaged()) {
            int var11 = (int)Math.round(13.0D - (double)var3.getItemDamageForDisplay() * 13.0D / (double)var3.getMaxDamage());
            int var7 = (int)Math.round(255.0D - (double)var3.getItemDamageForDisplay() * 255.0D / (double)var3.getMaxDamage());
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            Tessellator var8 = Tessellator.instance;
            int var9 = 255 - var7 << 16 | var7 << 8;
            int var10 = (255 - var7) / 4 << 16 | 16128;
            this.renderQuad(var8, var4 + 2, var5 + 13, 13, 2, 0);
            this.renderQuad(var8, var4 + 2, var5 + 13, 12, 1, var10);
            this.renderQuad(var8, var4 + 2, var5 + 13, var11, 1, var9);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

   }

   private void renderQuad(Tessellator var1, int var2, int var3, int var4, int var5, int var6) {
      var1.startDrawingQuads();
      var1.setColorOpaque_I(var6);
      var1.addVertex((double)(var2 + 0), (double)(var3 + 0), 0.0D);
      var1.addVertex((double)(var2 + 0), (double)(var3 + var5), 0.0D);
      var1.addVertex((double)(var2 + var4), (double)(var3 + var5), 0.0D);
      var1.addVertex((double)(var2 + var4), (double)(var3 + 0), 0.0D);
      var1.draw();
   }

   public void renderTexturedQuad(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = 0.0F;
      float var8 = 0.00390625F;
      float var9 = 0.00390625F;
      Tessellator var10 = Tessellator.instance;
      var10.startDrawingQuads();
      var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + var6), (double)var7, (double)((float)(var3 + 0) * var8), (double)((float)(var4 + var6) * var9));
      var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), (double)var7, (double)((float)(var3 + var5) * var8), (double)((float)(var4 + var6) * var9));
      var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + 0), (double)var7, (double)((float)(var3 + var5) * var8), (double)((float)(var4 + 0) * var9));
      var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + 0), (double)var7, (double)((float)(var3 + 0) * var8), (double)((float)(var4 + 0) * var9));
      var10.draw();
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      if (!(var1 instanceof EntityItem) || !NoRender.instance.isEnabled() || !Client.settingsmanager.getSettingByName("NoRender Items").getValBoolean()) {
         this.doRenderItem((EntityItem)var1, var2, var4, var6, var8, var9);
      }
   }
}
