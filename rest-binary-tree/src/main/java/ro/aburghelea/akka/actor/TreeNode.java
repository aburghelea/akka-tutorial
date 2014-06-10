package ro.aburghelea.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import ro.aburghelea.akka.message.*;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/5/2014
 */
public class TreeNode extends UntypedActor {

    public static final String ROOT_NANE = "root/";
    private int id;
    private int value;
    private int inserted;
    private boolean removed;
    private String name;
    private ActorRef leftChild = null;
    private ActorRef rightChild = null;

    public TreeNode(String name) {
        removed = true;
        this.name = name;
    }

    private void remove() {
        inserted = 0;
        removed = true;
        value = 0;

    }

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
        ActorRef receiver;
        if (message.getValue() < value) {
            receiver = leftChild != null ? leftChild : this.getContext().actorOf(Props.create(TreeNode.class, name + "left/"));
            leftChild = receiver;
        } else {
            receiver = rightChild != null ? rightChild : this.getContext().actorOf(Props.create(TreeNode.class, name + "right/"));
            rightChild = receiver;
        }
        receiver.tell(message, getSelf());


    }

    private void insertHere(InsertMessage message) {
        id = message.getId();
        value = message.getValue();
        removed = false;
        inserted++;

        getLog().info("Set info about insertion for node " + id + " path " + name + " value " + value + " " + removed + " " + inserted);

    }

    private void search(ContainsMessage message) {
        if (this.value == message.getValue()) {
            String response = removed ? "Not available" : name;
            getLog().info("=========>returning get response");
            getSender().tell(new ResponseMessage(message.getValue(), name, inserted, message.getId(), response, message.getClass()), getSelf());
        } else {
            ActorRef receiver = message.getValue() > this.value ? rightChild : leftChild;
            if (receiver == null) {
                getSender().tell(new ResponseMessage(message.getValue(), null, 0, message.getId(), "Not available", message.getClass()), getSelf());
            } else {
                receiver.tell(message, getSender());
            }
        }
    }

    private void delete(DeleteMessage message) {
        getLog().info("Delete message to " + name);
        if (this.value == message.getValue()) {
            remove();
            getLog().info("Removing from " + name);
        } else {
            ActorRef receiver = message.getValue() > this.value ? rightChild : leftChild;
            if (receiver != null) {
                getLog().info("Delegating to " + receiver);
                receiver.tell(message, getSelf());
            }
        }
    }

    private void cleanup(CleanupMessage message) {
        ActorRef root = getSender();
        if (!removed) {
            root.tell(new InsertMessage(value), getSelf());
        }

        if (rightChild != null) {
            rightChild.tell(message, root);
        }

        if (leftChild != null) {
            leftChild.tell(message, root);
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof InsertMessage) {
            insert((InsertMessage) message);
        } else if (message instanceof ContainsMessage) {
            search((ContainsMessage) message);
        } else if (message instanceof DeleteMessage) {
            delete((DeleteMessage) message);
        } else if (message instanceof CleanupMessage) {
            cleanup((CleanupMessage) message);
        }
        else {
            unhandled(message);
        }
    }




    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", value=" + value +
                ", inserted=" + inserted +
                ", removed=" + removed +
                ", name='" + name + '\'' +
                '}';
    }

    private LoggingAdapter getLog() {
        return getContext().system().log();
    }
}
