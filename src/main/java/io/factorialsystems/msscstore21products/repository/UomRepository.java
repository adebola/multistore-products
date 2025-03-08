package io.factorialsystems.msscstore21products.repository;


import com.github.pagehelper.Page;
import io.factorialsystems.msscstore21products.model.Uom;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UomRepository {
    public void save(Uom uom);
    public void update (Uom uom);
    public Uom findById(String id);
    Page<Uom> search(String search);
    Page<Uom> findAll();
    void suspend(String id);
    void unsuspend(String id);
    void delete (Map<String, String> parameters);
}
