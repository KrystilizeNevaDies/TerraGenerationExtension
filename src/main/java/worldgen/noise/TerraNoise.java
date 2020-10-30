package worldgen.noise;

import java.util.Random;

import de.articdive.jnoise.JNoise;
import terraextension.Terra;

public class TerraNoise {
	
	private JNoise noise = null;
	
	
	
	public TerraNoise(Terra terra, Integer type, Random random) {
		switch(type) {
			case NoiseType.LandmassTerrain:
				noise = JNoise.newBuilder()
	    		.openSimplex()
	    		.setFrequency(0.0025)
	    		.setSeed(random.nextInt())
	    		.build();
			case NoiseType.OverworldTerrain:
				noise = JNoise.newBuilder()
	    		.openSimplex()
	    		.setFrequency(0.01)
	    		.setSeed(random.nextInt())
	    		.build();
			case NoiseType.OverworldFauna:
				noise = JNoise.newBuilder()
	    		.openSimplex()
	    		.setFrequency(0.01)
	    		.setSeed(random.nextInt())
	    		.build();
			case NoiseType.RiverTerrain:
				noise = JNoise.newBuilder()
	    		.openSimplex()
	    		.setFrequency(0.01)
	    		.setSeed(random.nextInt())
	    		.build();
			case NoiseType.RiverFauna:
				noise = JNoise.newBuilder()
	    		.openSimplex()
	    		.setFrequency(0.01)
	    		.setSeed(random.nextInt())
	    		.build();
		}
	}
	
	public double GetNoise(double x) {
		return (noise.getNoise(x) + 1) / 2;
	}
	
	public double GetNoise(double x, double y) {
		return (noise.getNoise(x, y) + 1) / 2;
	}
	
	public double GetNoise(double x, double y, double z) {
		return (noise.getNoise(x, y, z) + 1) / 2;
	}
}
