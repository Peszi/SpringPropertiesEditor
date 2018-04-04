package com.spring.springPropertiesEditor.respository;

import com.spring.springPropertiesEditor.model.Log;
import org.springframework.data.repository.CrudRepository;

public interface ChangeLogRepository extends CrudRepository<Log, Long> {
}
