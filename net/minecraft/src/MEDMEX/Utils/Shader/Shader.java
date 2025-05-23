package net.minecraft.src.MEDMEX.Utils.Shader;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;

public abstract class Shader {
   private final Map<String, Integer> uniforms;
   protected int program;
   protected boolean canUse;

   public Shader(String frag) {
      this(frag, "/vertex.fsh");
   }

   public Shader(String frag, String vert) {
      this.uniforms = new LinkedHashMap();
      this.canUse = false;
      int fragShader = 0;
      int vertShader = 0;

      try {
         vertShader = compile(vert, 35633);
         fragShader = compile(frag, 35632);
      } catch (Exception var9) {
         var9.printStackTrace();
      } finally {
         if (fragShader == 0 || vertShader == 0) {
            System.out.println("Failed to compile shaders!");
            return;
         }

      }

      this.program = ARBShaderObjects.glCreateProgramObjectARB();
      if (this.program != 0) {
         ARBShaderObjects.glAttachObjectARB(this.program, vertShader);
         ARBShaderObjects.glAttachObjectARB(this.program, fragShader);
         ARBShaderObjects.glLinkProgramARB(this.program);
         if (ARBShaderObjects.glGetObjectParameteriARB(this.program, 35714) == 0) {
            System.out.println(getLogInfo(this.program));
         } else {
            ARBShaderObjects.glValidateProgramARB(this.program);
            if (ARBShaderObjects.glGetObjectParameteriARB(this.program, 35715) == 0) {
               System.out.println(getLogInfo(this.program));
            } else {
               this.canUse = true;
               this.onInitialize();
            }
         }
      }
   }

   protected abstract void onInitialize();

   public void use() {
      if (this.canUse) {
         ARBShaderObjects.glUseProgramObjectARB(this.program);
      }

   }

   public void stopUse() {
      ARBShaderObjects.glUseProgramObjectARB(0);
   }

   public void createUniform(String name) {
      this.uniforms.put(name, GL20.glGetUniformLocation(this.program, name));
   }

   public int getUniform(String name) {
      return (Integer)this.uniforms.getOrDefault(name, -1);
   }

   public static String getLogInfo(int obj) {
      return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
   }

   public static int compile(String loc, int type) {
      InputStream stream = Shader.class.getResourceAsStream(loc);
      if (stream == null) {
         return 0;
      } else {
         int shader = 0;

         try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(type);
            if (shader == 0) {
               return 0;
            }

            ARBShaderObjects.glShaderSourceARB(shader, read(stream));
            ARBShaderObjects.glCompileShaderARB(shader);
         } catch (Exception var5) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            var5.printStackTrace();
         }

         return shader;
      }
   }

   public static String read(InputStream stream) {
      StringBuilder builder = new StringBuilder();

      int i;
      try {
         while((i = stream.read()) != -1) {
            builder.append((char)i);
         }
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return builder.toString();
   }
}
