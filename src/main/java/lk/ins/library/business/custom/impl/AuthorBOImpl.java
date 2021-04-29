package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.AuthorBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.AuthorDAO;
import lk.ins.library.dto.AuthorDTO;
import lk.ins.library.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-24
 **/
@Transactional
@Service
public class AuthorBOImpl implements AuthorBO {

    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private EntityDTOMapper mapper;

    public AuthorBOImpl() {
    }

    @Override
    public void saveAuthor(AuthorDTO dto) throws Exception {
        Author author = mapper.getAuthor(dto);
        author.setCreatedAt(new Date(System.currentTimeMillis()));
        authorDAO.save(author);
    }

    @Override
    public void updateAuthor(AuthorDTO dto) throws Exception {
        Author author = authorDAO.getOne(dto.getId());
        author.setName(dto.getName());
        authorDAO.save(author);
    }

    @Override
    public void deleteAuthor(int id) throws Exception {
        authorDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDTO> findAllAuthors() throws Exception {
       return authorDAO.findAll().stream().map(author -> mapper.getAuthorDTO(author)).collect(Collectors.toList());
    }

    @Override
    public AuthorDTO findAuthor(int authorId) throws Exception {
        return authorDAO.findById(authorId).map(author -> mapper.getAuthorDTO(author)).get();
    }

    @Override
    public AuthorDTO findAuthorByName(String name) throws Exception {
        return authorDAO.findAuthorByName(name).map(author -> mapper.getAuthorDTO(author)).get();
    }
}
