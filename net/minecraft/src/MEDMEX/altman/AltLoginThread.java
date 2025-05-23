package net.minecraft.src.MEDMEX.altman;

import net.minecraft.client.Minecraft;

public final class AltLoginThread extends Thread {
   private String status;
   private final String username;
   private Minecraft mc;

   public AltLoginThread(String username) {
      super("Alt Login Thread");
      this.mc = Minecraft.theMinecraft;
      this.username = username;
      this.status = "§aAlt logged in (" + username + ")";
   }

   private void createSession(String username) {
      username = username;
   }

   public String getStatus() {
      return this.status;
   }

   public void run() {
      username = this.username;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
