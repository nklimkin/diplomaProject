package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.VoteService;

@RestController
@RequestMapping(value = ProfileVoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {

    private Logger log = LoggerFactory.getLogger(getClass());

    public static final String URL = "/api/votes";

    private VoteService service;

    @Autowired
    public ProfileVoteController(VoteService service) {
        this.service = service;
    }


}
