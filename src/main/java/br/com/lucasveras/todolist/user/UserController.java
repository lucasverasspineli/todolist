package br.com.lucasveras.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository iUserRepository;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.iUserRepository.findByUserName(userModel.getUserName());
        // Validação para quando o usuário já existir!
        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe este usuário!");
        }
        var userCreated = this.iUserRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

}
