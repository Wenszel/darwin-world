package agh.ics.oop.model.config.variants;

import agh.ics.oop.model.utils.Genotype;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MutationSwapTest {
    @Test
    public void givenGenotype_whenSwapMutateWithZeroMutations_thenShouldSortedBeforeAndAfterBeEqual() {
        Genotype genotype = new Genotype(8);
        List<Integer> genotypeListBefore = genotype.getGenotypeList().stream().sorted().toList();
        MutationSwap mutationSwap = new MutationSwap();
        mutationSwap.mutate(genotype.getGenotypeList(), 0, 0);
        List<Integer> genotypeListAfter = genotype.getGenotypeList().stream().sorted().toList();
        assertEquals(genotypeListBefore, genotypeListAfter);
    }
}
