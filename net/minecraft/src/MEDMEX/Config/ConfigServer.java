package net.minecraft.src.MEDMEX.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.serverman.Server;
import net.minecraft.src.MEDMEX.serverman.ServerManager;

public class ConfigServer {
   static String filedir;
   static String configfiledir;
   public static String input;

   static {
      filedir = Client.name + "\\";
      configfiledir = Client.name + "\\servers";
      input = "servers";
   }

   public static void load() {
      String config = Client.name + "\\" + input;
      createFiles();

      try {
         System.out.println(config);
         String account = "";
         BufferedReader reader = new BufferedReader(new FileReader(new File(config)));

         while((account = reader.readLine()) != null) {
            ServerManager.registry.add(new Server(account));
         }

         reader.close();
      } catch (IOException var3) {
         var3.printStackTrace();
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
               for(int i = 0; i < ServerManager.registry.size(); ++i) {
                  writer.write(((Server)ServerManager.registry.get(i)).getUsername() + "\n");
               }

               writer.flush();
               writer.close();
            } finally {
               if (writer != null) {
                  writer.close();
               }

            }
         } catch (Throwable var12) {
            if (var1 == null) {
               var1 = var12;
            } else if (var1 != var12) {
               var1.addSuppressed(var12);
            }

            throw var1;
         }
      } catch (IOException var13) {
         System.out.println(var13.getMessage());
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
