package net.minecraft.src.MEDMEX.Modules.World;

import java.util.List;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class VisualRange extends Module {
   public static VisualRange instance;

   public VisualRange() {
      super("VisualRange", 0, Module.Category.WORLD);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         List<EntityPlayer> p = this.mc.theWorld.playerEntities;
         p.remove(this.mc.thePlayer);
         int players = p.size();
         this.attribute = " §7[§f" + String.valueOf(players) + "§7]";
      }

   }
}
