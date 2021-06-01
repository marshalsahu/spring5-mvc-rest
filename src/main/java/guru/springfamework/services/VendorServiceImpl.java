package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper=VendorMapper.INSTANCE;
    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO =  vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        if(!vendorOptional.isPresent()){
            throw new ResourceNotFoundException();
        }
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendorOptional.get());
        vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));
        return vendorDTO;
    }

    public String getVendorUrl(Long id){
        return "/api/v1/vendors/"+id;
    }

    @Override
    public VendorDTO addVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO));
        VendorDTO vendorDTO1 = vendorMapper.vendorToVendorDTO(vendor);
        vendorDTO1.setVendorUrl(getVendorUrl(vendorDTO1.getId()));
        return vendorDTO1;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = VendorMapper.INSTANCE.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if(vendorDTO.getName()!=null){
                        vendor.setName(vendorDTO.getName());
                    }
                    VendorDTO vendorDTO1 = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                    return vendorDTO1;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendor(Long id) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        if(vendorOptional.isPresent()){
           vendorRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException();
        }
    }
}
