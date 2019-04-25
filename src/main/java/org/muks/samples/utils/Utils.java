package org.muks.samples.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.muks.samples.businessojects.StateObj;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
    private static Logger LOG = LoggerFactory.getLogger(Utils.class);


    /**
     * reads properties file "event -> state"
     *
     */
    public static Map<String, StateObj> buildStateTransitions(String stateTransitionsFilePath,
                                                              Map<String, List<String>> nextStateEvents) {
        PropertiesConfiguration stateTransitionTable = null;
        try {
            PropertiesConfiguration stateTransitionConfig = new PropertiesConfiguration(stateTransitionsFilePath);
            stateTransitionTable = (PropertiesConfiguration) stateTransitionConfig.interpolatedConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        Map<String, StateObj> transitionMap = new HashMap<>();

        LOG.info("\n\nState transitions:-");
        Iterator<String> transitions = stateTransitionTable.getKeys();
        while (transitions.hasNext()) {
            String transition = transitions.next();

            LOG.info("{}: {}", transition, stateTransitionTable.getProperty(transition) );

            StateObj state;
            if (stateTransitionTable.getProperty(transition) instanceof List) {
                List<String> stateListing = (List<String>) stateTransitionTable.getProperty(transition);

                state = new StateObj(
                        stateListing.get(0),
                        Boolean.valueOf(stateListing.get(1))
                );
            } else {
                state = new StateObj(stateTransitionTable.getProperty(transition).toString());
            }

            state.setNextState(nextStateEvents.get(state.Name));
            transitionMap.put(transition, state);
        }

        return transitionMap;
    }

    public static Map<String, List<String>> buildStateToEventsMapping(String eventListingFilePath) {
        PropertiesConfiguration eventToStateMappings = null;
        try {
            PropertiesConfiguration stateTransitionConfig = new PropertiesConfiguration(eventListingFilePath);
            eventToStateMappings = (PropertiesConfiguration) stateTransitionConfig.interpolatedConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        Map<String, List<String>> stateTransitionTable = new HashMap<>();

        LOG.info("\n\nState transitions:-");
        Iterator<String> eventMappings = eventToStateMappings.getKeys();
        while (eventMappings.hasNext()) {
            String event = eventMappings.next();
            LOG.info("{}: {}", event, eventToStateMappings.getProperty(event) );

            List<String> nextStatesListing;
            if (eventToStateMappings.getProperty(event) instanceof List)
                nextStatesListing = (List<String>) eventToStateMappings.getProperty(event);
            else
                nextStatesListing = Arrays.asList(eventToStateMappings.getProperty(event).toString());


            if (stateTransitionTable.containsKey(event))
                stateTransitionTable.get(event).addAll(nextStatesListing);
            else
                stateTransitionTable.put(event, nextStatesListing);
        }

        return stateTransitionTable;
    }
}
