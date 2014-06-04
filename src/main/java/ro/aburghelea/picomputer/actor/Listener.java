package ro.aburghelea.picomputer.actor;

import akka.actor.UntypedActor;
import ro.aburghelea.picomputer.message.PiApproximation;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/3/2014
 */
public class Listener extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PiApproximation) {
            PiApproximation approximation = (PiApproximation) message;

            System.out.println(String.format("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s",
                    approximation.getPi(), approximation.getDuration()));

            getContext().system().shutdown();
        } else {
            unhandled(message);
        }
    }
}