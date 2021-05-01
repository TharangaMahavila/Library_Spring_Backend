package lk.ins.library.api;

import lk.ins.library.business.custom.BookBO;
import lk.ins.library.business.custom.BookReferenceBO;
import lk.ins.library.business.custom.UserBO;
import lk.ins.library.dto.BookDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Role;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@ControllerAdvice
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookBO bookBO;
    @Autowired
    private BookReferenceBO bookReferenceBO;
    @Value("${path.images}")
    private String imagePath;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> getAllBooks(Pageable page)throws Exception{
        return new ResponseEntity<>(bookBO.findAllBooks(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getBook(@PathVariable String id)throws Exception{
        try{
            return new ResponseEntity<>(bookBO.findBook(id),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No book found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveBook(@Valid @RequestBody BookDTO bookDTO) throws Exception{
        try {
            bookBO.findBook(bookDTO.getBookId());
            return new ResponseEntity<>("Book already saved!",HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            bookDTO.setImage(null);
            bookBO.saveBook(bookDTO);
            return new ResponseEntity<>(bookDTO,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteBook(@PathVariable String id)throws Exception{
        try{
            bookBO.findBook(id);
            bookBO.deleteBook(id);
            return new ResponseEntity<>("Successfully deleted the book",HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Book found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> updateBook(@Valid @RequestBody BookDTO bookDTO,@PathVariable String id) throws Exception{
        if(!id.equals(bookDTO.getBookId())){
            return new ResponseEntity<>("Mismatched id",HttpStatus.BAD_REQUEST);
        }
        try{
            bookBO.findBook(id);
            bookBO.updateBook(bookDTO);
            return new ResponseEntity<>(bookDTO,HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No book found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PostMapping(value = "/image/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> saveImage(@RequestParam("image") MultipartFile image, @PathVariable String id) throws Exception{
        try{
            BookDTO book = bookBO.findBook(id);
            String filename = image.getOriginalFilename();
            String extentsion = FilenameUtils.getExtension(filename).toLowerCase();
            if(!(extentsion.equals("jpg") || extentsion.equals("jpeg") || extentsion.equals("png"))){
                return new ResponseEntity<>("File Format is not allowed",HttpStatus.BAD_REQUEST);
            }
            String savePath = imagePath+File.separator+"Books"+File.separator+ book.getEnglishName()+"_"
                    +new Timestamp(System.currentTimeMillis()) +"."+FilenameUtils.getExtension(filename);
            Path path = Paths.get(imagePath+File.separator+"Books");
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
            File file = new File(savePath);
            FileUtils.writeByteArrayToFile(file,image.getBytes());
            bookBO.updateBookImage(id,savePath);
            return new ResponseEntity<>("Successfully updated the book image",HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No book found!",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadError(RedirectAttributes ra){
        ra.addFlashAttribute("error","Image size should be less than "+maxFileSize);
        return ra.getFlashAttributes().get("error").toString();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/image/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBookImage(@PathVariable String id)throws Exception{
        try {
            BookDTO book = bookBO.findBook(id);
            String imagePath = book.getImage();
            if(imagePath!=null && !imagePath.equals("")){
                File file = new File(imagePath);
                if(!file.exists()){
                    return new ResponseEntity<>("Book image has been missed",HttpStatus.NOT_FOUND);
                }
                byte[] image = FileUtils.readFileToByteArray(file);

                return new ResponseEntity<>(image,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No book image found",HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No book found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @GetMapping(value = "/allBooks",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getAllCustomBooks(@RequestParam("status") boolean status,Pageable page)throws Exception{
        return new ResponseEntity<>(bookBO.getBooksByStatus(status,page),HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/searchByName",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getBookByName(@RequestParam String name, Pageable page)throws Exception{
        return new ResponseEntity<>(bookBO.getBookByName(name,page),HttpStatus.OK);
    }

    @GetMapping(value = "/searchByRefNo",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getBookByRefNo(@RequestParam String name, Pageable page)throws Exception{
        return new ResponseEntity<>(bookBO.getBookByRefNo(name,page),HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/searchCountByName",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getBookCountByName(@RequestParam String name)throws Exception{
        return new ResponseEntity<>(bookBO.getBookCountByName(name),HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/searchByAuthor",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getBookByAuthor(@RequestParam String name, Pageable page)throws Exception{
        return new ResponseEntity<>(bookBO.getBookByAuthor(name,page),HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/searchCountByAuthor",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getBookCountByAuthor(@RequestParam String name)throws Exception{
        return new ResponseEntity<>(bookBO.getBookCountByAuthor(name),HttpStatus.OK);
    }

    @GetMapping(value = "/categories/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBookCategories(@PathVariable String id) throws Exception{
        return new ResponseEntity<>(bookBO.getBookCategories(id),HttpStatus.OK);
    }

    @GetMapping("/count")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllBookCountByStatus(@RequestParam(required = false) boolean status
            ,@RequestParam(required = false) String refNo) throws Exception{

        if(refNo != null){
            return new ResponseEntity<>(bookBO.getBookCountByRefname(refNo),HttpStatus.OK);
        }

        return new ResponseEntity<>(bookBO.getAllBookCountByStatus(status),HttpStatus.OK);
    }

    @GetMapping(value = "/count/between")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllTodayAddedBooks(@RequestParam(value = "startDate") long startDate
            ,@RequestParam(value = "endDate") long endDate) throws Exception{

        Date start = new Date(startDate);
        Date end = new Date(endDate);
        return new ResponseEntity<>(bookReferenceBO.countBooksBetweenPeriod(start,end),HttpStatus.OK);
    }
}
