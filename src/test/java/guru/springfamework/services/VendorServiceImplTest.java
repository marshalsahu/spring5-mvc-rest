package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class VendorServiceImplTest {
    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper=VendorMapper.INSTANCE;

    VendorService vendorService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository);
    }

    @Test
    void getAllVendors() {
        Vendor vendor = new Vendor();
        vendor.setName("Alan");
        Vendor vendor1 = new Vendor();
        vendor1.setName("Charlile");
        List<Vendor> vendorList = Arrays.asList(vendor,vendor1);

        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(2,vendorDTOS.size());
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setName("Alan");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        assertNotNull(vendorDTO);
        assertEquals("Alan",vendorDTO.getName());
    }


}