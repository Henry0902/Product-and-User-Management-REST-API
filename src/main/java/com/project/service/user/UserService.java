//package com.project.service.user;
///*******************************************************
// * For Vietnamese readers:
// *    Các bạn thân mến, mình rất vui nếu project này giúp
// * ích được cho các bạn trong việc học tập và công việc. Nếu
// * bạn sử dụng lại toàn bộ hoặc một phần source code xin để
// * lại dường dẫn tới github hoặc tên tác giá.
// *    Xin cảm ơn!
// *******************************************************/
//
//import com.project.repository.UserInfoRepository;
//import com.project.table.UserInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
///**
// * Copyright 2019 {@author Loda} (https://loda.me).
// * This project is licensed under the MIT license.
// *
// * @since 4/30/2019
// * Github: https://github.com/loda-kun
// */
//@Service
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    private UserInfoRepository userInfoRepository;
//
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        // Kiểm tra xem user có tồn tại trong database không?
//        UserInfo userInfo = userInfoRepository.findByUsername(username);
//        if (userInfo == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new CustomUserDetails(userInfo);
//    }
//
//    // JWTAuthenticationFilter sẽ sử dụng hàm này
//    @Transactional
//    public UserDetails loadUserById(Long id) {
//        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with id : " + id)
//        );
//
//        return new CustomUserDetails(userInfo);
//    }
//
//
//}