package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Step extends Module {
   public Step() {
      super("Step", 0, Module.Category.MOVEMENT);
   }

   public void onDisable() {
      this.mc.thePlayer.stepHeight = 0.5F;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.mc.thePlayer.stepHeight = 1.0F;
      }

   }
}
