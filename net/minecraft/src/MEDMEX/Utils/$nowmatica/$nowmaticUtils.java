package net.minecraft.src.MEDMEX.Utils.$nowmatica;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.MEDMEX.Client;

public class $nowmaticUtils {
   public static NBTTagCompound readTagCompoundFromFile(File file) throws IOException {
      try {
         return CompressedStreamTools.readCompressed(new FileInputStream(file));
      } catch (Exception var2) {
         Client.addChatMessage("File doesn't exist.");
         return CompressedStreamTools.read(file);
      }
   }
}
