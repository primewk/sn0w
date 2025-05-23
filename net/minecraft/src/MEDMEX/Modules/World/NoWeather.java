package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class NoWeather extends Module {
   public static NoWeather instance;

   public NoWeather() {
      super("NoWeather", 25, Module.Category.WORLD);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (this.mc.theWorld.thunderingStrength > 0.0F) {
            this.attribute = " §7[§fThunder§7]";
         }

         if (this.mc.theWorld.rainingStrength > 0.0F) {
            this.attribute = " §7[§fRain§7]";
         }

         if (this.mc.theWorld.rainingStrength == 0.0F && this.mc.theWorld.thunderingStrength == 0.0F) {
            this.attribute = " §7[§fClear§7]";
         }
      }

   }
}
