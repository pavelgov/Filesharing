package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "shared_file_holders_t")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedFileHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    @Column(name = "file_holder_id")
    private String fileHolderId;


    @ManyToOne
    @JoinColumn(name = "file_id")
    private SharedFile fileId;
}
