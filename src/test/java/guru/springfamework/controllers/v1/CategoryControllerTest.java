package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends TestCase {

    private final static Long ID = 1L;
    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testListCategories() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setName("Jim");
        categoryDTO1.setId(ID);
        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setName("Jack");
        categoryDTO2.setId(2L);

        List<CategoryDTO> categoryDTOList = Arrays.asList(categoryDTO1, categoryDTO2);

        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories",hasSize(2)));

    }

    @Test
    public void testGetCategoriesByName() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Jim");
        categoryDTO.setId(ID);

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/Jim"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Jim")));
    }

}