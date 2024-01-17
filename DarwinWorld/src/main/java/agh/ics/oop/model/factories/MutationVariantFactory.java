package agh.ics.oop.model.factories;

import agh.ics.oop.model.config.variants.MutationCompleteRandomness;
import agh.ics.oop.model.config.variants.MutationSwap;
import agh.ics.oop.model.config.variants.MutationVariant;
import agh.ics.oop.model.config.variants.MutationVariantName;

public class MutationVariantFactory {
    public static MutationVariant createMutationVariant(MutationVariantName mutationVariantName) {
        return switch (mutationVariantName) {
            case SWAP -> new MutationSwap();
            case COMPLETE_RANDOMNESS -> new MutationCompleteRandomness();
        };
    }
}
