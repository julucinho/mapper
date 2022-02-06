package io.github.julucinho.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.julucinho.mapper.exceptions.JsonProcessingRuntimeException;
import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class MapperAuxTest {

    SomePojo somePojo;
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp(){
        this.somePojo = SomePojo.builder().name("julucinho").uuid(this.uuid.toString()).build();
    }

    @Test
    @DisplayName("Static constructor should return not null instance")
    void staticConstructorShouldReturnNotNullInstance(){
        var result = MapperAux.of(ObjectMapperFactory.createNewObjectMapper(), this.somePojo);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Should map to json")
    void shouldMapToJson(){
        var expectedValue = "{\"name\":\"julucinho\",\"uuid\":\"".concat(this.uuid.toString()).concat("\"}");
        var result = MapperAux.of(ObjectMapperFactory.createNewObjectMapper(), this.somePojo).toJson();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedValue, result);
    }

    @Test
    @DisplayName("Should map to SomeOtherPojo")
    void shouldMapToSomeOtherPojo(){
        var result = MapperAux.of(ObjectMapperFactory.createNewObjectMapper(), this.somePojo).to(SomeOtherPojo.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(this.somePojo.name, result.name);
        Assertions.assertEquals(this.somePojo.uuid, result.uuid);
    }

    @Test
    @DisplayName("Should map to SomeOtherPojoMissingField")
    void shouldMapToSomeOtherPojoMissingField(){
        var result = MapperAux.of(ObjectMapperFactory.createNewObjectMapper(), this.somePojo).to(SomeOtherPojoMissingField.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(this.somePojo.name, result.name);
    }

    @Test
    @DisplayName("Should throw JsonProcessingRuntimeException")
    void shouldThrowJsonProcessingRuntimeException() throws JsonProcessingException {
        var mockObjectMapper = Mockito.mock(ObjectMapper.class);
        var mockJsonProcessingException = Mockito.mock(JsonProcessingException.class);
        Mockito.when(mockObjectMapper.writeValueAsString(ArgumentMatchers.any())).thenThrow(mockJsonProcessingException);
        Mockito.when(mockJsonProcessingException.getMessage()).thenReturn("test");
        var mapperAux = MapperAux.of(mockObjectMapper, this.somePojo);
        Assertions.assertThrows(JsonProcessingRuntimeException.class, mapperAux::toJson);
    }

    @Getter
    @Setter
    @Builder
    private static class SomePojo{

        private String name;
        private String uuid;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SomeOtherPojo{

        private String name;
        private String uuid;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SomeOtherPojoMissingField{

        private String name;

    }

}
