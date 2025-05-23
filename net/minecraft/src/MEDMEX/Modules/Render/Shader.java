package net.minecraft.src.MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Colors;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.Shader.FillShader;
import net.minecraft.src.MEDMEX.Utils.Shader.Framebuffer;
import net.minecraft.src.MEDMEX.Utils.Shader.OutlineShader;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.opengl.GL11;

public class Shader extends Module {
   public static Shader instance;
   private Framebuffer framebuffer;
   private int w;
   private int h;
   private int s;
   private final OutlineShader outlineShader = new OutlineShader();
   private final FillShader fillShader = new FillShader();

   public Shader() {
      super("Shader", 0, Module.Category.RENDER);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("LineWidth", this, 1.0D, 0.0D, 20.0D, false));
      Client.settingsmanager.rSetting(new Setting("Animals", this, true));
      Client.settingsmanager.rSetting(new Setting("Monsters", this, false));
      Client.settingsmanager.rSetting(new Setting("Players", this, true));
      Client.settingsmanager.rSetting(new Setting("Items", this, false));
      Client.settingsmanager.rSetting(new Setting("Chests", this, true));
      Client.settingsmanager.rSetting(new Setting("EnderChests", this, false));
      Client.settingsmanager.rSetting(new Setting("Vehicles", this, true));
      Client.settingsmanager.rSetting(new Setting("Particles", this, true));
      Client.settingsmanager.rSetting(new Setting("Hand", this, true));
      Client.settingsmanager.rSetting(new Setting("Crystals", this, true));
      ArrayList<String> options = new ArrayList();
      options.add("Outline");
      options.add("Fill");
      Client.settingsmanager.rSetting(new Setting("Mode", this, "Outline", options));
      ArrayList<String> colors = new ArrayList();
      colors.add("ClientColor");
      colors.add("Rainbow");
      Client.settingsmanager.rSetting(new Setting("ColorMode", this, "ClientColor", colors));
   }

   public void onRender(float partialTicks) {
      if (this.isEnabled()) {
         try {
            GL11.glEnable(3008);
            GL11.glPushMatrix();
            GL11.glPushAttrib(8256);
            ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            if (this.framebuffer == null) {
               this.framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
            } else {
               this.framebuffer.framebufferClear();
               if (res.getScaledHeight() != this.h || res.getScaledWidth() != this.w || res.getScaleFactor() != this.s) {
                  this.framebuffer.deleteFramebuffer();
                  this.framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
                  this.framebuffer.framebufferClear();
               }

               this.w = res.getScaledWidth();
               this.h = res.getScaledHeight();
               this.s = res.getScaleFactor();
            }

            this.framebuffer.bindFramebuffer(false);
            this.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
            this.mc.theWorld.loadedEntityList.forEach((entity) -> {
               if (entity != null && !entity.equals(this.mc.thePlayer) && this.isValid(entity)) {
                  RenderManager.instance.renderEntity(entity, partialTicks);
               }

            });

            try {
               this.mc.theWorld.loadedTileEntityList.forEach((tileentity) -> {
                  if (tileentity != null && !tileentity.equals(this.mc.thePlayer) && this.isValid(tileentity)) {
                     TileEntityRenderer.instance.renderTileEntity(tileentity, partialTicks);
                  }

               });
            } catch (Exception var5) {
            }

            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.framebuffer.unbindFramebuffer();
            this.mc.entityRenderer.disableLightmap(0.0D);
            RenderHelper.disableStandardItemLighting();
            GL11.glPushMatrix();
            net.minecraft.src.MEDMEX.Utils.Shader.Shader shader = null;
            String var4;
            switch((var4 = this.getSet("Mode").getValString()).hashCode()) {
            case 2189731:
               if (var4.equals("Fill")) {
                  shader = this.fillShader;
                  if (this.getSet("ColorMode").getValString().equals("ClientColor")) {
                     this.fillShader.setColor(Colors.getClientColor());
                  } else if (this.getSet("ColorMode").getValString().equals("Rainbow")) {
                     this.fillShader.setColor(new Color(SkyRainbow(20, 1.0F, 0.5F).getRed(), SkyRainbow(20, 1.0F, 0.5F).getGreen(), SkyRainbow(20, 1.0F, 0.5F).getBlue()));
                  }

                  this.outlineShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.fillShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.fillShader.use();
                  this.fillShader.updateUniforms();
               }
               break;
            case 558407714:
               if (var4.equals("Outline")) {
                  shader = this.outlineShader;
                  if (this.getSet("ColorMode").getValString().equals("ClientColor")) {
                     this.outlineShader.setColor(Colors.getClientColor());
                  } else if (this.getSet("ColorMode").getValString().equals("Rainbow")) {
                     this.outlineShader.setColor(new Color(SkyRainbow(20, 1.0F, 0.5F).getRed(), SkyRainbow(20, 1.0F, 0.5F).getGreen(), SkyRainbow(20, 1.0F, 0.5F).getBlue()));
                  }

                  this.outlineShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.outlineShader.use();
                  this.outlineShader.updateUniforms();
               }
            }

            this.mc.entityRenderer.setupOverlayRendering();
            GL11.glBindTexture(3553, this.framebuffer.framebufferTexture);
            GL11.glBegin(7);
            GL11.glTexCoord2d(0.0D, 1.0D);
            GL11.glVertex2d(0.0D, 0.0D);
            GL11.glTexCoord2d(0.0D, 0.0D);
            GL11.glVertex2d(0.0D, (double)res.getScaledHeight());
            GL11.glTexCoord2d(1.0D, 0.0D);
            GL11.glVertex2d((double)res.getScaledWidth(), (double)res.getScaledHeight());
            GL11.glTexCoord2d(1.0D, 1.0D);
            GL11.glVertex2d((double)res.getScaledWidth(), 0.0D);
            GL11.glEnd();
            if (shader != null) {
               ((net.minecraft.src.MEDMEX.Utils.Shader.Shader)shader).stopUse();
            }

            GL11.glPopMatrix();
            this.mc.entityRenderer.enableLightmap(0.0D);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
            this.mc.entityRenderer.setupOverlayRendering();
         } catch (Exception var6) {
         }
      }

   }

   public void onRenderHand(float partialTicks, int var13) {
      if (this.isEnabled()) {
         if (this.getSet("Hand").getValBoolean()) {
            GL11.glEnable(3008);
            GL11.glPushMatrix();
            GL11.glPushAttrib(8256);
            ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            if (this.framebuffer != null) {
               this.framebuffer.framebufferClear();
               if (res.getScaledHeight() != this.h || res.getScaledWidth() != this.w || res.getScaleFactor() != this.s) {
                  this.framebuffer.deleteFramebuffer();
                  this.framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
                  this.framebuffer.framebufferClear();
               }

               this.w = res.getScaledWidth();
               this.h = res.getScaledHeight();
               this.s = res.getScaleFactor();
            } else {
               this.framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
            }

            this.framebuffer.bindFramebuffer(false);
            this.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
            this.mc.entityRenderer.renderHand(partialTicks, var13);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.framebuffer.unbindFramebuffer();
            this.mc.entityRenderer.disableLightmap(0.0D);
            RenderHelper.disableStandardItemLighting();
            GL11.glPushMatrix();
            net.minecraft.src.MEDMEX.Utils.Shader.Shader shader = null;
            String var5;
            switch((var5 = this.getSet("Mode").getValString()).hashCode()) {
            case 2189731:
               if (var5.equals("Fill")) {
                  shader = this.fillShader;
                  if (this.getSet("ColorMode").getValString().equals("ClientColor")) {
                     this.fillShader.setColor(Colors.getClientColor());
                  } else if (this.getSet("ColorMode").getValString().equals("Rainbow")) {
                     this.fillShader.setColor(new Color(SkyRainbow(20, 1.0F, 0.5F).getRed(), SkyRainbow(20, 1.0F, 0.5F).getGreen(), SkyRainbow(20, 1.0F, 0.5F).getBlue()));
                  }

                  this.outlineShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.fillShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.fillShader.use();
                  this.fillShader.updateUniforms();
               }
               break;
            case 558407714:
               if (var5.equals("Outline")) {
                  shader = this.outlineShader;
                  if (this.getSet("ColorMode").getValString().equals("ClientColor")) {
                     this.outlineShader.setColor(Colors.getClientColor());
                  } else if (this.getSet("ColorMode").getValString().equals("Rainbow")) {
                     this.outlineShader.setColor(new Color(SkyRainbow(20, 1.0F, 0.5F).getRed(), SkyRainbow(20, 1.0F, 0.5F).getGreen(), SkyRainbow(20, 1.0F, 0.5F).getBlue()));
                  }

                  this.outlineShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.outlineShader.use();
                  this.outlineShader.updateUniforms();
               }
            }

            this.mc.entityRenderer.setupOverlayRendering();
            GL11.glBindTexture(3553, this.framebuffer.framebufferTexture);
            GL11.glBegin(7);
            GL11.glTexCoord2d(0.0D, 1.0D);
            GL11.glVertex2d(0.0D, 0.0D);
            GL11.glTexCoord2d(0.0D, 0.0D);
            GL11.glVertex2d(0.0D, (double)res.getScaledHeight());
            GL11.glTexCoord2d(1.0D, 0.0D);
            GL11.glVertex2d((double)res.getScaledWidth(), (double)res.getScaledHeight());
            GL11.glTexCoord2d(1.0D, 1.0D);
            GL11.glVertex2d((double)res.getScaledWidth(), 0.0D);
            GL11.glEnd();
            if (shader != null) {
               ((net.minecraft.src.MEDMEX.Utils.Shader.Shader)shader).stopUse();
            }

            GL11.glPopMatrix();
            this.mc.entityRenderer.enableLightmap(0.0D);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
            this.mc.entityRenderer.setupOverlayRendering();
         }
      }
   }

   public void onRenderParticles(Entity entity, float partialTicks) {
      if (this.isEnabled()) {
         if (this.getSet("Particles").getValBoolean()) {
            GL11.glEnable(3008);
            GL11.glPushMatrix();
            GL11.glPushAttrib(8256);
            ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            if (this.framebuffer != null) {
               this.framebuffer.framebufferClear();
               if (res.getScaledHeight() != this.h || res.getScaledWidth() != this.w || res.getScaleFactor() != this.s) {
                  this.framebuffer.deleteFramebuffer();
                  this.framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
                  this.framebuffer.framebufferClear();
               }

               this.w = res.getScaledWidth();
               this.h = res.getScaledHeight();
               this.s = res.getScaleFactor();
            } else {
               this.framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
            }

            this.framebuffer.bindFramebuffer(false);
            this.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
            this.mc.effectRenderer.renderParticles(entity, partialTicks);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.framebuffer.unbindFramebuffer();
            this.mc.entityRenderer.disableLightmap(0.0D);
            RenderHelper.disableStandardItemLighting();
            GL11.glPushMatrix();
            net.minecraft.src.MEDMEX.Utils.Shader.Shader shader = null;
            String var5;
            switch((var5 = this.getSet("Mode").getValString()).hashCode()) {
            case 2189731:
               if (var5.equals("Fill")) {
                  shader = this.fillShader;
                  if (this.getSet("ColorMode").getValString().equals("ClientColor")) {
                     this.fillShader.setColor(Colors.getClientColor());
                  } else if (this.getSet("ColorMode").getValString().equals("Rainbow")) {
                     this.fillShader.setColor(new Color(SkyRainbow(20, 1.0F, 0.5F).getRed(), SkyRainbow(20, 1.0F, 0.5F).getGreen(), SkyRainbow(20, 1.0F, 0.5F).getBlue()));
                  }

                  this.outlineShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.fillShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.fillShader.use();
                  this.fillShader.updateUniforms();
               }
               break;
            case 558407714:
               if (var5.equals("Outline")) {
                  shader = this.outlineShader;
                  if (this.getSet("ColorMode").getValString().equals("ClientColor")) {
                     this.outlineShader.setColor(Colors.getClientColor());
                  } else if (this.getSet("ColorMode").getValString().equals("Rainbow")) {
                     this.outlineShader.setColor(new Color(SkyRainbow(20, 1.0F, 0.5F).getRed(), SkyRainbow(20, 1.0F, 0.5F).getGreen(), SkyRainbow(20, 1.0F, 0.5F).getBlue()));
                  }

                  this.outlineShader.setLineWidth((float)this.getSet("LineWidth").getValDouble());
                  this.outlineShader.use();
                  this.outlineShader.updateUniforms();
               }
            }

            this.mc.entityRenderer.setupOverlayRendering();
            GL11.glBindTexture(3553, this.framebuffer.framebufferTexture);
            GL11.glBegin(7);
            GL11.glTexCoord2d(0.0D, 1.0D);
            GL11.glVertex2d(0.0D, 0.0D);
            GL11.glTexCoord2d(0.0D, 0.0D);
            GL11.glVertex2d(0.0D, (double)res.getScaledHeight());
            GL11.glTexCoord2d(1.0D, 0.0D);
            GL11.glVertex2d((double)res.getScaledWidth(), (double)res.getScaledHeight());
            GL11.glTexCoord2d(1.0D, 1.0D);
            GL11.glVertex2d((double)res.getScaledWidth(), 0.0D);
            GL11.glEnd();
            if (shader != null) {
               ((net.minecraft.src.MEDMEX.Utils.Shader.Shader)shader).stopUse();
            }

            GL11.glPopMatrix();
            this.mc.entityRenderer.enableLightmap(0.0D);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
            this.mc.entityRenderer.setupOverlayRendering();
         }
      }
   }

   public boolean isValid(Entity e) {
      if (e != this.mc.thePlayer && !e.isDead) {
         if (e instanceof EntityPlayer && this.getSet("Players").getValBoolean()) {
            return true;
         } else if (e instanceof EntityAnimal && this.getSet("Animals").getValBoolean()) {
            return true;
         } else if (e instanceof EntityMob && this.getSet("Monsters").getValBoolean()) {
            return true;
         } else if (e instanceof EntityItem && this.getSet("Items").getValBoolean()) {
            return true;
         } else if (e instanceof EntityMinecart && this.getSet("Vehicles").getValBoolean()) {
            return true;
         } else {
            return e instanceof EntityBoat && this.getSet("Vehicles").getValBoolean();
         }
      } else {
         return false;
      }
   }

   public boolean isValid(TileEntity e) {
      return e instanceof TileEntityChest && this.getSet("Chests").getValBoolean();
   }

   public Color getColor(Entity e) {
      if (e instanceof EntityPlayer) {
         return Client.friends.contains(((EntityPlayer)e).username) ? new Color(68, 242, 204) : new Color(138, 68, 242);
      } else if (e instanceof EntityAnimal) {
         return new Color(3, 252, 136);
      } else if (e instanceof EntityMob) {
         return new Color(242, 68, 68);
      } else if (!(e instanceof EntityMinecart) && !(e instanceof EntityBoat)) {
         return e instanceof EntityItem ? new Color(68, 77, 242) : null;
      } else {
         return new Color(242, 233, 68);
      }
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.attribute = " §7[§f" + this.getSet("Mode").getValString() + "§7]";
      }

   }

   public static int[] getRGB(int hex) {
      int a = hex >> 24 & 255;
      int r = hex >> 16 & 255;
      int g = hex >> 8 & 255;
      int b = hex & 255;
      return new int[]{r, g, b, a};
   }

   public static int rainbowESP(int delay) {
      float rainbowSpeed = 25.0F;
      double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay)) / 25.0D;
      rainbowState %= 360.0D;
      return Color.getHSBColor((float)(rainbowState / 360.0D), 0.9F, 1.0F).getRGB();
   }

   public static Color SkyRainbow(int counter, float bright, float st) {
      double v1 = Math.ceil((double)(System.currentTimeMillis() + (long)counter * 109L)) / 6.0D;
      return Color.getHSBColor((double)((float)((v1 %= 360.0D) / 360.0D)) < 0.5D ? -((float)(v1 / 360.0D)) : (float)(v1 / 360.0D), st, bright);
   }
}
