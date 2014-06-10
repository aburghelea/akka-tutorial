package ro.aburghelea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.aburghelea.akka.message.ResponseMessage;
import ro.aburghelea.service.actor.BinaryTreeService;

@RestController
public class MainController {

    @Autowired
    private BinaryTreeService binaryTreeService;


    @RequestMapping(value = "/insert/{value}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "We will process it, use search to see if it really was inserted")
    public void insert(@PathVariable int value) {
        binaryTreeService.insert(value);
    }

    @RequestMapping(value = "/find/{value}", method = RequestMethod.GET)
    public ResponseMessage find(@PathVariable int value) {
        try {
            return binaryTreeService.find(value);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.ERROR_RESPONSE;
        }
    }

    @RequestMapping(value = "/delete/{value}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Value will be marked as deleted")
    public void delete(@PathVariable int value) {
        binaryTreeService.delete(value);
    }

    @RequestMapping(value = "/cleanup", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Value will be marked as deleted")
    public void cleanup() {
        binaryTreeService.cleanup();
    }

}
