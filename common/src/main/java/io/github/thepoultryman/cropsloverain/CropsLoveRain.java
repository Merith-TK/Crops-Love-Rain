package io.github.thepoultryman.cropsloverain;

import io.github.thepoultryman.cropsloverain.config.CropsLoveRainConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class CropsLoveRain {
	public static final String MOD_ID = "cropsloverain";

	public static void init() {
		CropsLoveRainConfig.init(MOD_ID, CropsLoveRainConfig.class);
	}

	public static boolean shouldGrowExtra(Level level, BlockPos blockPos, RandomSource random, CropType cropType) {
		if (!level.isRainingAt(blockPos) || !CropsLoveRainConfig.useRainGrowthSpeed) return false;
		if (CropsLoveRainConfig.useIndividualSpeeds) {
			int growthSpeed = switch (cropType) {
				case Bamboo -> CropsLoveRainConfig.bambooCustomSpeed;
				case Crop -> CropsLoveRainConfig.cropsCustomSpeed;
				case Sapling -> CropsLoveRainConfig.saplingCustomSpeed;
				case SugarCane -> CropsLoveRainConfig.sugarCaneCustomSpeed;
			};
			return random.nextInt(growthSpeed) == 0;
		} else {
			return random.nextInt(CropsLoveRainConfig.rainGrowthSpeed) == 0;
		}
	}

	public enum CropType {
		Bamboo,
		Crop,
		Sapling,
		SugarCane,
	}
}
