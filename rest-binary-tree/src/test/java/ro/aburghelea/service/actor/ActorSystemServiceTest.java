package ro.aburghelea.service.actor;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.aburghelea.akka.message.ContainsMessage;
import ro.aburghelea.akka.message.InsertMessage;
import ro.aburghelea.akka.message.ResponseMessage;
import ro.aburghelea.config.ApplicationConfig;
import ro.aburghelea.config.WebAppInitializer;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppInitializer.class, ApplicationConfig.class})
public class ActorSystemServiceTest {

    @Inject
    ActorSystemService actorSystemService;

    @Before
    public void setUp() throws Exception {
        System.out.println("==================");
        ActorRef rootNode = actorSystemService.getRootNode();
        rootNode.tell(new InsertMessage(5), null);
        rootNode.tell(new InsertMessage(7), null);
        rootNode.tell(new InsertMessage(4), null);
        rootNode.tell(new InsertMessage(8), null);
        rootNode.tell(new InsertMessage(7), null);
        rootNode.tell(new InsertMessage(5), null);
        rootNode.tell(new InsertMessage(7), null);
        rootNode.tell(new InsertMessage(4), null);
        rootNode.tell(new InsertMessage(8), null);
        rootNode.tell(new InsertMessage(7), null);
        System.out.println("==================");
    }

    @Test
    public void testRunQueryies() throws Exception {
        queryFor(5);
        queryFor(8);
        queryFor(4);
        queryFor(100);
        queryFor(-100);


    }

    private void queryFor(int value) {
        System.out.println("*****************");
        ActorRef rootNode = actorSystemService.getRootNode();

        Future result = Patterns.ask(rootNode, new ContainsMessage(value), new Timeout(100, TimeUnit.SECONDS));


        try {
            ResponseMessage message = (ResponseMessage) Await.result(result, new FiniteDuration(1, TimeUnit.SECONDS));
            System.out.println("=========> Got answer message for " + value + " " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("*****************");
    }
}