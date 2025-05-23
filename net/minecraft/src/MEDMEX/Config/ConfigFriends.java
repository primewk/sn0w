package net.minecraft.src.MEDMEX.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.src.MEDMEX.Client;

public class ConfigFriends {
   static String filedir;
   static String configfiledir;
   public static String input;

   static {
      filedir = Client.name + "\\";
      configfiledir = Client.name + "\\friends";
      input = "friends";
   }

   public static void load() {
      String config = Client.name + "\\" + input;
      createFiles();

      try {
         System.out.println(config);
         String friend = "";
         BufferedReader reader = new BufferedReader(new FileReader(new File(config)));

         while((friend = reader.readLine()) != null) {
            Client.friends.add(friend);
         }

         reader.close();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static void save() {
      String config = Client.name + "\\" + input;
      createFiles();

      try {
         Throwable var1 = null;
         Object var2 = null;

         try {
            FileWriter writer = new FileWriter(new File(config), false);

            try {
               Iterator var5 = Client.friends.iterator();

               while(var5.hasNext()) {
                  String f = (String)var5.next();
                  writer.write(f + "\n");
               }

               writer.flush();
               writer.close();
            } finally {
               if (writer != null) {
                  writer.close();
               }

            }
         } catch (Throwable var13) {
            if (var1 == null) {
               var1 = var13;
            } else if (var1 != var13) {
               var1.addSuppressed(var13);
            }

            throw var1;
         }
      } catch (IOException var14) {
         System.out.println(var14.getMessage());
      }

   }

   public static void createFiles() {
      if (!(new File(filedir)).exists()) {
         (new File(filedir)).mkdir();
      }

      if (!(new File(configfiledir)).exists()) {
         try {
            (new File(configfiledir)).createNewFile();
         } catch (IOException var1) {
            var1.printStackTrace();
         }
      }

   }
}
