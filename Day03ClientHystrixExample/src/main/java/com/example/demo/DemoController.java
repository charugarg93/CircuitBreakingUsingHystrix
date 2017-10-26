package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {
   private RestTemplate restTemplate = new RestTemplate();


    @RequestMapping("/hello/{name}")
    @HystrixCommand(fallbackMethod = "callFallback")
    public String sayHello(@PathVariable String name){

        String url= "http://localhost:8080/hello/"+name;
        ResponseEntity<String> output = restTemplate.getForEntity(url, String.class);
        return output.getBody();
    }

    public String callFallback(String name, Throwable ex){
        String output="Hey " + name + "the service is down right now.. Please ty in sometime !!";
        output += "Error: " + ex.getMessage();
        return output;
    }
}
