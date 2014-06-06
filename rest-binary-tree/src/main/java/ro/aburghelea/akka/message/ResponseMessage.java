package ro.aburghelea.akka.message;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/6/2014
 */
public class ResponseMessage extends Message {

    private String nodeName;
    private int requestMessageId;
    private String status;
    private Class requestMessageClass;

    public ResponseMessage(int value, String nodeName, int requestMessageId, String status, Class requestMessageClass) {
        super(value);
        this.nodeName = nodeName;
        this.requestMessageId = requestMessageId;
        this.status = status;
        this.requestMessageClass = requestMessageClass;
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
