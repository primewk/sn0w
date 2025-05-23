package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.Packet10Flying;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class FastPortal extends Module {
   public static FastPortal instance;

   public FastPortal() {
      super("FastPortal", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre() && this.mc.thePlayer.timeInPortal > 0.0F && this.mc.thePlayer.timeUntilPortal == 0) {
         for(int i = 0; i < 20; ++i) {
            Client.sendPacket(new Packet10Flying(true));
         }
      }

   }
}
