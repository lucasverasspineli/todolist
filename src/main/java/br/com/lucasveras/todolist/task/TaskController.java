package br.com.lucasveras.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ITaskRepository iTaskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("Chegou no controller "+request.getAttribute("idUser"));
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
        //Validação para datas
        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio ou de término tem que ser maior que a data atual");
        }
        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início tem que ser menor que a data de término");
        }
        var task = this.iTaskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

}
