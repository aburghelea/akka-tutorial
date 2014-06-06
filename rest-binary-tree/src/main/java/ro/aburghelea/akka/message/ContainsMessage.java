package ro.aburghelea.akka.message;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/6/2014
 */
public class ContainsMessage extends Message{

    public ContainsMessage(int value) {
        super(value);
    }

    @Override
    public String toString() {
        return "ContainsMessage{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
