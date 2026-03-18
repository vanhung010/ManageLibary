package library.service;

import library.data.DataCenter;
import library.model.Account;
import library.model.Librarain;
import library.model.Member;

public class AuthService {
    
    private static Account currentUser = null;
    
    // Đăng nhập
    public static Account login(String email, String password) {
        // Kiểm tra trong danh sách Member
        for (Member member : DataCenter.getInstace().getListMember()) {
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                currentUser = member;
                System.out.println("Đăng nhập thành công! Xin chào " + member.getName());
                return currentUser;
            }
        }
        
        // Kiểm tra trong danh sách Librarian
        for (Librarain librarian : DataCenter.getInstace().getListLibrarain()) {
            if (librarian.getEmail().equals(email) && librarian.getPassword().equals(password)) {
                currentUser = librarian;
                System.out.println("Đăng nhập thành công! Xin chào " + librarian.getName());
                return currentUser;
            }
        }

        System.out.println("Đăng nhập thất bại! Email hoặc mật khẩu không đúng.");
        return null;
    }
    
    // Đăng xuất
    public static void logout() {
        if (currentUser != null) {
            System.out.println("Đăng xuất thành công! Tạm biệt " + currentUser.getName());
            currentUser = null;
        } else {
            System.out.println("Chưa có ai đăng nhập!");
        }
    }
    
    // Lấy user hiện tại
    public static Account getCurrentUser() {
        return currentUser;
    }
    
    // Kiểm tra đã đăng nhập chưa
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    // Kiểm tra user là Member
    public static boolean isMember() {
        return currentUser instanceof Member;
    }
    
    // Kiểm tra user là Librarian
    public static boolean isLibrarian() {
        return currentUser instanceof Librarain;
    }
}
