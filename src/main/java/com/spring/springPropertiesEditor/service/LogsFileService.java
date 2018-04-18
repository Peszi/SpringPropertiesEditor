package com.spring.springPropertiesEditor.service;

import java.io.File;
import java.io.FileNotFoundException;

public interface LogsFileService {
    File getAuditLogFile() throws FileNotFoundException;
    File getApplicationLogFile() throws FileNotFoundException;
}
