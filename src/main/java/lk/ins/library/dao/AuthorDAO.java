package lk.ins.library.dao;

import lk.ins.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-24
 **/
public interface AuthorDAO extends JpaRepository<Author, Integer> {

    Optional<Author> findAuthorByName(String name);
}
