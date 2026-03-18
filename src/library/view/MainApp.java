package library.view;

import java.time.LocalDate;
import java.util.Scanner;
import library.data.DataCenter;
import library.factory.AccountFactory;
import library.model.*;
import library.service.AuthService;

public class MainApp {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Khởi tạo dữ liệu mẫu
        DataCenter.getInstace();
        
        while (true) {
            if (!AuthService.isLoggedIn()) {
                showLoginMenu();
            } else {
                if (AuthService.isMember()) {
                    showMemberMenu();
                } else if (AuthService.isLibrarian()) {
                    showLibrarianMenu();
                }
            }
        }
    }
    
    // Menu đăng nhập
    private static void showLoginMenu() {
        System.out.println("\n========== HỆ THỐNG QUẢN LÝ THƯ VIỆN ==========");
        System.out.println("1. Đăng nhập");
        System.out.println("2. Thoát");
        System.out.print("Chọn chức năng: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // clear buffer
        
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                System.out.println("Tạm biệt!");
                System.exit(0);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    
    // Xử lý đăng nhập
    private static void handleLogin() {
        System.out.print("Nhập email: ");
        String email = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        
        AuthService.login(email, password);
    }
    
    // Menu cho Member
    private static void showMemberMenu() {
        Member member = (Member) AuthService.getCurrentUser();
        
        System.out.println("\n========== MENU THÀNH VIÊN ==========");
        System.out.println("Xin chào: " + member.getName());
        System.out.println("1. Xem thông tin cá nhân");
        System.out.println("2. Xem danh sách sách");
        System.out.println("3. Xem sách đang mượn");
        System.out.println("4. Xem lịch sử giao dịch");
        System.out.println("5. Gia hạn sách");
        System.out.println("6. Đặt trước sách");
        System.out.println("7. Đăng xuất");
        System.out.print("Chọn chức năng: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // clear buffer
        
        switch (choice) {
            case 1:
                member.viewProfile();
                break;
            case 2:
                viewBookList();
                break;
            case 3:
                viewMyBorrowedBooks(member);
                break;
            case 4:
                viewMyTransactions(member);
                break;
            case 5:
                renewBook(member);
                break;
            case 6:
                reserveBook(member);
                break;
            case 7:
                AuthService.logout();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    
    // Menu cho Librarian
    private static void showLibrarianMenu() {
        Librarain librarian = (Librarain) AuthService.getCurrentUser();
        
        System.out.println("\n========== MENU THỦ THƯ ==========");
        System.out.println("Xin chào: " + librarian.getName());
        System.out.println("1. Quản lý sách");
        System.out.println("2. Quản lý thành viên");
        System.out.println("3. Cho mượn sách");
        System.out.println("4. Thu hồi sách");
        System.out.println("5. Thu phạt");
        System.out.println("6. Xem danh sách sách");
        System.out.println("7. Đăng xuất");
        System.out.print("Chọn chức năng: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // clear buffer
        
        switch (choice) {
            case 1:
                manageBooks(librarian);
                break;
            case 2:
                manageMembers(librarian);
                break;
            case 3:
                issueBook(librarian);
                break;
            case 4:
                returnBook(librarian);
                break;
            case 5:
                collectFine(librarian);
                break;
            case 6:
                viewBookList();
                break;
            case 7:
                AuthService.logout();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    
    // Xem danh sách sách
    private static void viewBookList() {
        System.out.println("\n========== DANH SÁCH SÁCH ==========");
        if (DataCenter.getInstace().getListBook().isEmpty()) {
            System.out.println("Chưa có sách nào trong thư viện.");
            return;
        }
        DataCenter.getInstace().getListBook().forEach(book -> {
            System.out.println("Barcode: " + book.getBarcode() +
                             " | Rack: " + book.getRacklocation() +
                             " | Status: " + book.getStatus() +
                             " | Title: " + book.getBook().getTitle());
        });
    }

    // ========== MEMBER FUNCTIONS ==========

    // Xem sách đang mượn
    private static void viewMyBorrowedBooks(Member member) {
        System.out.println("\n========== SÁCH ĐANG MƯỢN ==========");
        if (member.getLending() == null || member.getLending().isEmpty()) {
            System.out.println("Bạn chưa mượn sách nào.");
            return;
        }
        for (LendingTransaction lt : member.getLending()) {
            if (lt.getReturnDate() == null) { // Chưa trả
                BookItem book = findBookByBarcode(lt.getBarcodeBook());
                System.out.println("Mã giao dịch: " + lt.getTransactionID());
                System.out.println("  Barcode sách: " + lt.getBarcodeBook());
                if (book != null) {
                    System.out.println("  Tên sách: " + book.getBook().getTitle());
                }
                System.out.println("  Ngày mượn: " + lt.getCreationDate());
                System.out.println("  Ngày hết hạn: " + lt.getDueDate());
                System.out.println("  Số lần gia hạn: " + lt.getRenewalCount());
                System.out.println("---");
            }
        }
    }

    // Xem lịch sử giao dịch
    private static void viewMyTransactions(Member member) {
        System.out.println("\n========== LỊCH SỬ GIAO DỊCH ==========");
        if (member.getLending() == null || member.getLending().isEmpty()) {
            System.out.println("Chưa có giao dịch nào.");
            return;
        }
        for (LendingTransaction lt : member.getLending()) {
            BookItem book = findBookByBarcode(lt.getBarcodeBook());
            System.out.println("Mã giao dịch: " + lt.getTransactionID());
            System.out.println("  Barcode sách: " + lt.getBarcodeBook());
            if (book != null) {
                System.out.println("  Tên sách: " + book.getBook().getTitle());
            }
            System.out.println("  Ngày mượn: " + lt.getCreationDate());
            System.out.println("  Ngày hết hạn: " + lt.getDueDate());
            System.out.println("  Ngày trả: " + (lt.getReturnDate() != null ? lt.getReturnDate() : "Chưa trả"));
            System.out.println("  Số lần gia hạn: " + lt.getRenewalCount());
            System.out.println("---");
        }
    }

    // Gia hạn sách
    private static void renewBook(Member member) {
        System.out.println("\n========== GIA HẠN SÁCH ==========");
        viewMyBorrowedBooks(member);

        if (member.getLending() == null || member.getLending().isEmpty()) {
            return;
        }

        System.out.print("Nhập barcode sách cần gia hạn: ");
        String barcode = scanner.nextLine();

        try {
            BookItem book = findBookByBarcode(barcode);
            if (book == null) {
                System.out.println("Không tìm thấy sách với barcode: " + barcode);
                return;
            }
            member.requestRenewable(book);
            System.out.println("Gia hạn thành công! Ngày hết hạn mới: " +
                             member.findLending(barcode).getDueDate());
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    // Đặt trước sách
    private static void reserveBook(Member member) {
        System.out.println("\n========== ĐẶT TRƯỚC SÁCH ==========");
        viewBookList();

        System.out.print("Nhập barcode sách cần đặt: ");
        String barcode = scanner.nextLine();

        BookItem book = findBookByBarcode(barcode);
        if (book == null) {
            System.out.println("Không tìm thấy sách với barcode: " + barcode);
            return;
        }

        member.reserveBook(book);
        System.out.println("Đặt sách thành công!");
    }

    // ========== LIBRARIAN FUNCTIONS ==========

    // Quản lý sách
    private static void manageBooks(Librarain librarian) {
        System.out.println("\n========== QUẢN LÝ SÁCH ==========");
        System.out.println("1. Thêm sách");
        System.out.println("2. Xóa sách");
        System.out.println("3. Cập nhật trạng thái sách");
        System.out.println("4. Quay lại");
        System.out.print("Chọn chức năng: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addBook(librarian);
                break;
            case 2:
                deleteBook(librarian);
                break;
            case 3:
                updateBookStatus(librarian);
                break;
            case 4:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // Thêm sách
    private static void addBook(Librarain librarian) {
        System.out.println("\n========== THÊM SÁCH ==========");
        System.out.print("Nhập barcode: ");
        String barcode = scanner.nextLine();
        System.out.print("Nhập vị trí kệ: ");
        String rack = scanner.nextLine();
        System.out.print("Nhập ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Nhập tên sách: ");
        String title = scanner.nextLine();
        System.out.print("Nhập thể loại: ");
        String type = scanner.nextLine();

        Book book = new Book(isbn, title, type);
        BookItem bookItem = new BookItem(barcode, rack, "Available", book);
        librarian.addBook(bookItem);
        System.out.println("Thêm sách thành công!");
    }

    // Xóa sách
    private static void deleteBook(Librarain librarian) {
        System.out.println("\n========== XÓA SÁCH ==========");
        viewBookList();
        System.out.print("Nhập barcode sách cần xóa: ");
        String barcode = scanner.nextLine();

        BookItem book = findBookByBarcode(barcode);
        if (book == null) {
            System.out.println("Không tìm thấy sách với barcode: " + barcode);
            return;
        }

        librarian.deleteBook(book.getBook());
        System.out.println("Xóa sách thành công!");
    }

    // Cập nhật trạng thái sách
    private static void updateBookStatus(Librarain librarian) {
        System.out.println("\n========== CẬP NHẬT TRẠNG THÁI SÁCH ==========");
        viewBookList();
        System.out.print("Nhập barcode sách: ");
        String barcode = scanner.nextLine();
        System.out.print("Nhập trạng thái mới (Available/Borrowed/Reserved): ");
        String status = scanner.nextLine();

        librarian.updateBook(status, barcode);
        System.out.println("Cập nhật trạng thái thành công!");
    }

    // Quản lý thành viên
    private static void manageMembers(Librarain librarian) {
        System.out.println("\n========== QUẢN LÝ THÀNH VIÊN ==========");
        System.out.println("1. Thêm thành viên");
        System.out.println("2. Xóa thành viên");
        System.out.println("3. Cập nhật trạng thái thành viên");
        System.out.println("4. Xem danh sách thành viên");
        System.out.println("5. Quay lại");
        System.out.print("Chọn chức năng: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addMember(librarian);
                break;
            case 2:
                removeMember(librarian);
                break;
            case 3:
                updateMemberStatus(librarian);
                break;
            case 4:
                viewMemberList();
                break;
            case 5:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // Thêm thành viên
    private static void addMember(Librarain librarian) {
        System.out.println("\n========== THÊM THÀNH VIÊN ==========");
        System.out.print("Nhập ID: ");
        String id = scanner.nextLine();
        System.out.print("Nhập tên: ");
        String name = scanner.nextLine();
        System.out.print("Nhập email: ");
        String email = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        System.out.print("Nhập loại (STUDENT/TEACHER): ");
        String type = scanner.nextLine();

        Member member = AccountFactory.createMember(id, name, email, password, type, "ACTIVE");
        librarian.addMember(member);
        System.out.println("Thêm thành viên thành công!");
    }

    // Xóa thành viên
    private static void removeMember(Librarain librarian) {
        System.out.println("\n========== XÓA THÀNH VIÊN ==========");
        viewMemberList();
        System.out.print("Nhập ID thành viên cần xóa: ");
        String id = scanner.nextLine();

        Member member = findMemberById(id);
        if (member == null) {
            System.out.println("Không tìm thấy thành viên với ID: " + id);
            return;
        }

        librarian.removerMember(member);
        System.out.println("Xóa thành viên thành công!");
    }

    // Cập nhật trạng thái thành viên
    private static void updateMemberStatus(Librarain librarian) {
        System.out.println("\n========== CẬP NHẬT TRẠNG THÁI THÀNH VIÊN ==========");
        viewMemberList();
        System.out.print("Nhập ID thành viên: ");
        String id = scanner.nextLine();
        System.out.print("Nhập trạng thái mới (ACTIVE/INACTIVE): ");
        String status = scanner.nextLine();

        librarian.updateMember(id, status);
        System.out.println("Cập nhật trạng thái thành công!");
    }

    // Xem danh sách thành viên
    private static void viewMemberList() {
        System.out.println("\n========== DANH SÁCH THÀNH VIÊN ==========");
        if (DataCenter.getInstace().getListMember().isEmpty()) {
            System.out.println("Chưa có thành viên nào.");
            return;
        }
        DataCenter.getInstace().getListMember().forEach(member -> {
            System.out.println("ID: " + member.getAccountID() +
                             " | Tên: " + member.getName() +
                             " | Email: " + member.getEmail() +
                             " | Loại: " + member.getMembertype() +
                             " | Trạng thái: " + member.getStatus() +
                             " | Số sách đang mượn: " + member.getTotalBooksCheckedOut());
        });
    }

    // Cho mượn sách
    private static void issueBook(Librarain librarian) {
        System.out.println("\n========== CHO MƯỢN SÁCH ==========");
        viewMemberList();
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();

        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Không tìm thấy thành viên với ID: " + memberId);
            return;
        }

        viewBookList();
        System.out.print("Nhập barcode sách: ");
        String barcode = scanner.nextLine();

        BookItem book = findBookByBarcode(barcode);
        if (book == null) {
            System.out.println("Không tìm thấy sách với barcode: " + barcode);
            return;
        }

        if (!book.getStatus().equals("Available")) {
            System.out.println("Sách không có sẵn để mượn!");
            return;
        }

        librarian.issueBook(member, book);
        System.out.println("Cho mượn sách thành công!");
    }

    // Thu hồi sách
    private static void returnBook(Librarain librarian) {
        System.out.println("\n========== THU HỒI SÁCH ==========");
        System.out.print("Nhập barcode sách: ");
        String barcode = scanner.nextLine();

        BookItem book = findBookByBarcode(barcode);
        if (book == null) {
            System.out.println("Không tìm thấy sách với barcode: " + barcode);
            return;
        }

        // Tìm giao dịch mượn sách
        Member borrower = null;
        LendingTransaction transaction = null;
        for (Member m : DataCenter.getInstace().getListMember()) {
            if (m.getLending() != null) {
                for (LendingTransaction lt : m.getLending()) {
                    if (lt.getBarcodeBook().equals(barcode) && lt.getReturnDate() == null) {
                        borrower = m;
                        transaction = lt;
                        break;
                    }
                }
            }
            if (borrower != null) break;
        }

        if (transaction == null) {
            System.out.println("Không tìm thấy giao dịch mượn sách này!");
            return;
        }

        // Cập nhật ngày trả
        transaction.setReturnDate(LocalDate.now());
        borrower.setTotalBooksCheckedOut(borrower.getTotalBooksCheckedOut() - 1);

        // Kiểm tra quá hạn và tính phí phạt
        if (LocalDate.now().isAfter(transaction.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                transaction.getDueDate(), LocalDate.now());
            Fine fine = new Fine("F" + System.currentTimeMillis(), daysLate, false);
            transaction.setFine(fine);
            System.out.println("Sách trả trễ " + daysLate + " ngày!");
            System.out.println("Phí phạt: " + fine.calculateAmount(daysLate) + " VND");
        }

        librarian.returnBook(book);
        System.out.println("Thu hồi sách thành công!");
    }

    // Thu phạt
    private static void collectFine(Librarain librarian) {
        System.out.println("\n========== THU PHẠT ==========");

        // Hiển thị danh sách phạt chưa thanh toán
        boolean hasUnpaidFines = false;
        for (Member m : DataCenter.getInstace().getListMember()) {
            if (m.getLending() != null) {
                for (LendingTransaction lt : m.getLending()) {
                    if (lt.getFine() != null && !lt.getFine().isIspaid()) {
                        hasUnpaidFines = true;
                        System.out.println("Thành viên: " + m.getName() + " (ID: " + m.getAccountID() + ")");
                        System.out.println("  Mã phạt: " + lt.getFine().getFineID());
                        System.out.println("  Số tiền: " + lt.getFine().calculateAmount(lt.getFine().getAmount()) + " VND");
                        System.out.println("---");
                    }
                }
            }
        }

        if (!hasUnpaidFines) {
            System.out.println("Không có phạt nào chưa thanh toán.");
            return;
        }

        System.out.print("Nhập mã phạt cần thu: ");
        String fineId = scanner.nextLine();

        // Tìm và thanh toán phạt
        for (Member m : DataCenter.getInstace().getListMember()) {
            if (m.getLending() != null) {
                for (LendingTransaction lt : m.getLending()) {
                    if (lt.getFine() != null && lt.getFine().getFineID().equals(fineId)) {
                        int amount = librarian.collectFine(lt.getFine());
                        lt.getFine().markAsPaid();
                        System.out.println("Thu phạt thành công! Số tiền: " + amount + " VND");
                        return;
                    }
                }
            }
        }

        System.out.println("Không tìm thấy mã phạt: " + fineId);
    }

    // ========== HELPER FUNCTIONS ==========

    // Tìm sách theo barcode
    private static BookItem findBookByBarcode(String barcode) {
        for (BookItem book : DataCenter.getInstace().getListBook()) {
            if (book.getBarcode().equals(barcode)) {
                return book;
            }
        }
        return null;
    }

    // Tìm thành viên theo ID
    private static Member findMemberById(String id) {
        for (Member member : DataCenter.getInstace().getListMember()) {
            if (member.getAccountID().equals(id)) {
                return member;
            }
        }
        return null;
    }
}
