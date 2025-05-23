package net.minecraft.src.MEDMEX.UI;

import java.awt.Color;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.GuiTexturePacks;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.altman.GuiAltManager;
import net.minecraft.src.MEDMEX.serverman.GuiServerManager;
import org.lwjgl.opengl.GL11;

public class MainMenu extends GuiScreen {
   long timer = 1L;

   public void initGui() {
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      GL11.glBindTexture(3553, this.mc.renderEngine.getTexture("/background.png"));
      this.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, this.width, this.height, (float)this.width, (float)this.height);
      this.drawGradientRect(0, this.height - 100, this.width, this.height, 0, -16777216);
      String[] buttons = new String[]{"Singleplayer", "Multiplayer", "Settings", "Packs", "Account Manager", "Quit"};
      int count = 0;
      String[] var9 = buttons;
      int var8 = buttons.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         String name = var9[var7];
         float x = (float)(this.width / buttons.length * count) + (float)(this.width / buttons.length) / 2.0F - (float)this.mc.fontRenderer.getStringWidth(name) / 2.0F;
         float y = (float)(this.height - 20);
         boolean hovered = (float)mouseX >= x && (float)mouseY >= y && (float)mouseX < x + (float)this.mc.fontRenderer.getStringWidth(name) && (float)mouseY < y + (float)this.mc.fontRenderer.charHeight;
         this.drawCenteredString(this.mc.fontRenderer, name, this.width / buttons.length * count + this.width / buttons.length / 2, this.height - 20, hovered ? Color.RED.getRGB() : -1);
         ++count;
      }

      GL11.glPushMatrix();
      GL11.glTranslatef((float)this.width / 2.0F, (float)this.height / 2.0F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 1.0F);
      GL11.glTranslatef(-((float)this.width / 2.0F), -((float)this.height / 2.0F), 0.0F);
      this.drawCenteredString(this.mc.fontRenderer, Client.name, this.width / 2, this.height / 2 - 11, -1);
      GL11.glPopMatrix();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      String[] buttons = new String[]{"Singleplayer", "Multiplayer", "Settings", "Packs", "Account Manager", "Quit"};
      int count = 0;
      String[] var9 = buttons;
      int var8 = buttons.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         String name = var9[var7];
         float x = (float)(this.width / buttons.length * count) + (float)(this.width / buttons.length) / 2.0F - (float)this.mc.fontRenderer.getStringWidth(name) / 2.0F;
         float y = (float)(this.height - 20);
         if ((float)mouseX >= x && (float)mouseY >= y && (float)mouseX < x + (float)this.mc.fontRenderer.getStringWidth(name) && (float)mouseY < y + (float)this.mc.fontRenderer.charHeight) {
            switch(name.hashCode()) {
            case -2140469126:
               if (name.equals("Account Manager")) {
                  this.mc.displayGuiScreen(new GuiAltManager(this));
               }
               break;
            case -2064742086:
               if (name.equals("Multiplayer")) {
                  this.mc.displayGuiScreen(new GuiServerManager(this));
               }
               break;
            case -1500504759:
               if (name.equals("Singleplayer")) {
                  this.mc.displayGuiScreen(new GuiSelectWorld(this));
               }
               break;
            case 2528879:
               if (name.equals("Quit")) {
                  this.mc.shutdown();
               }
               break;
            case 76869978:
               if (name.equals("Packs")) {
                  this.mc.displayGuiScreen(new GuiTexturePacks(this));
               }
               break;
            case 1499275331:
               if (name.equals("Settings")) {
                  this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
               }
            }
         }

         ++count;
      }

   }

   public void onGuiClosed() {
   }
}
