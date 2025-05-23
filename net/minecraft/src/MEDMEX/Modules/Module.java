package net.minecraft.src.MEDMEX.Modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.settings.KeybindSetting;
import net.minecraft.src.MEDMEX.settings.Setting;

public class Module {
   public String name;
   public boolean toggled;
   public KeybindSetting keyCode = new KeybindSetting(0);
   public Module.Category category;
   public boolean getKey;
   public boolean setKey;
   public String attribute;
   public Minecraft mc;
   public List<Setting> settings;

   public void setup() {
   }

   public Module(String name, int key, Module.Category c) {
      this.mc = Minecraft.theMinecraft;
      this.settings = new ArrayList();
      this.name = name;
      this.keyCode.code = key;
      this.category = c;
      this.addSettings(this.keyCode);
      this.attribute = "";
      this.setup();
   }

   public void addSettings(Setting... settings) {
      this.settings.addAll(Arrays.asList(settings));
   }

   public boolean isEnabled() {
      return this.toggled;
   }

   public int getKey() {
      return this.keyCode.code;
   }

   public void onEvent(Event e) {
   }

   public void getPacket(EventPacket e) {
   }

   public void onRender() {
   }

   public void onRenderEntities() {
   }

   public void onLateRender() {
   }

   public void onRenderGUI() {
   }

   public void onLog(String bound, String p) {
   }

   public String onMessage(String s) {
      return s;
   }

   public void toggle() {
      this.toggled = !this.toggled;
      if (this.toggled) {
         this.onEnable();
      } else {
         this.onDisable();
      }

   }

   public net.minecraft.src.de.Hero.settings.Setting getSet(String s) {
      return Client.settingsmanager.getSettingByNameAndMod(s, this);
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public Module.Category getCategory() {
      return this.category;
   }

   public static enum Category {
      CLIENT("Client"),
      COMBAT("Combat"),
      MOVEMENT("Movement"),
      PLAYER("Player"),
      RENDER("Render"),
      WORLD("World");

      public String name;

      private Category(String name) {
         this.name = name;
      }
   }
}
