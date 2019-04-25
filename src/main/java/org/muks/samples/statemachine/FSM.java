package org.muks.samples.statemachine;

import org.muks.samples.businessojects.StateObj;
import org.muks.samples.interfaces.FiniteStateMachine;
import org.muks.samples.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FSM extends FiniteStateMachine {
    private static Logger LOG = LoggerFactory.getLogger(FSM.class);
    private StateObj CurrentState;
    private String TRANSITIONS_PROPS_FILE = null;
    private String EVENTS_PROPS_FILE = null;

    private Map<String, StateObj> StateTransitionTable;
    private Map<String, List<String>> NextStateEvents;

    public FSM setTransitionsPropsFile(String transitionProps) {
        this.TRANSITIONS_PROPS_FILE = transitionProps;
        return this;
    }

    public FSM setEventsTablePropsFile(String eventsProps) {
        this.EVENTS_PROPS_FILE = eventsProps;
        return this;
    }


    /**
     * Finite state machine logical implementation
     */
    public FSM init() {
        this.NextStateEvents = Utils.buildStateToEventsMapping(this.EVENTS_PROPS_FILE);
        this.StateTransitionTable = Utils.buildStateTransitions(this.TRANSITIONS_PROPS_FILE, NextStateEvents);
        this.CurrentState = new StateObj("ready", false);

        LOG.info("Adding next level of events for \"ready\" state of FSM");
        this.CurrentState.setNextState(this.NextStateEvents.get("ready"));

        printEventTransitions();
        return this;
    }



    @Override
    public StateObj getState() {
        return this.CurrentState;
    }

    @Override
    public StateObj nextState(String event) throws Exception {
        LOG.info("In event:- {}", event);

        if (this.StateTransitionTable.containsKey(event))
            this.CurrentState = this.StateTransitionTable.get(event);
        else
            throw new Exception("Event not found exception");

        if (this.CurrentState.IsEndState)
            throw new Exception("=== END === state reached");

        return this.CurrentState;
    }

    private void printEventTransitions() {
        StringBuilder transitions = new StringBuilder("\n\n=== event -> state (transitions) ===\n");
        Set<String> events = this.StateTransitionTable.keySet();
        for (String event : events) {
            transitions.append(event + " -> " + this.StateTransitionTable.get(event) + "\n");
        }

        LOG.info("");
        LOG.info(transitions.toString());
    }

}
