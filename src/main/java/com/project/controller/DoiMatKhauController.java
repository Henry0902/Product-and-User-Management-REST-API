package com.project.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.project.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.CommonUtils;
import com.project.repository.UserInfoRepository;
import com.project.table.UserInfo;

@RestController("/api/change-pass")
public class DoiMatKhauController extends BaseController {
	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@PutMapping(value = "")
	public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token,
											@RequestBody Map<String, String> allParams) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Decode the token to get the username
			String userName = jwtUtils.getUserNameFromJwtToken(token);

			Optional<UserInfo> userInfoDb = userInfoRepository.findByUsername(userName);

			if (!userInfoDb.isPresent())
				throw new Exception("Người dùng không tồn tại");
			if (!new BCryptPasswordEncoder().matches(allParams.get("old-password"), userInfoDb.get().getPassword()))
				throw new Exception("Mật khẩu không đúng");
			userInfoDb.get().setPassword(new BCryptPasswordEncoder().encode(allParams.get("new-password")));

			userInfoRepository.save(userInfoDb.get());

			response.put("success", "Đổi mật khẩu thành công");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
