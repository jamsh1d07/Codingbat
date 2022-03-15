package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Category;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.CategoryDTO;
import uz.pdp.codingbat.repository.CategoryRepository;
import uz.pdp.codingbat.repository.LanguageRepository;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
     @Autowired
     LanguageRepository languageRepository;


    public ApiResponse all() {
        return  new ApiResponse("all",true,categoryRepository.findAll());
    }

    public ApiResponse one(Integer id) {
        if (categoryRepository.existsById(id)) {
            return new ApiResponse("found",true,categoryRepository.getById(id));
        }
        return new ApiResponse("not found", false);
    }

    public ApiResponse delete(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return new ApiResponse("deleted",true);
        }
        return new ApiResponse("not found", false);    }

    public ApiResponse add(CategoryDTO dto) {
        if (!categoryRepository.existsByName(dto.getName())) {
            Category category = new Category();
            if (languageRepository.existsById(dto.getLanguageId())){

            category.setName(dto.getName());
            category.setLanguage(languageRepository.getById(dto.getLanguageId()));
                Category save = categoryRepository.save(category);
                return new ApiResponse("added",true,save);
            }
        }
        return new ApiResponse("something went wrong",false);
    }

    public ApiResponse edit(Integer id, CategoryDTO dto) {
        if (categoryRepository.existsById(id)) {
            Category category = categoryRepository.getById(id);
            if (!categoryRepository.existsByNameAndIdNot(dto.getName(),id)) {

                category.setName(dto.getName());
                Category save = categoryRepository.save(category);

                return new ApiResponse("edited",true,save);
            }
        }
        return new ApiResponse("something went wrong",false);
    }
}
