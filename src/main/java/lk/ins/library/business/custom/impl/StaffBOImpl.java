package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.StaffBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.StaffDAO;
import lk.ins.library.dao.UserDAO;
import lk.ins.library.dto.StaffDTO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Role;
import lk.ins.library.entity.Staff;
import lk.ins.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@Service
@Transactional
public class StaffBOImpl implements StaffBO {

    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EntityDTOMapper mapper;

    @Transactional
    @Override
    public void saveStaff(StaffDTO staffDTO, UserDTO userDTO) throws Exception {
        Staff staff = mapper.getStaff(staffDTO);
        staff.setCreatedAt(new Date(System.currentTimeMillis()));
        staffDAO.save(staff);

        User user = mapper.getUser(userDTO);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        userDAO.save(user);
    }

    @Transactional
    @Override
    public void updateStaff(StaffDTO dto, UserDTO userDTO) throws Exception {
        Staff savedStaff = staffDAO.getOne(dto.getId());
        Staff staff = mapper.getStaff(dto);
        staff.setCreatedAt(savedStaff.getCreatedAt());
        staffDAO.save(staff);

        User savedUser = userDAO.getOne(userDTO.getUsername());
        User user = mapper.getUser(userDTO);
        user.setCreatedAt(savedUser.getCreatedAt());
        userDAO.save(user);
    }

    @Transactional
    @Override
    public void deleteStaff(String id) throws Exception {
        staffDAO.deleteById(id);
        userDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StaffDTO> findAllStaffs(Pageable page) throws Exception {
        return staffDAO.findAll(page).stream().map(staff -> mapper.getStaffDTO(staff)).collect(Collectors.toList());
    }

    @Override
    public StaffDTO findStaff(String id) throws Exception {
        StaffDTO staffDTO = staffDAO.findById(id).map(staff -> mapper.getStaffDTO(staff)).get();
        User staffUser = userDAO.getOne(id);
        staffDTO.setRole(staffUser.getRole());
        staffDTO.setActive(staffUser.isActive());
        return staffDTO;
    }

    @Override
    public List<StaffDTO> findAllActiveStaff(boolean status,Pageable page) throws Exception {
        List<User> list = userDAO.findStaffByActive(status).stream().collect(Collectors.toList());
        List<String> staffIdList = new ArrayList<>();
        for (User user : list) {
            staffIdList.add(user.getUsername());
        }
        List<StaffDTO> staffDTOS = staffDAO.getAllByActiveStatus(staffIdList, page).stream().map(staff -> mapper.getStaffDTO(staff)).collect(Collectors.toList());
        for (StaffDTO staffDTO : staffDTOS) {
            staffDTO.setActive(status);
            staffDTO.setRole(Role.STAFF);
        }
        return staffDTOS;
    }

    @Override
    public void updateStaffImage(String id, String imagePath) throws Exception {
        staffDAO.updateStaffImage(id,imagePath);
    }

    @Override
    public long countAllStaffs(boolean status) throws Exception {
        return userDAO.countAllStaffsByActiveStatus(status);
    }

    @Override
    public int countStaffByRegNo(String regNo) throws Exception {
        return staffDAO.countAllByRegNo(regNo);
    }

    @Override
    public void deleteStaffImage(String regNo) throws Exception {
        staffDAO.deleteStaffImage(regNo);
    }

    @Override
    public int countStaffByName(String name) throws Exception {
        return staffDAO.countAllByName(name);
    }

    @Override
    public List<StaffDTO> searchStaffByRegNo(String regNo, Pageable page) throws Exception {
        return staffDAO.getAllByRegNo(regNo, page).stream().map(staff -> mapper.getStaffDTO(staff)).collect(Collectors.toList());
    }

    @Override
    public List<StaffDTO> searchStaffByName(String name, Pageable page) throws Exception {
        return staffDAO.getAllByName(name, page).stream().map(staff -> mapper.getStaffDTO(staff)).collect(Collectors.toList());
    }

    @Override
    public int countAllStaffsBetweenCreatedAtDates(Date startDate, Date endDate) throws Exception {
        return staffDAO.countAllStaffsBetweenCreatedAtDates(startDate, endDate);
    }
}
