package chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {

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
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ChessBoard.fxml"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }


    public static void main(String[] args) throws FileNotFoundException {

        //initialize all image member variable constants.
        FileInputStream inputStream = null;
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/BishopBlack.png");
        BLACK_BISHOP_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/KingBlack.png");
        BLACK_KING_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/KnightBlack.png");
        BLACK_KNIGHT_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/PawnBlack.png");
        BLACK_PAWN_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/QueenBlack.png");
        BLACK_QUEEN_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/RookBlack.png");
        BLACK_ROOK_IMAGE = new Image(inputStream);

        inputStream = new FileInputStream("Resources/Pictures/GamePieces/BishopWhite.png");
        WHITE_BISHOP_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/KingWhite.png");
        WHITE_KING_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/KnightWhite.png");
        WHITE_KNIGHT_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/PawnWhite.png");
        WHITE_PAWN_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/QueenWhite.png");
        WHITE_QUEEN_IMAGE = new Image(inputStream);
        inputStream = new FileInputStream("Resources/Pictures/GamePieces/RookWhite.png");
        WHITE_ROOK_IMAGE = new Image(inputStream);

        launch(args);
    }
}
