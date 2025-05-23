package net.minecraft.src.MEDMEX.Utils.Shader;

import java.io.IOException;

public interface ITextureObject {
   void loadTexture(IResourceManager var1) throws IOException;

   int getGlTextureId();
}
