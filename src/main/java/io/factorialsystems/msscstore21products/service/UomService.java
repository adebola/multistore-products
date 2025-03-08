package io.factorialsystems.msscstore21products.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.UomClientDto;
import io.factorialsystems.msscstore21products.mapper.UomMapper;
import io.factorialsystems.msscstore21products.model.Uom;
import io.factorialsystems.msscstore21products.repository.UomRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UomService {
    private final UomMapper uomMapper;
    private final UomRepository uomRepository;

    public void clientSave(UomClientDto uomClientDto) {
        Uom uom = uomMapper.toModel(uomClientDto);
        uom.setCreatedBy(JwtTokenWrapper.getUserName());
        uom.setId(UUID.randomUUID().toString());

        log.info("Saving Unit of Measure : {}", uom);
        uomRepository.save(uom);
    }

    public void clientUpdate(String id, UomClientDto uomClientDto) {
        Uom uom = uomMapper.toModel(uomClientDto);
        uom.setId(id);

        log.info("Update Unit of Measure {}", id);
        uomRepository.update(uom);
    }

    public PagedDto<UomClientDto> findAll(Integer pageNumber, Integer pageSize) {
        log.info("Find All Unit of Measure, PageNumber {}, PageSize {}", pageNumber, pageSize);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(uomRepository.findAll());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PagedDto<UomClientDto> search(Integer pageNumber, Integer pageSize, String search) {
        log.info("Search Unit of Measure, PageNumber {}, PageSize {}, Search {}", pageNumber, pageSize, search);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(uomRepository.search(search));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public UomClientDto findById(String id) {
        log.info("Find Unit of Measure By Id {}", id);
        return uomMapper.toClientDto(uomRepository.findById(id));
    }

    public void clientDelete(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("deletedBy", JwtTokenWrapper.getUserName());

        log.info("Delete Unit of Measure {}, Deleted By {}", id, JwtTokenWrapper.getUserName());
        uomRepository.delete(map);
    }

    public void clientSuspend(String id) {
        log.info("Suspend Unit of Measure {}", id);
        uomRepository.suspend(id);
    }

    public void clientUnsuspend(String id) {
        log.info("Un-suspend Unit of Measure {}", id);
        uomRepository.unsuspend(id);
    }

    private PagedDto<UomClientDto> createClientDto(Page<Uom> uoms) {
        List<Uom> result = uoms.getResult();

        PagedDto<UomClientDto> pagedDto = new PagedDto<>();
        pagedDto.setTotalSize((int) uoms.getTotal());
        pagedDto.setPageNumber(uoms.getPageNum());
        pagedDto.setPageSize(uoms.getPageSize());
        pagedDto.setPages(uoms.getPages());
        pagedDto.setList(result.stream().map(uomMapper::toClientDto).toList());

        return pagedDto;
    }
}
