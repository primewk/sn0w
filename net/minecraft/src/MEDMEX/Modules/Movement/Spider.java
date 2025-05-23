package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Spider extends Module {
   public static Spider instance;

   public Spider() {
      super("Spider", 0, Module.Category.MOVEMENT);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate) {
         e.isPre();
      }

   }
}
