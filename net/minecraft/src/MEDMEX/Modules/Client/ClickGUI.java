package net.minecraft.src.MEDMEX.Modules.Client;

import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.ClickGui;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class ClickGUI extends Module {
   public static ClickGUI instance;

   public ClickGUI() {
      super("ClickGUI", 54, Module.Category.CLIENT);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Red", this, 255.0D, 0.0D, 255.0D, true));
      Client.settingsmanager.rSetting(new Setting("Green", this, 0.0D, 0.0D, 255.0D, true));
      Client.settingsmanager.rSetting(new Setting("Blue", this, 0.0D, 0.0D, 255.0D, true));
   }

   public void onEnable() {
      super.onEnable();
      this.mc.displayGuiScreen(ClickGui.getClickGui());
      this.toggle();
   }
}
