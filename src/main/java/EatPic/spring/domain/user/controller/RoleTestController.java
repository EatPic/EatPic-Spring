//// 계정 권환 확인용 Controller
//
//package EatPic.spring.domain.user.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Tag(name = "Role Test")
//@RestController
//@RequestMapping("/api")
//public class RoleTestController {
//
//    @Operation(summary = "ADMIN 권한 확인용")
//    @PreAuthorize("hasRole('ADMIN')")       // ADMIN만
//    @GetMapping("/admin")
//    public ResponseEntity<?> admin(Authentication auth) {
//        return ResponseEntity.ok(Map.of(
//                "who", auth.getName(),
//                "authorities", auth.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()),
//                "message", "admin ok"
//        ));
//    }
//
//    @Operation(summary = "USER 권한 확인용")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')") // USER 또는 ADMIN
//    @GetMapping("/user")
//    public ResponseEntity<?> user(Authentication auth) {
//        return ResponseEntity.ok(Map.of(
//                "who", auth.getName(),
//                "authorities", auth.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()),
//                "message", "user ok"
//        ));
//    }
//
//    @Operation(summary = "현재 토큰 정보 확인")
//    @GetMapping("/me")
//    public ResponseEntity<?> me(Authentication auth) {
//        // 인증 안 되었으면 null일 수 있음
//        if (auth == null) return ResponseEntity.status(401).body(Map.of("message", "unauthorized"));
//        return ResponseEntity.ok(Map.of(
//                "who", auth.getName(),
//                "authorities", auth.getAuthorities().stream().map(Object::toString).collect(Collectors.toList())
//        ));
//    }
//}
