package game;

public enum Piece {
	
	Pawn_W("White"), En_Passant_W("White"), Knight_W("White"), Bishop_W("White"), Rook_W("White"), Queen_W("White"), King_W("White"),
	Pawn_B("Black"), En_Passant_B("Black"), Knight_B("Black"), Bishop_B("Black"), Rook_B("Black"), Queen_B("Black"), King_B("Black");
	
	public final String alligence;
	
	Piece(String alligence)
	{
		this.alligence = alligence;
	}
}
