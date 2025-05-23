package net.minecraft.src.MEDMEX.ProtocolSelection;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.UI.MainMenu;
import org.lwjgl.input.Keyboard;

public class GuiProtocolSelection extends GuiScreen {
   protected GuiScreen parentScreen;
   protected String centerString = "Protocol Selection";
   private String username;
   private GuiButton addButton;
   private GuiButton loginButton;
   private GuiButton cancelButton;
   private GuiTextField loginField;

   public GuiProtocolSelection(GuiScreen parentScreenIn) {
      this.parentScreen = parentScreenIn;
   }

   public void updateScreen() {
      this.loginField.updateCursorCounter();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.loginField = new GuiTextField(this, this.fontRenderer, this.width / 2 - 150, 50, 300, 20, "");
      this.loginField.setMaxStringLength(32767);
      this.loginField.setFocused(true);
      this.controlList.add(this.loginButton = new GuiButton(1, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, "Select"));
      this.controlList.add(this.cancelButton = new GuiButton(2, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, "Cancel"));
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.loginField.drawTextBox();
      this.fontRenderer.drawString(String.format("Protocol Version: %d", Client.protocolver), 1.0D, 1.0D, 16777215);
      this.drawCenteredString(this.fontRenderer, this.centerString, this.width / 2, 20, 16777215);
      this.drawCenteredString(this.fontRenderer, "Usage: Enter protocol version", this.width / 2, 40, 16777215);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   protected void actionPerformed(GuiButton button) {
      if (button.enabled) {
         if (button.id == 1) {
            String protocol = this.loginField.getText();
            int protocolver = Integer.valueOf(protocol);
            Client.protocolver = protocolver;
         } else if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
         }
      }

   }

   protected void keyTyped(char typedChar, int keyCode) {
      this.loginField.textboxKeyTyped(typedChar, keyCode);
      if (keyCode == 1) {
         this.mc.displayGuiScreen(new MainMenu());
      }

   }
}
