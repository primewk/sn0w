package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiAchievement extends Gui {
   private Minecraft theGame;
   private int achievementWindowWidth;
   private int achievementWindowHeight;
   private String achievementGetLocalText;
   private String achievementStatName;
   private Achievement theAchievement;
   private long achievementTime;
   private RenderItem itemRender;
   private boolean haveAchiement;

   public GuiAchievement(Minecraft var1) {
      this.theGame = var1;
      this.itemRender = new RenderItem();
   }

   public void queueTakenAchievement(Achievement var1) {
      this.achievementGetLocalText = StatCollector.translateToLocal("achievement.get");
      this.achievementStatName = var1.statName;
      this.achievementTime = System.currentTimeMillis();
      this.theAchievement = var1;
      this.haveAchiement = false;
   }

   public void queueAchievementInformation(Achievement var1) {
      this.achievementGetLocalText = var1.statName;
      this.achievementStatName = var1.getDescription();
      this.achievementTime = System.currentTimeMillis() - 2500L;
      this.theAchievement = var1;
      this.haveAchiement = true;
   }

   private void updateAchievementWindowScale() {
      GL11.glViewport(0, 0, this.theGame.displayWidth, this.theGame.displayHeight);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      this.achievementWindowWidth = this.theGame.displayWidth;
      this.achievementWindowHeight = this.theGame.displayHeight;
      ScaledResolution var1 = new ScaledResolution(this.theGame.gameSettings, this.theGame.displayWidth, this.theGame.displayHeight);
      this.achievementWindowWidth = var1.getScaledWidth();
      this.achievementWindowHeight = var1.getScaledHeight();
      GL11.glClear(256);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, (double)this.achievementWindowWidth, (double)this.achievementWindowHeight, 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
   }

   public void updateAchievementWindow() {
      if (Minecraft.hasPaidCheckTime > 0L) {
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         RenderHelper.disableStandardItemLighting();
         this.updateAchievementWindowScale();
         String var1 = "Minecraft Beta 1.7.3   Unlicensed Copy :(";
         String var2 = "(Or logged in from another location)";
         String var3 = "Purchase at minecraft.net";
         this.theGame.fontRenderer.drawStringWithShadow(var1, 2.0D, 2.0D, 16777215);
         this.theGame.fontRenderer.drawStringWithShadow(var2, 2.0D, 11.0D, 16777215);
         this.theGame.fontRenderer.drawStringWithShadow(var3, 2.0D, 20.0D, 16777215);
         GL11.glDepthMask(true);
         GL11.glEnable(2929);
      }

      if (this.theAchievement != null && this.achievementTime != 0L) {
         double var8 = (double)(System.currentTimeMillis() - this.achievementTime) / 3000.0D;
         if (this.haveAchiement || this.haveAchiement || var8 >= 0.0D && var8 <= 1.0D) {
            this.updateAchievementWindowScale();
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            double var9 = var8 * 2.0D;
            if (var9 > 1.0D) {
               var9 = 2.0D - var9;
            }

            var9 *= 4.0D;
            var9 = 1.0D - var9;
            if (var9 < 0.0D) {
               var9 = 0.0D;
            }

            var9 *= var9;
            var9 *= var9;
            int var5 = this.achievementWindowWidth - 160;
            int var6 = 0 - (int)(var9 * 36.0D);
            int var7 = this.theGame.renderEngine.getTexture("/achievement/bg.png");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3553);
            GL11.glBindTexture(3553, var7);
            GL11.glDisable(2896);
            this.drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
            if (this.haveAchiement) {
               this.theGame.fontRenderer.drawSplitString(this.achievementStatName, var5 + 30, var6 + 7, 120, -1);
            } else {
               this.theGame.fontRenderer.drawString(this.achievementGetLocalText, (double)(var5 + 30), (double)(var6 + 7), -256);
               this.theGame.fontRenderer.drawString(this.achievementStatName, (double)(var5 + 30), (double)(var6 + 18), -1);
            }

            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            this.itemRender.renderItemIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.theAchievement.theItemStack, var5 + 8, var6 + 8);
            GL11.glDisable(2896);
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
         } else {
            this.achievementTime = 0L;
         }
      }

   }
}
