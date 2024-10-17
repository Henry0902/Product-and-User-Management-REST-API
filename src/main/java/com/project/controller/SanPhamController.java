package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.CommonUtils;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.exception.ErrorException;
import com.project.model.dto.ProductInfoDto;
import com.project.model.search.ProductSearch;
import com.project.repository.ProductGroupReponsitory;
import com.project.repository.ProductInfoDtoReponsitory;
import com.project.repository.ProductInfoRepository;
import com.project.security.jwt.AuthTokenFilter;
import com.project.security.jwt.JwtUtils;
import com.project.table.ProductGroup;
import com.project.table.ProductInfo;
import com.project.utils.ConvertDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Date.parse;
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController extends BaseController{
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductInfoDtoReponsitory productInfoDtoRepository;
    @Autowired
    ProductGroupReponsitory productGroupRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    AuthTokenFilter authTokenFilter;
    @Autowired
    JwtUtils jwtUtils;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "")
    public ResponseEntity<?> sanPham(@RequestParam Map<String, String> allParams) {
        Map<String, Object> response = new HashMap<>();
        try {
            Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
            // clear all param if reset
            if (allParams.get("reset") != null) {
                allParams.clear();
            }

            ProductSearch productSearch = objectMapper.convertValue(allParams, ProductSearch.class);
            productSearch.normalize();

            Page<ProductInfoDto> productInfos = productInfoDtoRepository.selectParams(
                    productSearch.getS_pname(),
                    productSearch.getS_porigin(),
                    productSearch.getS_status(),
                    getPageable(allParams, paginate));

            response.put("currentPage", paginate.getPage());
            response.put("totalPage", productInfos.getTotalPages());
            response.put("totalElement", productInfos.getTotalElements());
            response.put("productInfos", productInfos.getContent());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping(value = "/them-moi")
    public ResponseEntity<?> postsanPhamThemMoi(@RequestBody ProductInfo productInfo) {
        Map<String, Object> response = new HashMap<>();
        try {
            checkErrorMessage(productInfo);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            if (productInfo.getGroupId() == 0) throw new Exception("Chọn nhóm cho sản phẩm");
            Optional<ProductInfo> checkProductName = productInfoRepository.findByProductName(productInfo.getProductName());
            if (checkProductName.isPresent()) throw new Exception("Tên sản phẩm đã tồn tại");

            String jwt = authTokenFilter.getJwtFromRequest(request);
            productInfo.setCreateBy(jwtUtils.getUserNameFromJwtToken(jwt));
            productInfo.setCreateTime(new Date());
            productInfo.setProductOrigin(productInfo.getProductOrigin());
            productInfo.setProductDate(productInfo.getProductDate());

            productInfoRepository.save(productInfo);

            response.put("success", "Thêm thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PutMapping(value = "/sua/{id}")
    public ResponseEntity<?> postsanphamSua(@RequestBody ProductInfo productInfo,@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            checkErrorMessage(productInfo);

            Optional<ProductInfo> productInfoDb = productInfoRepository.findById(id);

            updateObjectToObject(productInfoDb.get(), productInfo);

            productInfoRepository.save(productInfoDb.get());

            response.put("success", "Sửa thành công");
            return ResponseEntity.ok(response);
        } catch (ErrorException e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("error", "Lỗi hệ thống");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping(value = "/xoa/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<ProductInfo> productInfo = productInfoRepository.findById(id);
        if (productInfo.isPresent()) {
            productInfoRepository.delete(productInfo.get());
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa thất bại");
        }
    }

    @GetMapping("/api/product-info")
    @ResponseBody
    public long sanphamValid(@RequestParam("name") String name) {
        return productInfoRepository.countByProductName(name);
    }
}
