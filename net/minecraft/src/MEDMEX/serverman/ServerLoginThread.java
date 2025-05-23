package net.minecraft.src.MEDMEX.serverman;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Session;

public final class ServerLoginThread extends Thread {
   private String status;
   private final String server;
   private Minecraft mc;

   public ServerLoginThread(String server) {
      super("Alt Login Thread");
      this.mc = Minecraft.theMinecraft;
      this.server = server;
      this.status = "§aAlt logged in (" + server + ")";
   }

   private void createSession(String server) {
      Session.username = server;
   }

   public String getStatus() {
      return this.status;
   }

   public void run() {
      Connect.connectToServer(this.server, 25565);
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
