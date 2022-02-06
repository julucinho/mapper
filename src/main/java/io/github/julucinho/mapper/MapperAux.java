package io.github.julucinho.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.julucinho.mapper.exceptions.JsonProcessingRuntimeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperAux {

    private ObjectMapper objectMapper;
    private Object source;

    public static MapperAux of(ObjectMapper objectMapper, Object source){
        var mapperAux = new MapperAux();
        mapperAux.objectMapper = objectMapper;
        mapperAux.source = source;
        return mapperAux;
    }

    public <T> T to(Class<T> typeDestination){
        return this.objectMapper.convertValue(this.source, typeDestination);
    }

    public String toJson(){
        try {
            return this.objectMapper.writeValueAsString(this.source);
        } catch (JsonProcessingException exception) {
            throw new JsonProcessingRuntimeException(exception.getMessage());
        }
    }

}
