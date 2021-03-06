package io.github.abiadasi.ToDoApp.controller;

import io.github.abiadasi.ToDoApp.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
class InfoController {
    @Autowired
    private DataSourceProperties dataSource;
    private TaskConfigurationProperties myProp;

    InfoController(final DataSourceProperties dataSource, final TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/url")
    String ulr(){
        return dataSource.getUrl();
    }

    @GetMapping("/prop")
    boolean myProp(){
        return myProp.getTemplate().isAllowMultipleTasks();
    }

}
