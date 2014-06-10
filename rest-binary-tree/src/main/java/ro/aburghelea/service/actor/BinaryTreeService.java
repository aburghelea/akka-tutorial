package ro.aburghelea.service.actor;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.springframework.stereotype.Service;
import ro.aburghelea.akka.message.*;
import scala.SerialVersionUID;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/10/2014
 */
@Service
public class BinaryTreeService {

    @Inject
    private ActorSystemService actorSystemService;

    public void insert(int value) {
        actorSystemService.getRootNode().tell(new InsertMessage(value), null);
    }

    public ResponseMessage find(int value) throws Exception {

        Future result = Patterns.ask(actorSystemService.getRootNode(), new ContainsMessage(value), new Timeout(5, TimeUnit.SECONDS));
        return (ResponseMessage) Await.result(result, new FiniteDuration(5, TimeUnit.SECONDS));
    }

    public void delete(int value) {
        actorSystemService.getRootNode().tell(new DeleteMessage(value), null);
    }

    public void cleanup() {
        ActorRef oldRoot = actorSystemService.recreateRoootNode();
        ActorRef rootNode = actorSystemService.getRootNode();
        oldRoot.tell(new CleanupMessage(), rootNode);
    }
}
