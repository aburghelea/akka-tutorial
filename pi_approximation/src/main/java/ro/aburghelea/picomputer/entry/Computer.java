package ro.aburghelea.picomputer.entry;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import ro.aburghelea.picomputer.actor.Listener;
import ro.aburghelea.picomputer.actor.Master;
import ro.aburghelea.picomputer.message.Calculate;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/3/2014
 */
public class Computer {
    public void calculate(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) {
        // Create an Akka system
        ActorSystem system = ActorSystem.create("PiSystem");

        // create the result listener, which will print the result and shutdown the system
        final ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");

        // create the master
        ActorRef master = system.actorOf(Props.create(Master.class, nrOfWorkers, nrOfMessages, nrOfElements, listener));

        // start the calculation
        master.tell(new Calculate(), null);

    }
}
