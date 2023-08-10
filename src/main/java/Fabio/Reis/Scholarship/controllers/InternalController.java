package Fabio.Reis.Scholarship.controllers;


import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.services.Internal.impl.InternalService_i;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/internals")
public class InternalController {

    private InternalService_i internalService;
    public InternalController(InternalService_i internalService){
        this.internalService= internalService;
    }

    @PostMapping
    public ResponseEntity<InternalDTO> createStudent(@RequestBody InternalRequestDTO internalRequest) {
        ResponseEntity<InternalDTO> newStudent = internalService.create(internalRequest);
        return newStudent;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternal(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        ResponseEntity<Void> deletedInternal = internalService.delete(id);
        return deletedInternal;
    }
}


