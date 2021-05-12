package edu.iis.mto.oven;

import static java.util.Objects.requireNonNull;

public class ProgramStage {

    private final int targetTemp;
    private final int stageTime;
    private final HeatType heat;

    private ProgramStage(Builder builder) {
        this.targetTemp = requireNonNull(builder.targetTemp);
        this.stageTime = requireNonNull(builder.stageTime);
        this.heat = requireNonNull(builder.heat);
    }

    public int getTargetTemp() {
        return targetTemp;
    }

    public int getStageTime() {
        return stageTime;
    }

    public HeatType getHeat() {
        return heat;
    }

    @Override
    public String toString() {
        return "ProgramStage [targetTemp=" + targetTemp + ", stageTime=" + stageTime + ", heat=" + heat + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private int targetTemp;
        private int stageTime;
        private HeatType heat;

        private Builder() {}

        public Builder withTargetTemp(int targetTemp) {
            this.targetTemp = targetTemp;
            return this;
        }

        public Builder withStageTime(int stageTime) {
            this.stageTime = stageTime;
            return this;
        }

        public Builder withHeat(HeatType heat) {
            this.heat = heat;
            return this;
        }

        public ProgramStage build() {
            return new ProgramStage(this);
        }
    }
}
