package edu.iis.mto.oven;

public class Oven {

    static final int HEAT_UP_AND_FINISH_SETTING_TIME = 0;
    private final HeatingModule heatingModule;
    private final Fan fan;

    public Oven(HeatingModule heatingModule, Fan fan) {
        this.heatingModule = heatingModule;
        this.fan = fan;
    }

    public void start(BakingProgram program) {
        init(program.getInitialTemp());
        for (ProgramStage programStage : program) {
            runStage(programStage);
        }
    }

    private void init(int initialTemp) {
        heatingModule.heater(HeatingSettings.builder()
                                            .withTargetTemp(initialTemp)
                                            .withTimeInMinutes(HEAT_UP_AND_FINISH_SETTING_TIME)
                                            .build());
    }

    private void runStage(ProgramStage programStage) {
        try {
            if (programStage.getHeat() == HeatType.THERMO_CIRCULATION) {
                fan.on();
                heatingModule.termalCircuit(settings(programStage));
            } else {
                runHeatingProgram(programStage);
            }
        } catch (HeatingException ex) {
            throw new OvenException(ex);
        }
        finally {
            fan.off();
        }
    }

    private void runHeatingProgram(ProgramStage stage) {
        HeatingSettings settings = settings(stage);
        if (stage.getHeat() == HeatType.GRILL) {
            heatingModule.grill(settings);
        } else {
            heatingModule.heater(settings);
        }
    }

    private HeatingSettings settings(ProgramStage stage) {
        return HeatingSettings.builder()
                              .withTargetTemp(stage.getTargetTemp())
                              .withTimeInMinutes(stage.getStageTime())
                              .build();
    }
}
