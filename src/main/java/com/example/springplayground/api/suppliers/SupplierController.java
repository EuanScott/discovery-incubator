package com.example.springplayground.api.suppliers;

import com.example.springplayground.controller.api.SuppliersApi;
import com.example.springplayground.controller.model.SupplierDTO;
import com.example.springplayground.service.model.Supplier;
import com.example.springplayground.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController implements SuppliersApi {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public ResponseEntity<List<SupplierDTO>> getSupplierList() {
        List<Supplier> response = supplierService.getSuppliers();
        return ResponseEntity.ok(MapperUtil.mapList(response, SupplierDTO.class));
    }
}
