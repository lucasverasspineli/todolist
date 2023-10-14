package br.com.lucasveras.todolist.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_users")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String userName;
    private String name;
    private String password;

    //Data de quando o dado for criado
    @CreationTimestamp
    private LocalDateTime createdAt;
}
