package net.minecraft.src.MEDMEX.ExeterGUI.clickgui;

import java.awt.Color;
import java.awt.Rectangle;
import net.minecraft.src.Gui;
import org.lwjgl.opengl.GL11;

public class RenderMethods {
   public static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   public static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void glColor(int hex) {
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
      float red = 0.003921569F * (float)redRGB;
      float green = 0.003921569F * (float)greenRGB;
      float blue = 0.003921569F * (float)blueRGB;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void glColor(Color color) {
      GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
   }

   public static void drawRect(float x, float y, float x1, float y1, int color) {
      enableGL2D();
      glColor(color);
      drawRect(x, y, x1, y1);
      disableGL2D();
   }

   public static void drawRect(Rectangle rectangle, int color) {
      drawRect((float)rectangle.x, (float)rectangle.y, (float)(rectangle.x + rectangle.width), (float)(rectangle.y + rectangle.height), color);
   }

   public static void drawRect(float x, float y, float x1, float y1, float r, float g, float b, float a) {
      enableGL2D();
      GL11.glColor4f(r, g, b, a);
      drawRect(x, y, x1, y1);
      disableGL2D();
   }

   public static void drawRect(float x, float y, float x1, float y1) {
      GL11.glBegin(7);
      GL11.glVertex2f(x, y1);
      GL11.glVertex2f(x1, y1);
      GL11.glVertex2f(x1, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
   }

   public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
      enableGL2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      glColor(topColor);
      GL11.glVertex2f(x, y1);
      GL11.glVertex2f(x1, y1);
      glColor(bottomColor);
      GL11.glVertex2f(x1, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      disableGL2D();
   }

   public static void drawModalRect(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
      Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }
}
