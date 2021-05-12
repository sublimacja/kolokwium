package edu.iis.mto.oven;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BakingProgram implements Iterable<ProgramStage> {

    private final int initialTemp;
    private final List<ProgramStage> stages;

    private BakingProgram(Builder builder) {
        this.initialTemp = builder.initialTemp < 0 ? 0 : builder.initialTemp;
        this.stages = builder.stages;
    }

    @Override
    public Iterator<ProgramStage> iterator() {
        return List.copyOf(stages)
                   .iterator();
    }

    public int getInitialTemp() {
        return initialTemp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private int initialTemp;
        private List<ProgramStage> stages = Collections.emptyList();

        private Builder() {}

        public Builder withInitialTemp(int initialTemp) {
            this.initialTemp = initialTemp;
            return this;
        }

        public Builder withStages(List<ProgramStage> stages) {
            this.stages = stages;
            return this;
        }

        public BakingProgram build() {
            return new BakingProgram(this);
        }
    }

}
