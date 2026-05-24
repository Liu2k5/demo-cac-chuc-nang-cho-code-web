package liu.democacchucnangchocodeweb.listener.event;

// kiểu dữ liệu record dùng để lưu dữ liệu bất biến, tự động tạo constructor, getter, equals, hashCode và toString
public record DisableEvent(String username) {
    // không cần viết this.username = username; vì record tự động tạo constructor và gán giá trị cho trường username
}
