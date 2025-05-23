package net.minecraft.src.MEDMEX.Modules.Combat;

import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Modules.Client.AutoReconnect;
import net.minecraft.src.de.Hero.settings.Setting;

public class CombatLog extends Module {
   public static CombatLog instance;

   public CombatLog() {
      super("CombatLog", 0, Module.Category.COMBAT);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Log Health", this, 6.0D, 1.0D, 20.0D, true));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.attribute = " §7[§f" + (float)Client.settingsmanager.getSettingByName("Log Health").getValDouble() / 2.0F + "§7]";
         if ((double)this.mc.thePlayer.health <= Client.settingsmanager.getSettingByName("Log Health").getValDouble()) {
            if (AutoReconnect.instance.isEnabled()) {
               AutoReconnect.instance.toggle();
            }

            this.mc.theWorld.sendQuittingDisconnectingPacket();
            if (this.isEnabled()) {
               this.toggle();
            }
         }
      }

   }
}
