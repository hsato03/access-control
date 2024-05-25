package com.ufsc.access.control.user.model;

import com.ufsc.access.control.user.model.enums.Category;
import com.ufsc.access.control.user.model.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String cpf;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;

    public User(UserDTO userDTO) {
        updateFromDto(userDTO);
    }

    public void updateFromDto(UserDTO userDTO) {
        if (userDTO.cpf() != null) {
            cpf = userDTO.cpf();
        }
        if (userDTO.name() != null) {
            name = userDTO.name();
        }
        if (userDTO.category() != null) {
            category = userDTO.category();
        }
    }
}
