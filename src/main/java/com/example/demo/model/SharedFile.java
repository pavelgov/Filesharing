package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "file_t")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedFile {

    @Id
    @NotNull
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private String owner;

    @NotNull
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_holders")
    @OneToMany(mappedBy = "fileId")
    private Set<SharedFileHolder> fileHolders;
}
