package J2EE.com.example.project;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Mật khẩu bạn muốn dùng để đăng nhập
        String rawPassword = "123456"; // $2a$10$ZTqtNMIcI7R.X3phQUtvgeWzh8LlH1CnEqI4ytn.DzssAfbAs7Ive

        // Tạo mã băm
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("=====================================");
        System.out.println("Mật khẩu gốc: " + rawPassword);
        System.out.println("Mã băm BCrypt: " + encodedPassword);
        System.out.println("=====================================");
    }
}