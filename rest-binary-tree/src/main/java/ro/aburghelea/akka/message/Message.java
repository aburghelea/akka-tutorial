package ro.aburghelea.akka.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/6/2014
 */
public abstract class Message implements Serializable {

    private final Logger LOG = LoggerFactory.getLogger(Message.class);

    @JsonProperty
    final int id;
    @JsonProperty
    final int value;

    private static final AtomicInteger counter = new AtomicInteger(1);

    public Message(int value) {
        this.value = value;
        id = counter.getAndIncrement();
        LOG.info("Given seq no " + id);
    }

    public int getValue() {
        return value;
    }


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
