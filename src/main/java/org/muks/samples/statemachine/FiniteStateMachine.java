package org.muks.samples.statemachine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiniteStateMachine {
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

        public org.muks.samples.businessojects.State nextState(String input, org.muks.samples.businessojects.State current) {
            if (inputs.contains(input)) {
                return transitionMap.getOrDefault(input, current);
            }
            return current;
        }

        public final List<String> inputs;
        public final static Map<String, org.muks.samples.businessojects.State> transitionMap = new HashMap<>();
        public final boolean isEndState;

        static {
            transitionMap.put("Fullfil", org.muks.samples.businessojects.State.Created);
            transitionMap.put("Activate", org.muks.samples.businessojects.State.Activated);
            transitionMap.put("Cancel", org.muks.samples.businessojects.State.Cancelled);
            transitionMap.put("MakeProgress", org.muks.samples.businessojects.State.InProgress);
            transitionMap.put("Complete", org.muks.samples.businessojects.State.Completed);
        }

    }
}