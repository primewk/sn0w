package net.minecraft.src.MEDMEX.settings;

public class KeybindSetting extends Setting {
   public int code;

   public KeybindSetting(int code) {
      this.name = "Keybind";
      this.code = code;
   }

   public int getKeyCode() {
      return this.code;
   }

   public void setKeyCode(int code) {
      this.code = code;
   }
}
