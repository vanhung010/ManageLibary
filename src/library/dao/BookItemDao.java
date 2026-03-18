package library.dao;

import library.data.DBConection;
import library.model.Book;
import library.model.BookItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookItemDao {
    //Tìm sách theo ten
    public List<BookItem> searchByName(String name) {
        List<BookItem> result = new ArrayList<>();
        String query = "SELECT bi.barcode, bi.racklocation, bi.status, b.isbn, b.title, b.type " +
                        "FROM BookItem bi JOIN Book b ON bi.isbn = b.isbn" +
                        "WHERE b.title ILIKE ?";
        try(Connection conn = DBConection.getConnection()) {
            PreparedStatement p = conn.prepareStatement(query);

            ResultSet re = p.executeQuery();

            while(re.next()){
                Book book = new Book(re.getString("isbn"), re.getString("title"), re.getString("type"));
                BookItem bi = new BookItem(re.getString("barcode"), re.getString("racklocation"), re.getString("status"), book);
                result.add(bi);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
return result;
    }

    //Caapj nhật trạng thái sách dùng khi người dùng hoặc thủ thư cho mượn hoặc trả sách
    public void updateStatus(String barcode, String newStatus){
        String query = "UPDATE BookItem SET status = ? WHERE barcode = ?";
        try(Connection conn = DBConection.getConnection()) {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, newStatus);
            p.setString(2, barcode);
            p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
