package controller;

/**
 * Interface dành cho các đối tượng cần được cập nhật trạng thái
 * trong mỗi chu kỳ (frame) của vòng lặp game (Game Loop).
 */
public interface Updatable {
    
    // Phương thức này sẽ được gọi liên tục trong vòng lặp game
    void update();
}
