package agh.ics.oop.model.Config.variants;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MutationSwap implements MutationVariant {
    @Override
    public void mutate(List<Integer> genotypeList, int minMutations, int maxMutations) {
        Random rand = new Random();
        int mutationsAmount = rand.nextInt(maxMutations - minMutations + 1);
        for(int i=0; i<mutationsAmount; i++) {
            if(rand.nextInt(2)==0) {
                genotypeList.set(rand.nextInt(genotypeList.size()), rand.nextInt(8));
            } else {
                Collections.swap(genotypeList, rand.nextInt(genotypeList.size()), rand.nextInt(genotypeList.size()));
            }

        }
    }
}
