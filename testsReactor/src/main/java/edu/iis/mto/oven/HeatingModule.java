package edu.iis.mto.oven;

public interface HeatingModule {

    void termalCircuit(HeatingSettings settings) throws HeatingException;

    void heater(HeatingSettings settings);

    void grill(HeatingSettings settings);
}
