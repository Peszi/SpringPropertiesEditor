package com.spring.springPropertiesEditor.respository;

import com.spring.springPropertiesEditor.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

}
