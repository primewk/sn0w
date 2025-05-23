package net.minecraft.src.MEDMEX.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public abstract class Command {
   public Minecraft mc;
   public String name;
   public String description;
   public String syntax;
   public List<String> aliases;

   public Command(String name, String description, String syntax, String... aliases) {
      this.mc = Minecraft.theMinecraft;
      this.aliases = new ArrayList();
      this.name = name;
      this.description = description;
      this.syntax = syntax;
      this.aliases = Arrays.asList(aliases);
   }

   public abstract void onCommand(String[] var1, String var2);

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getSyntax() {
      return this.syntax;
   }

   public void setSyntax(String syntax) {
      this.syntax = syntax;
   }

   public List<String> getAliases() {
      return this.aliases;
   }

   public void setAliases(List<String> aliases) {
      this.aliases = aliases;
   }
}
