package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.AuthorDTO;
import lk.ins.library.entity.Author;

import java.util.List;
import java.util.Optional;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-24
 **/
public interface AuthorBO extends SuperBO {

    public void saveAuthor(AuthorDTO dto) throws Exception;
    public void updateAuthor(AuthorDTO dto) throws Exception;
    public void deleteAuthor(int authorId) throws Exception;
    public List<AuthorDTO> findAllAuthors() throws Exception;
    public AuthorDTO findAuthor(int authorId) throws Exception;

    public AuthorDTO findAuthorByName(String name) throws Exception;
}
