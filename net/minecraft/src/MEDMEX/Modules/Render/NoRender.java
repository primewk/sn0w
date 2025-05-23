package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class NoRender extends Module {
   public static NoRender instance;

   public NoRender() {
      super("NoRender", 0, Module.Category.RENDER);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("NoRender Players", this, true));
      Client.settingsmanager.rSetting(new Setting("NoRender Items", this, true));
      Client.settingsmanager.rSetting(new Setting("NoRender Mobs", this, false));
      Client.settingsmanager.rSetting(new Setting("NoRender Animals", this, false));
      Client.settingsmanager.rSetting(new Setting("NoRender Vehicles", this, false));
   }
}
