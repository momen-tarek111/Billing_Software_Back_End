package MomenTarek.billingsoftware.repository;

import MomenTarek.billingsoftware.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByCategoryCode(String categoryCode);
}
