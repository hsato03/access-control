package com.ufsc.access.control.credit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_credit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credit {

    @Id
    private UUID id;
    private Integer value;
}
