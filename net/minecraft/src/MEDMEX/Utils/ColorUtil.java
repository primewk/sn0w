package net.minecraft.src.MEDMEX.Utils;

import java.awt.Color;

public class ColorUtil {
   static int red = 0;
   static boolean flip = false;

   public static Color getSnow(int speed) {
      int green = 255;
      int blue = 255;
      if (!flip) {
         red += 1 + speed;
      }

      if (flip) {
         red = red - 1 - speed;
      }

      if (red >= 255) {
         red = 255;
         flip = true;
      }

      if (red <= 0) {
         red = 0;
         flip = false;
      }

      return new Color(red, green, blue);
   }

   public static int getRainbow(float seconds, float saturation, float brightness) {
      float hue = (float)(System.currentTimeMillis() % 4000L * 1000L) / 4000000.0F;
      int color = Color.HSBtoRGB(hue, saturation, brightness);
      return color;
   }

   public static int getRainbow(float seconds, float saturation, float brightness, long index) {
      float hue = (float)((System.currentTimeMillis() + index) % (long)((int)(seconds * 1000.0F)) * 1000L) / (seconds * 1000.0F * 1000.0F);
      int color = Color.HSBtoRGB(hue, saturation, brightness);
      return color;
   }

   public static int astolfoColorsDraw(int yOffset, int yTotal) {
      return astolfoColorsDraw(yOffset, yTotal, 2000.0F);
   }

   public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
      float hue;
      for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
      }

      hue /= speed;
      if ((double)hue > 0.5D) {
         hue = 0.5F - (hue - 0.5F);
      }

      hue += 0.5F;
      return Color.HSBtoRGB(hue, 0.1F, 1.0F);
   }
}
