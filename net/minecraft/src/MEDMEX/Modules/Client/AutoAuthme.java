package net.minecraft.src.MEDMEX.Modules.Client;

import java.util.Iterator;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.Timer;

public class AutoAuthme extends Module {
   public static AutoAuthme instance;
   Timer timer = new Timer();

   public AutoAuthme() {
      super("AutoAuthme", 0, Module.Category.CLIENT);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet3Chat) {
         Packet3Chat p = (Packet3Chat)e.getPacket();
         String pass;
         if (p.message.equals("§cPlease login with \"/login password\"")) {
            Iterator var4 = Client.authme.iterator();

            while(var4.hasNext()) {
               pass = (String)var4.next();
               if (pass.contains(this.mc.thePlayer.username)) {
                  this.mc.thePlayer.sendChatMessage("/login " + pass.split(":")[1]);
                  Client.addChatMessage("Logged in automatically with AutoAuthme");
               }
            }
         }

         if (p.message.startsWith("/login")) {
            pass = p.message.split(" ")[1];
            if (!Client.authme.contains(this.mc.thePlayer.username + ":" + pass)) {
               Client.authme.add(this.mc.thePlayer.username + ":" + pass);
               Client.addChatMessage("Added new entry to AutoAuthme");
            }
         }
      }

   }
}
