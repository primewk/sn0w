package net.minecraft.src;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public final class J_CompactJsonFormatter implements J_JsonFormatter {
   public String format(J_JsonRootNode var1) {
      StringWriter var2 = new StringWriter();

      try {
         this.format(var1, var2);
      } catch (IOException var4) {
         throw new RuntimeException("Coding failure in Argo:  StringWriter gave an IOException", var4);
      }

      return var2.toString();
   }

   public void format(J_JsonRootNode var1, Writer var2) throws IOException {
      this.formatJsonNode(var1, var2);
   }

   private void formatJsonNode(J_JsonNode var1, Writer var2) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
