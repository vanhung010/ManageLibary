package library.dao;

import library.data.DataCenter;
import library.model.BookItem;

import java.util.ArrayList;
import java.util.List;

public class BookItemDao {
    // Tim sach theo ten tu du lieu trong bo nho
    public List<BookItem> searchByName(String name) {
        List<BookItem> result = new ArrayList<>();
        if (name == null) {
            return result;
        }

        String keyword = name.trim().toLowerCase();
        for (BookItem bookItem : DataCenter.getInstace().getListBook()) {
            if (bookItem.getBook() == null || bookItem.getBook().getTitle() == null) {
                continue;
            }

            if (bookItem.getBook().getTitle().toLowerCase().contains(keyword)) {
                result.add(bookItem);
            }
        }
        return result;
    }

    // Cap nhat trang thai sach trong DataCenter
    public void updateStatus(String barcode, String newStatus) {
        for (BookItem bookItem : DataCenter.getInstace().getListBook()) {
            if (bookItem.getBarcode().equals(barcode)) {
                bookItem.setStatus(newStatus);
                break;
            }
        }
    }
}
