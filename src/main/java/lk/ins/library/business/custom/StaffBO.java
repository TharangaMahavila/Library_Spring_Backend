package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.StaffDTO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
public interface StaffBO extends SuperBO {

    public void saveStaff(StaffDTO staffDTO, UserDTO userDTO) throws Exception;
    public void updateStaff(StaffDTO dto,UserDTO userDTO) throws Exception;
    public void deleteStaff(String id) throws Exception;
    public List<StaffDTO> findAllStaffs(Pageable page) throws Exception;
    public StaffDTO findStaff(String id) throws Exception;

    public List<StaffDTO> findAllActiveStaff(boolean status,Pageable page) throws Exception;
    public void updateStaffImage(String id, String imagePath) throws Exception;
    public void deleteStaffImage(String regNo) throws Exception;

    public long countAllStaffs(boolean status) throws Exception;

    public int countStaffByRegNo(String regNo) throws Exception;
    public int countStaffByName(String name) throws Exception;

    public List<StaffDTO> searchStaffByRegNo(String regNo, Pageable page) throws Exception;
    public List<StaffDTO> searchStaffByName(String name, Pageable page) throws Exception;

    public int countAllStaffsBetweenCreatedAtDates(Date startDate, Date endDate) throws Exception;
}
