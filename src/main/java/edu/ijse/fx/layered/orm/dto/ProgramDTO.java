package edu.ijse.fx.layered.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramDTO {

    private String id;
    private String name;
    private String duration;
    private double cost;

}
