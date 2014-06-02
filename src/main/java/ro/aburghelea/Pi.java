package ro.aburghelea;

import akka.actor.*;
import akka.routing.RoundRobinPool;
import akka.routing.RoundRobinRouter;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/2/2014
 */
public class Pi {

    static class Calculate {
    }

    static class Work {
        private final int start;
        private final int numberOfElements;

        public Work(int start, int numberOfElements) {
            this.start = start;
            this.numberOfElements = numberOfElements;
        }

        public int getStart() {
            return start;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }
    }

    static class Result {
        private final double value;

        Result(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    static class PiApproximation {
        private final double pi;
        private final Duration duration;

        PiApproximation(double pi, Duration duration) {
            this.pi = pi;
            this.duration = duration;
        }

        public Duration getDuration() {
            return duration;
        }

        public double getPi() {

            return pi;
        }
    }

    public static final class Worker extends UntypedActor {
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

        /**
         * Calulating PI by the following formula
         * <img src="http://doc.akka.io/docs/akka/2.0.2/_images/pi-formula.png"  alt="PI Sum formula"/>
         *
         * @param start
         * @param numberOfElements
         * @return
         */
        private double calculatePiFor(int start, int numberOfElements) {
            double accumulator = .0;

            for (int i = start * numberOfElements; i <= ((start + 1) * numberOfElements - 1); i++) {
                accumulator += 4.0 * (1 - (i % 2 * 2)) / 2 * (i + 1);
            }

            return accumulator;
        }
    }

    public static class Master extends UntypedActor {
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
                numberOfResults++;

                if (numberOfResults == numberOfMessages) {
                    Duration duration = Duration.create(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
                    listener.tell (new PiApproximation(pi, duration), getSelf());
                    getContext().stop(getSelf());
                }
            } else {
                unhandled(message);
            }
        }
    }


    public static class Listener extends UntypedActor {

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

    public static void main(String[] args) {
        Pi pi = new Pi();
        pi.calculate(4, 10000, 10000);
    }

    // actors and messages ...

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
