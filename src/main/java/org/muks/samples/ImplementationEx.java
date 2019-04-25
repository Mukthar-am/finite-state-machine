package org.muks.samples;

import org.muks.samples.statemachine.FSM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class ImplementationEx {
    private static Logger LOG = LoggerFactory.getLogger(ImplementationEx.class);

    public static void main(String[] args) {
        //Get file from resources folder
        String eventsTableFile = "events_table.properties";
        String stateTransitionsFile = "fsm_transition.properties";

        String eventsTableFilePath = ImplementationEx.class.getClassLoader().getResource(eventsTableFile).getFile();
        String stateTransitionsFilePath = ImplementationEx.class.getClassLoader().getResource(stateTransitionsFile).getFile();

        FSM fsm =
                new FSM()
                        .setTransitionsPropsFile(stateTransitionsFilePath)
                        .setEventsTablePropsFile(eventsTableFilePath)
                        .init();

        Scanner sc = new Scanner(System.in);
        try {
            boolean process = true;
            while (process) {
                if (fsm.getState().IsEndState)
                    process = false;

                LOG.info("FSM is in state -> " + fsm.getState().toString());
                System.out.println("> Input from - " + fsm.getState().getNextStateEvents());
                fsm.nextState(sc.nextLine().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
