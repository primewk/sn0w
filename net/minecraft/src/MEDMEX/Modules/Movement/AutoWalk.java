package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class AutoWalk extends Module {
   public AutoWalk() {
      super("AutoWalk", 0, Module.Category.MOVEMENT);
   }

   public void onDisable() {
      this.mc.thePlayer.movementInput.checkKeyForMovementInput(this.mc.gameSettings.keyBindForward.keyCode, false);
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.mc.thePlayer.moveEntityWithHeading(0.0F, 1.0F);
      }

   }
}
