package net.minecraft.src;

public class MathHelper {
   private static float[] SIN_TABLE = new float[65536];

   static {
      for(int var0 = 0; var0 < 65536; ++var0) {
         SIN_TABLE[var0] = (float)Math.sin((double)var0 * 3.141592653589793D * 2.0D / 65536.0D);
      }

   }

   public static final float sin(float var0) {
      return SIN_TABLE[(int)(var0 * 10430.378F) & '\uffff'];
   }

   public static final float cos(float var0) {
      return SIN_TABLE[(int)(var0 * 10430.378F + 16384.0F) & '\uffff'];
   }

   public static final float sqrt_float(float var0) {
      return (float)Math.sqrt((double)var0);
   }

   public static final float sqrt_double(double var0) {
      return (float)Math.sqrt(var0);
   }

   public static double clamp_double(double p_151237_0_, double p_151237_2_, double p_151237_4_) {
      return p_151237_0_ < p_151237_2_ ? p_151237_2_ : (p_151237_0_ > p_151237_4_ ? p_151237_4_ : p_151237_0_);
   }

   public static int clamp_int(int par0, int par1, int par2) {
      return par0 < par1 ? par1 : (par0 > par2 ? par2 : par0);
   }

   public static float wrapDegrees(float value) {
      value %= 360.0F;
      if (value >= 180.0F) {
         value -= 360.0F;
      }

      if (value < -180.0F) {
         value += 360.0F;
      }

      return value;
   }

   public static int floor_float(float var0) {
      int var1 = (int)var0;
      return var0 < (float)var1 ? var1 - 1 : var1;
   }

   public static int floor_double(double var0) {
      int var2 = (int)var0;
      return var0 < (double)var2 ? var2 - 1 : var2;
   }

   public static float abs(float var0) {
      return var0 >= 0.0F ? var0 : -var0;
   }

   public static double abs_max(double var0, double var2) {
      if (var0 < 0.0D) {
         var0 = -var0;
      }

      if (var2 < 0.0D) {
         var2 = -var2;
      }

      return var0 > var2 ? var0 : var2;
   }

   public static int bucketInt(int var0, int var1) {
      return var0 < 0 ? -((-var0 - 1) / var1) - 1 : var0 / var1;
   }

   public static boolean stringNullOrLengthZero(String var0) {
      return var0 == null || var0.length() == 0;
   }
}
