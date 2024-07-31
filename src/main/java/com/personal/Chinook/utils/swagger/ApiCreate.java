package com.personal.Chinook.utils.swagger;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "В случае успешного создания сущности"),
        @ApiResponse(responseCode = "401", description = "В случае, если пользователь не аутентифицирован"),
        @ApiResponse(responseCode = "403", description = "В случае, если у пользователя недостаточно прав"),
        @ApiResponse(responseCode = "404", description = "В случае, если связанная сущность не найдена")
})
public @interface ApiCreate {

}
