package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.project.common.Contains;
import com.project.repository.*;
import com.project.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.Authenticator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller()
public class PageController extends BaseController{

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductInfoDtoReponsitory productInfoDtoRepository;
    @Autowired
    ProductGroupReponsitory productGroupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserModuleRepository userModuleRepository;

    @Autowired
    UserGroupPermissionRepository userGroupPermissionRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserInfoDtoRepository userInfoDtoRepository;

    @Autowired
    ProductModuleReponsitory productModuleRepository;

    //LOGIN AND CHANGE PASSWORD

    @GetMapping(value = "/login")
    public String get(Model model) {
        return "login";
    }

    @GetMapping(value = "")
    public String get(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
        forwartParams(allParams, model);

        return "nguoidung/doimatkhau";
    }

    //NGUOI DUNG

    @GetMapping(value = "/api/nguoi-dung/them-moi")
    public String getnguoiDungThemMoi(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) throws JsonProcessingException {
        Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

        model.addAttribute("userGroups", userGroups);
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "nguoidung/addnguoidung";
    }

    @GetMapping(value = "/api/nguoi-dung/sua")
    public String getnguoiDungSua(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception("Sửa thất bại");
            }
            Optional<UserInfo> userInfo = userInfoRepository.findById(Long.valueOf(allParams.get("id")));
            if (!userInfo.isPresent()) {
                throw new Exception("Không tồn tại bản ghi");
            }

            Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

            model.addAttribute("userGroups", userGroups);
            model.addAttribute("userInfo", userInfo.get());
            model.addAttribute("name", "Sửa");
            forwartParams(allParams, model);
            return "nguoidung/addnguoidung";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/api/nguoi-dung";
        }
    }

    //NHOM NGUOI DUNG

    @GetMapping(value = "/api/nhom-nguoi-dung/them-moi")
    public String getNhomnguoiDungThemMoi (Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {

        Iterable<UserModule> userModules = userModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

        model.addAttribute("userModules", userModules);
        model.addAttribute("userGroup", new UserGroup());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "nhomnguoidung/addnhomnguoidung";
    }

    @GetMapping(value = "/api/nhom-nguoi-dung/sua")
    public String getNhomnguoiDungSua (Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception("Sửa thất bại");
            }
            Optional<UserGroup> userGroup = userGroupRepository.findById(Long.valueOf(allParams.get("id")));
            if (!userGroup.isPresent()) {
                throw new Exception("Không tồn tại bản ghi");
            }
            Iterable<UserModule> userModules = userModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

            List<UserGroupPermission> userGroupPermissions = userGroupPermissionRepository.findByGroupId(userGroup.get().getId());

            model.addAttribute("userGroupPermissions", new Gson().toJson(userGroupPermissions));
            model.addAttribute("userModules", userModules);
            model.addAttribute("userGroup", userGroup.get());
            model.addAttribute("name", "Sửa");
            forwartParams(allParams, model);
            return "nhomnguoidung/addnhomnguoidung";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/api/nhom-nguoi-dung";
        }
    }

    // NHOM SAN PHAM

    @GetMapping(value = "/api/nhom-san-pham/them-moi")
    public String getNhomsanPhamThemMoi (Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {

        Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

        model.addAttribute("productModules", productModules);
        model.addAttribute("productGroup", new ProductGroup());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "nhomsanpham/addnhomsanpham";
    }

    @GetMapping(value = "/api/nhom-san-pham/sua")
    public String getNhomsanPhamSua (Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception("Sửa thất bại");
            }
            Optional<ProductGroup> productGroup = productGroupRepository.findById(Long.valueOf(allParams.get("id")));
            if (!productGroup.isPresent()) {
                throw new Exception("Không tồn tại bản ghi");
            }
            Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

            List<UserGroupPermission> userGroupPermissions = userGroupPermissionRepository.findByGroupId(productGroup.get().getId());

            model.addAttribute("userGroupPermissions", new Gson().toJson(userGroupPermissions));
            model.addAttribute("productModules", productModules);
            model.addAttribute("productGroup", productGroup.get());
            model.addAttribute("name", "Sửa");
            forwartParams(allParams, model);
            return "nhomsanpham/addnhomsanpham";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/api/nhom-san-pham";
        }
    }

    //SAN PHAM

    @GetMapping(value = "/api/san-pham/them-moi")
    public String getsanPhamThemMoi(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) throws JsonProcessingException {
        Iterable<ProductGroup> productGroups = productGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

        model.addAttribute("productGroups", productGroups);
        model.addAttribute("productInfo", new ProductInfo());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "sanpham/addsanpham";
    }

    @GetMapping(value = "/api/san-pham/sua")
    public String getsanPhamSua(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception("Sửa thất bại");
            }
            Optional<ProductInfo> productInfo = productInfoRepository.findById(Long.valueOf(allParams.get("id")));
            if (!productInfo.isPresent()) {
                throw new Exception("Không tồn tại bản ghi");
            }

            Iterable<ProductGroup> productGroups = productGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

            model.addAttribute("productGroups", productGroups);
            model.addAttribute("productInfo", productInfo.get());
            model.addAttribute("name", "Sửa");
            forwartParams(allParams, model);
            return "sanpham/addsanpham";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/api/san-pham";
        }
    }

}
