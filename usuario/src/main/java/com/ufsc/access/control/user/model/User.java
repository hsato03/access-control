package com.ufsc.access.control.user.model;

import com.ufsc.access.control.user.model.enums.Category;
import com.ufsc.access.control.user.model.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String cpf;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;

    public User(UserDTO userDTO) {
        cpf = userDTO.cpf();
        name = userDTO.name();
        category = userDTO.category();
    }
}
