package com.microservice.product_service.service.serviceImpl;

import com.microservice.product_service.DTO.ManufacturerDTO;
import com.microservice.product_service.mapper.ManufacturerMapper;
import com.microservice.product_service.modal.Manufacturer;
import com.microservice.product_service.repository.ManufacturerRepository;
import com.microservice.product_service.service.ManufacturerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerRepository  manufacturerRepository;

    @Override
    public Page<ManufacturerDTO> getAllManufacturers(Pageable page) {
        return manufacturerRepository.findAll(page)
                .map(ManufacturerMapper::toDTO);
    }

    @Override
    public ManufacturerDTO getManufacturerById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Khong tim thấy nhà sản xuất"));
        return ManufacturerMapper.toDTO(manufacturer);
    }

    @Override
    public ManufacturerDTO createManufacturer(ManufacturerDTO dto) {
        if(manufacturerRepository.existsByTenNhaSX(dto.getTenNhaSX())){
            throw new IllegalArgumentException("Nhà sản xuất đã tồn tại");
        }
        Manufacturer manufacturer = ManufacturerMapper.toEntity(dto);
        return ManufacturerMapper.toDTO(manufacturerRepository.save(manufacturer));
    }

    @Override
    public ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO dto) {
        Manufacturer p = manufacturerRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Không tìm thấy nhà sản xuất"));
        p.setTenNhaSX(dto.getTenNhaSX());
        p.setNoiSX(dto.getNoiSX());

        return ManufacturerMapper.toDTO(manufacturerRepository.save(p));
    }

    @Override
    public void deleteManufacturerByID(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
