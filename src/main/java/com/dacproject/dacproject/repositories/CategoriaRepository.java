package com.dacproject.dacproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dacproject.dacproject.entities.Categoria;

/*Interface que herda componentes do spring: JPA
 * Existem métodos já pré definidos
 */

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}
