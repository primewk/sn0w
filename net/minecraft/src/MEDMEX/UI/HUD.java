package net.minecraft.src.MEDMEX.UI;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.Gui;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Modules.Movement.Speed;
import net.minecraft.src.MEDMEX.Modules.Render.Coords;
import net.minecraft.src.MEDMEX.Utils.ColorUtil;
import org.lwjgl.opengl.GL11;

public class HUD {
   public static int height;
   public static int width;
   public static boolean potionHUD = false;
   public static boolean clickgui;
   long timer = 0L;
   public static boolean infoenabled = false;
   public static int itemcount = 0;
   public static String var;
   public static String coords;
   public static boolean antiss = false;
   public Minecraft mc;

   public HUD() {
      this.mc = Minecraft.theMinecraft;
   }

   public void draw() {
      ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
      FontRenderer fr = this.mc.fontRenderer;
      int count;
      if (Coords.instance.isEnabled()) {
         count = (int)this.mc.thePlayer.posX;
         int Y = (int)this.mc.thePlayer.posY;
         int Z = (int)this.mc.thePlayer.posZ;
         if (this.mc.thePlayer.dimension == -1) {
            this.mc.fontRenderer.drawStringWithShadow("§bO:§r §6[§f" + count * 8 + ", " + Y + ", " + Z * 8 + "§6]", (double)(sr.getScaledWidth() - fr.getStringWidth("O: [" + count * 8 + ", " + Y + ", " + Z * 8 + "]") - 4), (double)(sr.getScaledHeight() - 20), -1);
            this.mc.fontRenderer.drawStringWithShadow("§bN:§r §6[§f" + count + ", " + Y + ", " + Z + "§6]", (double)(sr.getScaledWidth() - fr.getStringWidth("N: [" + count + ", " + Y + ", " + Z + "]") - 4), (double)(sr.getScaledHeight() - 10), -1);
         } else {
            this.mc.fontRenderer.drawStringWithShadow("§bO:§r §6[§f" + count + ", " + Y + ", " + Z + "§6]", (double)(sr.getScaledWidth() - fr.getStringWidth("O: [" + count + ", " + Y + ", " + Z + "]") - 4), (double)(sr.getScaledHeight() - 20), -1);
            this.mc.fontRenderer.drawStringWithShadow("§bN:§r §6[§f" + count / 8 + ", " + Y + ", " + Z / 8 + "§6]", (double)(sr.getScaledWidth() - fr.getStringWidth("N: [" + count / 8 + ", " + Y + ", " + Z / 8 + "]") - 4), (double)(sr.getScaledHeight() - 10), -1);
         }
      }

      double diffX;
      if (this.mc.getSendQueue().netManager.timeSinceLastRead >= 20) {
         diffX = (double)this.mc.getSendQueue().netManager.timeSinceLastRead / 20.0D;
         this.mc.fontRenderer.drawCenteredString(fr, "Server has been frozen for: " + String.format("%.1f", diffX) + "s", (double)(sr.getScaledWidth() / 2), 10.0D, 16777215);
      }

      Collections.sort(Client.modules, new HUD.ModuleComparator());
      if (!GameSettings.showDebugInfo) {
         this.mc.fontRenderer.drawStringWithShadow(Client.name, 4.0D, 4.0D, ColorUtil.getSnow(1).getRGB());
         diffX = this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX;
         double diffZ = this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ;
         double speed = Math.sqrt(diffX * diffX + diffZ * diffZ) / 1000.0D / 1.388888888888889E-5D * (double)this.mc.timer.timerSpeed;
         if (Speed.instance.isEnabled() && Speed.instance.getSet("Mode").getValString().equalsIgnoreCase("Bypass")) {
            speed = Math.sqrt(diffX * diffX + diffZ * diffZ) / 1000.0D / 1.388888888888889E-5D * Speed.instance.fakeTimer;
         }

         BigDecimal speed3 = (new BigDecimal(speed)).setScale(1, RoundingMode.HALF_UP);
         GL11.glScalef(0.7F, 0.7F, 1.0F);
         fr.drawStringWithShadow("Speed: " + speed3 + " km/h", 6.0D, 20.0D, ColorUtil.getSnow(1).getRGB());
         GL11.glScalef(1.428577F, 1.428577F, 1.0F);
      }

      count = 0;
      Iterator var13 = Client.modules.iterator();

      while(var13.hasNext()) {
         Module m = (Module)var13.next();
         if (m.toggled && !Client.drawn.contains(m)) {
            if (!GameSettings.showDebugInfo) {
               Gui.drawRect((double)(sr.getScaledWidth() - fr.getStringWidth(m.name + m.attribute) - 10), (double)(3 + count * 9), (double)(sr.getScaledWidth() - 8), (double)(12 + count * 9), -2145180893);
               this.mc.fontRenderer.drawStringWithShadow(m.name + m.attribute, (double)(sr.getScaledWidth() - fr.getStringWidth(m.name + m.attribute) - 8), (double)(4 + count * 9), ColorUtil.astolfoColorsDraw(1000, -1000));
               Gui.drawRect((double)sr.getScaledWidth() - 3.5D, 4.0D, (double)(sr.getScaledWidth() - 5), (double)(12 + count * 9), ColorUtil.astolfoColorsDraw(1000, -1000));
            }

            ++count;
         }
      }

   }

   public void drawRainbowString(String s, double x, double y) {
      FontRenderer fr = this.mc.fontRenderer;

      for(int i = 0; i < s.length(); ++i) {
         if (i == 0) {
            this.mc.fontRenderer.drawStringWithShadow(String.valueOf(s.charAt(i)), x, y, ColorUtil.getRainbow(2.0F, 0.6F, 1.0F, (long)(i * -100)));
         } else {
            this.mc.fontRenderer.drawStringWithShadow(String.valueOf(s.charAt(i)), x + (double)(fr.getCharWidth(s.charAt(i - 1)) * i), y, ColorUtil.getRainbow(2.0F, 0.6F, 1.0F, (long)(i * -100)));
         }
      }

   }

   public static class ModuleComparator implements Comparator<Module> {
      public int compare(Module o1, Module o2) {
         if (Minecraft.theMinecraft.fontRenderer.getStringWidth(o1.name + o1.attribute) > Minecraft.theMinecraft.fontRenderer.getStringWidth(o2.name + o2.attribute)) {
            return -1;
         } else {
            return Minecraft.theMinecraft.fontRenderer.getStringWidth(o1.name + o1.attribute) < Minecraft.theMinecraft.fontRenderer.getStringWidth(o2.name + o2.attribute) ? 1 : 0;
         }
      }
   }
}
