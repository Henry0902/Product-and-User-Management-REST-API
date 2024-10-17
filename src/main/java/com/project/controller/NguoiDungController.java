package com.project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.project.security.jwt.AuthTokenFilter;
import com.project.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.CommonUtils;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.exception.ErrorException;
import com.project.model.dto.UserInfoDto;
import com.project.model.search.UserSearch;
import com.project.repository.UserGroupRepository;
import com.project.repository.UserInfoDtoRepository;
import com.project.repository.UserInfoRepository;
import com.project.table.UserGroup;
import com.project.table.UserInfo;
@RestController
@RequestMapping("/api/nguoi-dung")
public class NguoiDungController extends BaseController {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserInfoDtoRepository userInfoDtoRepository;
    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    AuthTokenFilter authTokenFilter;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping(value = "")
    public ResponseEntity<?> nguoiDung(@RequestParam Map<String, String> allParams) {
        Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
        if (allParams.get("reset") != null) {
            allParams.clear();
        }
        UserSearch userSearch = objectMapper.convertValue(allParams, UserSearch.class);
        userSearch.normalize();
        Page<UserInfoDto> userInfos = userInfoDtoRepository.selectParams(
                userSearch.getS_uname(),
                userSearch.getS_fname(),
                userSearch.getS_email(),
                userSearch.getS_status(),
                getPageable(allParams, paginate));
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", paginate.getPage());
        response.put("totalPage", userInfos.getTotalPages());
        response.put("totalElement", userInfos.getTotalElements());
        response.put("userInfos", userInfos.getContent());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/them-moi")
    public ResponseEntity<?> postnguoiDungThemMoi(@RequestBody UserInfo userInfo) {
        try {
            checkErrorMessage(userInfo);
            if (userInfo.getGroupId() == 0) throw new Exception("Chọn nhóm cho người dùng");
            Optional<UserInfo> checkUserName = userInfoRepository.findByUsername(userInfo.getUsername());
            if (checkUserName.isPresent()) throw new Exception("Tên đăng nhập đã tồn tại");
            String jwt = authTokenFilter.getJwtFromRequest(request);
            userInfo.setCreateBy(jwtUtils.getUserNameFromJwtToken(jwt));
            userInfo.setCreateTime(new Date());
            userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
            userInfoRepository.save(userInfo);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/sua/{id}")
    public ResponseEntity<?> postnguoiDungSua(@RequestBody UserInfo userInfo,@PathVariable long id) {
        try {
            checkErrorMessage(userInfo);
            Optional<UserInfo> userInfoDb = userInfoRepository.findById(id);

            if (!userInfoDb.isPresent()) {
                throw new ErrorException("Không tìm thấy người dùng");
            }
            if (!StringUtils.isEmpty(userInfo.getPassword())) {
                userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
            }

            updateObjectToObject(userInfoDb.get(), userInfo);
            userInfoRepository.save(userInfoDb.get());
            return ResponseEntity.ok("Sửa thành công");
        } catch (ErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống");
        }
    }

    @DeleteMapping(value = "/xoa/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<UserInfo> userInfo = userInfoRepository.findById(id);
        if (userInfo.isPresent()) {
            userInfoRepository.delete(userInfo.get());
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa thất bại");
        }
    }

    @GetMapping("/api/user-info")
    public long nguoiDungValid(@RequestParam("name") String name) {
        return userInfoRepository.countByUsername(name);
    }
}