package edu.iis.mto.oven;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OvenTest {

    private Oven oven;
    @Mock
    private HeatingModule heatingModule;
    @Mock
    private Fan fan;


    private HeatingSettings heatingSettings;
    private BakingProgram bakingProgram;

    private final int temp = 100;
    private final int time = 30;

    @BeforeEach
    void setUp() {

        oven = new Oven(heatingModule, fan);
    }

    @Test
    void shouldThrowOvenExceptionWhenTermalCircuitThrowsHeatingException() throws HeatingException {

        heatingSettings = settingsBuilder().build();
        bakingProgram = bakingProgramBuilder().build();
        doThrow(HeatingException.class).when(heatingModule).termalCircuit(heatingSettings);
        assertThrows(OvenException.class, () -> oven.start(bakingProgram));

    }

    @Test
    void heatTypeGrillShouldCallGrillMethod()
    {
       heatingSettings = settingsBuilder().build();
       List<ProgramStage> stages = new ArrayList<>();
       stages.add(programStageBuilder().withHeat(HeatType.GRILL).build());
       bakingProgram = bakingProgramBuilder().withStages(stages).build();
       oven.start(bakingProgram);
       verify(heatingModule).grill(heatingSettings);

    }

    @Test
    void heatTypeOtherThanGrillShouldCallHeatherMethod()
    {
        heatingSettings = settingsBuilder().build();
        List<ProgramStage> stages = new ArrayList<>();
        stages.add(programStageBuilder().withHeat(HeatType.HEATER).build());
        bakingProgram = bakingProgramBuilder().withStages(stages).build();
        oven.start(bakingProgram);
        verify(heatingModule).heater(heatingSettings);
    }

    private BakingProgram.Builder bakingProgramBuilder() {
        int initialTemp = 0;
        List<ProgramStage> stages = new ArrayList<>();
        stages.add(programStageBuilder().build());
        BakingProgram.Builder bakingProgram = BakingProgram.builder().withInitialTemp(initialTemp).withStages(stages);
        return bakingProgram;
    }

    private ProgramStage.Builder programStageBuilder() {

        ProgramStage.Builder stage = ProgramStage.builder().withStageTime(time).withHeat(HeatType.THERMO_CIRCULATION).withTargetTemp(temp);
        return stage;
    }

    private HeatingSettings.Builder settingsBuilder() {
        HeatingSettings.Builder settings = HeatingSettings.builder().withTargetTemp(temp).withTimeInMinutes(time);
        return settings;
    }

}
