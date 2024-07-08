package io.github.thepoultryman.cropsloverain.mixin;

import io.github.thepoultryman.cropsloverain.CropsLoveRain;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SugarCaneBlock.class)
public class SugarCaneGrowthSpeedup extends Block {
    // DO NOT USE; FOR BLOCK STATE ONLY
    public SugarCaneGrowthSpeedup(Properties settings) throws Exception {
        super(settings);
        throw new Exception("This class should not be used. It is a mixin.");
    }

    @Shadow @Final public static IntegerProperty AGE;

    @Inject(at = @At("HEAD"), method = "randomTick")
    public void crops_love_rain$sugarCaneExtraTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (level.getBlockState(pos.above()).isAir()) {
            int caneBlocks; // Determines how may sugar canes are in a "pillar".
            for (caneBlocks = 1; level.getBlockState(pos.below(caneBlocks)).is(Blocks.SUGAR_CANE); ++caneBlocks);

            if (caneBlocks < 3 && CropsLoveRain.shouldGrowExtra(level, pos, random, CropsLoveRain.CropType.SugarCane)) {
                int age = state.getValue(AGE);
                if (age == 15) {
                    level.setBlockAndUpdate(pos.above(), this.defaultBlockState());               // Creates a sugar cane block above this one.
                    level.setBlock(pos, state.setValue(AGE, 0), Block.UPDATE_INVISIBLE); // Sets this sugar cane's age to 0.
                } else {
                    level.setBlock(pos, state.setValue(AGE, 15), Block.UPDATE_INVISIBLE); // Skip to last age.
                }
            }
        }
    }
}
