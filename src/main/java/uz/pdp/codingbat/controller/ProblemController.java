package uz.pdp.codingbat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.ProblemDTO;
import uz.pdp.codingbat.repository.ProblemRepository;
import uz.pdp.codingbat.service.ProblemService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/problem")
@RequiredArgsConstructor
public class ProblemController {
    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemService problemService;

    @GetMapping
    public ResponseEntity<?> all(){
        ApiResponse apiResponse = problemService.all();
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable Integer id){
        ApiResponse apiResponse = problemService.one(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = problemService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @PostMapping
    @Validated // checking valid
    public ResponseEntity<?> add(@RequestBody ProblemDTO dto){
        ApiResponse apiResponse = problemService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@RequestBody ProblemDTO dto){
        ApiResponse apiResponse = problemService.edit(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
