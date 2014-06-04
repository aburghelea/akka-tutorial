package ro.aburghelea.picomputer.message;

import scala.concurrent.duration.Duration;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/3/2014
 */
public class PiApproximation {
    private final double pi;
    private final Duration duration;

    public PiApproximation(double pi, Duration duration) {
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

