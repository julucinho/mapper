package io.github.julucinho.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    public static MapperAux map(Object source){
        return MapperAux.of(new ObjectMapper(), source);
    }

}
