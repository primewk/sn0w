package net.minecraft.src.MEDMEX.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Render.Waypoints;

public class ConfigWaypoints {
   static String filedir;
   static String configfiledir;
   public static String input;

   static {
      filedir = Client.name + "\\";
      configfiledir = Client.name + "\\waypoints";
      input = "waypoints";
   }

   public static void load() {
      String config = Client.name + "\\" + input;
      createFiles();

      try {
         System.out.println(config);
         String waypoint = "";
         BufferedReader reader = new BufferedReader(new FileReader(new File(config)));

         while((waypoint = reader.readLine()) != null) {
            String sX = waypoint.split(":")[0];
            String sY = waypoint.split(":")[1];
            String sZ = waypoint.split(":")[2];
            Double X = Double.valueOf(sX);
            Double Y = Double.valueOf(sY);
            Double Z = Double.valueOf(sZ);
            Vec3D pos = new Vec3D(X, Y, Z);
            String name = waypoint.split(":")[3];
            String server = waypoint.split(":")[4];
            String sDim = waypoint.split(":")[5];
            Integer dim = Integer.valueOf(sDim);
            Waypoints.instance.add(pos, name, server, dim);
         }

         reader.close();
      } catch (IOException var14) {
         var14.printStackTrace();
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
               ArrayList<Waypoints.WayPoint> wp = Waypoints.instance.wayPointList;
               System.out.println(wp.size());

               for(int i = 0; i < wp.size(); ++i) {
                  writer.write(((Waypoints.WayPoint)wp.get(i)).pos.xCoord + ":" + ((Waypoints.WayPoint)wp.get(i)).pos.yCoord + ":" + ((Waypoints.WayPoint)wp.get(i)).pos.zCoord + ":" + ((Waypoints.WayPoint)wp.get(i)).name + ":" + ((Waypoints.WayPoint)wp.get(i)).server + ":" + ((Waypoints.WayPoint)wp.get(i)).dim + "\n");
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
