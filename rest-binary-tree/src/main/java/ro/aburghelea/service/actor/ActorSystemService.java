package ro.aburghelea.service.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.aburghelea.akka.actor.TreeNode;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;


/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/5/2014
 */
@Service
@Singleton
public final class ActorSystemService {

    private final static String REST_CONTROLLED_ACTOR_SYSTEM_NAME = "restControlledActorSystem";
    private final Logger log = LoggerFactory.getLogger(ActorSystemService.class);
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

    synchronized public ActorRef getRootNode() {
        return rootNode;
    }

    synchronized public ActorRef recreateRoootNode() {
        ActorRef oldRoot = rootNode;
        rootNode = actorSystem.actorOf(Props.create(TreeNode.class, TreeNode.ROOT_NANE));
        return oldRoot;
    }
}
