package net.minecraft.src.MEDMEX.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;

public class ModuleUtils {
   public static Module getModuleByName(String name) {
      Iterator var2 = Client.modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (m.name.equalsIgnoreCase(name)) {
            return m;
         }
      }

      return null;
   }

   public static ArrayList<Module> getModulesByCategory(Module.Category c) {
      ArrayList<Module> mods = new ArrayList();
      Iterator var3 = Client.modules.iterator();

      while(var3.hasNext()) {
         Module m = (Module)var3.next();
         if (m.category == c) {
            mods.add(m);
         }
      }

      return mods;
   }
}
