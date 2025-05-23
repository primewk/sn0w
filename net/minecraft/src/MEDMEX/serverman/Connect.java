package net.minecraft.src.MEDMEX.serverman;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiScreen;

public class Connect extends GuiScreen {
   public static void connectToServer(String ip, int port) {
      Minecraft mc = Minecraft.theMinecraft;
      mc.displayGuiScreen(new GuiConnecting(mc, ip, port));
   }
}
