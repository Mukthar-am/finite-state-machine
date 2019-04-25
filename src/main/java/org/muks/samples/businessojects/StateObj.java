package org.muks.samples.businessojects;

import java.util.ArrayList;
import java.util.List;

public class StateObj {
    public String Name = null;
    public boolean IsEndState;
    public List<String> NextStateEvents = new ArrayList<>();

    public StateObj(String state) {
        this.Name = state;
    }

    public StateObj(String state, boolean isEndState) {
        this.Name = state;
        this.IsEndState = isEndState;
    }

    public StateObj(String state, boolean isEndState, List<String> nextValidTransitions) {
        this.Name = state;
        this.IsEndState = true;
        this.NextStateEvents = nextValidTransitions;
    }

    public void setNextState(List<String> nextStateListing) {
        this.NextStateEvents = nextStateListing;
    }

    public List<String> getNextStateEvents() { return this.NextStateEvents; }

    public String toString() {
        return this.Name + " [IsEnd: " + this.IsEndState + ", NextStateEvents: " + this.NextStateEvents.toString() + "]";
    }
}

