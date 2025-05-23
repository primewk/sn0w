package net.minecraft.src.MEDMEX.serverman;

import java.io.IOException;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MEDMEX.Client;
import org.lwjgl.input.Keyboard;

public class GuiAddServer extends GuiScreen {
   private final GuiServerManager manager;
   private String status = "§7Idle...";
   private GuiTextField username;

   public GuiAddServer(GuiServerManager manager) {
      this.manager = manager;
   }

   protected void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         GuiAddServer.AddAltThread login = new GuiAddServer.AddAltThread(this.username.getText());
         login.start();
         break;
      case 1:
         this.mc.displayGuiScreen(this.manager);
      }

   }

   public void drawScreen(int i2, int j2, float f2) {
      this.drawDefaultBackground();
      this.username.drawTextBox();
      this.drawCenteredString(this.fontRenderer, "Add Server", this.width / 2, 20, -1);
      if (this.username.getText().isEmpty()) {
         this.drawString(this.mc.fontRenderer, "Server IP", this.width / 2 - 96, 66, -7829368);
      }

      this.drawCenteredString(this.fontRenderer, this.status, this.width / 2, 30, -1);
      super.drawScreen(i2, j2, f2);
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.controlList.clear();
      this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Add"));
      this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
      this.username = new GuiTextField(this, this.mc.fontRenderer, this.width / 2 - 100, 60, 200, 20, "");
   }

   protected void keyTyped(char par1, int par2) {
      this.username.textboxKeyTyped(par1, par2);
      if (par1 == '\t' && this.username.isFocused()) {
         this.username.setFocused(!this.username.isFocused());
      }

      if (par1 == '\r') {
         this.actionPerformed((GuiButton)this.controlList.get(0));
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.username.mouseClicked(par1, par2, par3);
   }

   static void access$0(GuiAddServer guiAddAlt, String status) {
      guiAddAlt.status = status;
   }

   private class AddAltThread extends Thread {
      private final String username;

      public AddAltThread(String username) {
         this.username = username;
         GuiAddServer.access$0(GuiAddServer.this, "§7Idle...");
      }

      private final void checkAndAddAlt(String username) throws IOException {
         ServerManager ServerManager = Client.serverManager;
         ServerManager.registry.add(new Server(username));
         GuiAddServer.access$0(GuiAddServer.this, "Server added. (" + username + ")");
      }

      public void run() {
         ServerManager ServerManager = Client.serverManager;
         ServerManager.registry.add(new Server(this.username));
         GuiAddServer.access$0(GuiAddServer.this, "§aServer added. (" + this.username + ")");
      }
   }
}
