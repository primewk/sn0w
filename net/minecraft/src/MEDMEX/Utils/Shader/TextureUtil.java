package net.minecraft.src.MEDMEX.Utils.Shader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GLAllocation;
import org.lwjgl.opengl.GL11;

public class TextureUtil {
   private static final IntBuffer dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
   public static final DynamicTexture missingTexture = new DynamicTexture(16, 16);
   public static final int[] missingTextureData;
   private static int field_147958_e;
   private static int field_147956_f;
   private static final int[] field_147957_g;
   private static final String __OBFID = "CL_00001067";

   static {
      missingTextureData = missingTexture.getTextureData();
      field_147958_e = -1;
      field_147956_f = -1;
      int var0 = -16777216;
      int var1 = -524040;
      int[] var2 = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
      int[] var3 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
      int var4 = var2.length;

      for(int var5 = 0; var5 < 16; ++var5) {
         System.arraycopy(var5 < var4 ? var2 : var3, 0, missingTextureData, 16 * var5, var4);
         System.arraycopy(var5 < var4 ? var3 : var2, 0, missingTextureData, 16 * var5 + var4, var4);
      }

      missingTexture.updateDynamicTexture();
      field_147957_g = new int[4];
   }

   public static int glGenTextures() {
      return GL11.glGenTextures();
   }

   public static void deleteTexture(int p_147942_0_) {
      GL11.glDeleteTextures(p_147942_0_);
   }

   public static int uploadTextureImage(int par0, BufferedImage par1BufferedImage) {
      return uploadTextureImageAllocate(par0, par1BufferedImage, false, false);
   }

   public static void uploadTexture(int par0, int[] par1ArrayOfInteger, int par2, int par3) {
      bindTexture(par0);
      func_147947_a(0, par1ArrayOfInteger, par2, par3, 0, 0, false, false, false);
   }

   public static int[][] func_147949_a(int p_147949_0_, int p_147949_1_, int[][] p_147949_2_) {
      int[][] var3 = new int[p_147949_0_ + 1][];
      var3[0] = p_147949_2_[0];
      if (p_147949_0_ > 0) {
         boolean var4 = false;

         int var5;
         for(var5 = 0; var5 < p_147949_2_.length; ++var5) {
            if (p_147949_2_[0][var5] >> 24 == 0) {
               var4 = true;
               break;
            }
         }

         for(var5 = 1; var5 <= p_147949_0_; ++var5) {
            if (p_147949_2_[var5] != null) {
               var3[var5] = p_147949_2_[var5];
            } else {
               int[] var6 = var3[var5 - 1];
               int[] var7 = new int[var6.length >> 2];
               int var8 = p_147949_1_ >> var5;
               int var9 = var7.length / var8;
               int var10 = var8 << 1;

               for(int var11 = 0; var11 < var8; ++var11) {
                  for(int var12 = 0; var12 < var9; ++var12) {
                     int var13 = 2 * (var11 + var12 * var10);
                     var7[var11 + var12 * var8] = func_147943_a(var6[var13 + 0], var6[var13 + 1], var6[var13 + 0 + var10], var6[var13 + 1 + var10], var4);
                  }
               }

               var3[var5] = var7;
            }
         }
      }

      return var3;
   }

   private static int func_147943_a(int p_147943_0_, int p_147943_1_, int p_147943_2_, int p_147943_3_, boolean p_147943_4_) {
      return alphaBlend(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_);
   }

   public static int alphaBlend(int c1, int c2, int c3, int c4) {
      int cx1 = alphaBlend(c1, c2);
      int cx2 = alphaBlend(c3, c4);
      int cx = alphaBlend(cx1, cx2);
      return cx;
   }

   private static int alphaBlend(int c1, int c2) {
      int a1 = (c1 & -16777216) >> 24 & 255;
      int a2 = (c2 & -16777216) >> 24 & 255;
      int ax = (a1 + a2) / 2;
      if (a1 == 0 && a2 == 0) {
         a1 = 1;
         a2 = 1;
      } else {
         if (a1 == 0) {
            c1 = c2;
            ax /= 2;
         }

         if (a2 == 0) {
            c2 = c1;
            ax /= 2;
         }
      }

      int r1 = (c1 >> 16 & 255) * a1;
      int g1 = (c1 >> 8 & 255) * a1;
      int b1 = (c1 & 255) * a1;
      int r2 = (c2 >> 16 & 255) * a2;
      int g2 = (c2 >> 8 & 255) * a2;
      int b2 = (c2 & 255) * a2;
      int rx = (r1 + r2) / (a1 + a2);
      int gx = (g1 + g2) / (a1 + a2);
      int bx = (b1 + b2) / (a1 + a2);
      return ax << 24 | rx << 16 | gx << 8 | bx;
   }

   private static int func_147944_a(int p_147944_0_, int p_147944_1_, int p_147944_2_, int p_147944_3_, int p_147944_4_) {
      float var5 = (float)Math.pow((double)((float)(p_147944_0_ >> p_147944_4_ & 255) / 255.0F), 2.2D);
      float var6 = (float)Math.pow((double)((float)(p_147944_1_ >> p_147944_4_ & 255) / 255.0F), 2.2D);
      float var7 = (float)Math.pow((double)((float)(p_147944_2_ >> p_147944_4_ & 255) / 255.0F), 2.2D);
      float var8 = (float)Math.pow((double)((float)(p_147944_3_ >> p_147944_4_ & 255) / 255.0F), 2.2D);
      float var9 = (float)Math.pow((double)(var5 + var6 + var7 + var8) * 0.25D, 0.45454545454545453D);
      return (int)((double)var9 * 255.0D);
   }

   public static void func_147955_a(int[][] p_147955_0_, int p_147955_1_, int p_147955_2_, int p_147955_3_, int p_147955_4_, boolean p_147955_5_, boolean p_147955_6_) {
      for(int var7 = 0; var7 < p_147955_0_.length; ++var7) {
         int[] var8 = p_147955_0_[var7];
         func_147947_a(var7, var8, p_147955_1_ >> var7, p_147955_2_ >> var7, p_147955_3_ >> var7, p_147955_4_ >> var7, p_147955_5_, p_147955_6_, p_147955_0_.length > 1);
      }

   }

   private static void func_147947_a(int p_147947_0_, int[] p_147947_1_, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean p_147947_7_, boolean p_147947_8_) {
      int var9 = 4194304 / p_147947_2_;
      func_147954_b(p_147947_6_, p_147947_8_);
      setTextureClamped(p_147947_7_);

      int var12;
      for(int var10 = 0; var10 < p_147947_2_ * p_147947_3_; var10 += p_147947_2_ * var12) {
         int var11 = var10 / p_147947_2_;
         var12 = Math.min(var9, p_147947_3_ - var11);
         int var13 = p_147947_2_ * var12;
         copyToBufferPos(p_147947_1_, var10, var13);
         GL11.glTexSubImage2D(3553, p_147947_0_, p_147947_4_, p_147947_5_ + var11, p_147947_2_, var12, 32993, 33639, dataBuffer);
      }

   }

   public static int uploadTextureImageAllocate(int par0, BufferedImage par1BufferedImage, boolean par2, boolean par3) {
      allocateTexture(par0, par1BufferedImage.getWidth(), par1BufferedImage.getHeight());
      return uploadTextureImageSub(par0, par1BufferedImage, 0, 0, par2, par3);
   }

   public static void allocateTexture(int par0, int par1, int par2) {
      func_147946_a(par0, 0, par1, par2, 1.0F);
   }

   public static void func_147946_a(int p_147946_0_, int p_147946_1_, int p_147946_2_, int p_147946_3_, float p_147946_4_) {
      Class monitor = TextureUtil.class;
      synchronized(monitor) {
         deleteTexture(p_147946_0_);
         bindTexture(p_147946_0_);
      }

      if (OpenGlHelper.anisotropicFilteringSupported) {
         GL11.glTexParameterf(3553, 34046, p_147946_4_);
      }

      if (p_147946_1_ > 0) {
         GL11.glTexParameteri(3553, 33085, p_147946_1_);
         GL11.glTexParameterf(3553, 33082, 0.0F);
         GL11.glTexParameterf(3553, 33083, (float)p_147946_1_);
         GL11.glTexParameterf(3553, 34049, 0.0F);
      }

      for(int var5 = 0; var5 <= p_147946_1_; ++var5) {
         GL11.glTexImage2D(3553, var5, 6408, p_147946_2_ >> var5, p_147946_3_ >> var5, 0, 32993, 33639, (IntBuffer)null);
      }

   }

   public static int uploadTextureImageSub(int par0, BufferedImage par1BufferedImage, int par2, int par3, boolean par4, boolean par5) {
      bindTexture(par0);
      uploadTextureImageSubImpl(par1BufferedImage, par2, par3, par4, par5);
      return par0;
   }

   private static void uploadTextureImageSubImpl(BufferedImage par0BufferedImage, int par1, int par2, boolean par3, boolean par4) {
      int var5 = par0BufferedImage.getWidth();
      int var6 = par0BufferedImage.getHeight();
      int var7 = 4194304 / var5;
      int[] var8 = new int[var7 * var5];
      func_147951_b(par3);
      setTextureClamped(par4);

      for(int var9 = 0; var9 < var5 * var6; var9 += var5 * var7) {
         int var10 = var9 / var5;
         int var11 = Math.min(var7, var6 - var10);
         int var12 = var5 * var11;
         par0BufferedImage.getRGB(0, var10, var5, var11, var8, 0, var5);
         copyToBuffer(var8, var12);
         GL11.glTexSubImage2D(3553, 0, par1, par2 + var10, var5, var11, 32993, 33639, dataBuffer);
      }

   }

   private static void setTextureClamped(boolean par0) {
      if (par0) {
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
      } else {
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10243, 10497);
      }

   }

   private static void func_147951_b(boolean p_147951_0_) {
      func_147954_b(p_147951_0_, false);
   }

   public static void func_147950_a(boolean p_147950_0_, boolean p_147950_1_) {
      field_147958_e = GL11.glGetTexParameteri(3553, 10241);
      field_147956_f = GL11.glGetTexParameteri(3553, 10240);
      func_147954_b(p_147950_0_, p_147950_1_);
   }

   public static void func_147945_b() {
      if (field_147958_e >= 0 && field_147956_f >= 0) {
         func_147952_b(field_147958_e, field_147956_f);
         field_147958_e = -1;
         field_147956_f = -1;
      }

   }

   private static void func_147952_b(int p_147952_0_, int p_147952_1_) {
      GL11.glTexParameteri(3553, 10241, p_147952_0_);
      GL11.glTexParameteri(3553, 10240, p_147952_1_);
   }

   private static void func_147954_b(boolean p_147954_0_, boolean p_147954_1_) {
      if (p_147954_0_) {
         GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9987 : 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
      } else {
         GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9986 : 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
      }

   }

   private static void copyToBuffer(int[] par0ArrayOfInteger, int par1) {
      copyToBufferPos(par0ArrayOfInteger, 0, par1);
   }

   private static void copyToBufferPos(int[] par0ArrayOfInteger, int par1, int par2) {
      int[] var3 = par0ArrayOfInteger;
      if (Minecraft.theMinecraft.gameSettings.anaglyph) {
         var3 = updateAnaglyph(par0ArrayOfInteger);
      }

      dataBuffer.clear();
      dataBuffer.put(var3, par1, par2);
      dataBuffer.position(0).limit(par2);
   }

   public static void bindTexture(int par0) {
      GL11.glBindTexture(3553, par0);
   }

   public static int[] readImageData(IResourceManager par0ResourceManager, ResourceLocation par1ResourceLocation) throws IOException {
      BufferedImage var2 = ImageIO.read(par0ResourceManager.getResource(par1ResourceLocation).getInputStream());
      if (var2 == null) {
         return null;
      } else {
         int var3 = var2.getWidth();
         int var4 = var2.getHeight();
         int[] var5 = new int[var3 * var4];
         var2.getRGB(0, 0, var3, var4, var5, 0, var3);
         return var5;
      }
   }

   public static int[] updateAnaglyph(int[] par0ArrayOfInteger) {
      int[] var1 = new int[par0ArrayOfInteger.length];

      for(int var2 = 0; var2 < par0ArrayOfInteger.length; ++var2) {
         int var3 = par0ArrayOfInteger[var2] >> 24 & 255;
         int var4 = par0ArrayOfInteger[var2] >> 16 & 255;
         int var5 = par0ArrayOfInteger[var2] >> 8 & 255;
         int var6 = par0ArrayOfInteger[var2] & 255;
         int var7 = (var4 * 30 + var5 * 59 + var6 * 11) / 100;
         int var8 = (var4 * 30 + var5 * 70) / 100;
         int var9 = (var4 * 30 + var6 * 70) / 100;
         var1[var2] = var3 << 24 | var7 << 16 | var8 << 8 | var9;
      }

      return var1;
   }

   public static int[] func_147948_a(int[] p_147948_0_, int p_147948_1_, int p_147948_2_, int p_147948_3_) {
      int var4 = p_147948_1_ + 2 * p_147948_3_;

      int var5;
      int var6;
      for(var5 = p_147948_2_ - 1; var5 >= 0; --var5) {
         var6 = var5 * p_147948_1_;
         int var7 = p_147948_3_ + (var5 + p_147948_3_) * var4;

         int var8;
         for(var8 = 0; var8 < p_147948_3_; var8 += p_147948_1_) {
            int var9 = Math.min(p_147948_1_, p_147948_3_ - var8);
            System.arraycopy(p_147948_0_, var6 + p_147948_1_ - var9, p_147948_0_, var7 - var8 - var9, var9);
         }

         System.arraycopy(p_147948_0_, var6, p_147948_0_, var7, p_147948_1_);

         for(var8 = 0; var8 < p_147948_3_; var8 += p_147948_1_) {
            System.arraycopy(p_147948_0_, var6, p_147948_0_, var7 + p_147948_1_ + var8, Math.min(p_147948_1_, p_147948_3_ - var8));
         }
      }

      for(var5 = 0; var5 < p_147948_3_; var5 += p_147948_2_) {
         var6 = Math.min(p_147948_2_, p_147948_3_ - var5);
         System.arraycopy(p_147948_0_, (p_147948_3_ + p_147948_2_ - var6) * var4, p_147948_0_, (p_147948_3_ - var5 - var6) * var4, var4 * var6);
      }

      for(var5 = 0; var5 < p_147948_3_; var5 += p_147948_2_) {
         var6 = Math.min(p_147948_2_, p_147948_3_ - var5);
         System.arraycopy(p_147948_0_, p_147948_3_ * var4, p_147948_0_, (p_147948_2_ + p_147948_3_ + var5) * var4, var4 * var6);
      }

      return p_147948_0_;
   }

   public static void func_147953_a(int[] p_147953_0_, int p_147953_1_, int p_147953_2_) {
      int[] var3 = new int[p_147953_1_];
      int var4 = p_147953_2_ / 2;

      for(int var5 = 0; var5 < var4; ++var5) {
         System.arraycopy(p_147953_0_, var5 * p_147953_1_, var3, 0, p_147953_1_);
         System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - var5) * p_147953_1_, p_147953_0_, var5 * p_147953_1_, p_147953_1_);
         System.arraycopy(var3, 0, p_147953_0_, (p_147953_2_ - 1 - var5) * p_147953_1_, p_147953_1_);
      }

   }
}
