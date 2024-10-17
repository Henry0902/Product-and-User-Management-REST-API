package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.common.CommonUtils;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.model.search.ProductGroupSearch;
import com.project.repository.ProductGroupReponsitory;
import com.project.repository.ProductModuleReponsitory;
import com.project.repository.UserGroupPermissionRepository;
import com.project.security.jwt.AuthTokenFilter;
import com.project.security.jwt.JwtUtils;
import com.project.table.ProductGroup;
import com.project.table.ProductModule;
import com.project.table.UserGroupPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/nhom-san-pham")
public class NhomSanPhamController extends BaseController{
    @Autowired
    ProductGroupReponsitory productGroupRepository;
    @Autowired
    ProductModuleReponsitory productModuleRepository;
    @Autowired
    UserGroupPermissionRepository userGroupPermissionRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthTokenFilter authTokenFilter;

    @Autowired
    private HttpServletRequest request;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "")
    public ResponseEntity<?> nhomsanPham(@RequestParam Map<String, String> allParams) {
        Map<String, Object> response = new HashMap<>();
        try {
            Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
            // clear all param if reset
            if (allParams.get("reset") != null) {
                allParams.clear();
            }

            ProductGroupSearch productGroupSearch = objectMapper.convertValue(allParams, ProductGroupSearch.class);
            productGroupSearch.normalize();

            Page<ProductGroup> productGroups = productGroupRepository.selectParams(
                    productGroupSearch.getS_pname(),
                    productGroupSearch.getS_status(),
                    getPageable(allParams, paginate));

            response.put("currentPage", paginate.getPage());
            response.put("totalPage", productGroups.getTotalPages());
            response.put("totalElement", productGroups.getTotalElements());
            response.put("productGroups", productGroups.getContent());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/them-moi")
    public ResponseEntity<?> postNhomsanPhamThemMoi(@RequestBody ProductGroup productGroup,
                                                    @RequestParam(value = "permissions", required = false) List<Long> permissions) {
        Map<String, Object> response = new HashMap<>();
        try {
            productGroup.setCreateTime(new Date());
            String jwt = authTokenFilter.getJwtFromRequest(request);
            productGroup.setCreateBy(jwtUtils.getUserNameFromJwtToken(jwt));
            checkErrorMessage(productGroup);

            productGroupRepository.save(productGroup);

            if(permissions != null) {
                ArrayList<UserGroupPermission> listUserGroupPermission = new ArrayList<>();
                for (Long moduleId : permissions) {
                    UserGroupPermission userGroupPermission = new UserGroupPermission();
                    userGroupPermission.setGroupId(productGroup.getId());
                    userGroupPermission.setModuleId(moduleId);
                    listUserGroupPermission.add(userGroupPermission);
                }
                userGroupPermissionRepository.saveAll(listUserGroupPermission);
            }
            response.put("success", "Thêm thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);
            response.put("productModules", productModules);
            response.put("name", "Thêm mới");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping(value = "/sua/{id}")
    public ResponseEntity<?> postNhomsanPhamSua(@RequestBody ProductGroup productGroup,
                                                @RequestParam(value = "permissions", required = false) List<Long> permissions,@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println(permissions);
            checkErrorMessage(productGroup);

            Optional<ProductGroup> productGroupDb = productGroupRepository.findById(id);

            updateObjectToObject(productGroupDb.get(), productGroup);

            productGroupRepository.save(productGroupDb.get());

            if(permissions != null) {
                userGroupPermissionRepository.deleteByGroupId(productGroup.getId());
                ArrayList<UserGroupPermission> listUserGroupPermission = new ArrayList<>();
                for (Long moduleId : permissions) {
                    UserGroupPermission userGroupPermission = new UserGroupPermission();
                    userGroupPermission.setGroupId(productGroup.getId());
                    userGroupPermission.setModuleId(moduleId);
                    listUserGroupPermission.add(userGroupPermission);
                }
                userGroupPermissionRepository.saveAll(listUserGroupPermission);
            }

            response.put("success", "Sửa thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);
            response.put("productModules", productModules);
            response.put("name", "Sửa");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping(value = "/xoa/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<ProductGroup> productGroup = productGroupRepository.findById(id);
        if (productGroup.isPresent()) {
            productGroupRepository.delete(productGroup.get());
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa thất bại");
        }
    }

    @GetMapping("/api/product-group")
    @ResponseBody
    public long nhomSanPhamValid(@RequestParam("name") String name, @RequestParam("id") Long id) {
        return productGroupRepository.countUpdate(name, id);
    }
}
