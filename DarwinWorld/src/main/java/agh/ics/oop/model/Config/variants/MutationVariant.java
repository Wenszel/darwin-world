package agh.ics.oop.model.Config.variants;

import java.util.List;

public interface MutationVariant {
    void mutate(List<Integer> genotypeList, int minMutations, int maxMutations);
}
