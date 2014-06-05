package ro.aburghelea.akka.message;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/5/2014
 */
public class InsertMessage {

    private final int id;
    private final int value;

    public InsertMessage(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }
}
