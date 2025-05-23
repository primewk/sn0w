package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Fly extends Module {
   net.minecraft.src.MEDMEX.Utils.Timer timer = new net.minecraft.src.MEDMEX.Utils.Timer();

   public Fly() {
      super("Fly", 0, Module.Category.MOVEMENT);
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.mc.thePlayer.motionY = 0.0D;
      }

   }
}
