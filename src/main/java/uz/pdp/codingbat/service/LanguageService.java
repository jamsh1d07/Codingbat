package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Language;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.LanguageDTO;
import uz.pdp.codingbat.repository.LanguageRepository;

@Service
public class LanguageService {
  @Autowired
  LanguageRepository languageRepository;

    public ApiResponse all() {
        return  new ApiResponse("all",true,languageRepository.findAll());
    }

    public ApiResponse one(Integer id) {
        if (languageRepository.existsById(id)) {
            return new ApiResponse("found",true,languageRepository.getById(id));
        }
        return new ApiResponse("not found", false);
    }

    public ApiResponse delete(Integer id) {
        if (languageRepository.existsById(id)) {
            languageRepository.deleteById(id);
            return new ApiResponse("deleted",true);
        }
        return new ApiResponse("not found", false);    }

    public ApiResponse add(LanguageDTO dto) {
        if (!languageRepository.existsByName(dto.getName())) {
            Language language = new Language();
            language.setName(dto.getName());
            Language save = languageRepository.save(language);

            return new ApiResponse("added",true,save);
        }
        return new ApiResponse("something went wrong",false);
    }

    public ApiResponse edit(Integer id, LanguageDTO dto) {
        if (languageRepository.existsById(id)) {
            Language language = languageRepository.getById(id);
            if (!languageRepository.existsByName(dto.getName())) {

                language.setName(dto.getName());
                Language save = languageRepository.save(language);

                return new ApiResponse("edited",true,save);
            }
        }
        return new ApiResponse("something went wrong",false);
    }
}
