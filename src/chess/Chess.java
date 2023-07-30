package chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Chess extends Application {

    public static Image BLACK_PAWN_IMAGE;
    public static Image BLACK_ROOK_IMAGE;
    public static Image BLACK_BISHOP_IMAGE;
    public static Image BLACK_KNIGHT_IMAGE;
    public static Image BLACK_QUEEN_IMAGE;
    public static Image BLACK_KING_IMAGE;

    public static Image WHITE_PAWN_IMAGE;
    public static Image WHITE_ROOK_IMAGE;
    public static Image WHITE_BISHOP_IMAGE;
    public static Image WHITE_KNIGHT_IMAGE;
    public static Image WHITE_QUEEN_IMAGE;
    public static Image WHITE_KING_IMAGE;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = getClass().getResource("ChessBoard.fxml");
        if(url == null) {
            throw new IOException("ChessBoard.fxml not found");
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        initGameImages();
        launch(args);
    }

    private static void initGameImages() throws FileNotFoundException {
        String path = "Resources/Pictures/GamePieces/";

        BLACK_BISHOP_IMAGE = getImage(path, "BishopBlack.png");
        BLACK_KING_IMAGE = getImage(path, "KingBlack.png");
        BLACK_KNIGHT_IMAGE = getImage(path, "KnightBlack.png");
        BLACK_PAWN_IMAGE = getImage(path, "PawnBlack.png");
        BLACK_QUEEN_IMAGE = getImage(path, "QueenBlack.png");
        BLACK_ROOK_IMAGE = getImage(path, "RookBlack.png");

        WHITE_BISHOP_IMAGE = getImage(path, "BishopWhite.png");
        WHITE_KING_IMAGE = getImage(path, "KingWhite.png");
        WHITE_KNIGHT_IMAGE = getImage(path, "KnightWhite.png");
        WHITE_PAWN_IMAGE = getImage(path, "PawnWhite.png");
        WHITE_QUEEN_IMAGE = getImage(path, "QueenWhite.png");
        WHITE_ROOK_IMAGE = getImage(path, "RookWhite.png");
    }

    private static Image getImage(String path, String file) throws FileNotFoundException {
        //initialize all image member variable constants.
        FileInputStream inputStream = new FileInputStream(path + file);
        return new Image(inputStream);
    }

}
