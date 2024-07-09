package com.compass.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN aCommand);
}
