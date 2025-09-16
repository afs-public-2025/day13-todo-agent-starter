package com.afs.restapi.agent;

import com.afs.restapi.service.TodoService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MCPConfiguration {
    @Bean
    public ToolCallbackProvider myTools(TodoService todoService) {
        return MethodToolCallbackProvider.builder().toolObjects(todoService).build();
    }
}
