//package com.alterra.demo.service;
//
//import com.alterra.demo.repository.*;
//import com.alterra.demo.domain.dao.*;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest
//@AutoConfigureMockMvc
//public class UserServiceTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UsersRepository usersRepository;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    public void createNewUser_Test(){
//        UsersDao usersDao = new UsersDao();
//        usersDao.setId(1L);
//        usersDao.setName("Laz");
//        usersDao.setCity(123);
//        usersRepository.save(usersDao);
//        org.junit.jupiter.api.Assertions.assertNotNull(usersRepository.findById(1L).get());
//    }
//
//    @Test
//    public void testReadAllUser_Test() {
//        List<UsersDao> usersDaoList = usersRepository.findAll();
//        Assertions.assertThat(usersDaoList).size().isGreaterThan(0);
//    }
//
//    @Test
//    public void testDeleteUser_Test() {
//        usersRepository.deleteById(1L);
//        Assertions.assertThat(usersRepository.existsById(1L)).isFalse();
//    }
//}