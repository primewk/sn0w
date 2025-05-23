package net.minecraft.src.MEDMEX.Modules.Client;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
   boolean preventloop = false;
   public static MCF instance;

   public MCF() {
      super("MCF", 0, Module.Category.CLIENT);
      instance = this;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (Mouse.isButtonDown(2)) {
            if (!this.preventloop && this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
               EntityPlayer playerhit = (EntityPlayer)this.mc.objectMouseOver.entityHit;
               if (Client.friends.contains(playerhit.username)) {
                  Client.friends.remove(playerhit.username);
                  Client.addChatMessage("Removed " + playerhit.username + " from friendslist");
               } else {
                  Client.friends.add(playerhit.username);
                  Client.addChatMessage("Added " + playerhit.username + " to friendslist");
               }

               this.preventloop = true;
            }
         } else {
            this.preventloop = false;
         }
      }

   }
}
