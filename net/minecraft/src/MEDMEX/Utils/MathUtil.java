package net.minecraft.src.MEDMEX.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
   public static double round(double value, int places) {
      return places < 0 ? value : (new BigDecimal(value)).setScale(places, RoundingMode.HALF_UP).doubleValue();
   }

   public static float round(float value, int places) {
      return places < 0 ? value : (new BigDecimal((double)value)).setScale(places, RoundingMode.HALF_UP).floatValue();
   }
}
