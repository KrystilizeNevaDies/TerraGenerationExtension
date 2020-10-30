package worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.biomes.Biome;
import terraextension.Terra;
import worldgen.noise.NoiseType;
import worldgen.noise.TerraNoise;

public class TerraWorldgen implements ChunkGenerator  {
	
	private static final Random random = new Random(System.currentTimeMillis());
	
	private TerraNoise OverworldTerrainNoise;
	private TerraNoise LandmassTerrainNoise;
	private TerraNoise RiverTerrainNoise;

	public TerraWorldgen(Terra terra) {
		// Generate noise profiles
		OverworldTerrainNoise = new TerraNoise(terra, NoiseType.OverworldTerrain, random);
		LandmassTerrainNoise = new TerraNoise(terra, NoiseType.LandmassTerrain, random);
		RiverTerrainNoise = new TerraNoise(terra, NoiseType.RiverTerrain, random);
	}
	
	@Override
    public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {
        for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
            for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                int posX = chunkX*16+x;
                int posZ = chunkZ*16+z;
                
                // set bedrock
                batch.setBlock(posX, 0, posZ, Block.BEDROCK);
                
                // General Terrain Generation Alg
                
                // Get normalised balance between water and land
                double landmassDelta = LandmassTerrainNoise.GetNoise(posX, posZ);
                double waterDelta = RiverTerrainNoise.GetNoise(posX, posZ);
                double ratio = 1 / (landmassDelta + waterDelta);
                landmassDelta = landmassDelta * ratio;
                waterDelta = waterDelta * ratio;
                
                // Calculate height based on normalised balance
                double terrainHeight = 32 + 64 * landmassDelta * Math.pow(OverworldTerrainNoise.GetNoise(posX, posZ), 1/3);
                double waterHeight = 64 * waterDelta * RiverTerrainNoise.GetNoise(posX, posZ);
                int height = (int) (terrainHeight + waterHeight);
                
                for (int y = 1; y < height; y++) {
                	batch.setBlock(posX, y, posZ, Block.DIRT);
                }
                batch.setBlock(posX, height, posZ, Block.DIRT);
                for (int y = height; y < 64; y++) {
                	batch.setBlock(posX, y, posZ, Block.WATER);
                }
            }
        }
    }

    @Override
    public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
        Arrays.fill(biomes, Biome.PLAINS);
    }

    @Override
    public List<ChunkPopulator> getPopulators() {
    	
    	class TestPopulator implements ChunkPopulator {

			@Override
			public void populateChunk(ChunkBatch batch, Chunk chunk) {
				// TODO Auto-generated method stub
			}
    		
    	}
    	
    	List<ChunkPopulator> list = List.of(new TestPopulator());
        return list;
    }
}
