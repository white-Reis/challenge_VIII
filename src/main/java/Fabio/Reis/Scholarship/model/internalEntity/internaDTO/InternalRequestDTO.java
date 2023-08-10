package Fabio.Reis.Scholarship.model.internalEntity.internaDTO;

import Fabio.Reis.Scholarship.model.teamEntity.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
    public class InternalRequestDTO {
        private String name;
        private String lastName;
        private String email;
        private String position;
        private String role;
    }