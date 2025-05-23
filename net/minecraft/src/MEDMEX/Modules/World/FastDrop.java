package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.Timer;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.input.Mouse;

public class FastDrop extends Module {
   public static Timer timer = new Timer();

   public FastDrop() {
      super("FastDrop", 0, Module.Category.WORLD);
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Drop Speed", this, 1.0D, 1.0D, 1000.0D, true));
      Client.settingsmanager.rSetting(new Setting("Insta 130", this, false));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         int i;
         if (Client.settingsmanager.getSettingByName("Insta 130").getValBoolean()) {
            if (Mouse.isButtonDown(1) && (this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof GuiContainer) && timer.hasTimeElapsed(1000L, true)) {
               for(i = 0; i < 129; ++i) {
                  this.mc.currentScreen.handleMouseInput();
               }
            }
         } else if (Mouse.isButtonDown(1) && (this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof GuiContainer)) {
            for(i = 0; (double)i < Client.settingsmanager.getSettingByName("Drop Speed").getValDouble(); ++i) {
               this.mc.currentScreen.handleMouseInput();
            }
         }
      }

   }
}
