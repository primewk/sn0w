package net.minecraft.src.MEDMEX.serverman;

import java.util.ArrayList;

public class ServerManager {
   public static Server lastServer;
   public static ArrayList<Server> registry = new ArrayList();

   public ArrayList<Server> getRegistry() {
      return registry;
   }

   public void setLastServer(Server server) {
      lastServer = server;
   }
}
