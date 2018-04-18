package com.spring.springPropertiesEditor.service;

import java.io.File;
import java.io.FileNotFoundException;

public interface LogFileService {
    File getAuditLogFile() throws FileNotFoundException;
    File getApplicationLogFile() throws FileNotFoundException;
}
