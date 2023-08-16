package Fabio.Reis.Scholarship.controllers;


import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.services.Internal.impl.InternalService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/internals")
public class InternalController {

    private InternalService internalService;

    InternalController(InternalService internalService) {
        this.internalService = internalService;
    }

    @PostMapping
    ResponseEntity<InternalDTO> createStudent(@RequestBody InternalRequestDTO internalRequest) {
        ResponseEntity<InternalDTO> newStudent = internalService.create(internalRequest);
        return newStudent;
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteInternal(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        ResponseEntity<Void> deletedInternal = internalService.delete(id);
        return deletedInternal;
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateInternal(@PathVariable Long id, @RequestBody InternalRequestDTO internalRequest) throws ChangeSetPersister.NotFoundException {
        return internalService.update(id, internalRequest);
    }
}



