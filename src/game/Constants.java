package game;

public interface Constants {

//	private static int[] firstRow =  {4,3,2,5,6,2,3,4};
//	private Piece[] firstRow = {Piece.Rook, Piece.Knight, Piece.Bishop, Piece.Queen, Piece.King, Piece.Bishop, Piece.Knight, Piece.Rook};
//	private Piece[] secondRow = {Piece.Pawn, Piece.Pawn, Piece.Pawn, Piece.Pawn, Piece.Pawn, Piece.Pawn, Piece.Pawn, Piece.Pawn};
//	private Piece[] emptyRow = {null, null, null, null, null, null, null, null};
	
	static final Piece[][] INIT_SET = {
		{Piece.Rook_B, Piece.Knight_B, Piece.Bishop_B, Piece.Queen_B, Piece.King_B, Piece.Bishop_B, Piece.Knight_B, Piece.Rook_B},
		{Piece.Pawn_B, Piece.Pawn_B, Piece.Pawn_B, Piece.Pawn_B, Piece.Pawn_B, Piece.Pawn_B, Piece.Pawn_B, Piece.Pawn_B},
		{null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null},
		{Piece.Pawn_W, Piece.Pawn_W, Piece.Pawn_W, Piece.Pawn_W, Piece.Pawn_W, Piece.Pawn_W, Piece.Pawn_W, Piece.Pawn_W},
		{Piece.Rook_W, Piece.Knight_W, Piece.Bishop_W, Piece.Queen_W, Piece.King_W, Piece.Bishop_W, Piece.Knight_W, Piece.Rook_W},
		};
}