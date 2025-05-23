package net.minecraft.src.MEDMEX.Modules.World;

import java.util.Iterator;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class ItemDeleter extends Module {
   public static ItemDeleter instance;

   public ItemDeleter() {
      super("ItemDeleter", 25, Module.Category.WORLD);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         Iterator var3 = this.mc.theWorld.loadedEntityList.iterator();

         while(var3.hasNext()) {
            Entity ent = (Entity)var3.next();
            if (ent instanceof EntityItem) {
               ent.setEntityDead();
            }
         }
      }

   }
}
