package net.minecraft.src.MEDMEX.Utils.Shader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IResourceManager {
   Set getResourceDomains();

   IResource getResource(ResourceLocation var1) throws IOException;

   List getAllResources(ResourceLocation var1) throws IOException;
}
