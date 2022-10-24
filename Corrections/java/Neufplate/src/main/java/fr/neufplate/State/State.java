package fr.neufplate.State;

import fr.neufplate.Neufplate;

public abstract class State {

    Neufplate neufplate;

    public State(Neufplate neufplate) {
        this.neufplate = neufplate;
    }

    public abstract String onTitling();
    public abstract String onMakingCollision();
    public abstract String onGenerating();
}