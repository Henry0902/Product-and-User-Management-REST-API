package com.project.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.project.common.CommonUtils;
import com.project.security.jwt.AuthTokenFilter;
import com.project.security.jwt.JwtUtils;
import com.project.table.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.model.search.UserGroupSearch;
import com.project.repository.UserGroupPermissionRepository;
import com.project.repository.UserGroupRepository;
import com.project.repository.UserModuleRepository;
import com.project.table.UserGroup;
import com.project.table.UserGroupPermission;
import com.project.table.UserModule;

@RestController
@RequestMapping("/api/nhom-nguoi-dung")
public class NhomNguoiDungController extends BaseController{
    @Autowired
    UserGroupRepository userGroupRepository;
    @Autowired
    UserModuleRepository userModuleRepository;
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
    public ResponseEntity<?> nhomnguoiDung(@RequestParam Map<String, String> allParams) {
        Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
        if (allParams.get("reset") != null) {
            allParams.clear();
        }
        UserGroupSearch userGroupSearch = objectMapper.convertValue(allParams, UserGroupSearch.class);
        userGroupSearch.normalize();
        Page<UserGroup> userGroups = userGroupRepository.selectParams(
                userGroupSearch.getS_gname(),
                userGroupSearch.getS_desc(),
                userGroupSearch.getS_status(),
                getPageable(allParams, paginate));
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", paginate.getPage());
        response.put("totalPage", userGroups.getTotalPages());
        response.put("totalElement", userGroups.getTotalElements());
        response.put("userInfos", userGroups.getContent());
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/them-moi")
    public ResponseEntity<?> postNhomnguoiDungThemMoi(@RequestBody UserGroup userGroup ,
                                                      @RequestParam(value = "permissions", required = false) List<Long> permissions) {
        Map<String, Object> response = new HashMap<>();
        try {
            userGroup.setCreateTime(new Date());
            String jwt = authTokenFilter.getJwtFromRequest(request);
            userGroup.setCreateBy(jwtUtils.getUserNameFromJwtToken(jwt));
            checkErrorMessage(userGroup);

            userGroupRepository.save(userGroup);

            if(permissions != null) {
                ArrayList<UserGroupPermission> listUserGroupPermission = new ArrayList<>();
                for (Long moduleId : permissions) {
                    UserGroupPermission userGroupPermission = new UserGroupPermission();
                    userGroupPermission.setGroupId(userGroup.getId());
                    userGroupPermission.setModuleId(moduleId);
                    listUserGroupPermission.add(userGroupPermission);
                }
                userGroupPermissionRepository.saveAll(listUserGroupPermission);
            }
            response.put("success", "Thêm thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Iterable<UserModule> userModules = userModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);
            response.put("userModules", userModules);
            response.put("name", "Thêm mới");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping(value = "/sua/{id}")
    public ResponseEntity<?> postNhomnguoiDungSua(@RequestBody UserGroup userGroup,
                                                  @RequestParam(value = "permissions", required = false) List<Long> permissions,@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println(permissions);
            checkErrorMessage(userGroup);

            Optional<UserGroup> userGroupDb = userGroupRepository.findById(id);

            updateObjectToObject(userGroupDb.get(), userGroup);

            userGroupRepository.save(userGroupDb.get());

            if (permissions != null) {
                userGroupPermissionRepository.deleteByGroupId(userGroup.getId());
                ArrayList<UserGroupPermission> listUserGroupPermission = new ArrayList<>();
                for (Long moduleId : permissions) {
                    UserGroupPermission userGroupPermission = new UserGroupPermission();
                    userGroupPermission.setGroupId(userGroup.getId());
                    userGroupPermission.setModuleId(moduleId);
                    listUserGroupPermission.add(userGroupPermission);
                }
                userGroupPermissionRepository.saveAll(listUserGroupPermission);
            }

            response.put("success", "Sửa thành công");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Iterable<UserModule> userModules = userModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

            response.put("userModules", userModules);
            response.put("name", "Sửa");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping(value = "/xoa/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<UserGroup> userGroup = userGroupRepository.findById(id);
        if (userGroup.isPresent()) {
            userGroupRepository.delete(userGroup.get());
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa thất bại");
        }
    }
    
    @GetMapping("/api/user-group")
    @ResponseBody
    public long nhomNguoiDungValid(@RequestParam("name") String name, @RequestParam("id") Long id) {
        return userGroupRepository.countUpdate(name, id);
    }
}
