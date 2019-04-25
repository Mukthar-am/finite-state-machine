package org.muks.samples.businessojects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * State object:
 *  Holding state transitions from one state to another
 *
 *  nextState()
 */
public enum State {

    /**
     * state -> transitions
     */
    Ready(true, "Fullfil"),
    Created(true, "Activate", "Cancel"),
    Activated(true, "MakeProgress"),
    InProgress(true, "Complete"),
    Cancelled(false, "Cancelled"),
    Completed(false, "Completed");

    State(boolean isEndState, String... in) {
        inputs = Arrays.asList(in);
        this.isEndState = isEndState;
    }

    public State nextState(String input, State current) {
        if (inputs.contains(input)) {
            System.out.println("-- Contain.. - " + input.toString());
            return transitionMap.getOrDefault(input, current);
        } else {
            System.out.println("-- doesn't contain");
            return current;
        }

    }

    public final List<String> inputs;
    public final static Map<String, State> transitionMap = new HashMap<>();
    public final boolean isEndState;

    static {
        transitionMap.put("Fullfil", State.Created);
        transitionMap.put("Activate", State.Activated);
        transitionMap.put("Cancel", State.Cancelled);
        transitionMap.put("MakeProgress", State.InProgress);
        transitionMap.put("Complete", State.Completed);
    }

}
