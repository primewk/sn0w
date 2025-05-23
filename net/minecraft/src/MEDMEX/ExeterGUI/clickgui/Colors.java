package net.minecraft.src.MEDMEX.ExeterGUI.clickgui;

import java.awt.Color;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MEDMEX.Modules.Client.ClickGUI;

public class Colors {
   public static Color getClientColor() {
      int red = (int)ClickGUI.instance.getSet("Red").getValDouble();
      int green = (int)ClickGUI.instance.getSet("Green").getValDouble();
      int blue = (int)ClickGUI.instance.getSet("Blue").getValDouble();
      Color c = new Color(red, green, blue);
      return c;
   }

   public static int getClientColorCustomAlpha(int alpha) {
      int red = (int)ClickGUI.instance.getSet("Red").getValDouble();
      int green = (int)ClickGUI.instance.getSet("Green").getValDouble();
      int blue = (int)ClickGUI.instance.getSet("Blue").getValDouble();
      Color c = new Color(red, green, blue);
      Color color = setAlpha(c, alpha);
      return color.getRGB();
   }

   public static final Color setAlpha(Color color, int alpha) {
      alpha = MathHelper.clamp_int(alpha, 0, 255);
      return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
   }
}
