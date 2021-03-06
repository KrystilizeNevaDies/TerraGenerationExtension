/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package terraextension;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import worldgen.TerraWorldgen;

public class Terra extends Extension {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		MinecraftServer.getInstanceManager().getInstances().forEach(instance -> {
			System.out.println("Applying TerraWorldgen to: " + instance.getStorageLocation().toString());
			instance.setChunkGenerator(new TerraWorldgen(this));
		});
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}
	
}
