package ro.aburghelea.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import ro.aburghelea.akka.message.InsertMessage;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/5/2014
 */
public class TreeNode extends UntypedActor {

    int id;
    int value;
    int inserted;
    boolean removed;

    public TreeNode() {
        removed = true;
        id = 0;
        value = 0;
        inserted = 0;
        leftChild = rightChild = null;
    }

    private ActorRef leftChild = null;
    private ActorRef rightChild = null;

    public void insert(InsertMessage message) {
        if (shouldInsertHere(message)) {
            insertHere(message);
        } else {
            insertIntoChildren(message);
        }
    }

    private boolean shouldInsertHere(InsertMessage message) {

        return removed && leftChild == null && rightChild == null || value == message.getValue();

    }

    private void insertIntoChildren(InsertMessage message) {
        ActorRef receiver = getContext().system().actorOf(Props.create(TreeNode.class));
        if (message.getValue() < value) {
            leftChild = receiver;
            getLog().info("Left child" + leftChild);
        } else {
            rightChild = receiver;
            getLog().info("Right child" + rightChild);
        }
        receiver.tell(message, getSelf());

    }

    private void insertHere(InsertMessage message) {
        id = message.getId();
        value = message.getValue();
        removed = false;
        inserted++;
        getLog().info("Set info about insertion for node " + id);

    }

    private LoggingAdapter getLog() {
        return getContext().system().log();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof InsertMessage) {
            insert((InsertMessage) message);
        } else {
            unhandled(message);
        }
    }
}
