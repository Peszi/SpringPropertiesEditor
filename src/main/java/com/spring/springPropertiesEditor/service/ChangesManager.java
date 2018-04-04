package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Log;
import com.spring.springPropertiesEditor.respository.ChangeLogRepository;
import org.springframework.stereotype.Component;

@Component
public class ChangesManager {

    private ChangeLogRepository changeChangeLogRepository;

    public ChangesManager(ChangeLogRepository changeChangeLogRepository) {
        this.changeChangeLogRepository = changeChangeLogRepository;
    }

    public void addChange(Log changeLog) {
        this.changeChangeLogRepository.save(changeLog);
    }

//    private void savePropertiesAsJson(String filePath) {
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + FILE_JSON_EXT));
//            fileOutputStream.write(new ObjectMapper().writeValueAsString(this.properties).getBytes());
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void savePropertiesAsYaml(String filePath) {
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + FILE_YAML_EXT));
//            new ObjectMapper(new YAMLFactory()).writeValue(fileOutputStream, this.properties);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
