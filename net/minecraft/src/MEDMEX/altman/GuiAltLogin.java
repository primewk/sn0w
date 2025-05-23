package net.minecraft.src.MEDMEX.altman;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import org.lwjgl.input.Keyboard;

public final class GuiAltLogin extends GuiScreen {
   private final GuiScreen previousScreen;
   private AltLoginThread thread;
   private GuiTextField username;

   public GuiAltLogin(GuiScreen previousScreen) {
      this.previousScreen = previousScreen;
   }

   protected void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         this.thread = new AltLoginThread(this.username.getText());
         this.thread.start();
         break;
      case 1:
         this.mc.displayGuiScreen(this.previousScreen);
      }

   }

   public void drawScreen(int x2, int y2, float z2) {
      this.drawDefaultBackground();
      this.username.drawTextBox();
      this.drawCenteredString(this.mc.fontRenderer, "Alt Login", this.width / 2, 20, -1);
      this.drawCenteredString(this.mc.fontRenderer, this.thread == null ? "§7Idle..." : this.thread.getStatus(), this.width / 2, 29, -1);
      if (this.username.getText().isEmpty()) {
         this.drawString(this.mc.fontRenderer, "Username", this.width / 2 - 96, 66, -7829368);
      }

      super.drawScreen(x2, y2, z2);
   }

   public void initGui() {
      int var3 = this.height / 4 + 24;
      this.controlList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
      this.controlList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
      this.username = new GuiTextField(this, this.mc.fontRenderer, this.width / 2 - 100, 60, 200, 20, "");
      this.username.setFocused(true);
      Keyboard.enableRepeatEvents(true);
   }

   protected void keyTyped(char character, int key) {
      super.keyTyped(character, key);
      if (character == '\t' && !this.username.isFocused()) {
         this.username.setFocused(true);
      }

      if (character == '\r') {
         this.actionPerformed((GuiButton)this.controlList.get(0));
      }

      this.username.textboxKeyTyped(character, key);
   }

   protected void mouseClicked(int x2, int y2, int button) {
      super.mouseClicked(x2, y2, button);
      this.username.mouseClicked(x2, y2, button);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void updateScreen() {
      this.username.updateCursorCounter();
   }
}
