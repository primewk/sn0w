package net.minecraft.src.MEDMEX.Modules.Render;

import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import org.lwjgl.input.Keyboard;

public class TabList extends Module {
   public static boolean down = false;
   public static CopyOnWriteArrayList<String> players = new CopyOnWriteArrayList();
   public static String messages = "";
   public static TabList instance;

   public TabList() {
      super("TabList", 0, Module.Category.RENDER);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet3Chat && Keyboard.isKeyDown(15)) {
         Packet3Chat p = (Packet3Chat)e.getPacket();
         if (p.message.contains(",")) {
            String message = p.message.replace(" ", "");
            messages = messages + message;

            for(int i = 0; i < message.split(",").length; ++i) {
               players.add(message.split(",")[i]);
            }

            e.setCancelled(true);
         }

         if (p.message.contains("Players online:")) {
            e.setCancelled(true);
         }
      }

   }

   public void onRenderGUI() {
      if (this.isEnabled()) {
         if (Keyboard.isKeyDown(15)) {
            ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);

            for(int i = 0; i < players.size(); ++i) {
               GuiIngame var10000 = this.mc.ingameGUI;
               GuiIngame.drawRect((double)(var5.getScaledWidth() / 2 - 40), (double)(4 + i * 10), (double)(var5.getScaledWidth() / 2 + 40), (double)(13 + i * 10), -13421773);
               this.mc.ingameGUI.drawCenteredString(this.mc.fontRenderer, (String)players.get(i), var5.getScaledWidth() / 2, 4 + i * 10, -1);
            }
         } else {
            players.clear();
         }
      }

   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (Keyboard.isKeyDown(15)) {
            if (!down) {
               down = true;
               this.mc.thePlayer.sendChatMessage("/list");
            }
         } else {
            down = false;
         }
      }

   }
}
