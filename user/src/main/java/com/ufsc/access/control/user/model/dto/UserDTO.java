package com.ufsc.access.control.user.model.dto;

import com.ufsc.access.control.user.model.enums.Category;

public record UserDTO(String cpf, String name, Category category) {

}
