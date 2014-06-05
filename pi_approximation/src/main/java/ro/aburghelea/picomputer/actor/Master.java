package ro.aburghelea.picomputer.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import ro.aburghelea.picomputer.message.Calculate;
import ro.aburghelea.picomputer.message.PiApproximation;
import ro.aburghelea.picomputer.message.Result;
import ro.aburghelea.picomputer.message.Work;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/3/2014
 */
public class Master extends UntypedActor {
    private final int numberOfMessages;
    private final int numberOfElements;

    private double pi;
    private int numberOfResults;
    private final long start = System.currentTimeMillis();

    private final ActorRef listener;
    private final ActorRef workerRouter;

    public Master(final int numberOfWorkers, int numberOfMessages, int numberOfElements, ActorRef listener) {
        this.numberOfMessages = numberOfMessages;
        this.numberOfElements = numberOfElements;
        this.listener = listener;

        workerRouter = this.getContext().actorOf(
                Props.create(Worker.class).withRouter(new RoundRobinPool(numberOfWorkers))
                , "workerRouter");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Calculate) {
            for (int start = 0; start < numberOfMessages; start++) {
                workerRouter.tell(new Work(start, numberOfElements), getSelf());
            }
        } else if (message instanceof Result) {
            Result result = (Result) message;
            pi += result.getValue();
            numberOfResults += 1;

            if (numberOfResults == numberOfMessages) {
                Duration duration = Duration.create(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
                listener.tell(new PiApproximation(pi, duration), getSelf());
                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}