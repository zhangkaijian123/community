package com.zkj.demo.Service;

import com.zkj.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User,Integer> {
    User findByEmail(String email);
    User findByName(String name);
    void deleteByEmail(String email);
}
