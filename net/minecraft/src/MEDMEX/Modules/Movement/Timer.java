package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MathHelper;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class Timer extends Module {
   public static Timer instance;
   private float[] ticks = new float[20];
   private long prevTime;
   public static int currentTick;

   public Timer() {
      super("Timer", 0, Module.Category.MOVEMENT);
      int i = 0;

      for(int len = this.ticks.length; i < len; ++i) {
         this.ticks[i] = 0.0F;
      }

      this.prevTime = -1L;
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Timer", this, 1.0D, 1.0D, 2.0D, false));
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.mc.timer.timerSpeed = (float)Client.settingsmanager.getSettingByName("Timer").getValDouble();
      }

   }

   public float getTickRate() {
      int tickCount = 0;
      float tickRate = 0.0F;

      for(int i = 0; i < this.ticks.length; ++i) {
         float tick = this.ticks[i];
         if (tick > 0.0F) {
            tickRate += tick;
            ++tickCount;
         }
      }

      return (float)(20.0D - (20.0D - MathHelper.clamp_double((double)(tickRate / (float)tickCount), 0.0D, 20.0D)) * 2.0D);
   }

   public void TimeUpdate() {
      if (this.prevTime != -1L) {
         this.ticks[currentTick % this.ticks.length] = (float)MathHelper.clamp_double((double)(20.0F / (float)(System.currentTimeMillis() - this.prevTime) / 1000.0F), 0.0D, 20.0D);
         ++currentTick;
      }

      this.prevTime = System.currentTimeMillis();
   }
}
