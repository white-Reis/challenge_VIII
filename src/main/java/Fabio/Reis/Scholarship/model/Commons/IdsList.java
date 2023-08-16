package Fabio.Reis.Scholarship.model.Commons;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class IdsList {
    Set<Long> ids= new HashSet<>();
}