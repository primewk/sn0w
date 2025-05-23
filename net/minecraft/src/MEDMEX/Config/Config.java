package net.minecraft.src.MEDMEX.Config;

import java.util.Iterator;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.StorageUtils;
import net.minecraft.src.de.Hero.settings.Setting;

public class Config {
   public static void loadConfig() {
      NBTTagCompound cfg = StorageUtils.ClientConfig;
      if (cfg != null) {
         NBTTagList var1 = cfg.getTagList("Modules");

         for(int i = 0; i < var1.tagCount(); ++i) {
            NBTTagCompound tags = (NBTTagCompound)var1.tagAt(i);
            Module mod = Client.getModuleByName(tags.getString("Name"));
            if (tags.getBoolean("Enabled")) {
               mod.toggle();
            }

            mod.keyCode.setKeyCode(tags.getInteger("Key"));
            if (!tags.getBoolean("Drawn")) {
               Client.drawn.add(mod);
            }

            if (Client.settingsmanager.getSettingsByMod(mod) != null) {
               Iterator var6 = Client.settingsmanager.getSettingsByMod(mod).iterator();

               while(var6.hasNext()) {
                  Setting s = (Setting)var6.next();
                  if (s.isCheck() && tags.getString(s.getName()) != null) {
                     s.setValBoolean(tags.getBoolean(s.getName()));
                  }

                  if (s.isSlider() && tags.getString(s.getName()) != null) {
                     s.setValDouble(tags.getDouble(s.getName()));
                  }

                  if (s.isCombo() && tags.getString(s.getName()) != null) {
                     s.setValString(tags.getString(s.getName()));
                  }
               }
            }
         }

      }
   }

   public static void saveConfig() {
      NBTTagList var1 = new NBTTagList();

      NBTTagCompound module;
      for(Iterator var2 = Client.modules.iterator(); var2.hasNext(); var1.appendTag(module)) {
         Module m = (Module)var2.next();
         module = new NBTTagCompound();
         module.setString("Name", m.name);
         module.setInteger("Key", m.getKey());
         module.setBoolean("Enabled", m.isEnabled());
         module.setBoolean("Drawn", !Client.drawn.contains(m));
         if (Client.settingsmanager.getSettingsByMod(m) != null) {
            Iterator var5 = Client.settingsmanager.getSettingsByMod(m).iterator();

            while(var5.hasNext()) {
               Setting s = (Setting)var5.next();
               if (s.isCheck()) {
                  module.setBoolean(s.getName(), s.getValBoolean());
               }

               if (s.isSlider()) {
                  module.setDouble(s.getName(), s.getValDouble());
               }

               if (s.isCombo()) {
                  module.setString(s.getName(), s.getValString());
               }
            }
         }
      }

      NBTTagCompound var5 = StorageUtils.ClientConfig;
      var5.setTag("Modules", var1);
      StorageUtils.saveConfig();
   }
}
