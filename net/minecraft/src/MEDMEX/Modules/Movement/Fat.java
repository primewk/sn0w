package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Fat extends Module {
   public Fat() {
      super("Fat", 0, Module.Category.MOVEMENT);
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre() && this.mc.thePlayer.fallDistance > 1.0F) {
         this.mc.thePlayer.motionY = -1.0D;
      }

   }
}
