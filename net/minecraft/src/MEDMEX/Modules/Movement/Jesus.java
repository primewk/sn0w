package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.Material;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Jesus extends Module {
   public static Jesus instance;

   public Jesus() {
      super("Jesus", 0, Module.Category.MOVEMENT);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre() && (this.mc.thePlayer.isInWater() || this.mc.thePlayer.isInsideOfMaterial(Material.lava))) {
         this.mc.thePlayer.motionY = 0.6000000238418579D;
      }

   }
}
