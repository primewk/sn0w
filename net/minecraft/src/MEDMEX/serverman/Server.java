package net.minecraft.src.MEDMEX.serverman;

public final class Server {
   private final String server;

   public Server(String server) {
      this.server = server;
   }

   public String getUsername() {
      return this.server;
   }
}
