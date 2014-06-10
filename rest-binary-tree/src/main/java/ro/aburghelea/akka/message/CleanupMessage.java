package ro.aburghelea.akka.message;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/10/2014
 */
public class CleanupMessage extends Message {
    public CleanupMessage(int value) {
        super(value);
    }

    public CleanupMessage() {
        super(-1);
    }
}
