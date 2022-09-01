public class TT_data {


    public String bestMove;
    public int bestScore;
    public int depth;
    public int nodeType;

    /*########################*/
    /*----- Constructors -----*/
    /*########################*/
    public TT_data(int nodeType, int bestScore, int depth) {
        this.nodeType = nodeType;
        this.bestScore = bestScore;
        this.depth = depth;
        this.bestMove = "";
    }

    public void updateData(int nodeType, int bestScore, int depth) {
        this.nodeType = nodeType;
        this.bestScore = bestScore;
        this.depth = depth;
    }

}
