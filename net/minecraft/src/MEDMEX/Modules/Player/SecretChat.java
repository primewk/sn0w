package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.Packet3Chat;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class SecretChat extends Module {
   public static SecretChat instance;
   String keys = "";
   public String signalCode = "cd:";
   public String spaceCode = ":spd:";
   public String junk = "fgfe";
   String toDeobf = "";
   boolean startListening = false;
   boolean stopListening = false;

   public SecretChat() {
      super("SecretChat", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Frequency", this, 87.0D, 10.0D, 1000.0D, true));
   }

   public int getValueFromFrequency(int i) {
      char[] a = String.valueOf(Client.settingsmanager.getSettingByName("Frequency").getValDouble()).toCharArray();
      int v = Integer.parseInt(String.valueOf(a[i]));
      return v;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet3Chat) {
         Packet3Chat p = (Packet3Chat)e.getPacket();
         String m = p.message;
         this.generateCodes();
         if (m.startsWith("<") && m.contains(this.signalCode)) {
            this.startListening = true;
         }

         if (this.startListening) {
            this.toDeobf = this.toDeobf + m;
         }

         if (m.endsWith("$$")) {
            this.stopListening = true;
         }

         if (this.stopListening) {
            System.out.println(this.toDeobf);
            this.toDeobf = this.toDeobf.replace("$$", "");
            String start = this.toDeobf.substring(0, this.toDeobf.indexOf(this.signalCode));
            String end = this.toDeobf.substring(this.toDeobf.indexOf(this.signalCode) + this.signalCode.length());
            String res = this.Shift(end.replace(" ", "").replace(this.junk, ""), (float)(-this.getValueFromFrequency(0)), (float)(-this.getValueFromFrequency(1)));
            Client.addChatMessage("§9[SC]§r" + start + res.replace(this.spaceCode, " ") + " §6| §c" + this.toDeobf.substring(this.toDeobf.indexOf(this.signalCode)));
            this.toDeobf = "";
            this.startListening = false;
            this.stopListening = false;
         }
      }

   }

   public void generateCodes() {
      this.signalCode = String.valueOf(this.generateString((float)this.getValueFromFrequency(1) * 0.5F, 4)) + this.generateString((float)this.getValueFromFrequency(0) * 0.6F, 3);
      this.spaceCode = this.generateString((float)this.getValueFromFrequency(1) * 0.3F, 4);
      this.junk = this.generateString((float)this.getValueFromFrequency(0) * 0.7F, 4);
      String s = "mbvwxfghijcyaopqrskdentulz";

      int i;
      for(this.keys = ""; s.length() != 0; s = s.replace(String.valueOf(s.charAt(i)), "")) {
         i = Math.min(s.length() / this.getValueFromFrequency(0), s.length() - 1);
         this.keys = String.valueOf(this.keys) + s.charAt(i);
      }

   }

   String generateString(float seed, int l) {
      StringBuilder result = new StringBuilder();

      for(int i = 0; i < l; ++i) {
         char newCharacter = (char)(99 + (int)((float)i * seed * 0.8F));
         result.append(newCharacter);
      }

      return result.toString();
   }

   public String onMessage(String t) {
      if (!this.isEnabled()) {
         return t;
      } else {
         String m = t.replace("$$", "");
         this.generateCodes();
         if (!m.contains(this.signalCode)) {
            return t;
         } else {
            String start = m.substring(0, m.indexOf(this.signalCode));
            String end = m.substring(m.indexOf(this.signalCode) + this.signalCode.length());
            String res = this.Shift(end.replace(" ", "").replace(this.junk, ""), (float)(-this.getValueFromFrequency(0)), (float)(-this.getValueFromFrequency(1)));
            return "§9[SC]§r" + start + res.replace(this.spaceCode, " ") + " §6| §c" + m.substring(m.indexOf(this.signalCode));
         }
      }
   }

   public String Obfuscate(String m) {
      if (m.startsWith("/")) {
         return m;
      } else {
         this.generateCodes();
         m = String.valueOf(this.signalCode) + this.Shift(m.replace(" ", this.spaceCode), (float)this.getValueFromFrequency(0), (float)this.getValueFromFrequency(1));
         return m;
      }
   }

   public String Shift(String m, float shiftStart, float shiftJump) {
      double offset = (double)shiftStart;
      StringBuilder result = new StringBuilder();
      char[] arrayOfChar;
      int i = (arrayOfChar = m.toCharArray()).length;

      for(byte b = 0; b < i; ++b) {
         char character = arrayOfChar[b];
         if (this.keys.contains(String.valueOf(character))) {
            int ind = this.keys.indexOf(character);
            ind = (int)((double)ind + offset);

            while(ind < 0 || ind >= this.keys.length()) {
               if (ind < 0) {
                  ind += this.keys.length();
               }

               if (ind >= this.keys.length()) {
                  ind -= this.keys.length();
               }
            }

            result.append(this.keys.charAt(ind));
            offset += (double)shiftJump;
            if (shiftStart > 0.0F && Math.random() * 10.0D > 8.0D) {
               result.append(" ");
            }

            if (shiftStart > 0.0F && Math.random() * 10.0D > 9.0D) {
               result.append(this.junk);
            }
         }
      }

      return result.toString();
   }

   public static SecretChat getInstance() {
      return instance;
   }
}
