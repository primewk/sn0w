package net.minecraft.src.MEDMEX.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;

public class StorageUtils {
   public static NBTTagCompound ClientConfig = null;

   public static void loadConfig() {
      byte[] config = loadLocalStorage();
      if (config != null) {
         try {
            ClientConfig = CompressedStreamTools.readUncompressed(config);
         } catch (IOException var2) {
         }
      }

      if (ClientConfig == null) {
         ClientConfig = new NBTTagCompound();
      }

   }

   public static void saveConfig() {
      try {
         saveLocalStorage("config", CompressedStreamTools.writeUncompressed(ClientConfig));
      } catch (IOException var1) {
      }

   }

   public static final void saveLocalStorage(String key, byte[] data) {
      try {
         FileOutputStream f = new FileOutputStream(new File("$now.dat"));
         f.write(data);
         f.close();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public static final byte[] loadLocalStorage() {
      try {
         File f = new File("$now.dat");
         byte[] b = new byte[(int)f.length()];
         FileInputStream s = new FileInputStream(f);
         s.read(b);
         s.close();
         return b;
      } catch (IOException var3) {
         return null;
      }
   }
}
