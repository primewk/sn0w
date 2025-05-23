package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class NoClip extends Module {
   public NoClip() {
      super("NoClip", 0, Module.Category.MOVEMENT);
   }

   public void onDisable() {
      this.mc.thePlayer.noClip = false;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.mc.thePlayer.noClip = true;
      }

   }
}
