package org.muks.samples.interfaces;

import org.muks.samples.businessojects.StateObj;

public abstract class FiniteStateMachine {
    public StateObj getState() {
        return null;
    }

    public StateObj nextState(String event) throws Exception {
        return null;
    }
}
