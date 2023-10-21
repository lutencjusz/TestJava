
public class KolkoIKrzyzyk {
    private static final int SIZE = 3; // Rozmiar planszy (3x3)

    public static class Move {
        int row;
        int col;

        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static class Result {
        int score;
        Move move;

        public Result(int score) {
            this.score = score;
        }

        public Result(int score, Move move) {
            this.score = score;
            this.move = move;
        }
    }

    public static Result minimax(char[][] board, boolean isMaximizing) {
        char player = isMaximizing ? 'X' : 'O';
        char opponent = isMaximizing ? 'O' : 'X';

        // Sprawdzamy, czy jest wygrana lub remis na planszy
        int score = evaluate(board);
        if (score == 10) {
            return new Result(score);
        }
        if (score == -10) {
            return new Result(score);
        }
        if (!isMovesLeft(board)) {
            return new Result(0);
        }

        // Jeśli to maksymalizujący gracz (X), wybieramy najwyższy wynik
        // Jeśli to minimalizujący gracz (O), wybieramy najniższy wynik
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = null;

        // Przechodzimy przez wszystkie dostępne ruchy na planszy
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;

                    // Rekurencyjnie obliczamy wartość ruchu przeciwnika
                    Result result = minimax(board, !isMaximizing);

                    // Cofamy ruch
                    board[i][j] = ' ';

                    // Aktualizujemy najlepszy wynik i najlepszy ruch
                    if ((isMaximizing && result.score > bestScore) || (!isMaximizing && result.score < bestScore)) {
                        bestScore = result.score;
                        bestMove = new Move(i, j);
                    }
                }
            }
        }

        // Zwracamy wynik i najlepszy ruch
        return new Result(bestScore, bestMove);
    }

    public static int evaluate(char[][] board) {
        // Sprawdzamy, czy występuje wygrana w rzędach, kolumnach lub przekątnych
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == 'X') {
                    return 10;
                } else if (board[i][0] == 'O') {
                    return -10;
                }
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == 'X') {
                    return 10;
                } else if (board[0][i] == 'O') {
                    return -10;
                }
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'X') {
                return 10;
            } else if (board[0][0] == 'O') {
                return -10;
            }
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'X') {
                return 10;
            } else if (board[0][2] == 'O') {
                return -10;
            }
        }
        return 0;
    }

    public static boolean isMovesLeft(char[][] board) {
        // Sprawdzamy, czy są jeszcze dostępne ruchy na planszy
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'X', ' ', 'X'},
                {'O', 'O', 'X'},
                {'O', '0', ' '}
        };

        Result result = minimax(board, true);

        System.out.println("Najlepszy ruch: " + result.move.row + "," + result.move.col+", wynik: "+result.score);
    }
}
