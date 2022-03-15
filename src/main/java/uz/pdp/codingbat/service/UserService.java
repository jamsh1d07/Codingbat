package uz.pdp.codingbat.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Users;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.UserDTO;
import uz.pdp.codingbat.repository.UserRepository;

import javax.validation.constraints.NotNull;

@Service

public class UserService {

    @Autowired
    UserRepository userRepository;

    public ApiResponse all() {
        return new ApiResponse("all",true,userRepository.findAll());
    }

    public ApiResponse one(Integer id) {
        if (userRepository.existsById(id)) {
            return new ApiResponse("found",true,userRepository.getById(id));
        }
        return new ApiResponse("not found",false);
    }

    public ApiResponse delete(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new ApiResponse("deleted",true);
        }
        return new ApiResponse("not found",false);
    }

    public ApiResponse add(@NotNull UserDTO dto) {
        if (!userRepository.existsByEmail(dto.getEmail())) {
            Users user = new Users();

             user.setEmail(dto.getEmail());
             user.setPassword(dto.getPassword());

            Users save = userRepository.save(user);

            return new ApiResponse("added",true,save);
        }
        return new ApiResponse("already exists",false);
    }

    public ApiResponse edit(Integer id, UserDTO dto) {
        if (userRepository.existsById(id)) {
            Users user = userRepository.getById(id);
            if (!userRepository.existsByEmailAndIdNot(dto.getEmail(),id)) {

                user.setEmail(dto.getEmail());
                user.setPassword(dto.getPassword());

                Users save = userRepository.save(user);

                return new ApiResponse("edited",true,save);
            }
        }
        return new ApiResponse("something went wrong",false);
    }
}
