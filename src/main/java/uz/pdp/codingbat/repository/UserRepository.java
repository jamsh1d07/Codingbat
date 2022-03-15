package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Integer id);
}
