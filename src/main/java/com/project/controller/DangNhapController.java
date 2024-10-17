package com.project.controller;

import java.util.*;

import com.project.payload.request.LoginRequest;
import com.project.repository.ProductInfoRepository;
import com.project.repository.ProductModuleReponsitory;
import com.project.exception.ErrorException;
import com.project.repository.UserInfoRepository;
import com.project.repository.UserModuleRepository;
import com.project.security.jwt.JwtUtils;
import com.project.security.services.UserDetailsImpl;
import com.project.service.refreshtoken.RefreshTokenService;
import com.project.table.UserInfo;
import com.project.table.UserModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class DangNhapController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DangNhapController.class);

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserModuleRepository userModuleRepository;

    @Autowired
    ProductModuleReponsitory productModuleRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest ) {
        try {

            Optional<UserInfo> userInfo = userInfoRepository.findByUsername(loginRequest.getUsername());

            if (!userInfo.isPresent()) throw new ErrorException("Tên đăng nhập không đúng");

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(loginRequest.getPassword(), userInfo.get().getPassword()))
                throw new ErrorException("Mật khẩu không đúng");

            List<UserModule> userModules = userModuleRepository.selectGroupId(userInfo.get().getGroupId());
//            if (userModules.isEmpty()) throw new ErrorException("Không có quyền truy cập");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            List<UserModule> userModules = userDetails.getUserModules();
//            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//            response.put("refreshToken", refreshToken.getToken());

            // Create a map to store user info and modules
            Map<String, Object> response = new HashMap<>();
            response.put("userModules", userModules);
            response.put("username", userInfo.get().getUsername());
            response.put("fullName", userInfo.get().getFullName());
            response.put("email", userInfo.get().getEmail());
            response.put("userId", userInfo.get().getId());
            response.put("token", jwt);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error("ErrorException", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
//        String requestRefreshToken = request.getRefreshToken();
//
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUserInfo)
//                .map(userInfo -> {
//                    String token = jwtUtils.generateTokenFromUsername(userInfo.getUsername());
//                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//                        "Refresh token is not in database!"));
//    }

}
