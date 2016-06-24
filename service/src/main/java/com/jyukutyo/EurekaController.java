package com.jyukutyo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaController {

    /**
     * curl -v -X GET http://localhost:8080/jyukutyo
     */
    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    ResponseEntity<?> get(@PathVariable String name) {
        return new ResponseEntity<>(name, HttpStatus.OK);
    }

}
