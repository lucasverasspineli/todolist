package br.com.lucasveras.todolist.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/")
    public void create(@RequestBody UserModel userModel){
        System.out.println(userModel.getUserName());
    }

}
