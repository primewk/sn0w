package net.minecraft.src.de.Hero.settings;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;

public class SettingsManager {
   private ArrayList<Setting> settings = new ArrayList();

   public void rSetting(Setting in) {
      this.settings.add(in);
   }

   public ArrayList<Setting> getSettings() {
      return this.settings;
   }

   public ArrayList<Setting> getSettingsByMod(Module mod) {
      ArrayList<Setting> out = new ArrayList();
      Iterator var4 = this.getSettings().iterator();

      while(var4.hasNext()) {
         Setting s = (Setting)var4.next();
         if (s.getParentMod().equals(mod)) {
            out.add(s);
         }
      }

      if (out.isEmpty()) {
         return null;
      } else {
         return out;
      }
   }

   public Setting getSettingByName(String name) {
      Iterator var3 = this.getSettings().iterator();

      while(var3.hasNext()) {
         Setting set = (Setting)var3.next();
         if (set.getName().equalsIgnoreCase(name)) {
            return set;
         }
      }

      System.err.println("[" + Client.name + "] Error Setting NOT found: '" + name + "'!");
      return null;
   }

   public Setting getSettingByNameAndMod(String name, Module m) {
      Iterator var4 = this.getSettingsByMod(m).iterator();

      while(var4.hasNext()) {
         Setting set = (Setting)var4.next();
         if (set.getName().equalsIgnoreCase(name)) {
            return set;
         }
      }

      System.err.println("[" + Client.name + "] Error Setting NOT found: '" + name + "'!");
      return null;
   }
}
