package net.minecraft.src;

import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.Minecraft;

public class ThreadCheckHasPaid extends Thread {
   final Minecraft field_28146_a;

   public ThreadCheckHasPaid(Minecraft var1) {
      this.field_28146_a = var1;
   }

   public void run() {
      try {
         StringBuilder var10002 = new StringBuilder("https://login.minecraft.net/session?name=");
         Session var10003 = this.field_28146_a.session;
         HttpURLConnection var1 = (HttpURLConnection)(new URL(var10002.append(Session.username).append("&session=").append(this.field_28146_a.session.sessionId).toString())).openConnection();
         var1.connect();
         if (var1.getResponseCode() == 400) {
            Minecraft.hasPaidCheckTime = System.currentTimeMillis();
         }

         var1.disconnect();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }
}
