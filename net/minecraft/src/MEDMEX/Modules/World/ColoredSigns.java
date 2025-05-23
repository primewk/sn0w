package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.Packet130UpdateSign;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class ColoredSigns extends Module {
   public static String[] newlines = new String[]{"", "", "", ""};
   String newline0;
   String newline1;
   String newline2;
   String newline3;

   public ColoredSigns() {
      super("ColoredSigns", 0, Module.Category.WORLD);
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet130UpdateSign) {
         Packet130UpdateSign packet = (Packet130UpdateSign)e.getPacket();
         packet.signLines[0] = newlines[0];
         packet.signLines[1] = newlines[1];
         packet.signLines[2] = newlines[2];
         packet.signLines[3] = newlines[3];
      }

   }
}
