package com.scasmar.carregistry.rest;

import com.scasmar.carregistry.controller.BrandController;
import com.scasmar.carregistry.controller.mapper.BrandMapper;
import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BrandController brandController;
    @Mock
    private BrandService brandService;
    @Mock
    private BrandMapper brandMapper;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "CLIENT")
    void test_getBrandById() throws Exception{
        Brand brand = new Brand();
        brand.setName("BMW");

        when(brandService.getBrandById(2)).thenReturn(brand);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/car/2"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", "BMW"));
    }
}