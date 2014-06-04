package ro.aburghelea.picomputer.actor;

import akka.actor.UntypedActor;
import ro.aburghelea.picomputer.message.Result;
import ro.aburghelea.picomputer.message.Work;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/3/2014
 */
public final class Worker extends UntypedActor {
    /**
     * Calulating PI by the following formula
     * <img src="http://doc.akka.io/docs/akka/2.0.2/_images/pi-formula.png"  alt="PI Sum formula"/>
     *
     * @param start
     * @param numberOfElements
     * @return
     */
    private double calculatePiFor(int start, int numberOfElements) {
        double accumulator = 0.0;

        for (int i = start * numberOfElements; i <= ((start + 1) * numberOfElements - 1); i++) {
            accumulator += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
        }

        return accumulator;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Work) {
            Work work = (Work) message;
            double result = calculatePiFor(work.getStart(), work.getNumberOfElements());
            getSender().tell(new Result(result), getSelf());
        } else {
            unhandled(message);
        }
    }



}