package net.minecraft.src.MEDMEX.Utils.Shader;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL20;

public class FillShader extends Shader {
   private Color color;
   private float lineWidth;
   Minecraft mc;

   public FillShader() {
      super("/fill.fsh");
      this.color = Color.white;
      this.lineWidth = 1.5F;
      this.mc = Minecraft.theMinecraft;
   }

   protected void onInitialize() {
      this.createUniform("texture");
      this.createUniform("texelSize");
      this.createUniform("color");
      this.createUniform("divider");
      this.createUniform("radius");
      this.createUniform("maxSample");
   }

   public void updateUniforms() {
      GL20.glUniform1i(this.getUniform("texture"), 0);
      GL20.glUniform2f(this.getUniform("texelSize"), 1.0F / (float)this.mc.displayWidth, 1.0F / (float)this.mc.displayHeight);
      GL20.glUniform4f(this.getUniform("color"), (float)this.color.getRed() / 255.0F, (float)this.color.getGreen() / 255.0F, (float)this.color.getBlue() / 255.0F, (float)this.color.getAlpha() / 255.0F);
      GL20.glUniform1f(this.getUniform("radius"), this.lineWidth);
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public float getLineWidth() {
      return this.lineWidth;
   }

   public void setLineWidth(float lineWidth) {
      this.lineWidth = lineWidth;
   }
}
