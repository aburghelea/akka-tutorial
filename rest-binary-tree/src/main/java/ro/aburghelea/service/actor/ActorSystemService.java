package ro.aburghelea.service.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.aburghelea.akka.actor.TreeNode;
import ro.aburghelea.akka.message.ContainsMessage;
import ro.aburghelea.akka.message.InsertMessage;
import ro.aburghelea.akka.message.ResponseMessage;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;


/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/5/2014
 */
@Service
@Singleton
public final class ActorSystemService {

    private final Logger log = LoggerFactory.getLogger(ActorSystemService.class);
    private final static String REST_CONTROLLED_ACTOR_SYSTEM_NAME = "restControlledActorSystem";
    private ActorSystem actorSystem;
    private ActorRef rootNode;

    @PostConstruct
    public void init() {
        log.info("Initiating actor system from within Spring application context");
        actorSystem = ActorSystem.create(REST_CONTROLLED_ACTOR_SYSTEM_NAME);
        rootNode = actorSystem.actorOf(Props.create(TreeNode.class, TreeNode.ROOT_NANE));


        log.info("Actor system initialized");
    }


    @PreDestroy
    public void destroy() {
        log.info("Stopping actor system from within Spring application context");
        actorSystem.shutdown();
        log.info("Actor system stopped");
    }

    public ActorRef getRootNode() {
        return rootNode;
    }
}
