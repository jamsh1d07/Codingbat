package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Problem;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.ProblemDTO;
import uz.pdp.codingbat.repository.CategoryRepository;
import uz.pdp.codingbat.repository.ProblemRepository;
import uz.pdp.codingbat.repository.UserRepository;

@Service
public class ProblemService {
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse all() {
        return new ApiResponse("all",true,problemRepository.findAll());
    }

    public ApiResponse one(Integer id) {
        if (problemRepository.existsById(id)) {
            return new ApiResponse("found",true,problemRepository.getById(id));
        }
        return new ApiResponse("not found",false);
    }

    public ApiResponse delete(Integer id) {
        if (problemRepository.existsById(id)) {
            problemRepository.deleteById(id);
            return new ApiResponse("deleted",true);
        }
        return new ApiResponse("not found",false);
    }

    public ApiResponse add(ProblemDTO dto) {
        if (problemRepository.existsByName(dto.getName())) {
            Problem problem = new Problem();
            return Duplicable_code(dto, problem);
        }
        return new ApiResponse("something went wrong",false);
    }

    public ApiResponse edit(Integer id, ProblemDTO dto) {
        if (problemRepository.existsById(id)) {
            Problem problem = problemRepository.getById(id);
            if (problemRepository.existsByNameAndIdNot(dto.getName(),id)) {

                Duplicable_code(dto, problem);
            }
        }
        return new ApiResponse("something went wrong",false);
    }

    private ApiResponse Duplicable_code(ProblemDTO dto, Problem problem) {
        problem.setName(dto.getName());
        problem.setDescription(dto.getDescription());
        problem.setSolution(dto.getSolution());
        if (categoryRepository.existsById(dto.getCategoryId())){
            problem.setCategory(categoryRepository.getById(dto.getCategoryId()));
            if (userRepository.existsById(dto.getUserId())) {
                problem.setUser(userRepository.getById(dto.getUserId()));
                Problem save = problemRepository.save(problem);

                return new ApiResponse("added",true,save);
            }
        }
        return new ApiResponse("something went wrong",false);
    }
}
