package ro.aburghelea.akka.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/6/2014
 */
public class ResponseMessage extends Message {

    @JsonProperty
    private String nodeName;
    @JsonProperty
    private int requestMessageId;
    @JsonProperty
    private String status;
    @JsonProperty
    private Class requestMessageClass;
    @JsonProperty
    private int inserted;


    public static final ResponseMessage ERROR_RESPONSE = new ResponseMessage(0, "-", -1,  -1,
            HttpStatus.INTERNAL_SERVER_ERROR.toString(), Message.class);

    public ResponseMessage(int value, String nodeName,int inserted,  int requestMessageId, String status, Class requestMessageClass) {
        super(value);
        this.nodeName = nodeName;
        this.requestMessageId = requestMessageId;
        this.status = status;
        this.requestMessageClass = requestMessageClass;
        this.inserted = inserted;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "nodeName='" + nodeName + '\'' +
                ", id='" + getId() + '\'' +
                ", value='" + getValue() + '\'' +
                ", requestMessageId='" + requestMessageId + '\'' +
                ", status='" + status + '\'' +
                ", requestMessageClass=" + requestMessageClass +
                '}';
    }
}
