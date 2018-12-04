package LearWebMVC.LearWebMVC.controller;

import LearWebMVC.LearWebMVC.exeptions.NotFoundExeption;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
       add(new HashMap<String, String>() {{put("id", "1"); put("text", "First message");}});
       add(new HashMap<String, String>() {{put("id", "2"); put("text", "Twoes message");}});
       add(new HashMap<String, String>() {{put("id", "3"); put("text", "Third message");}});
    }};
    @GetMapping
    public  List<Map<String, String>> list() {
        return messages;
    }
    @GetMapping("{id}") // добавляет мапинг на
    public Map<String, String> getId(@PathVariable String id) {
        return getMessage(id);
    }
    @PostMapping // maping for post request
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }
    @PutMapping("{id}") // maping for put request
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDB = getMessage(id); // мапим ИД из урла
        messageFromDB.putAll(message);
        messageFromDB.put("id", id);
        return messageFromDB;
    }
    @DeleteMapping("{id}") // maping for delete request
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }

    private Map<String, String> getMessage(@PathVariable String id) { // find object on collection
        return messages.stream().filter(messages -> messages.get("id").equals(id)).findFirst().orElseThrow(NotFoundExeption::new);
    }
}

// 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({ text: 'Test'})}).then(result => console.log(result))   Метод fetch для тестирования веба через браузер