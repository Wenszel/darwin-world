package agh.ics.oop.model.factories;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.Config.variants.MutationCompleteRandomness;
import agh.ics.oop.model.Config.variants.MutationSwap;
import agh.ics.oop.model.Config.variants.MutationVariant;
import agh.ics.oop.model.Config.variants.MutationVariantName;

public class MutationVariantFactory {
    public static MutationVariant createMutationVariant(MutationVariantName mutationVariantName) {
        return switch (mutationVariantName) {
            case SWAP -> new MutationSwap();
            case COMPLETE_RANDOMNESS -> new MutationCompleteRandomness();
        };
    }
}
