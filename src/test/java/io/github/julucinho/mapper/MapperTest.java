package io.github.julucinho.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MapperTest {

    @Test
    @DisplayName("Should return not null instance of MapperAux")
    void shouldReturnNotNullInstanceOfMapperAux(){
        var result = Mapper.map(new Object());
        Assertions.assertNotNull(result);
    }

}
